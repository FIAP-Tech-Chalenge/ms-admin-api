package com.fiap.msadminapi.infra.dependecy.kafka.resolver.consumers;

import com.fiap.msadminapi.infra.dependecy.kafka.resolver.KafkaTopicsEnum;

public class KafkaConsumerResolver {

    public String getClienteConsumer() {
        return KafkaTopicsEnum.cliente.name();
    }
    public String getClienteInativoConsumer() {
        return KafkaTopicsEnum.clienteInativo.name();
    }

    public String getPedidoEntregueConsumer() {
        return KafkaTopicsEnum.entrega.name();
    }

    public String getPedidoPagoConsumer() {
        return KafkaTopicsEnum.pedido.name();
    }

}

