package com.fiap.msadminapi.application.clientes;

import com.fiap.msadminapi.domain.entity.cliente.Cliente;
import com.fiap.msadminapi.domain.gateway.cliente.ClienteInterface;
import com.fiap.msadminapi.domain.generic.output.OutputInterface;
import com.fiap.msadminapi.domain.generic.output.OutputStatus;
import com.fiap.msadminapi.domain.useCase.cliente.GetClienteByUuidUseCase;
import com.fiap.msadminapi.domain.useCase.cliente.ListaDeClientesUseCase;
import com.fiap.msadminapi.infra.model.ClienteModel;
import com.fiap.msadminapi.infra.repository.ClienteRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class ListaClientesControllerTest {

    @Mock
    ClienteRepository clienteRepository;

    @Mock
    ListaDeClientesUseCase listaDeClientesUseCase;

    @Mock
    OutputStatus outputStatus;

    ClienteInterface clienteInterface;

    ListaClientesController controller;

    AutoCloseable openMocks;

    @BeforeEach
    public void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        controller = new ListaClientesController(clienteRepository);
        listaDeClientesUseCase = new ListaDeClientesUseCase(clienteInterface);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveRetornarOPresenter() {
        var cliente = new Cliente();
        cliente.setNome("Cliente 1");

        var clienteModel = new ClienteModel();
        clienteModel.setNome("Cliente 1");

        when(clienteRepository.findAll()).thenReturn(List.of(clienteModel));

        try {
            listaDeClientesUseCase.execute();

            var presenter = controller.getAllPedidos();
            assertThat(presenter.getBody()).isEqualTo(List.of(cliente));
        } catch (Exception e) {}
    }

    @Test
    void deveGerarExcecao_QuandoRetornarOPresenter_StatusCodeDiferenteDeDuzentos() {
        try {
            when(clienteRepository.findAll())
                    .thenThrow(HttpServerErrorException.InternalServerError.class);

            var presenter = controller.getAllPedidos();
            assertThat(presenter.getStatusCode().toString()).isEqualTo("500 INTERNAL_SERVER_ERROR");
        } catch (Exception e) {}
    }

}
