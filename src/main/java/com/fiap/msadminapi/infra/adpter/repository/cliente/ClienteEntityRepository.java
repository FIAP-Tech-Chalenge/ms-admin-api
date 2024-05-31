package com.fiap.msadminapi.infra.adpter.repository.cliente;

import com.fiap.msadminapi.domain.entity.pedido.Cliente;
import com.fiap.msadminapi.domain.exception.pedido.ClienteNaoEncontradoException;
import com.fiap.msadminapi.domain.gateway.cliente.ClienteInterface;
import com.fiap.msadminapi.infra.model.ClienteModel;
import com.fiap.msadminapi.infra.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class ClienteEntityRepository implements ClienteInterface {
    private final ClienteRepository clienteRepository;

    @Override
    public Cliente buscaClientePorUuid(UUID uuid) throws ClienteNaoEncontradoException {
        ClienteModel clienteModel = clienteRepository.findByUuid(uuid);
        if (clienteModel == null) {
            throw new ClienteNaoEncontradoException("Cliente não encontrado");
        }
        return new Cliente(
                clienteModel.getNome(),
                clienteModel.getCpf(),
                clienteModel.getEmail(),
                clienteModel.getUuid()
        );
    }

    @Override
    public List<com.fiap.msadminapi.domain.entity.cliente.Cliente> buscarClientes() {
        List<ClienteModel> clientesModels = clienteRepository.findAll();
        List<com.fiap.msadminapi.domain.entity.cliente.Cliente> clienteList = new ArrayList<>();
        for (ClienteModel clienteModel : clientesModels) {
            com.fiap.msadminapi.domain.entity.cliente.Cliente pedidoEntity = new com.fiap.msadminapi.domain.entity.cliente.Cliente(
                    clienteModel.getNome(),
                    clienteModel.getCpf(),
                    clienteModel.getEmail(),
                    clienteModel.getUuid()
            );
            clienteList.add(pedidoEntity);
        }
        return clienteList;
    }

    @Override
    public com.fiap.msadminapi.domain.entity.cliente.Cliente getClienteByUuid(UUID uuid) {
        ClienteModel clienteModel = clienteRepository.findByUuid(uuid);
        if (clienteModel == null) {
            return null;
        }
        return new com.fiap.msadminapi.domain.entity.cliente.Cliente(
                clienteModel.getNome(),
                clienteModel.getCpf(),
                clienteModel.getEmail(),
                clienteModel.getUuid()
        );
    }
}
