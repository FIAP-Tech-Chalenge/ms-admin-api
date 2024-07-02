package com.fiap.msadminapi.infra.queue.kafka.consumers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.msadminapi.domain.enums.pedido.StatusPagamento;
import com.fiap.msadminapi.domain.enums.pedido.StatusPedido;
import com.fiap.msadminapi.infra.dependecy.kafka.resolver.consumers.KafkaConsumerResolver;
import com.fiap.msadminapi.infra.model.PedidoModel;
import com.fiap.msadminapi.infra.repository.PedidoRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;


@Component
public class PedidoEntregueConsumer {

    private final KafkaConsumer<String, String> consumer;
    private final ObjectMapper objectMapper;
    private final PedidoRepository pedidoRepository;

    public PedidoEntregueConsumer(
            Properties kafkaConsumerProperties,
            PedidoRepository pedidoRepository
    ) {
        this.consumer = new KafkaConsumer<>(kafkaConsumerProperties);
        this.consumer.subscribe(Collections.singletonList(new KafkaConsumerResolver().getPedidoEntregueConsumer()));
        this.objectMapper = new ObjectMapper();
        this.pedidoRepository = pedidoRepository;
    }

    public void runConsumer() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("Mensagem recebida - Tópico: %s, Chave: %s, Valor: %s%n", record.topic(), record.key(), record.value());
                    try {
                        JsonNode messageJson = objectMapper.readTree(record.value());
                        String pedidoUuid = messageJson.get("pedido_uuid").asText();
                        String clienteUuid = messageJson.get("cliente_uuid").asText();
                        long numeroPedido = messageJson.get("numero_pedido").asLong();
                        double total = messageJson.get("total").asDouble();

                        PedidoModel pedidoModel = pedidoRepository.findByUuid(UUID.fromString(pedidoUuid));
                        if (pedidoModel == null) {
                            System.out.println("Criando o pedido");
                            PedidoModel novoPedidoModel = new PedidoModel();
                            novoPedidoModel.setUuid(UUID.fromString(pedidoUuid));
                            novoPedidoModel.setClienteId(UUID.fromString(clienteUuid));
                            novoPedidoModel.setStatusPedido(StatusPedido.FINALIZADO);
                            novoPedidoModel.setStatusPagamento(StatusPagamento.PAGO);
                            novoPedidoModel.setNumeroPedido(numeroPedido);
                            novoPedidoModel.setValorTotal((float)total);
                            pedidoRepository.save(novoPedidoModel);
                            continue;
                        }
                        pedidoModel.setStatusPedido(StatusPedido.FINALIZADO);
                        pedidoRepository.save(pedidoModel);
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


