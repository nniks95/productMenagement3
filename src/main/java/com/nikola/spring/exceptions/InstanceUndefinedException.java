package com.nikola.spring.exceptions;

import javax.management.RuntimeErrorException;
import java.io.Serial;

public class InstanceUndefinedException extends RuntimeErrorException {


    public InstanceUndefinedException(Error e) {
        super(e);
    }
    @Serial
    private static final long serialVersionUID = 1L;
}
