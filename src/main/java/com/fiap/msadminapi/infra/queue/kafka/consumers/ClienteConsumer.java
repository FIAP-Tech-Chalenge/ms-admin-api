package com.fiap.msadminapi.infra.queue.kafka.consumers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.msadminapi.infra.dependecy.kafka.resolver.consumers.KafkaConsumerResolver;
import com.fiap.msadminapi.infra.model.ClienteModel;
import com.fiap.msadminapi.infra.repository.ClienteRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;


@Component
public class ClienteConsumer {

    private final KafkaConsumer<String, String> consumer;
    private final ObjectMapper objectMapper;
    private final ClienteRepository clienteRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public ClienteConsumer(Properties kafkaConsumerProperties,
                           ClienteRepository clienteRepository) {
        this.consumer = new KafkaConsumer<>(kafkaConsumerProperties);
        this.consumer.subscribe(Collections.singletonList(new KafkaConsumerResolver().getClienteConsumer()));
        this.objectMapper = new ObjectMapper();
        this.clienteRepository = clienteRepository;
    }

    public void runConsumerCliente() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    logger.info("Mensagem recebida - Tópico: %s, Chave: %s, Valor: %s%n", record.topic(), record.key(), record.value());
                    try {
                        JsonNode messageJson = objectMapper.readTree(record.value());
                        String uuid = messageJson.get("cliente_id").asText();
                        String nome = messageJson.get("cliente_nome").asText();
                        String cpf = messageJson.get("cliente_cpf").asText();
                        String email = messageJson.get("cliente_email").asText();
                        ClienteModel clienteModel = new ClienteModel();
                        clienteModel.setUuid(UUID.fromString(uuid));
                        clienteModel.setNome(nome);
                        clienteModel.setCpf(cpf);
                        clienteModel.setEmail(email);
                        clienteRepository.save(clienteModel);
                    } catch (Exception e) {
                        logger.error("Erro ao processar a mensagem: {}", e.getMessage(), e);
                    }
                }
            }
        } finally {
            this.consumer.close();
            logger.info("Consumidor Kafka fechado.");
        }
    }
}


