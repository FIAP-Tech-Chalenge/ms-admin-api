package com.fiap.msadminapi.domain.output.pedido;

import com.fiap.msadminapi.domain.entity.pedido.Pedido;
import com.fiap.msadminapi.domain.generic.output.OutputStatus;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class BuscaTodosPedidoOutputTest {

    BuscaTodosPedidoOutput output;
    OutputStatus outputStatus;

    @Test
    void deveRetornarOPedidoNoBody() {
        var pedido = new Pedido(UUID.randomUUID());
        var listPedidos = List.of(pedido);

        outputStatus = new OutputStatus(200, "OK", "Cliente encontrado");
        output = new BuscaTodosPedidoOutput(listPedidos, outputStatus);

        assertThat(output.getBody()).isEqualTo(listPedidos);
        assertThat(output.getListPedidos()).isEqualTo(listPedidos);
    }

    @Test
    void deveRetornarOPedidoAdicionadoAposCriacaoSemArgumentos() {
        var pedido = new Pedido(UUID.randomUUID());
        var listPedidos = List.of(pedido);

        output = new BuscaTodosPedidoOutput();
        outputStatus = new OutputStatus(200, "OK", "Cliente encontrado");
        output.setListPedidos(listPedidos);
        output.setOutputStatus(outputStatus);

        assertThat(output.getBody()).isEqualTo(listPedidos);
        assertThat(output.getListPedidos()).isEqualTo(listPedidos);
    }

}
