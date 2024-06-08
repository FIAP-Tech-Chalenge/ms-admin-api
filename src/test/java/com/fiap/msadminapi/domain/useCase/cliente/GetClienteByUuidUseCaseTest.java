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

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class GetClienteByUuidUseCaseTest {

    @Mock
    private ClienteInterface clienteInterface;

    private GetClienteByUuidUseCase useCase;

    AutoCloseable openMocks;

    @BeforeEach
    void setup(){
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new GetClienteByUuidUseCase(clienteInterface);
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
    void devePermitirBuscarClientePorUuid() {
        var uuid = UUID.randomUUID();
        var cliente = new Cliente();
        cliente.setUuid(uuid);

        when(clienteInterface.getClienteByUuid(any(UUID.class))).thenReturn(cliente);

        try {
            useCase.execute(uuid);
            var output = useCase.getOutputInterface();
            AssertionsForClassTypes.assertThat(output.getOutputStatus().getCode())
                    .isEqualTo(200);
            AssertionsForClassTypes.assertThat(output.getOutputStatus().getCodeName())
                    .isEqualTo("Ok");
            AssertionsForClassTypes.assertThat(output.getOutputStatus().getMessage())
                    .isEqualTo("Cliente encontrado");
        } catch (Exception e) {}
    }

    @Test
    void deveGerarExcecao_QuandoBuscarClientePorUuid_ClienteIgualANulo() {
        when(clienteInterface.getClienteByUuid(any(UUID.class))).thenReturn(null);

        try {
            useCase.execute(UUID.randomUUID());
        } catch (Exception e) {
            assertThat(e.getMessage()).isEqualTo("Cliente não encontrado");
        }
    }

    @Test
    void deveGerarExcecao_QuandoBuscarClientePorUuid_ErroDeServidor() {
        var uuid = UUID.fromString("123e4567-e89b-12d3-a456-426614174009");
        when(clienteInterface.getClienteByUuid(uuid))
                .thenThrow(HttpServerErrorException.InternalServerError.class);

        try {
            useCase.execute(uuid);
        } catch (Exception e) {
            var output = useCase.getOutputInterface();
            AssertionsForClassTypes.assertThat(output.getOutputStatus().getCode())
                    .isEqualTo(500);
            AssertionsForClassTypes.assertThat(output.getOutputStatus().getCodeName())
                    .isEqualTo("Internal Server Error");
            AssertionsForClassTypes.assertThat(output.getOutputStatus().getMessage())
                    .isEqualTo("Erro no servidor");
        }
    }

}
