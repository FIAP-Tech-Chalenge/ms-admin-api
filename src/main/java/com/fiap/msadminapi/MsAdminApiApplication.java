package com.fiap.msadminapi;

import com.fiap.msadminapi.infra.queue.kafka.consumers.ClienteConsumer;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MsAdminApiApplication {

    private final ClienteConsumer consumerCliente;

    @Autowired
    public MsAdminApiApplication(ClienteConsumer consumerCliente) {
        this.consumerCliente = consumerCliente;
    }

    public static void main(String[] args) {
        SpringApplication.run(MsAdminApiApplication.class, args);
    }

    @PostConstruct
    public void startConsumerCliente() {
        Thread consumerThread = new Thread(consumerCliente::runConsumerCliente);
        consumerThread.start();
    }
}
