package com.fiap.msadminapi.infra.queue.kafka.consumers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.msadminapi.domain.enums.pedido.StatusPagamento;
import com.fiap.msadminapi.domain.enums.pedido.StatusPedido;
import com.fiap.msadminapi.domain.enums.produto.CategoriaEnum;
import com.fiap.msadminapi.infra.dependecy.kafka.resolver.consumers.KafkaConsumerResolver;
import com.fiap.msadminapi.infra.model.PedidoModel;
import com.fiap.msadminapi.infra.model.PedidoProdutoModel;
import com.fiap.msadminapi.infra.repository.PedidoProdutoRepository;
import com.fiap.msadminapi.infra.repository.PedidoRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.*;


@Component
public class PedidoPagoConsumer {

    private final KafkaConsumer<String, String> consumer;
    private final ObjectMapper objectMapper;
    private final PedidoRepository pedidoRepository;
    private final PedidoProdutoRepository pedidoProdutoRepository;

    public PedidoPagoConsumer(
            Properties kafkaConsumerProperties,
            PedidoRepository pedidoRepository,
            PedidoProdutoRepository pedidoProdutoRepository
    ) {
        this.consumer = new KafkaConsumer<>(kafkaConsumerProperties);
        this.consumer.subscribe(Collections.singletonList(new KafkaConsumerResolver().getPedidoPagoConsumer()));
        this.objectMapper = new ObjectMapper();
        this.pedidoRepository = pedidoRepository;
        this.pedidoProdutoRepository = pedidoProdutoRepository;
    }

    public void runConsumer() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("Mensagem recebida - Tópico: %s, Chave: %s, Valor: %s%n", record.topic(), record.key(), record.value());
                    try {
                        JsonNode messageJson = objectMapper.readTree(record.value());
                        String uuidPedido = messageJson.get("pedido_uuid").asText();
                        String uuidCliente = messageJson.get("cliente_uuid").asText();
                        long numeroPedido = messageJson.get("numero_pedido").asLong();
                        Double total = messageJson.get("total").asDouble();

                        PedidoModel pedidoModel = pedidoRepository.findByUuid(UUID.fromString(uuidPedido));
                        if (pedidoModel == null) {
                            System.out.println("Criando o pedido");
                            PedidoModel novoPedidoModel = new PedidoModel();
                            novoPedidoModel.setUuid(UUID.fromString(uuidPedido));
                            novoPedidoModel.setClienteId(UUID.fromString(uuidCliente));
                            novoPedidoModel.setStatusPedido(StatusPedido.RECEBIDO);
                            novoPedidoModel.setStatusPagamento(StatusPagamento.PAGO);
                            novoPedidoModel.setNumeroPedido(numeroPedido);
                            novoPedidoModel.setValorTotal(total.floatValue());
                            pedidoRepository.save(novoPedidoModel);

                            Map<String, Object> deserializedMap = objectMapper.readValue(
                                    messageJson.toString(), new TypeReference<Map<String, Object>>() {}
                            );
                            String produtosJsonString = (String) deserializedMap.get("produtos");
                            List<Object> deserializedProdutosList = objectMapper.readValue(produtosJsonString, new TypeReference<List<Object>>() {
                            });

                            for (Object produto : deserializedProdutosList) {
                                java.util.Map<?, ?> produtoMap = (java.util.Map<?, ?>) produto;
                                PedidoProdutoModel pedidoProduto = new PedidoProdutoModel();
                                pedidoProduto.setPedidoUuid(novoPedidoModel.getUuid());
                                pedidoProduto.setProdutoUuid(UUID.fromString((String) produtoMap.get("uuid")));
                                pedidoProduto.setQuantidade(Integer.parseInt(produtoMap.get("quantidade").toString()));
                                pedidoProduto.setValor(Float.parseFloat(produtoMap.get("valor").toString()));
                                pedidoProduto.setCategoria(CategoriaEnum.valueOf((String) produtoMap.get("categoria")));
                                pedidoProdutoRepository.save(pedidoProduto);
                            }

                            continue;
                        }
                        if (pedidoModel.getStatusPedido() == StatusPedido.FINALIZADO) {
                            System.out.println("Pedido já finalizado");
                            continue;
                        }
                        pedidoModel.setStatusPedido(StatusPedido.RECEBIDO);
                        pedidoRepository.save(pedidoModel);
                    } catch (Exception e) {
                        System.err.println("Erro ao processar a mensagem: " + e.getMessage());
                        System.err.println(e);
                    }
                }
            }
        } finally {
            this.consumer.close();
            System.out.println("Consumidor Kafka fechado.");
        }
    }
}


