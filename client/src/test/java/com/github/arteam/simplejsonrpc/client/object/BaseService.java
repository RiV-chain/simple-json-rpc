package com.github.arteam.simplejsonrpc.client.object;

import com.github.arteam.simplejsonrpc.client.JsonRpcParams;
import com.github.arteam.simplejsonrpc.client.ParamsType;
import com.github.arteam.simplejsonrpc.client.domain.Player;

import java.util.List;

import org.riv.annotations.Contract;
import org.riv.annotations.External;
import org.riv.annotations.Parameter;

/**
 * Date: 11/17/14
 * Time: 9:59 PM
 */
@Contract
public interface BaseService {

    @External
    @JsonRpcParams(ParamsType.ARRAY)
    List<Player> getPlayers();

    @External
    @JsonRpcParams(ParamsType.ARRAY)
    Player getPlayer();

    @External
    long login(@Parameter("login") String login, @Parameter("password") String password);

    @External
    void logout(@Parameter("token") String token);
}
