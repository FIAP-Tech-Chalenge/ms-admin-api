package com.fiap.msadminapi.domain.output.pedido;

import com.fiap.msadminapi.domain.entity.pedido.Pedido;
import com.fiap.msadminapi.domain.generic.output.OutputInterface;
import com.fiap.msadminapi.domain.generic.output.OutputStatus;
import lombok.Getter;

@Getter
public class CriaPedidoOutput implements OutputInterface {
    private final Pedido pedido;
    private final OutputStatus outputStatus;

    public CriaPedidoOutput(Pedido pedidoEntity, OutputStatus outputStatus) {
        this.pedido = pedidoEntity;
        this.outputStatus = outputStatus;
    }

    @Override
    public Object getBody() {
        return this.pedido;
    }
}
