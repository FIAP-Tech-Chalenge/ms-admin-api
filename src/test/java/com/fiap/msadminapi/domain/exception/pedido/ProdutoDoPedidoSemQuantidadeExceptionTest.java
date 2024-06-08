package com.fiap.msadminapi.domain.exception.pedido;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProdutoDoPedidoSemQuantidadeExceptionTest {

    ProdutoDoPedidoSemQuantidadeException exception;

    @Test
    void devePassarAMensagemCorreta() {
        var mensagem = "Produto do pedido sem quantidade válida";
        exception = new ProdutoDoPedidoSemQuantidadeException(mensagem);
        assertThat(exception).hasMessage(mensagem);
    }

}
