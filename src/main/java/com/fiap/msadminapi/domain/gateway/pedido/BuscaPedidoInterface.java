package com.fiap.msadminapi.domain.gateway.pedido;

import com.fiap.msadminapi.domain.entity.pedido.Pedido;
import com.fiap.msadminapi.domain.exception.pedido.PedidoNaoEncontradoException;

import java.util.List;
import java.util.UUID;


public interface BuscaPedidoInterface {
    Pedido encontraPedidoPorUuid(UUID pedidoUuid, UUID clienteUuid) throws PedidoNaoEncontradoException;

    List<Pedido> findAll();
}
