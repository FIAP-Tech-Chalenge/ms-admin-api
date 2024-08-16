package com.fiap.msadminapi.infra.queue.kafka.consumers;

import com.fiap.msadminapi.infra.model.ClienteModel;
import com.fiap.msadminapi.infra.model.PedidoModel;
import com.fiap.msadminapi.infra.repository.ClienteRepository;
import com.fiap.msadminapi.infra.repository.PedidoRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PedidoEntregueConsumerTest {

    @Mock
    private KafkaConsumer<String, String> kafkaConsumer;

    @Mock
    private PedidoRepository pedidoRepository;

    private PedidoEntregueConsumer pedidoEntregueConsumer;

    @BeforeEach
    public void setUp() {
        Properties kafkaConsumerProperties = new Properties();
        kafkaConsumerProperties.setProperty("bootstrap.servers", "localhost:9092");
        kafkaConsumerProperties.setProperty("group.id", "test-group");
        kafkaConsumerProperties.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaConsumerProperties.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        pedidoEntregueConsumer = new PedidoEntregueConsumer(kafkaConsumerProperties, pedidoRepository);

        ReflectionTestUtils.setField(pedidoEntregueConsumer, "consumer", kafkaConsumer);
    }

    @Test
    public void testRunConsumer() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);

        String jsonMessage = "{\"pedido_uuid\":\"123e4567-e89b-12d3-a456-426614174000\",\"cliente_uuid\":\"123e4567-e89b-12d3-a456-426614174001\",\"numero_pedido\":123,\"total\":100.0}";
        ConsumerRecord<String, String> record = new ConsumerRecord<>("topic", 0, 0L, "key", jsonMessage);
        ConsumerRecords<String, String> records = new ConsumerRecords<>(Collections.singletonMap(new TopicPartition("topic", 0), List.of(record)));

        when(kafkaConsumer.poll(any(Duration.class))).thenAnswer(invocation -> {
            latch.countDown();
            return records;
        });

        Thread consumerThread = new Thread(() -> {
            pedidoEntregueConsumer.runConsumer();
        });
        consumerThread.start();

        latch.await(5, TimeUnit.SECONDS);

        consumerThread.interrupt();
        consumerThread.join();

        verify(pedidoRepository, times(1)).save(any(PedidoModel.class));
    }

}
