package com.fiap.msadminapi.domain.outputerror;

import com.fiap.msadminapi.domain.generic.output.OutputStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BuscarPedidoOutputErrorTest {

    OutputStatus outputStatus;
    BuscarPedidoOutputError output;

    @Test
    void deveRetornarOBodyCorretamente() {
        outputStatus = new OutputStatus(404, "Not Found", "Produto não encontrado");
        output = new BuscarPedidoOutputError();
        output.setOutputStatus(outputStatus);

        output.getBody();

        assertThat(output.getOutputStatus().getCode()).isEqualTo(404);
    }
}
