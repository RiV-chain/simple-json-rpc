package com.github.arteam.simplejsonrpc.server.simple.exception;

import com.github.arteam.simplejsonrpc.core.annotation.Error;
import com.github.arteam.simplejsonrpc.core.annotation.JsonRpcErrorData;

@Error(code = -30000, message = "Error with data (field)")
public class ExceptionWithDataField extends RuntimeException {

    @JsonRpcErrorData
    private final String[] data;

    public ExceptionWithDataField(String message, String[] data) {
        super(message);
        this.data = data;
    }

}
