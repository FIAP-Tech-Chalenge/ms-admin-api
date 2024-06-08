package com.fiap.msadminapi.domain.output.cliente;

import com.fiap.msadminapi.domain.entity.cliente.Cliente;
import com.fiap.msadminapi.domain.generic.output.OutputStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ClienteOutputTest {

    ClienteOutput output;
    OutputStatus outputStatus;

    @Test
    void deveRetornarOClienteNoBody() {
        var cliente = new Cliente();
        cliente.setNome("Cliente 1");

        outputStatus = new OutputStatus(200, "OK", "Cliente encontrado");
        output = new ClienteOutput(cliente, outputStatus);

        assertThat(output.getBody()).isEqualTo(cliente);
        assertThat(output.getCliente()).isEqualTo(cliente);
    }

    @Test
    void deveRetornarOClienteAdicionadoAposCriacaoSemArgumentos() {
        var cliente = new Cliente();
        cliente.setNome("Cliente 1");

        output = new ClienteOutput();
        outputStatus = new OutputStatus(200, "OK", "Cliente encontrado");
        output.setCliente(cliente);
        output.setOutputStatus(outputStatus);

        assertThat(output.getBody()).isEqualTo(cliente);
        assertThat(output.getCliente()).isEqualTo(cliente);
    }

}
