package com.fiap.msadminapi.infra.adpter.repository.cliente;

import com.fiap.msadminapi.domain.entity.cliente.Cliente;
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
    public List<Cliente> buscarClientes() {
        List<ClienteModel> clientesModels = clienteRepository.findAll();
        List<Cliente> clienteList = new ArrayList<>();
        for (ClienteModel clienteModel : clientesModels) {
            Cliente pedidoEntity = new Cliente(
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
    public Cliente getClienteByUuid(UUID uuid) {
        ClienteModel clienteModel = clienteRepository.findByUuid(uuid);
        if (clienteModel == null) {
            return null;
        }
        return new Cliente(
                clienteModel.getNome(),
                clienteModel.getCpf(),
                clienteModel.getEmail(),
                clienteModel.getUuid()
        );
    }
}
