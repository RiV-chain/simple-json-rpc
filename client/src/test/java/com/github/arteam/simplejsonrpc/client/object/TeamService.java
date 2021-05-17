package com.github.arteam.simplejsonrpc.client.object;

import com.github.arteam.simplejsonrpc.client.JsonRpcId;
import com.github.arteam.simplejsonrpc.client.JsonRpcParams;
import com.github.arteam.simplejsonrpc.client.ParamsType;
import com.github.arteam.simplejsonrpc.client.domain.Player;
import com.github.arteam.simplejsonrpc.client.domain.Position;
import com.github.arteam.simplejsonrpc.client.domain.Team;
import com.github.arteam.simplejsonrpc.core.annotation.External;
import com.github.arteam.simplejsonrpc.core.annotation.JsonRpcOptional;
import com.github.arteam.simplejsonrpc.core.annotation.Param;
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
    boolean add(@Param("player") Player s);

    @External("find_by_birth_year")
    List<Player> findByBirthYear(@Param("birth_year") int birthYear);

    @External
    Player findByInitials(@Param("firstName") String firstName,
                          @Param("lastName") String lastName);

    @External("findByInitials")
    Optional<Player> optionalFindByInitials(@Param("firstName") String firstName,
                                            @Param("lastName") String lastName);

    @External
    List<Player> find(@JsonRpcOptional @Param("position") @Nullable Position position,
                      @JsonRpcOptional @Param("number") int number,
                      @JsonRpcOptional @Param("team") @NotNull Optional<Team> team,
                      @JsonRpcOptional @Param("firstName") @Nullable String firstName,
                      @JsonRpcOptional @Param("lastName") @Nullable String lastName,
                      @JsonRpcOptional @Param("birthDate") @Nullable Date birthDate,
                      @JsonRpcOptional @Param("capHit") @NotNull Optional<Double> capHit);

    @External
    Player findByCapHit(@Param("cap") double capHit);

    @External
    List<Player> findPlayersByFirstNames(@Param("names") List<String> names);

    @External
    List<Player> findPlayersByNumbers(@Param("numbers") int... numbers);

    @External
    <T> List<Player> genericFindPlayersByNumbers(@Param("numbers") T... numbers);

    @External
    LinkedHashMap<String, Double> getContractSums(@Param("contractLengths") Map<String, ? extends Number> contractLengths);
}
