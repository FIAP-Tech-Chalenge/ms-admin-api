package com.fiap.msadminapi;

import com.fiap.msadminapi.infra.dependecy.cb.CircuitBreaker;
import com.fiap.msadminapi.infra.queue.kafka.consumers.ClienteConsumer;
import com.fiap.msadminapi.infra.queue.kafka.consumers.PedidoEntregueConsumer;
import com.fiap.msadminapi.infra.queue.kafka.consumers.PedidoPagoConsumer;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MsAdminApiApplication {

    private static final Logger logger = LoggerFactory.getLogger(MsAdminApiApplication.class);

    private final ClienteConsumer consumerCliente;
    private final PedidoEntregueConsumer consumerPedidoEntregue;
    private final PedidoPagoConsumer consumerPedidoPago;
    private final CircuitBreaker circuitBreaker = new CircuitBreaker();

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
        logger.info("Starting Kafka consumers...");
        Thread consumerThread = new Thread(consumerCliente::runConsumerCliente);
        consumerThread.start();

        Thread consumerPedidoPagoThread = new Thread(consumerPedidoPago::runConsumer);
        consumerPedidoPagoThread.start();

        Thread consumerPedidoEntregaThread = new Thread(consumerPedidoEntregue::runConsumer);
        consumerPedidoEntregaThread.start();
    }

    public void someMethod() {
        if (circuitBreaker.isRequestAllowed()) {
            try {
                logger.info("Request allowed, processing...");
                circuitBreaker.incrementSuccess();
            } catch (Exception e) {
                circuitBreaker.incrementFailure();
                fallbackMethod(e);
            }
        } else {
            fallbackMethod(new RuntimeException("Circuit Breaker is open"));
        }
    }

    public void fallbackMethod(Throwable t) {
        logger.error("Fallback triggered due to: {}", t.getMessage(), t);
        // lógica do fallback
        logger.info("Returning fallback response");
    }
}