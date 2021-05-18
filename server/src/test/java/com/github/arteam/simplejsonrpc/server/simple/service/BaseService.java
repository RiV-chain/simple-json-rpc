package com.github.arteam.simplejsonrpc.server.simple.service;

import org.riv.annotations.External;

/**
 * Date: 7/30/14
 * Time: 3:29 PM
 */
public class BaseService {

    @External
    public boolean isAlive() {
        return true;
    }

    @External
    public void updateCache() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Updating cache...");
                // Some time...
                System.out.println("Done!");
            }
        }).start();
    }
}
