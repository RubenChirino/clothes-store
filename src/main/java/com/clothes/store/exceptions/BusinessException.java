package com.clothes.store.exceptions;

import java.io.Serial;

public class BusinessException extends Exception {
    @Serial
    private static final long serialVersionUID = 7905754903888004478L;
    public BusinessException(String message) {
        super(message);
    }
}
