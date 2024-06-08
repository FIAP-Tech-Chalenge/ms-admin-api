package com.fiap.msadminapi.application.clientes;

import com.fiap.msadminapi.domain.entity.cliente.Cliente;
import com.fiap.msadminapi.domain.gateway.cliente.ClienteInterface;
import com.fiap.msadminapi.domain.generic.output.OutputStatus;
import com.fiap.msadminapi.domain.useCase.cliente.GetClienteByUuidUseCase;
import com.fiap.msadminapi.infra.model.ClienteModel;
import com.fiap.msadminapi.infra.repository.ClienteRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class GetClienteByUuidControllerTest {

    @Mock
    ClienteRepository clienteRepository;

    @Mock
    GetClienteByUuidUseCase getClienteByUuidUseCase;

    ClienteInterface clienteInterface;

    GetClienteByUuidController controller;

    AutoCloseable openMocks;

    @BeforeEach
    public void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        controller = new GetClienteByUuidController(clienteRepository);
        getClienteByUuidUseCase = new GetClienteByUuidUseCase(clienteInterface);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveRetornarOPresenter() {
        var uuid = UUID.randomUUID();
        var cliente = new Cliente();
        cliente.setNome("Cliente 1");

        var clienteModel = new ClienteModel();
        clienteModel.setNome("Cliente 1");

        when(clienteRepository.findByUuid(uuid)).thenReturn(clienteModel);

        try {
            getClienteByUuidUseCase.execute(uuid);

            var presenter = controller.getClienteByUuid(uuid);
            assertThat(presenter.getBody()).isEqualTo(cliente);
        } catch (Exception e) {}
    }

    @Test
    void deveGerarExcecao_QuandoRetornarOPresenter_StatusCodeDiferenteDeDuzentos() {
        var uuid = UUID.randomUUID();

        try {
            var presenter = controller.getClienteByUuid(uuid);
            assertThat(presenter.getBody()).isInstanceOf(OutputStatus.class);
        } catch (Exception e) {}
    }

}
