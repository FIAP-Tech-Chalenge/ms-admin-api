package com.fiap.msadminapi;


import com.fiap.msadminapi.infra.dependecy.cb.CircuitBreaker;
import com.fiap.msadminapi.infra.queue.kafka.consumers.ClienteConsumer;
import com.fiap.msadminapi.infra.queue.kafka.consumers.PedidoEntregueConsumer;
import com.fiap.msadminapi.infra.queue.kafka.consumers.PedidoPagoConsumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MsAdminApiApplicationTest {

    private MsAdminApiApplication msAdminApiApplication;
    private CircuitBreaker circuitBreaker;

    @BeforeEach
    void setUp() {
        ClienteConsumer clienteConsumer = Mockito.mock(ClienteConsumer.class);
        PedidoEntregueConsumer pedidoEntregueConsumer = Mockito.mock(PedidoEntregueConsumer.class);
        PedidoPagoConsumer pedidoPagoConsumer = Mockito.mock(PedidoPagoConsumer.class);
        circuitBreaker = new CircuitBreaker();
        //msAdminApiApplication = new MsAdminApiApplication(clienteConsumer, pedidoEntregueConsumer, pedidoPagoConsumer);
    }

//    @Test
//    void testSomeMethodWithCircuitBreakerClosed() {
//        msAdminApiApplication.someMethod();
//        // Verify if the method logic was executed
//    }

//    @Test
//    void testSomeMethodWithCircuitBreakerOpen() {
//        for (int i = 0; i < 3; i++) {
//            circuitBreaker.incrementFailure();
//        }
//        msAdminApiApplication.someMethod();
//        // Verify if the fallback was called
//    }
}