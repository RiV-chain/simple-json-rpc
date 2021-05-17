package com.github.arteam.simplejsonrpc.client.object;

import com.github.arteam.simplejsonrpc.client.JsonRpcParams;
import com.github.arteam.simplejsonrpc.client.ParamsType;
import com.github.arteam.simplejsonrpc.client.domain.Player;
import com.github.arteam.simplejsonrpc.core.annotation.External;
import com.github.arteam.simplejsonrpc.core.annotation.Param;
import com.github.arteam.simplejsonrpc.core.annotation.Contract;

import java.util.List;

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
    long login(@Param("login") String login, @Param("password") String password);

    @External
    void logout(@Param("token") String token);
}
