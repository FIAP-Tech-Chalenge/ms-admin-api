package com.fiap.msadminapi.domain.gateway.cliente;

import com.fiap.msadminapi.domain.entity.pedido.Cliente;
import com.fiap.msadminapi.domain.exception.pedido.ClienteNaoEncontradoException;

import java.util.List;
import java.util.UUID;


public interface ClienteInterface {

    Cliente buscaClientePorUuid(UUID uuid) throws ClienteNaoEncontradoException;

    List<com.fiap.msadminapi.domain.entity.cliente.Cliente> buscarClientes();

    com.fiap.msadminapi.domain.entity.cliente.Cliente getClienteByUuid(UUID uuid);
}
