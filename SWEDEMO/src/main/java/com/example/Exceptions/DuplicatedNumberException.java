package com.example.Exceptions;

/**
 * Eccezione lanciata nel caso di numeri duplicati in un contatto
 */
public class DuplicatedNumberException extends Exception {
    public DuplicatedNumberException(String message) {
        super(message);
    }
}
