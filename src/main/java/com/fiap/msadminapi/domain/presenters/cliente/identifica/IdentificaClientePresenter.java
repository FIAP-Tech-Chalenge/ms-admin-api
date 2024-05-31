package com.fiap.msadminapi.domain.presenters.cliente.identifica;

import com.fiap.msadminapi.domain.generic.presenter.PresenterInterface;

import java.util.HashMap;
import java.util.Map;

public class IdentificaClientePresenter implements PresenterInterface {
    private final com.fiap.msadminapi.domain.output.cliente.ClienteOutput identificaClienteOutput;

    public IdentificaClientePresenter(com.fiap.msadminapi.domain.output.cliente.ClienteOutput identificaClienteOutput) {
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

    public com.fiap.msadminapi.domain.output.cliente.ClienteOutput getOutput() {
        return this.identificaClienteOutput;
    }
}
