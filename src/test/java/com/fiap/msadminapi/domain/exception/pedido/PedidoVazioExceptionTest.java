package com.fiap.msadminapi.domain.exception.pedido;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PedidoVazioExceptionTest {

    PedidoVazioException exception;

    @Test
    void devePassarAMensagemCorreta() {
        var mensagem = "Pedido vazio";
        exception = new PedidoVazioException(mensagem);
        assertThat(exception).hasMessage(mensagem);
    }

}
