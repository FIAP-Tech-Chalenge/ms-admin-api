package com.fiap.msadminapi.infra.adpter.repository.cliente;

import com.fiap.msadminapi.domain.entity.cliente.Cliente;
import com.fiap.msadminapi.domain.gateway.cliente.IdentificarClienteInterface;
import com.fiap.msadminapi.infra.model.ClienteModel;
import com.fiap.msadminapi.infra.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IdentificarClienteRepository implements IdentificarClienteInterface {

    private final ClienteRepository clienteRepository;

    public Cliente buscaClientePorCpf(String cpf) {
        ClienteModel clienteModel = this.clienteRepository.findByCpf(cpf);
        if (clienteModel == null) {
            return null;
        }
        return new Cliente(clienteModel.getNome(), clienteModel.getCpf(), clienteModel.getEmail(), clienteModel.getUuid());
    }

    @Override
    public Cliente identificarCliente(Cliente cliente) {
        ClienteModel clienteModel =this.clienteRepository.save(
            new ClienteModel(cliente.getNome(), cliente.getCpf(), cliente.getEmail(), cliente.getUuid())
        );
        cliente.setUuid(clienteModel.getUuid());
        return cliente;
    }
}
