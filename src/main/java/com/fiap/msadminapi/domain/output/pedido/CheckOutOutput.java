package com.fiap.msadminapi.domain.output.pedido;

import com.fiap.msadminapi.domain.entity.pedido.Checkout;
import com.fiap.msadminapi.domain.generic.output.OutputInterface;
import com.fiap.msadminapi.domain.generic.output.OutputStatus;
import lombok.Getter;

@Getter
public class CheckOutOutput implements OutputInterface {
    private final Checkout checkout;
    private final OutputStatus outputStatus;

    public CheckOutOutput(Checkout checkout, OutputStatus outputStatus) {
        this.checkout = checkout;
        this.outputStatus = outputStatus;
    }

    @Override
    public Object getBody() {
        return this.checkout;
    }
}
