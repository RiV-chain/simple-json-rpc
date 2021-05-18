package com.github.arteam.simplejsonrpc.server.simple.exception;

import org.riv.annotations.Error;

/**
 * Date: 7/31/14
 * Time: 6:23 PM
 */
@Error(code = -32000)
public class EmptyMessageTeamServiceException extends RuntimeException {

    public EmptyMessageTeamServiceException(String message) {
        super(message);
    }
}
