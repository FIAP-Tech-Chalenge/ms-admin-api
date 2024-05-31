package com.fiap.msadminapi.domain.output.cliente;

import com.fiap.msadminapi.domain.entity.cliente.Cliente;
import com.fiap.msadminapi.domain.generic.output.OutputInterface;
import com.fiap.msadminapi.domain.generic.output.OutputStatus;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@RequiredArgsConstructor
@Getter
@Setter
public class ClienteOutput implements OutputInterface {
    private Cliente cliente;
    private OutputStatus outputStatus;

    public ClienteOutput(Cliente clienteEntity, OutputStatus outputStatus) {
        this.cliente = clienteEntity;
        this.outputStatus = outputStatus;
    }

    @Override
    public Object getBody() {
        return this.cliente;
    }
}
