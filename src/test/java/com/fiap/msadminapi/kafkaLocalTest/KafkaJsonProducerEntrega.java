package com.fiap.msadminapi.kafkaLocalTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.msadminapi.domain.enums.pedido.StatusPedido;
import com.fiap.msadminapi.infra.dependecy.kafka.resolver.KafkaTopicsEnum;
import com.fiap.msadminapi.infra.model.PedidoModel;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class KafkaJsonProducerEntrega {

    public static void main(String[] args) {
        String topicName = KafkaTopicsEnum.entrega.name(); // alterado para o tópico de entrega
        String bootstrapServers = "localhost:9092"; // substitua pelo endereço do seu broker Kafka

        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            for (int i = 0; i < 1; i++) {
                String key = "key-" + i;

                Map<String, Object> valueMap = new HashMap<>();
                valueMap.put("pedido_uuid", UUID.randomUUID().toString());
                valueMap.put("cliente_uuid", UUID.randomUUID().toString());
                valueMap.put("numero_pedido", i);
                valueMap.put("status_pedido", StatusPedido.FINALIZADO);
                valueMap.put("total", 100.0);

                String value = objectMapper.writeValueAsString(valueMap);

                ProducerRecord<String, String> record = new ProducerRecord<>(topicName, key, value);

                RecordMetadata metadata = producer.send(record).get();
                System.out.printf("Mensagem enviada %s com o valor %s para a partição %d com o offset %d%n",
                        key, value, metadata.partition(), metadata.offset());
            }
        } catch (ExecutionException | InterruptedException | com.fasterxml.jackson.core.JsonProcessingException e) {
            e.printStackTrace();
        } finally {
            producer.close();
        }
    }
}
