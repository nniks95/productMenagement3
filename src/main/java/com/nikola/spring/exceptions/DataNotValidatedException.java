package com.nikola.spring.exceptions;

import javax.management.RuntimeErrorException;
import java.io.Serial;

public class DataNotValidatedException extends RuntimeErrorException {

    public DataNotValidatedException(Error e) {
        super(e);
    }

    @Serial
    private static final long serialVersionUID = 1L;
}
