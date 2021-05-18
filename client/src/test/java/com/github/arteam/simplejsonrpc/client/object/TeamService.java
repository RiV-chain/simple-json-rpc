package com.github.arteam.simplejsonrpc.client.object;

import com.github.arteam.simplejsonrpc.client.JsonRpcId;
import com.github.arteam.simplejsonrpc.client.JsonRpcParams;
import com.github.arteam.simplejsonrpc.client.ParamsType;
import com.github.arteam.simplejsonrpc.client.domain.Player;
import com.github.arteam.simplejsonrpc.client.domain.Position;
import com.github.arteam.simplejsonrpc.client.domain.Team;
import com.github.arteam.simplejsonrpc.core.annotation.External;
import com.github.arteam.simplejsonrpc.core.annotation.JsonRpcOptional;
import com.github.arteam.simplejsonrpc.core.annotation.JsonRpcParam;
import com.github.arteam.simplejsonrpc.core.annotation.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Date: 24.08.14
 * Time: 18:02
 */
@Contract
@JsonRpcId(TestIdGenerator.class)
@JsonRpcParams(ParamsType.MAP)
public interface TeamService extends BaseService {

    @External
    boolean add(@JsonRpcParam("player") Player s);

    @External("find_by_birth_year")
    List<Player> findByBirthYear(@JsonRpcParam("birth_year") int birthYear);

    @External
    Player findByInitials(@JsonRpcParam("firstName") String firstName,
                          @JsonRpcParam("lastName") String lastName);

    @External("findByInitials")
    Optional<Player> optionalFindByInitials(@JsonRpcParam("firstName") String firstName,
                                            @JsonRpcParam("lastName") String lastName);

    @External
    List<Player> find(@JsonRpcOptional @JsonRpcParam("position") @Nullable Position position,
                      @JsonRpcOptional @JsonRpcParam("number") int number,
                      @JsonRpcOptional @JsonRpcParam("team") @NotNull Optional<Team> team,
                      @JsonRpcOptional @JsonRpcParam("firstName") @Nullable String firstName,
                      @JsonRpcOptional @JsonRpcParam("lastName") @Nullable String lastName,
                      @JsonRpcOptional @JsonRpcParam("birthDate") @Nullable Date birthDate,
                      @JsonRpcOptional @JsonRpcParam("capHit") @NotNull Optional<Double> capHit);

    @External
    Player findByCapHit(@JsonRpcParam("cap") double capHit);

    @External
    List<Player> findPlayersByFirstNames(@JsonRpcParam("names") List<String> names);

    @External
    List<Player> findPlayersByNumbers(@JsonRpcParam("numbers") int... numbers);

    @External
    <T> List<Player> genericFindPlayersByNumbers(@JsonRpcParam("numbers") T... numbers);

    @External
    LinkedHashMap<String, Double> getContractSums(@JsonRpcParam("contractLengths") Map<String, ? extends Number> contractLengths);
}
