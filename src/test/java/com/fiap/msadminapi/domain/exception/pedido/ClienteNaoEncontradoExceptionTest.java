package com.fiap.msadminapi.domain.exception.pedido;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ClienteNaoEncontradoExceptionTest {

    ClienteNaoEncontradoException exception;

    @Test
    void devePassarAMensagemCorreta() {
        var mensagem = "Cliente não encontrado";
        exception = new ClienteNaoEncontradoException(mensagem);
        assertThat(exception).hasMessage(mensagem);
    }

}
