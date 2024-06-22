package com.fiap.msadminapi.infra.dependecy.kafka.resolver.consumers;

import com.fiap.msadminapi.infra.dependecy.kafka.resolver.KafkaTopicsEnum;

public class KafkaConsumerResolver {

    public String getProdutoConsumer() {
        return KafkaTopicsEnum.produto.name();
    }

    public String getClienteConsumer() {
        return KafkaTopicsEnum.cliente.name();
    }
}

