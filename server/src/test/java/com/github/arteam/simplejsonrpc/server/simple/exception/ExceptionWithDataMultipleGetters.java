package com.github.arteam.simplejsonrpc.server.simple.exception;

import org.riv.annotations.Error;
import org.riv.annotations.ErrorData;

@Error(code = -30003, message = "Error with data (multiple getters)")
public class ExceptionWithDataMultipleGetters extends RuntimeException {

    private final String[] data;
    private final String anotherData;

    public ExceptionWithDataMultipleGetters(String message, String[] data, String anotherData) {
        super(message);
        this.data = data;
        this.anotherData = anotherData;
    }

    @ErrorData
    public String[] getData() {
        return data;
    }

    @ErrorData
    public String getAnotherData() {
        return anotherData;
    }
}
