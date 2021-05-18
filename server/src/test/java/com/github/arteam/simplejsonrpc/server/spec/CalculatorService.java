package com.github.arteam.simplejsonrpc.server.spec;

import org.riv.annotations.Contract;
import org.riv.annotations.External;
import org.riv.annotations.Parameter;

/**
 * Date: 7/31/14
 * Time: 8:42 PM
 */
@Contract
public class CalculatorService {

    @External
    public long subtract(@Parameter("minuend") int m, @Parameter("subtrahend") int s) {
        return (long) m - (long) s;
    }

    @External
    public long sum(@Parameter("first") int first, @Parameter("second") int second,
                    @Parameter("third") int third) {
        return (long) first + (long) second + (long) third;
    }

    @External
    public void update(@Parameter("i1") int i1, @Parameter("i2") int i2,
                       @Parameter("i3") int i3, @Parameter("i4") int i4,
                       @Parameter("i5") int i5) {
        System.out.println("Update");
    }

    @External("get_data")
    public Object[] getData() {
        return new Object[]{"hello", 5};
    }
}
