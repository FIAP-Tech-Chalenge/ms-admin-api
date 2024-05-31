package com.fiap.msadminapi.domain.gateway.cliente;

import com.fiap.msadminapi.domain.entity.cliente.Cliente;


public interface IdentificarClienteInterface {

    Cliente buscaClientePorCpf(String cpf);
    Cliente identificarCliente(Cliente cliente);
}
