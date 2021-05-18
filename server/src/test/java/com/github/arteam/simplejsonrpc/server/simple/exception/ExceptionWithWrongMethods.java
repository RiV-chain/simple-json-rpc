package com.github.arteam.simplejsonrpc.server.simple.exception;

import org.riv.annotations.Error;
import org.riv.annotations.ErrorData;

@Error(code = -30005, message = "Error with wrong methods")
public class ExceptionWithWrongMethods extends RuntimeException {

    private final String[] data;

    public ExceptionWithWrongMethods(String message, String[] data) {
        super(message);
        this.data = data;
    }

    @ErrorData
    public void returnVoid() {
    }

    @ErrorData
    public String hasArgs(String s) {
        return s;
    }

    @ErrorData
    public String[] getData() {
        return data;
    }

}
