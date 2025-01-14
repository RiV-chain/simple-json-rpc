package com.github.arteam.simplejsonrpc.client;

import com.github.arteam.simplejsonrpc.client.domain.Player;
import com.github.arteam.simplejsonrpc.client.domain.Position;
import com.github.arteam.simplejsonrpc.client.domain.Team;
import com.github.arteam.simplejsonrpc.client.exception.JsonRpcException;
import com.github.arteam.simplejsonrpc.client.object.FixedIntegerIdGenerator;
import com.github.arteam.simplejsonrpc.client.object.FixedStringIdGenerator;
import com.github.arteam.simplejsonrpc.client.object.TeamService;
import com.github.arteam.simplejsonrpc.core.domain.ErrorMessage;
import org.joda.time.DateTimeZone;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.riv.annotations.Contract;
import org.riv.annotations.External;
import org.riv.annotations.Parameter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.zip.Checksum;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

/**
 * Date: 24.08.14
 * Time: 18:06
 */
public class JsonRpcObjectAPITest extends BaseClientTest {

    @Test
    public void testAddPlayer() {
        JsonRpcClient client = initClient("add_player");
        TeamService teamService = client.onDemand(TeamService.class, new FixedStringIdGenerator("asd671"));
        boolean result = teamService.add(new Player("Kevin", "Shattenkirk", new Team("St. Louis Blues", "NHL"), 22, Position.DEFENDER,
                ISODateTimeFormat.date().withZone(DateTimeZone.UTC).parseDateTime("1989-01-29").toDate(),
                4.25));
        assertThat(result).isTrue();
    }

    @Test
    public void findPlayerByInitials() {
        JsonRpcClient client = initClient("find_player");
        Player player = client.onDemand(TeamService.class, new FixedIntegerIdGenerator(43121)).findByInitials("Steven", "Stamkos");
        assertThat(player).isNotNull();
        assertThat(player.getFirstName()).isEqualTo("Steven");
        assertThat(player.getLastName()).isEqualTo("Stamkos");
    }

    @Test
    public void testPlayerIsNotFound() {
        JsonRpcClient client = initClient("player_is_not_found");
        Player player = client.onDemand(TeamService.class, new FixedIntegerIdGenerator(4111)).findByInitials("Vladimir", "Sobotka");
        assertThat(player).isNull();
    }

    @Test
    public void testOptionalParams() {
        JsonRpcClient client = initClient("optional_params");
        List<Player> players = client.onDemand(TeamService.class, new FixedStringIdGenerator("xar331"))
                .find(null, 91, Optional.of(new Team("St. Louis Blues", "NHL")), null, null, null, Optional.empty());
        Assertions.assertEquals(players.size(), 1);
        Player player = players.get(0);
        assertThat(player.getTeam()).isEqualTo(new Team("St. Louis Blues", "NHL"));
        assertThat(player.getNumber()).isEqualTo(91);
        assertThat(player.getFirstName()).isEqualTo("Vladimir");
        assertThat(player.getLastName()).isEqualTo("Tarasenko");
    }

    @Test
    public void testOptionalArray() {
        JsonRpcClient client = initClient("find_array_null_params");
        List<Player> players = client.onDemand(TeamService.class, ParamsType.ARRAY, new FixedStringIdGenerator("pasd81"))
                .find(null, 19, Optional.of(new Team("St. Louis Blues", "NHL")), null, null, null, Optional.empty());
        Assertions.assertEquals(players.size(), 1);
        Player player = players.get(0);
        assertThat(player.getTeam()).isEqualTo(new Team("St. Louis Blues", "NHL"));
        assertThat(player.getNumber()).isEqualTo(19);
        assertThat(player.getFirstName()).isEqualTo("Jay");
        assertThat(player.getLastName()).isEqualTo("Bouwmeester");
    }

    @Test
    public void testFindArray() {
        JsonRpcClient client = initClient("find_player_array");
        Player player = client.onDemand(TeamService.class, ParamsType.ARRAY, new FixedStringIdGenerator("dsfs1214"))
                .findByInitials("Ben", "Bishop");
        assertThat(player).isNotNull();
        assertThat(player.getFirstName()).isEqualTo("Ben");
        assertThat(player.getLastName()).isEqualTo("Bishop");
    }

    @Test
    public void testReturnList() {
        JsonRpcClient client = initClient("findByBirthYear");
        List<Player> players = client.onDemand(TeamService.class, new FixedIntegerIdGenerator(5621)).findByBirthYear(1990);
        assertThat(players).isNotNull();
        assertThat(players).hasSize(3);
        assertThat(players.get(0).getLastName()).isEqualTo("Allen");
        assertThat(players.get(1).getLastName()).isEqualTo("Stamkos");
        assertThat(players.get(2).getLastName()).isEqualTo("Hedman");
    }

    @Test
    public void testNoParams() {
        JsonRpcClient client = initClient("getPlayers");
        List<Player> players = client.onDemand(TeamService.class, new FixedIntegerIdGenerator(1000)).getPlayers();
        assertThat(players).isNotNull();
        assertThat(players).hasSize(3);
        assertThat(players.get(0).getLastName()).isEqualTo("Bishop");
        assertThat(players.get(1).getLastName()).isEqualTo("Tarasenko");
        assertThat(players.get(2).getLastName()).isEqualTo("Bouwmeester");
    }

    @Test
    public void testReturnVoid() {
        JsonRpcClient client = initClient("logout");
        client.onDemand(TeamService.class, new FixedIntegerIdGenerator(29314)).logout("fgt612");
    }

    @Test
    public void testMap() {
        Map<String, Integer> contractLengths = new LinkedHashMap<String, Integer>() {{
            put("Backes", 4);
            put("Tarasenko", 3);
            put("Allen", 2);
            put("Bouwmeester", 5);
            put("Stamkos", 8);
            put("Callahan", 3);
            put("Bishop", 4);
            put("Hedman", 2);
        }};
        JsonRpcClient client = initClient("getContractSums");
        Map<String, Double> contractSums = client.onDemand(TeamService.class, new FixedIntegerIdGenerator(97555))
                .getContractSums(contractLengths);
        assertThat(contractSums).isExactlyInstanceOf(LinkedHashMap.class);
        assertThat(contractSums).isEqualTo(new HashMap<String, Double>() {{
            put("Backes", 18.0);
            put("Tarasenko", 2.7);
            put("Allen", 1.0);
            put("Bouwmeester", 27.0);
            put("Stamkos", 60.0);
            put("Callahan", 17.4);
            put("Bishop", 9.2);
            put("Hedman", 8.0);
        }});
    }

    @Test
    public void testOptional() {
        JsonRpcClient client = initClient("player_is_not_found");
        Optional<Player> optionalPlayer = client.onDemand(TeamService.class, new FixedIntegerIdGenerator(4111))
                .optionalFindByInitials("Vladimir", "Sobotka");
        assertThat(optionalPlayer.isPresent()).isFalse();
    }

    @Test
    public void testJsonRpcError() {
        JsonRpcClient client = initClient("methodNotFound");
        try {
            client.onDemand(TeamService.class, new FixedIntegerIdGenerator(1001)).getPlayer();
            Assertions.fail();
        } catch (JsonRpcException e) {
            e.printStackTrace();
            ErrorMessage errorMessage = e.getErrorMessage();
            assertThat(errorMessage.getCode()).isEqualTo(-32601);
            assertThat(errorMessage.getMessage()).isEqualTo("Method not found");
        }
    }

    @Contract
    static interface BogusTeamService {

        @External
        void bogusLogin(String username, @Parameter("password") String password);
    }

    @Test
    public void testParameterIsNotAnnotated() {
        assertThatIllegalStateException()
                .isThrownBy(() -> fakeClient().onDemand(BogusTeamService.class).bogusLogin("super", "secret"))
                .withMessage("Parameter with index=0 of method 'bogusLogin' is not annotated with @JsonRpcParam");
    }

    @Test
    public void testNotJsonRpcMethod() {
        assertThatIllegalStateException()
                .isThrownBy(() -> fakeClient().onDemand(TeamService.class).equals("Test"))
                .withMessage("Method 'equals' is not JSON-RPC available");
    }

    @Contract
    public static interface MethodIsNotAnnotatedService {

        boolean find(@Parameter("name") String name);
    }

    @Test
    public void testMethodIsNotAnnotated() {
        assertThatIllegalStateException()
                .isThrownBy(() -> fakeClient().onDemand(MethodIsNotAnnotatedService.class).find("Logan"))
                .withMessage("Method 'find' is not annotated as @JsonRpcMethod");
    }

    @Test
    public void testServiceIsNotAnnotated() {
        assertThatIllegalStateException()
                .isThrownBy(() -> fakeClient().onDemand(Checksum.class).getValue())
                .withMessage("Class 'java.util.zip.Checksum' is not annotated as @JsonRpcService");
    }

    @Contract
    static interface DuplicateParametersService {

        @External
        boolean find(@Parameter("code") String username, @Parameter("code") String code, @Parameter("number") int number);
    }

    @Test
    public void testDuplicatedParameterNames() {
        assertThatIllegalStateException()
                .isThrownBy(() -> fakeClient().onDemand(DuplicateParametersService.class).find("Joe", "12", 21))
                .withMessage("Two parameters of method 'find' have the same name 'code'");
    }

}
