package com.x20.frogger.graphics;

public class BatchException extends RuntimeException {
    public BatchException() {
        this("Batch was not begun");
    }

    public BatchException(String message) {
        super(message);
    }
}
