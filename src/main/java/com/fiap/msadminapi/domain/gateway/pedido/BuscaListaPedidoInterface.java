package com.fiap.msadminapi.domain.gateway.pedido;

import com.fiap.msadminapi.domain.entity.pedido.Pedido;

import java.util.List;

public interface BuscaListaPedidoInterface {
    List<Pedido> findListaPedidos();
}
