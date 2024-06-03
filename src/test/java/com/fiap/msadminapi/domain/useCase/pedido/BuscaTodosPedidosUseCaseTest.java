package com.fiap.msadminapi.domain.useCase.pedido;

import com.fiap.msadminapi.domain.entity.pedido.Pedido;
import com.fiap.msadminapi.domain.gateway.pedido.BuscaPedidoInterface;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class BuscaTodosPedidosUseCaseTest {

    @Mock
    private BuscaPedidoInterface buscaPedidoInterface;

    private BuscaTodosPedidosUseCase useCase;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new BuscaTodosPedidosUseCase(buscaPedidoInterface);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveInstanciarOsRepositoriosCorretos() {
        assertThat(useCase.getBuscaPedidoInterface()).isInstanceOf(BuscaPedidoInterface.class);
    }

    @Test
    void devePermitirBuscarTodosPedidos() {

        var clienteUuid = UUID.randomUUID();
        var pedido = new Pedido(clienteUuid);
        var listaPedidos = List.of(pedido);

        when(buscaPedidoInterface.findAll())
                .thenReturn(listaPedidos);

        useCase.execute();

        var output = useCase.getBuscaProdutoOutput();
        assertThat(output.getBody()).isEqualTo(listaPedidos);
        assertThat(output.getOutputStatus().getCode()).isEqualTo(200);
        assertThat(output.getOutputStatus().getCodeName()).isEqualTo("OK");
        assertThat(output.getOutputStatus().getMessage()).isEqualTo("Lista de pedidos");
    }

    @Test
    void deveGerarExcecao_QuandoBuscarPedidoPorUuid_ErroDeServidor() {
        when(buscaPedidoInterface.findAll())
                .thenThrow(HttpServerErrorException.InternalServerError.class);

        useCase.execute();

        var output = useCase.getBuscaProdutoOutput();
        assertThat(output.getOutputStatus().getCode())
                .isEqualTo(500);
        assertThat(output.getOutputStatus().getCodeName())
                .isEqualTo("Internal Server Error");
        assertThat(output.getOutputStatus().getMessage())
                .isEqualTo("Erro no servidor");
    }
}
