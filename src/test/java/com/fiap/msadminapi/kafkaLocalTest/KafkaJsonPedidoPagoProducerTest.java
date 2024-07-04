package com.fiap.msadminapi.kafkaLocalTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.msadminapi.domain.enums.pedido.StatusPagamento;
import com.fiap.msadminapi.domain.enums.produto.CategoriaEnum;
import com.fiap.msadminapi.infra.dependecy.kafka.resolver.consumers.KafkaConsumerResolver;
import com.fiap.msadminapi.infra.model.ImagemModel;
import com.fiap.msadminapi.infra.model.ProdutoModel;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ExecutionException;

public class KafkaJsonPedidoPagoProducerTest {
    public static void main(String[] args) {
        String topicName = new KafkaConsumerResolver().getPedidoPagoConsumer();
        String bootstrapServers = "localhost:9092"; // substitua pelo endereço do seu broker Kafka

        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            for (int i = 0; i < 10; i++) {
                String key = "key-" + i;

                // Crie um mapa para simular o JSON
                List<ProdutoModel> produtosListProdutos = new ArrayList<>();
                List<ImagemModel> imagens = new ArrayList<>();
                produtosListProdutos.add(
                        new ProdutoModel(
                                "nome " + i,
                                (float)123,
                                "descricao" + i,
                                CategoriaEnum.LANCHE,
                                12,
                                imagens
                        )
                );
                Map<String, Object> valueMap = new HashMap<>();
                valueMap.put("pedido_uuid", UUID.randomUUID().toString());
                valueMap.put("cliente_uuid", UUID.randomUUID().toString());
                valueMap.put("status_pagamento", StatusPagamento.PAGO.toString());
                valueMap.put("numero_pedido", i);
                valueMap.put("total", 23423);
                valueMap.put("produtos", objectMapper.writeValueAsString(produtosListProdutos));

                // Converta o mapa para uma string JSON
                String value = objectMapper.writeValueAsString(valueMap);

                ProducerRecord<String, String> record = new ProducerRecord<>(topicName, key, value);

                RecordMetadata metadata = producer.send(record).get();
                System.out.printf("Sent record with key %s and value %s to partition %d with offset %d%n",
                        key, value, metadata.partition(), metadata.offset());
            }
        } catch (ExecutionException | InterruptedException | JsonProcessingException e) {
            e.printStackTrace();
        } finally {
            producer.close();
        }
    }
}
