package com.fiap.msadminapi.domain.output.pedido;

import com.fiap.msadminapi.domain.entity.pedido.Pedido;
import com.fiap.msadminapi.domain.generic.output.OutputStatus;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class BuscaPedidoOutputTest {

    BuscaPedidoOutput output;
    OutputStatus outputStatus;

    @Test
    void deveRetornarOPedidoNoBody() {
        var pedido = new Pedido(UUID.randomUUID());

        outputStatus = new OutputStatus(200, "OK", "Cliente encontrado");
        output = new BuscaPedidoOutput(pedido, outputStatus);

        assertThat(output.getBody()).isEqualTo(pedido);
        assertThat(output.getPedido()).isEqualTo(pedido);
    }

    @Test
    void deveRetornarOPedidoAdicionadoAposCriacaoSemArgumentos() {
        var pedido = new Pedido(UUID.randomUUID());

        output = new BuscaPedidoOutput();
        outputStatus = new OutputStatus(200, "OK", "Cliente encontrado");
        output.setPedido(pedido);
        output.setOutputStatus(outputStatus);

        assertThat(output.getBody()).isEqualTo(pedido);
        assertThat(output.getPedido()).isEqualTo(pedido);
    }
}
