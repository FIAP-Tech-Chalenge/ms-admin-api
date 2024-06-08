package com.fiap.msadminapi.domain.useCase.cliente;

import com.fiap.msadminapi.domain.entity.cliente.Cliente;
import com.fiap.msadminapi.domain.gateway.cliente.ClienteInterface;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class ListaDeClientesUseCaseTest {

    @Mock
    private ClienteInterface clienteInterface;

    private ListaDeClientesUseCase useCase;

    AutoCloseable openMocks;

    @BeforeEach
    void setup(){
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new ListaDeClientesUseCase(clienteInterface);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveInstanciarORepositorioCorreto() {
        assertThat(useCase.getClienteInterface()).isInstanceOf(ClienteInterface.class);
    }

    @Test
    void deveListarClientes() {
        var cliente = new Cliente();
        cliente.setNome("Joao");
        var listaCliente = List.of(cliente);

        when(clienteInterface.buscarClientes()).thenReturn(listaCliente);

        try {
            useCase.execute();

            var output = useCase.getOutputStatus();
            AssertionsForClassTypes.assertThat(output.getOutputStatus().getCode())
                    .isEqualTo(200);
            AssertionsForClassTypes.assertThat(output.getOutputStatus().getCodeName())
                    .isEqualTo("OK");
            AssertionsForClassTypes.assertThat(output.getOutputStatus().getMessage())
                    .isEqualTo("Lista de pedidos");
        } catch (Exception e) {}
    }

    @Test
    void deveGerarExcecao_QuandoBuscarClientePorUuid_ErroDeServidor() {
        when(clienteInterface.buscarClientes())
                .thenThrow(HttpServerErrorException.InternalServerError.class);

        try {
            useCase.execute();
        } catch (Exception e) {
            var output = useCase.getOutputStatus();
            AssertionsForClassTypes.assertThat(output.getOutputStatus().getCode())
                    .isEqualTo(500);
            AssertionsForClassTypes.assertThat(output.getOutputStatus().getCodeName())
                    .isEqualTo("Internal Server Error");
            AssertionsForClassTypes.assertThat(output.getOutputStatus().getMessage())
                    .isEqualTo("Erro no servidor");
        }
    }

}
