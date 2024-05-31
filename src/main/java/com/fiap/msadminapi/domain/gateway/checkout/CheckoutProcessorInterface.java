package com.fiap.msadminapi.domain.gateway.checkout;
import com.fiap.msadminapi.domain.entity.pedido.Checkout;
import com.fiap.msadminapi.domain.entity.pedido.Pedido;

public interface CheckoutProcessorInterface {
    public Checkout processarCheckout(Pedido pedido);
}
