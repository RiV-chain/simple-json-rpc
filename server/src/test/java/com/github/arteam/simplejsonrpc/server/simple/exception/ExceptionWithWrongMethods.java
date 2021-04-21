package com.github.arteam.simplejsonrpc.server.simple.exception;

import com.github.arteam.simplejsonrpc.core.annotation.Error;
import com.github.arteam.simplejsonrpc.core.annotation.JsonRpcErrorData;

@Error(code = -30005, message = "Error with wrong methods")
public class ExceptionWithWrongMethods extends RuntimeException {

    private final String[] data;

    public ExceptionWithWrongMethods(String message, String[] data) {
        super(message);
        this.data = data;
    }

    @JsonRpcErrorData
    public void returnVoid() {
    }

    @JsonRpcErrorData
    public String hasArgs(String s) {
        return s;
    }

    @JsonRpcErrorData
    public String[] getData() {
        return data;
    }

}
