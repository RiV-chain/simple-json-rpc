package com.github.arteam.simplejsonrpc.server.simple.exception;

import org.riv.annotations.Error;
import org.riv.annotations.ErrorData;

@Error(code = -30004, message = "Error with data (multiple getters)")
public class ExceptionWithDataMultipleMixed extends RuntimeException {

    @ErrorData
    private final String[] data;

    public ExceptionWithDataMultipleMixed(String message, String[] data) {
        super(message);
        this.data = data;
    }

    @ErrorData
    public String[] getData() {
        return data;
    }

}
