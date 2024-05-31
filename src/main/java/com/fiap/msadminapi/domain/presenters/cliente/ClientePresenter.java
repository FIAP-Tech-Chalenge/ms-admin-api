package com.fiap.msadminapi.domain.presenters.cliente;

import com.fiap.msadminapi.domain.generic.presenter.PresenterInterface;
import com.fiap.msadminapi.domain.output.cliente.ClienteOutput;

import java.util.HashMap;
import java.util.Map;

public class ClientePresenter implements PresenterInterface {
    private final ClienteOutput identificaClienteOutput;

    public ClientePresenter(ClienteOutput identificaClienteOutput) {
        this.identificaClienteOutput = identificaClienteOutput;
    }

    public Map<String, Object> toArray() {
        Map<String, Object> cliente = new HashMap<>();
        cliente.put("nome", this.identificaClienteOutput.getCliente().getNome());
        cliente.put("cpf", this.identificaClienteOutput.getCliente().getCpf());
        cliente.put("email", this.identificaClienteOutput.getCliente().getEmail());
        cliente.put("uuid", this.identificaClienteOutput.getCliente().getUuid().toString());
        return cliente;
    }

    public ClienteOutput getOutput() {
        return this.identificaClienteOutput;
    }
}
