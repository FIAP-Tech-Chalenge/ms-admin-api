package com.fiap.msadminapi.domain.gateway.pedido;

import com.fiap.msadminapi.domain.entity.pedido.Pedido;
import com.fiap.msadminapi.domain.enums.pedido.StatusPagamento;
import com.fiap.msadminapi.domain.exception.produto.ProdutoNaoEncontradoException;

public interface PedidoInterface {
    Pedido criaPedido(Pedido pedido) throws ProdutoNaoEncontradoException;

    Pedido atualizaPagamento(Pedido pedido, StatusPagamento statusPagamento);

}
