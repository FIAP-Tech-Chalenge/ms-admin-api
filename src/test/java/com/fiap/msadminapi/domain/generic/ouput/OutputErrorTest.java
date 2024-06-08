package com.fiap.msadminapi.domain.generic.ouput;

import com.fiap.msadminapi.domain.entity.produto.Produto;
import com.fiap.msadminapi.domain.enums.produto.CategoriaEnum;
import com.fiap.msadminapi.domain.generic.output.OutputError;
import com.fiap.msadminapi.domain.generic.output.OutputStatus;
import com.fiap.msadminapi.domain.output.produto.DeletaProdutoOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OutputErrorTest {

    OutputError output;
    OutputStatus outputStatus;

    @BeforeEach
    public void setup() {
        outputStatus = new OutputStatus(200, "OK", "Mensagem base");
    }

    @Test
    void deveRetornarOBodyCorreto() {
        output = new OutputError("Nova mensagem", outputStatus);

        assertThat(output.getMensagem()).isEqualTo("Nova mensagem");
        assertThat(output.getBody()).isEqualTo(outputStatus);
    }

}
