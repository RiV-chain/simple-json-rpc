package com.github.arteam.simplejsonrpc.server.simple.exception;

import org.riv.annotations.Error;
import org.riv.annotations.ErrorData;

@Error(code = -30002, message = "Error with data (multiple fields)")
public class ExceptionWithDataMultipleFields extends RuntimeException {

    @ErrorData
    private final String[] data;
    @ErrorData
    private final String anotherData;

    public ExceptionWithDataMultipleFields(String message, String[] data, String anotherData) {
        super(message);
        this.data = data;
        this.anotherData = anotherData;
    }

}
