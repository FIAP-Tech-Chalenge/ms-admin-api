package com.fiap.msadminapi.infra.dependecy.kafka.resolver.producers;


import com.fiap.msadminapi.infra.dependecy.kafka.resolver.KafkaTopicsEnum;

public class KafkaProducerResolver {

    public String getProdutoProducer() {
        return KafkaTopicsEnum.produto.name();
    }

}
