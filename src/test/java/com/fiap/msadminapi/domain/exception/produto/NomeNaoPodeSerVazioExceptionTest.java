package com.fiap.msadminapi.domain.exception.produto;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NomeNaoPodeSerVazioExceptionTest {

    NomeNaoPodeSerVazioException exception;

    @Test
    void devePassarAMensagemCorreta() {
        var mensagem = "Produto não pode ter o nome vazio";
        exception = new NomeNaoPodeSerVazioException(mensagem);
        assertThat(exception).hasMessage(mensagem);
    }

}
