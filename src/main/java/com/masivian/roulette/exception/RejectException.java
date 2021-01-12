package com.masivian.roulette.exception;

public abstract class RejectException extends Exception {

    RejectException(String message) {
        super(message);
    }
}
