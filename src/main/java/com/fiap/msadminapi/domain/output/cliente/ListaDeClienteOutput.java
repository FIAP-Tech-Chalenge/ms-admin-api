package com.fiap.msadminapi.domain.output.cliente;

import com.fiap.msadminapi.domain.entity.cliente.Cliente;
import com.fiap.msadminapi.domain.generic.output.OutputInterface;
import com.fiap.msadminapi.domain.generic.output.OutputStatus;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@RequiredArgsConstructor
@Getter
@Setter
public class ListaDeClienteOutput implements OutputInterface {
    private List<com.fiap.msadminapi.domain.entity.cliente.Cliente> clientes;
    private OutputStatus outputStatus;

    public ListaDeClienteOutput(List<Cliente> clientes, OutputStatus outputStatus) {
        this.clientes = clientes;
        this.outputStatus = outputStatus;
    }

    @Override
    public Object getBody() {
        return this.clientes;
    }
}
