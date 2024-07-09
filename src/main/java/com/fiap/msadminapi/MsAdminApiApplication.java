package com.fiap.msadminapi;

import com.fiap.msadminapi.infra.queue.kafka.consumers.ClienteConsumer;
import com.fiap.msadminapi.infra.queue.kafka.consumers.PedidoEntregueConsumer;
import com.fiap.msadminapi.infra.queue.kafka.consumers.PedidoPagoConsumer;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MsAdminApiApplication {

    private final ClienteConsumer consumerCliente;
    private final PedidoEntregueConsumer consumerPedidoEntregue;
    private final PedidoPagoConsumer consumerPedidoPago;

    @Autowired
    public MsAdminApiApplication(
            ClienteConsumer consumerCliente,
            PedidoEntregueConsumer consumerPedidoEntregue,
            PedidoPagoConsumer consumerPedidoPago
    ) {
        this.consumerCliente = consumerCliente;
        this.consumerPedidoEntregue = consumerPedidoEntregue;
        this.consumerPedidoPago = consumerPedidoPago;
    }

    public static void main(String[] args) {
        SpringApplication.run(MsAdminApiApplication.class, args);
    }

    @PostConstruct
    public void startConsumerCliente() {
        Thread consumerThread = new Thread(consumerCliente::runConsumerCliente);
        consumerThread.start();

        Thread consumerPedidoPagoThread = new Thread(consumerPedidoPago::runConsumer);
        consumerPedidoPagoThread.start();

        Thread consumerPedidoEntregaThread = new Thread(consumerPedidoEntregue::runConsumer);
        consumerPedidoEntregaThread.start();
    }
}
