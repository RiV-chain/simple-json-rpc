package com.github.arteam.simplejsonrpc.server.spec;

import com.github.arteam.simplejsonrpc.core.annotation.External;
import com.github.arteam.simplejsonrpc.core.annotation.Param;
import com.github.arteam.simplejsonrpc.core.annotation.Contract;

/**
 * Date: 7/31/14
 * Time: 8:42 PM
 */
@Contract
public class CalculatorService {

    @External
    public long subtract(@Param("minuend") int m, @Param("subtrahend") int s) {
        return (long) m - (long) s;
    }

    @External
    public long sum(@Param("first") int first, @Param("second") int second,
                    @Param("third") int third) {
        return (long) first + (long) second + (long) third;
    }

    @External
    public void update(@Param("i1") int i1, @Param("i2") int i2,
                       @Param("i3") int i3, @Param("i4") int i4,
                       @Param("i5") int i5) {
        System.out.println("Update");
    }

    @External("get_data")
    public Object[] getData() {
        return new Object[]{"hello", 5};
    }
}
