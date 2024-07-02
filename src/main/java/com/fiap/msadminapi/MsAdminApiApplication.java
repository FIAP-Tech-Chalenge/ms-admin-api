package com.fiap.msadminapi;

import com.fiap.msadminapi.infra.queue.kafka.consumers.ClienteConsumer;
import com.fiap.msadminapi.infra.queue.kafka.consumers.PedidoEntregueConsumer;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MsAdminApiApplication {

    private final ClienteConsumer consumerCliente;
    private final PedidoEntregueConsumer consumerPedidoEntregue;

    @Autowired
    public MsAdminApiApplication(
            ClienteConsumer consumerCliente,
            PedidoEntregueConsumer consumerPedidoEntregue
    ) {
        this.consumerCliente = consumerCliente;
        this.consumerPedidoEntregue = consumerPedidoEntregue;
    }

    public static void main(String[] args) {
        SpringApplication.run(MsAdminApiApplication.class, args);
    }

    @PostConstruct
    public void startConsumerCliente() {
        Thread consumerThread = new Thread(consumerCliente::runConsumerCliente);
        consumerThread.start();

        Thread consumerPedidoEntregaThread = new Thread(consumerPedidoEntregue::runConsumer);
        consumerPedidoEntregaThread.start();
    }
}
