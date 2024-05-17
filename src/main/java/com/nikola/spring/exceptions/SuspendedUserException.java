package com.nikola.spring.exceptions;

import java.io.Serial;

public class SuspendedUserException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;
    public SuspendedUserException(Error e){
        super(e);
    }
}
