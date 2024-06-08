package com.fiap.msadminapi.domain.presenters.cliente;

import com.fiap.msadminapi.domain.entity.cliente.Cliente;
import com.fiap.msadminapi.domain.output.cliente.ClienteOutput;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class ClientePresenterTest {

    @Mock
    ClienteOutput output;

    ClientePresenter presenter;

    AutoCloseable openMocks;

    @BeforeEach
    public void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        presenter = new ClientePresenter(output);
    }

    @AfterEach
    public void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveRetornarOOutputDeClienteCorretamente() {
        var nome = "Cliente 1";
        var cpf = "123456789";
        var email = "cliente1@email.com";
        var uuid = UUID.randomUUID();
        var cliente = new Cliente(nome, cpf, email, uuid);

        when(output.getCliente()).thenReturn(cliente);

        var clienteRetornado = presenter.getOutput().getCliente();
        assertThat(clienteRetornado.getNome()).isEqualTo(nome);
        assertThat(clienteRetornado.getCpf()).isEqualTo(cpf);
        assertThat(clienteRetornado.getEmail()).isEqualTo(email);
        assertThat(clienteRetornado.getUuid()).isEqualTo(uuid);
    }

    @Test
    void deveRetornarUmArrayDeCliente() {
        var nome = "Cliente 1";
        var cpf = "123456789";
        var email = "cliente1@email.com";
        var uuid = UUID.randomUUID();
        var cliente = new Cliente(nome, cpf, email, uuid);
        var clienteArray = new HashMap<>();
        clienteArray.put("nome", nome);
        clienteArray.put("cpf", cpf);
        clienteArray.put("email", email);
        clienteArray.put("uuid", uuid.toString());

        when(output.getCliente()).thenReturn(cliente);

        var clienteRetornado = presenter.toArray();
        assertThat(clienteRetornado).isEqualTo(clienteArray);
    }

}
