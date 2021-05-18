package com.github.arteam.simplejsonrpc.server.simple.exception;

import org.riv.annotations.Error;
import org.riv.annotations.ErrorData;

@Error(code = -30000, message = "Error with data (field)")
public class ExceptionWithDataField extends RuntimeException {

    @ErrorData
    private final String[] data;

    public ExceptionWithDataField(String message, String[] data) {
        super(message);
        this.data = data;
    }

}
