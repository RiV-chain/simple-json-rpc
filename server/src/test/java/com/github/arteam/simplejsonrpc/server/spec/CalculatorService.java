package com.github.arteam.simplejsonrpc.server.spec;

import com.github.arteam.simplejsonrpc.core.annotation.External;
import com.github.arteam.simplejsonrpc.core.annotation.JsonRpcParam;
import com.github.arteam.simplejsonrpc.core.annotation.Contract;

/**
 * Date: 7/31/14
 * Time: 8:42 PM
 */
@Contract
public class CalculatorService {

    @External
    public long subtract(@JsonRpcParam("minuend") int m, @JsonRpcParam("subtrahend") int s) {
        return (long) m - (long) s;
    }

    @External
    public long sum(@JsonRpcParam("first") int first, @JsonRpcParam("second") int second,
                    @JsonRpcParam("third") int third) {
        return (long) first + (long) second + (long) third;
    }

    @External
    public void update(@JsonRpcParam("i1") int i1, @JsonRpcParam("i2") int i2,
                       @JsonRpcParam("i3") int i3, @JsonRpcParam("i4") int i4,
                       @JsonRpcParam("i5") int i5) {
        System.out.println("Update");
    }

    @External("get_data")
    public Object[] getData() {
        return new Object[]{"hello", 5};
    }
}
