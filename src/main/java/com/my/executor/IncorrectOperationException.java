package com.my.executor;

/**
 * Created by marcin on 13.01.16.
 */
public class IncorrectOperationException extends Exception {
    public IncorrectOperationException() {
    }

    public IncorrectOperationException(String message) {
        super(message);
    }
}
