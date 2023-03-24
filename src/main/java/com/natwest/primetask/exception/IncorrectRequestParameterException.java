package com.natwest.primetask.exception;

public class IncorrectRequestParameterException extends Exception {
    private static final long SerialVersionUID = 10l;
    public IncorrectRequestParameterException(String message) {
        super(message);
    }
}
