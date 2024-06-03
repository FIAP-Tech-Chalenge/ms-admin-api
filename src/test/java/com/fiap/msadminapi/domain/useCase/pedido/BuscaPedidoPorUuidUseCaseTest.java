package com.fiap.msadminapi.domain.useCase.pedido;

import com.fiap.msadminapi.domain.entity.pedido.Pedido;
import com.fiap.msadminapi.domain.exception.pedido.PedidoNaoEncontradoException;
import com.fiap.msadminapi.domain.gateway.pedido.BuscaPedidoInterface;
import com.fiap.msadminapi.domain.generic.output.OutputStatus;
import com.fiap.msadminapi.domain.outputerror.BuscarPedidoOutputError;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.HttpServerErrorException;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class BuscaPedidoPorUuidUseCaseTest {

    @Mock
    private BuscaPedidoInterface buscaPedidoInterface;

    private BuscaPedidoPorUuidUseCase useCase;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new BuscaPedidoPorUuidUseCase(buscaPedidoInterface);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveInstanciarOsRepositoriosCorretos() {
        assertThat(useCase.getBuscaPedido()).isInstanceOf(BuscaPedidoInterface.class);
    }

    @Test
    void devePermitirBuscarPedidoPorUuid() {

        var pedidoUuid = UUID.randomUUID();
        var clienteUuid = UUID.randomUUID();
        var pedido = new Pedido(clienteUuid);

        try {
            when(buscaPedidoInterface.encontraPedidoPorUuid(pedidoUuid, clienteUuid))
                    .thenReturn(pedido);

            useCase.execute(pedidoUuid, clienteUuid);

            var output = useCase.getBuscaPedidoOutput();
            assertThat(output.getBody()).isEqualTo(pedido);
            assertThat(output.getOutputStatus().getCode()).isEqualTo(200);
            assertThat(output.getOutputStatus().getCodeName()).isEqualTo("OK");
            assertThat(output.getOutputStatus().getMessage()).isEqualTo("Pedido encontrado");
        } catch (PedidoNaoEncontradoException e) {
            assertThat(e.getMessage()).isEqualTo("Pedido não encontrado");
        }
    }

    @Test
    void deveGerarExcecao_QuandoBuscarPedidoPorUuid_PedidoNaoEncontradoPoisValorRetornadoIgualNull() {

        var pedidoUuid = UUID.randomUUID();
        var clienteUuid = UUID.randomUUID();

        try {
            when(buscaPedidoInterface.encontraPedidoPorUuid(pedidoUuid, clienteUuid))
                    .thenReturn(null);

            useCase.execute(pedidoUuid, clienteUuid);

            var output = useCase.getBuscaPedidoOutput();
            assertThat(output.getOutputStatus().getCode()).isEqualTo(404);
            assertThat(output.getOutputStatus().getCodeName()).isEqualTo("Not found");
            assertThat(output.getOutputStatus().getMessage()).isEqualTo("Pedido não encontrado");
            assertThat(output.getOutputStatus()).isInstanceOf(OutputStatus.class);
        } catch (PedidoNaoEncontradoException e) {
            assertThat(e.getMessage()).isEqualTo("Pedido não encontrado");
        }
    }

    @Test
    void deveGerarExcecao_QuandoBuscarPedidoPorUuid_PedidoNaoEncontrado() {

        var pedidoUuid = UUID.randomUUID();
        var clienteUuid = UUID.randomUUID();

        try {
            when(buscaPedidoInterface.encontraPedidoPorUuid(pedidoUuid, clienteUuid))
                    .thenThrow(PedidoNaoEncontradoException.class);

            useCase.execute(pedidoUuid, clienteUuid);

            var output = useCase.getBuscaPedidoOutput();
            assertThat(output.getOutputStatus().getCode()).isEqualTo(404);
            assertThat(output.getOutputStatus().getCodeName()).isEqualTo("Not Found");
            assertThat(output.getOutputStatus().getMessage()).isEqualTo("Pedido não encontrado");
            assertThat(output.getOutputStatus()).isInstanceOf(OutputStatus.class);
        } catch (PedidoNaoEncontradoException e) {
            assertThat(e.getMessage()).isEqualTo("Pedido não encontrado");
        }
    }

    @Test
    void deveGerarExcecao_QuandoBuscarPedidoPorUuid_ErroDeServidor() {
        var pedidoUuid = UUID.randomUUID();
        var clienteUuid = UUID.randomUUID();

        try {
            when(buscaPedidoInterface.encontraPedidoPorUuid(pedidoUuid, clienteUuid))
                    .thenThrow(HttpServerErrorException.InternalServerError.class);

            useCase.execute(pedidoUuid, clienteUuid);

            var output = useCase.getBuscaPedidoOutput();
            assertThat(output.getOutputStatus().getCode())
                    .isEqualTo(500);
            assertThat(output.getOutputStatus().getCodeName())
                    .isEqualTo("Internal Server Error");
            assertThat(output.getOutputStatus().getMessage())
                    .isEqualTo("Erro no servidor");
        } catch (PedidoNaoEncontradoException e) {
            assertThat(e.getMessage()).isEqualTo("Pedido não encontrado");
        }
    }
}