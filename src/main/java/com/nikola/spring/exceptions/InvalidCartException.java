package com.nikola.spring.exceptions;


import javax.management.RuntimeErrorException;
import java.io.Serial;

public class InvalidCartException extends RuntimeErrorException {

    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidCartException(Error e) {
        super(e);
    }
}
