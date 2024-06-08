package com.fiap.msadminapi.domain.output.cliente;

import com.fiap.msadminapi.domain.entity.cliente.Cliente;
import com.fiap.msadminapi.domain.generic.output.OutputStatus;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ListaDeClienteOutputTest {

    ListaDeClienteOutput output;
    OutputStatus outputStatus;

    @Test
    void deveRetornarOClienteNoBody() {
        var cliente = new Cliente();
        cliente.setNome("Cliente 1");
        var listaClientes = List.of(cliente);

        outputStatus = new OutputStatus(200, "OK", "Cliente encontrado");
        output = new ListaDeClienteOutput(listaClientes, outputStatus);

        assertThat(output.getBody()).isEqualTo(listaClientes);
        assertThat(output.getClientes()).isEqualTo(listaClientes);
    }

    @Test
    void deveRetornarOClienteAdicionadoAposCriacaoSemArgumentos() {
        var cliente = new Cliente();
        cliente.setNome("Cliente 1");
        var listaClientes = List.of(cliente);

        output = new ListaDeClienteOutput();
        outputStatus = new OutputStatus(200, "OK", "Cliente encontrado");
        output.setClientes(listaClientes);
        output.setOutputStatus(outputStatus);

        assertThat(output.getBody()).isEqualTo(listaClientes);
        assertThat(output.getClientes()).isEqualTo(listaClientes);
    }
}
