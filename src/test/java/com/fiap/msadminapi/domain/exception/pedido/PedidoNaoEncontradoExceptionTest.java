package com.fiap.msadminapi.domain.exception.pedido;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PedidoNaoEncontradoExceptionTest {

    PedidoNaoEncontradoException exception;

    @Test
    void devePassarAMensagemCorreta() {
        var mensagem = "Pedido não encontrado";
        exception = new PedidoNaoEncontradoException(mensagem);
        assertThat(exception).hasMessage(mensagem);
    }

}
