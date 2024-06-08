package com.fiap.msadminapi.domain.presenters.cliente.lista;

import com.fiap.msadminapi.domain.entity.cliente.Cliente;
import com.fiap.msadminapi.domain.output.cliente.ListaDeClienteOutput;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class ListaClientesPresenterTest {

    @Mock
    ListaDeClienteOutput output;

    ListaClientesPresenter presenter;

    AutoCloseable openMocks;

    @BeforeEach
    public void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        presenter = new ListaClientesPresenter(output);
    }

    @AfterEach
    public void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveRetornarOOutputDeListaDeClientesCorretamente() {
        var nome = "Cliente 1";
        var cpf = "123456789";
        var email = "cliente1@email.com";
        var uuid = UUID.randomUUID();
        var cliente = new Cliente(nome, cpf, email, uuid);
        var listaClientes = List.of(cliente);

        when(output.getClientes()).thenReturn(listaClientes);

        var clientesRetornados = presenter.getOutput().getClientes();
        assertThat(clientesRetornados).satisfies(c -> {
            var cliente1 = c.getFirst();
            assertThat(cliente1.getNome()).isEqualTo(nome);
            assertThat(cliente1.getCpf()).isEqualTo(cpf);
            assertThat(cliente1.getEmail()).isEqualTo(email);
            assertThat(cliente1.getUuid()).isEqualTo(uuid);
        });
    }

    @Test
    void deveRetornarUmArrayDeListaDeClientes() {
        var nome = "Cliente 1";
        var cpf = "123456789";
        var email = "cliente1@email.com";
        var uuid = UUID.randomUUID();
        var cliente = new Cliente(nome, cpf, email, uuid);
        var listaClientes = List.of(cliente);

        var clienteArray = new HashMap<>();
        clienteArray.put("nome", nome);
        clienteArray.put("cpf", cpf);
        clienteArray.put("email", email);
        clienteArray.put("uuid", uuid.toString());
        var listaClientesArray = new HashMap<>();
        listaClientesArray.put("clientes", List.of(clienteArray));

        when(output.getClientes()).thenReturn(listaClientes);

        var clientesRetornados = presenter.toArray();
        assertThat(clientesRetornados).isEqualTo(listaClientesArray);
    }
}
