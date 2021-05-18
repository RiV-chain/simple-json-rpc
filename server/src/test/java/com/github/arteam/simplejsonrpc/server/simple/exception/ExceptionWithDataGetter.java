package com.github.arteam.simplejsonrpc.server.simple.exception;

import org.riv.annotations.Error;
import org.riv.annotations.ErrorData;

@Error(code = -30001, message = "Error with data (getter)")
public class ExceptionWithDataGetter extends RuntimeException {

    private final String[] data;

    public ExceptionWithDataGetter(String message, String[] data) {
        super(message);
        this.data = data;
    }

    @ErrorData
    public String[] getData() {
        return data;
    }
}
