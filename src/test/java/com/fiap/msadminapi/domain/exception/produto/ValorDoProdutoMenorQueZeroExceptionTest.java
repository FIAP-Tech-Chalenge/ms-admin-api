package com.fiap.msadminapi.domain.exception.produto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ValorDoProdutoMenorQueZeroExceptionTest {

    ValorDoProdutoMenorQueZeroException exception;

    @Test
    void devePassarAMensagemCorreta() {
        var mensagem = "Produto não pode ter o valor menor que zero";
        exception = new ValorDoProdutoMenorQueZeroException(mensagem);
        assertThat(exception).hasMessage(mensagem);
    }

}
