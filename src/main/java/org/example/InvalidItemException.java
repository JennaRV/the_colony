package org.example;

public class InvalidItemException extends Throwable {
    public InvalidItemException(String message) {
        super(message);
    }
}
