package com.github.arteam.simplejsonrpc.server.simple.service;

import com.github.arteam.simplejsonrpc.server.simple.domain.Player;
import com.github.arteam.simplejsonrpc.server.simple.domain.Position;
import com.github.arteam.simplejsonrpc.server.simple.domain.Team;
import com.github.arteam.simplejsonrpc.server.simple.exception.EmptyMessageTeamServiceException;
import com.github.arteam.simplejsonrpc.server.simple.exception.ExceptionWithDataField;
import com.github.arteam.simplejsonrpc.server.simple.exception.ExceptionWithDataGetter;
import com.github.arteam.simplejsonrpc.server.simple.exception.ExceptionWithDataMultipleFields;
import com.github.arteam.simplejsonrpc.server.simple.exception.ExceptionWithDataMultipleGetters;
import com.github.arteam.simplejsonrpc.server.simple.exception.ExceptionWithDataMultipleMixed;
import com.github.arteam.simplejsonrpc.server.simple.exception.ExceptionWithWrongMethods;
import com.github.arteam.simplejsonrpc.server.simple.exception.TeamServiceAuthException;
import com.google.common.collect.Maps;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.riv.annotations.Contract;
import org.riv.annotations.External;
import org.riv.annotations.OptionalParameter;
import org.riv.annotations.Parameter;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Date: 7/27/14
 * Time: 8:46 PM
 */
@Contract
public class TeamService extends BaseService {

    private static final DateTimeFormatter fmt = ISODateTimeFormat.date().withZone(DateTimeZone.UTC);

    private final List<Player> players = Stream.of(
            new Player("David", "Backes", new Team("St. Louis Blues", "NHL"), 42, Position.CENTER, date("1984-05-01"), 4.5),
            new Player("Vladimir", "Tarasenko", new Team("St. Louis Blues", "NHL"), 91, Position.RIGHT_WINGER, date("1991-12-13"), 0.9),
            new Player("Jack", "Allen", new Team("St. Louis Blues", "NHL"), 34, Position.GOALTENDER, date("1990-08-07"), 0.5),
            new Player("Jay", "Bouwmeester", new Team("St. Louis Blues", "NHL"), 19, Position.DEFENDER, date("1985-08-07"), 5.4),
            new Player("Steven", "Stamkos", new Team("Tampa Bay Lightning", "NHL"), 91, Position.CENTER, date("1990-02-07"), 7.5),
            new Player("Ryan", "Callahan", new Team("Tampa Bay Lightning", "NHL"), 24, Position.RIGHT_WINGER, date("1985-03-21"), 5.8),
            new Player("Ben", "Bishop", new Team("Tampa Bay Lightning", "NHL"), 30, Position.GOALTENDER, date("1986-11-21"), 2.3),
            new Player("Victor", "Hedman", new Team("Tampa Bay Lightning", "NHL"), 77, Position.DEFENDER, date("1990-12-18"), 4.0))
            .collect(Collectors.toList());

    @External
    public boolean add(@Parameter("player") Player s) {
        return players.add(s);
    }

    @External("find_by_birth_year")
    public List<Player> findByBirthYear(@Parameter("birth_year") final int birthYear) {
        return players.stream().filter(player -> {
            int year = new DateTime(player.getBirthDate()).getYear();
            return year == birthYear;
        }).collect(Collectors.toList());
    }

    @External
    public Player findByInitials(@Parameter("firstName") final String firstName,
                                 @Parameter("lastName") final String lastName) {
        return players.stream()
                .filter(player -> player.getFirstName().equals(firstName) && player.getLastName().equals(lastName))
                .findAny()
                .orElse(null);
    }

    @External
    public List<Player> find(@OptionalParameter @Parameter("position") @Nullable Position position,
                             @OptionalParameter @Parameter("number") @Nullable int number,
                             @OptionalParameter @Parameter("team") @NotNull Optional<Team> team,
                             @OptionalParameter @Parameter("firstName") @Nullable String firstName,
                             @OptionalParameter @Parameter("lastName") @Nullable String lastName,
                             @OptionalParameter @Parameter("birthDate") @Nullable Date birthDate,
                             @OptionalParameter @Parameter("capHit") @NotNull Optional<Double> capHit) {
        return players.stream().filter(player -> {
            if (position != null && !player.getPosition().equals(position)) return false;
            if (number != 0 && player.getNumber() != number) return false;
            if (team.isPresent() && !player.getTeam().equals(team.get())) return false;
            if (firstName != null && !player.getFirstName().equals(firstName)) return false;
            if (lastName != null && !player.getLastName().equals(lastName)) return false;
            if (birthDate != null && !player.getBirthDate().equals(birthDate)) return false;
            if (capHit.isPresent() && player.getCapHit() != capHit.get()) return false;
            return true;
        }).collect(Collectors.toList());
    }

    @External
    public List<Player> getPlayers() {
        return players;
    }

    public List<Player> bogusGetPlayers() {
        return players;
    }

    @External
    private List<Player> privateGetPlayers() {
        return players;
    }

    @External
    public Player bogusFindByInitials(@Parameter("firstName") String firstName, String lastName) {
        return findByInitials(firstName, lastName);
    }

    @External
    public Player findByCapHit(@Parameter("cap") double capHit) {
        throw new IllegalStateException("Not implemented");
    }

    @External
    public long login(@Parameter("login") String login, @Parameter("password") String password) {
        if (!login.equals("CAFE") && !password.equals("BABE")) {
            throw new TeamServiceAuthException("Not authorized");
        }
        return 0xCAFEBABE;
    }

    @External
    public long bogusMessageLogin(@Parameter("login") String login, @Parameter("password") String password) {
        if (!login.equals("CAFE") && !password.equals("BABE")) {
            throw new EmptyMessageTeamServiceException("Not authorized");
        }
        return 0xCAFEBABE;
    }

    @External
    public long errorDataFieldLogin(@Parameter("login") String login, @Parameter("password") String password) {
        if (!login.equals("CAFE") && !password.equals("BABE")) {
            throw new ExceptionWithDataField("Detailed message", new String[]{"Data 1", "Data 2"});
        }
        return 0xCAFEBABE;
    }

    @External
    public long errorDataGetterLogin(@Parameter("login") String login, @Parameter("password") String password) {
        if (!login.equals("CAFE") && !password.equals("BABE")) {
            throw new ExceptionWithDataGetter("Detailed message", new String[]{"Data 1", "Data 2"});
        }
        return 0xCAFEBABE;
    }

    @External
    public long errorDataMultipleFieldsLogin(@Parameter("login") String login,
                                             @Parameter("password") String password) {
        if (!login.equals("CAFE") && !password.equals("BABE")) {
            throw new ExceptionWithDataMultipleFields(
                    "Detailed message",
                    new String[]{"Data 1", "Data 2"},
                    "AnotherData");
        }
        return 0xCAFEBABE;
    }

    @External
    public long errorDataMultipleGettersLogin(@Parameter("login") String login,
                                              @Parameter("password") String password) {
        if (!login.equals("CAFE") && !password.equals("BABE")) {
            throw new ExceptionWithDataMultipleGetters(
                    "Detailed message",
                    new String[]{"Data 1", "Data 2"},
                    "AnotherData");
        }
        return 0xCAFEBABE;
    }

    @External
    public long errorDataMultipleMixedLogin(@Parameter("login") String login,
                                            @Parameter("password") String password) {
        if (!login.equals("CAFE") && !password.equals("BABE")) {
            throw new ExceptionWithDataMultipleMixed(
                    "Detailed message",
                    new String[]{"Data 1", "Data 2"});
        }
        return 0xCAFEBABE;
    }

    @External
    public long errorDataWrongMethodsLogin(@Parameter("login") String login,
                                           @Parameter("password") String password) {
        if (!login.equals("CAFE") && !password.equals("BABE")) {
            throw new ExceptionWithWrongMethods(
                    "Detailed message",
                    new String[]{"Data 1", "Data 2"});
        }
        return 0xCAFEBABE;
    }

    @External
    public Player bogusFind(@Parameter("firstName") String firstName,
                            @Parameter("firstName") String lastName,
                            @Parameter("age") int age) {
        return null;
    }

    @External
    public List<Player> findPlayersByFirstNames(@Parameter("names") final List<String> names) {
        return players.stream().filter(player -> names.contains(player.getFirstName())).collect(Collectors.toList());
    }

    @External
    public List<Player> findPlayersByNumbers(@Parameter("numbers") final int... numbers) {
        return players.stream().filter(player -> {
            for (int number : numbers) {
                if (player.getNumber() == number) {
                    return true;
                }
            }
            return false;
        }).collect(Collectors.toList());
    }

    @External
    public <T> List<Player> genericFindPlayersByNumbers(@Parameter("numbers") final T... numbers) {
        return players.stream().filter(player -> {
            for (T number : numbers) {
                if (String.valueOf(player.getNumber()).equals(number.toString())) {
                    return true;
                }
            }
            return false;
        }).collect(Collectors.toList());
    }

    @External
    public Map<String, Double> getContractSums(@Parameter("contractLengths") Map<String, ? extends Number> contractLengths) {
        Map<String, Double> playerContractSums = Maps.newLinkedHashMap();
        for (Player player : players) {
            playerContractSums.put(player.getLastName(),
                    player.getCapHit() * contractLengths.get(player.getLastName()).intValue());
        }
        return playerContractSums;
    }


    @External
    public static Date date(@Parameter("textDate") String textDate) {
        return fmt.parseDateTime(textDate).toDate();
    }

}
