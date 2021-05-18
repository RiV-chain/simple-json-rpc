package com.github.arteam.simplejsonrpc.client.object;

import com.github.arteam.simplejsonrpc.client.JsonRpcId;
import com.github.arteam.simplejsonrpc.client.JsonRpcParams;
import com.github.arteam.simplejsonrpc.client.ParamsType;
import com.github.arteam.simplejsonrpc.client.domain.Player;
import com.github.arteam.simplejsonrpc.client.domain.Position;
import com.github.arteam.simplejsonrpc.client.domain.Team;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.riv.annotations.Contract;
import org.riv.annotations.External;
import org.riv.annotations.OptionalParameter;
import org.riv.annotations.Parameter;

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
    boolean add(@Parameter("player") Player s);

    @External("find_by_birth_year")
    List<Player> findByBirthYear(@Parameter("birth_year") int birthYear);

    @External
    Player findByInitials(@Parameter("firstName") String firstName,
                          @Parameter("lastName") String lastName);

    @External("findByInitials")
    Optional<Player> optionalFindByInitials(@Parameter("firstName") String firstName,
                                            @Parameter("lastName") String lastName);

    @External
    List<Player> find(@OptionalParameter @Parameter("position") @Nullable Position position,
                      @OptionalParameter @Parameter("number") int number,
                      @OptionalParameter @Parameter("team") @NotNull Optional<Team> team,
                      @OptionalParameter @Parameter("firstName") @Nullable String firstName,
                      @OptionalParameter @Parameter("lastName") @Nullable String lastName,
                      @OptionalParameter @Parameter("birthDate") @Nullable Date birthDate,
                      @OptionalParameter @Parameter("capHit") @NotNull Optional<Double> capHit);

    @External
    Player findByCapHit(@Parameter("cap") double capHit);

    @External
    List<Player> findPlayersByFirstNames(@Parameter("names") List<String> names);

    @External
    List<Player> findPlayersByNumbers(@Parameter("numbers") int... numbers);

    @External
    <T> List<Player> genericFindPlayersByNumbers(@Parameter("numbers") T... numbers);

    @External
    LinkedHashMap<String, Double> getContractSums(@Parameter("contractLengths") Map<String, ? extends Number> contractLengths);
}
