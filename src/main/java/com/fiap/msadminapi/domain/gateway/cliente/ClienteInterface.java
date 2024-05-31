package com.fiap.msadminapi.domain.gateway.cliente;

import com.fiap.msadminapi.domain.entity.cliente.Cliente;

import java.util.List;
import java.util.UUID;

public interface ClienteInterface {

    List<Cliente> buscarClientes();

    Cliente getClienteByUuid(UUID uuid);
}
