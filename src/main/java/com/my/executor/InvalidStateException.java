package com.my.executor;

/**
 * Created by marcin on 09.01.16.
 */
public class InvalidStateException extends Exception {
    public InvalidStateException(String message) {
        super(message);
    }

    public InvalidStateException() {
    }
}
