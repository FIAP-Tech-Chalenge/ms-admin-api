package com.fiap.msadminapi.infra.adpter.repository.checkout;

import com.fiap.msadminapi.domain.entity.pedido.Checkout;
import com.fiap.msadminapi.domain.entity.pedido.Pedido;
import com.fiap.msadminapi.domain.enums.pedido.StatusPagamento;
import com.fiap.msadminapi.domain.enums.pedido.StatusPedido;
import com.fiap.msadminapi.domain.gateway.checkout.CheckoutProcessorInterface;
import com.fiap.msadminapi.infra.model.PedidoModel;
import com.fiap.msadminapi.infra.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CheckoutRepository implements CheckoutProcessorInterface {

    private final PedidoRepository pedidoRepository;

    @Override
    public Checkout processarCheckout(Pedido pedido) {
        PedidoModel pedidoModel = pedidoRepository.findByUuid(pedido.getUuid());
        pedidoModel.setStatusPedido(StatusPedido.EM_PREPARACAO);
        pedidoModel.setStatusPagamento(StatusPagamento.AGUARDANDO_PAGAMENTO);
        pedidoRepository.save(pedidoModel);
        return new Checkout(pedido.getUuid(), StatusPagamento.AGUARDANDO_PAGAMENTO);
    }
}
