package com.fiap.msadminapi.infra.queue.kafka.producers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fiap.msadminapi.domain.gateway.producers.NovoProdutoProducerInterface;
import com.fiap.msadminapi.domain.generic.output.ProdutoOutput;
import com.fiap.msadminapi.infra.dependecy.kafka.resolver.producers.KafkaProducerResolver;
import com.fiap.msadminapi.infra.dependecy.kafka.resolver.producers.KafkaSenderConfig;

import java.util.UUID;

public class NovoProdutoProducer extends KafkaSenderConfig implements NovoProdutoProducerInterface {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public NovoProdutoProducer(String servers){
        super(servers, new KafkaProducerResolver().getProdutoProducer());
    }

    public void send(ProdutoOutput produtoOutput){
        try {
            ObjectNode jsonNode = objectMapper.createObjectNode();
            jsonNode.put("produto_uuid", produtoOutput.getUuid().toString());
            jsonNode.put("produto_nome", produtoOutput.getNome());
            jsonNode.put("produto_valor", produtoOutput.getValor());
            jsonNode.put("produto_descricao", produtoOutput.getDescricao());
            jsonNode.put("produto_categoria", produtoOutput.getCategoria().toString());
            jsonNode.put("produto_quantidade", produtoOutput.getQuantidade());
            jsonNode.put("produto_imagens", objectMapper.writeValueAsString(produtoOutput.getImagens()));
            String json = jsonNode.toString();
            send(UUID.randomUUID().toString(), json);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}


