package com.kingbbode;

import com.kingbbode.game.GameConsoleMessager;
import com.kingbbode.game.GameMessengerInterface;
import com.kingbbode.game.domain.Dealer;
import com.kingbbode.game.domain.Mc;
import com.kingbbode.game.domain.Player;
import com.kingbbode.game.enums.Scenario;
import com.kingbbode.game.exceptions.NotEnoughPlayerException;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by YG on 2017-07-20.
 */
public class ScenarioTest {
    Mc mc;
    Dealer dealer;
    Map<String, Player> players;
    Scenario scenario;
    
    @Before
    public void setUp() throws Exception {
        GameMessengerInterface gameMessengerInterface = new GameConsoleMessager();
        mc = new Mc(gameMessengerInterface);
        dealer = new Dealer(gameMessengerInterface, new HashMap<>());
        players = new HashMap<>();
        players.put("1" ,new Player("1", "권용근", 400));
        players.put("2", new Player("2", "강홍구", 400));
        players.put("3", new Player("3", "김영재", 400));
        players.put("4", new Player("4", "이동욱", 400));
    }

    @Test
    public void 정해진_명령어_이외에는_무시하는가() throws Exception {
        scenario = Scenario.READY.play(mc, dealer, players, players.get("1"), "테테테");
        assertThat(scenario, not(Scenario.DOING));
        scenario = Scenario.READY.play(mc, dealer, players, players.get("1"), "시작");
        assertThat(scenario, is(Scenario.DOING));
    }

    @Test
    public void READY_본인의_순서가_아니면_무시하는가() throws Exception {
        scenario = Scenario.READY.play(mc, dealer, players, players.get("1"), "시작");
        scenario = scenario.play(mc, dealer, players, players.get("2"), "콜");
        assertThat(players.get("2").getGameMoney(), is(400));
    }

    @Test(expected = NotEnoughPlayerException.class)
    public void READY_인원_불충분일_때_Exception이_발생하는가() throws Exception {
        GameMessengerInterface gameMessengerInterface = new GameConsoleMessager();
        mc = new Mc(gameMessengerInterface);
        dealer = new Dealer(gameMessengerInterface, new HashMap<>());
        players = new HashMap<>();
        players.put("1" ,new Player("1", "권용근", 400));
        scenario = Scenario.READY.play(mc, dealer, players, players.get("1"), "시작");
    }

    @Test
    public void 풀_시나리오_테스트() throws Exception {
        scenario = Scenario.READY.play(mc, dealer, players, players.get("1"), "시작");
        assertThat(scenario, is(Scenario.DOING));
        scenario = scenario.play(mc, dealer, players, players.get("1"), "콜");
        scenario = scenario.play(mc, dealer, players, players.get("2"), "콜");
        scenario = scenario.play(mc, dealer, players, players.get("3"), "콜");
        scenario = scenario.play(mc, dealer, players, players.get("4"), "콜");
        assertThat(scenario, is(Scenario.DOING));
        scenario = scenario.play(mc, dealer, players, players.get("1"), "콜");
        scenario = scenario.play(mc, dealer, players, players.get("2"), "콜");
        scenario = scenario.play(mc, dealer, players, players.get("3"), "콜");
        scenario = scenario.play(mc, dealer, players, players.get("4"), "콜");
        assertThat(scenario, is(Scenario.DOING));
        scenario = scenario.play(mc, dealer, players, players.get("1"), "콜");
        scenario = scenario.play(mc, dealer, players, players.get("2"), "콜");
        scenario = scenario.play(mc, dealer, players, players.get("3"), "콜");
        scenario = scenario.play(mc, dealer, players, players.get("4"), "콜");
        assertThat(scenario, is(Scenario.CHOICE));
        scenario = scenario.play(mc, dealer, players, players.get("1"), players.get("1").getAvailableResult().get(new Random().nextInt(players.get("1").getAvailableResult().size())).getName());
        scenario = scenario.play(mc, dealer, players, players.get("2"), players.get("2").getAvailableResult().get(new Random().nextInt(players.get("2").getAvailableResult().size())).getName());
        scenario = scenario.play(mc, dealer, players, players.get("3"), players.get("3").getAvailableResult().get(new Random().nextInt(players.get("3").getAvailableResult().size())).getName());
        scenario = scenario.play(mc, dealer, players, players.get("4"), players.get("4").getAvailableResult().get(new Random().nextInt(players.get("4").getAvailableResult().size())).getName());
        assertThat(scenario, is(Scenario.RESULT));
        scenario = scenario.play(mc, dealer, players, null, "");
        assertThat(scenario, is(Scenario.END));
    }

    @Test
    public void 다이_시나리오_테스트() throws Exception {
        scenario = Scenario.READY.play(mc, dealer, players, players.get("1"), "시작");
        assertThat(scenario, is(Scenario.DOING));
        scenario = scenario.play(mc, dealer, players, players.get("1"), "콜");
        scenario = scenario.play(mc, dealer, players, players.get("2"), "다이");
        scenario = scenario.play(mc, dealer, players, players.get("3"), "콜");
        scenario = scenario.play(mc, dealer, players, players.get("4"), "콜");
        assertThat(scenario, is(Scenario.DOING));
        scenario = scenario.play(mc, dealer, players, players.get("1"), "콜");
        scenario = scenario.play(mc, dealer, players, players.get("3"), "다이");
        scenario = scenario.play(mc, dealer, players, players.get("4"), "콜");
        assertThat(scenario, is(Scenario.DOING));
        scenario = scenario.play(mc, dealer, players, players.get("1"), "콜");
        scenario = scenario.play(mc, dealer, players, players.get("4"), "다이");
        assertThat(scenario, is(Scenario.RESULT));
        scenario = scenario.play(mc, dealer, players, null, "");
        assertThat(scenario, is(Scenario.END));
    }

    @Test
    public void 올인_시나리오_테스트() throws Exception {
        scenario = Scenario.READY.play(mc, dealer, players, players.get("1"), "시작");
        assertThat(scenario, is(Scenario.DOING));
        scenario = scenario.play(mc, dealer, players, players.get("1"), "따당");
        scenario = scenario.play(mc, dealer, players, players.get("2"), "따당");
        scenario = scenario.play(mc, dealer, players, players.get("3"), "따당");
        scenario = scenario.play(mc, dealer, players, players.get("4"), "따당");
        assertThat(scenario, is(Scenario.DOING));
        scenario = scenario.play(mc, dealer, players, players.get("1"), "따당");
        scenario = scenario.play(mc, dealer, players, players.get("2"), "따당");
        scenario = scenario.play(mc, dealer, players, players.get("3"), "따당");
        scenario = scenario.play(mc, dealer, players, players.get("4"), "따당");
        assertThat(scenario, is(Scenario.CHOICE));
        scenario = scenario.play(mc, dealer, players, players.get("1"), players.get("1").getAvailableResult().get(new Random().nextInt(players.get("1").getAvailableResult().size())).getName());
        scenario = scenario.play(mc, dealer, players, players.get("2"), players.get("2").getAvailableResult().get(new Random().nextInt(players.get("2").getAvailableResult().size())).getName());
        scenario = scenario.play(mc, dealer, players, players.get("3"), players.get("3").getAvailableResult().get(new Random().nextInt(players.get("3").getAvailableResult().size())).getName());
        scenario = scenario.play(mc, dealer, players, players.get("4"), players.get("4").getAvailableResult().get(new Random().nextInt(players.get("4").getAvailableResult().size())).getName());
        assertThat(scenario, is(Scenario.RESULT));
        scenario = scenario.play(mc, dealer, players, null, "");
        assertThat(scenario, is(Scenario.END));
    }
}
