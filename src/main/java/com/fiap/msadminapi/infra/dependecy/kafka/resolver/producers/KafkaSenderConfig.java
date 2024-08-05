package com.fiap.msadminapi.infra.dependecy.kafka.resolver.producers;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class KafkaSenderConfig {
    private final KafkaProducer<String, String> producer;
    private final String topic;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public KafkaSenderConfig(String servers, String topic) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        this.producer = new KafkaProducer<>(props);
        this.topic = topic;
    }

    protected void send(String key, String value) {
        logger.info("Conteúdo da mensagem: " + value);
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);
        producer.send(record, (metadata, exception) -> {
            if (exception == null) {
                logger.info("Mensagem enviada com sucesso para o tópico: " + metadata.topic()
                        + "\nPartition: " + metadata.partition()
                        + "\nOffset: " + metadata.offset()
                        + "\nTimestamp: " + metadata.timestamp());
            } else {
                logger.error("Erro ao enviar mensagem: " + exception.getMessage());
            }
        });
    }
}
