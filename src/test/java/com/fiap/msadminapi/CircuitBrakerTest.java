package com.fiap.msadminapi;

import com.fiap.msadminapi.infra.dependecy.cb.CircuitBreaker;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CircuitBrakerTest {

    @Test
    void testCircuitBreakerOpenState() {
        CircuitBreaker circuitBreaker = new CircuitBreaker();
        for (int i = 0; i < 3; i++) {
            circuitBreaker.incrementFailure();
        }
        assertFalse(circuitBreaker.isRequestAllowed());
    }

    @Test
    void testCircuitBreakerClosedState() {
        CircuitBreaker circuitBreaker = new CircuitBreaker();
        for (int i = 0; i < 3; i++) {
            circuitBreaker.incrementFailure();
        }
        assertFalse(circuitBreaker.isRequestAllowed());
        circuitBreaker.reset();
        assertTrue(circuitBreaker.isRequestAllowed());
    }

    @Test
    void testCircuitBreakerHalfOpenState() throws InterruptedException {
        CircuitBreaker circuitBreaker = new CircuitBreaker();
        for (int i = 0; i < 3; i++) {
            circuitBreaker.incrementFailure();
        }
        assertFalse(circuitBreaker.isRequestAllowed());
        Thread.sleep(11000); // Wait until the state changes to HALF_OPEN
        assertTrue(circuitBreaker.isRequestAllowed());
    }
}