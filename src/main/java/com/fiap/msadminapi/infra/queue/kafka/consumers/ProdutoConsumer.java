package com.fiap.msadminapi.infra.queue.kafka.consumers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.msadminapi.domain.enums.produto.CategoriaEnum;
import com.fiap.msadminapi.infra.dependecy.kafka.resolver.consumers.KafkaConsumerResolver;
import com.fiap.msadminapi.infra.model.ProdutoModel;
import com.fiap.msadminapi.infra.repository.ProdutoRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;


@Component
public class ProdutoConsumer {

    private final KafkaConsumer<String, String> consumer;
    private final ObjectMapper objectMapper;
    private final ProdutoRepository produtoRepository;

    public ProdutoConsumer(Properties kafkaConsumerProperties,
                           ProdutoRepository produtoRepository) {
        this.consumer = new KafkaConsumer<>(kafkaConsumerProperties);
        this.consumer.subscribe(Collections.singletonList(new KafkaConsumerResolver().getProdutoConsumer()));
        this.objectMapper = new ObjectMapper();
        this.produtoRepository = produtoRepository;
    }

    public void runConsumerProduto() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("Mensagem recebida - Tópico: %s, Chave: %s, Valor: %s%n", record.topic(), record.key(), record.value());
                    try {
                        JsonNode messageJson = objectMapper.readTree(record.value());
                        String uuid = messageJson.get("produto_uuid").asText();
                        double valor = messageJson.get("produto_valor").asDouble();
                        String nome = messageJson.get("produto_nome").asText();
                        String descricao = messageJson.get("produto_descricao").asText();
                        String categoria = messageJson.get("produto_categoria").asText();
                        int quantidade = messageJson.get("produto_quantidade").asInt();

                        ProdutoModel produtoModel = new ProdutoModel();
                        produtoModel.setUuid(UUID.fromString(uuid));
                        produtoModel.setValor((float) valor);
                        produtoModel.setNome(nome);
                        produtoModel.setDescricao(descricao);
                        produtoModel.setCategoria(CategoriaEnum.valueOf(categoria));
                        produtoModel.setQuantidade(quantidade);
                        produtoRepository.save(produtoModel);
                    } catch (Exception e) {
                        System.err.println("Erro ao processar a mensagem: " + e.getMessage());
                    }
                }
            }
        } finally {
            this.consumer.close();
            System.out.println("Consumidor Kafka fechado.");
        }
    }
}