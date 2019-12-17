package ru.javawebinar.topjava.util.exception;

public class DublicateEmailException extends RuntimeException {
    public DublicateEmailException(String message) {
        super(message);
    }
}
