package com.fiap.msadminapi.infra.dependecy.cb;

public class CircuitBreaker {
    private int failureCount = 0;
    private int successCount = 0;
    private int threshold = 3;
    private long openStateTimeout = 10000; // 10 seconds
    private long lastFailureTime = 0;
    private State state = State.CLOSED;

    private enum State {
        CLOSED, OPEN, HALF_OPEN
    }

    public synchronized void incrementFailure() {
        failureCount++;
        lastFailureTime = System.currentTimeMillis();
        if (failureCount >= threshold) {
            state = State.OPEN;
        }
    }

    public synchronized void incrementSuccess() {
        successCount++;
        if (state == State.HALF_OPEN && successCount >= threshold) {
            reset();
            state = State.CLOSED;
        }
    }

    public synchronized boolean isRequestAllowed() {
        if (state == State.OPEN) {
            if (System.currentTimeMillis() - lastFailureTime > openStateTimeout) {
                state = State.HALF_OPEN;
                return true;
            }
            return false;
        }
        return true;
    }

    public synchronized void reset() {
        failureCount = 0;
        successCount = 0;
        state = State.CLOSED;
    }
}