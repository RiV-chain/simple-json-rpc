package com.github.arteam.simplejsonrpc.server.simple.exception;

import org.riv.annotations.Error;

/**
 * Date: 7/31/14
 * Time: 6:23 PM
 */
@Error(code = -32032, message = "You are not authorized to the team service")
public class TeamServiceAuthException extends RuntimeException {

    public TeamServiceAuthException(String message) {
        super(message);
    }
}
