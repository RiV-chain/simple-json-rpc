package com.github.arteam.simplejsonrpc.server.simple.service;

import org.riv.annotations.Contract;
import org.riv.annotations.External;

/**
 * Date: 8/2/14
 * Time: 6:25 PM
 */
@Contract
public class BogusService {

    @External
    public void bogus() {
        System.out.println("Bogus");
    }

    @External("bogus")
    public void bogus2() {
        System.out.println("Bogus2");
    }

    @External
    public void notBogus() {
        System.out.println("Not bogus");
    }
}
