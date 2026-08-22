package org.example.springbootadventure.exceptions;

public class OrderProcessingException extends RuntimeException {
    public OrderProcessingException(String message) {
        super(message);

    }
}
