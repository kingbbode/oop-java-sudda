package com.kingbbode;

import com.kingbbode.game.GameConsoleMessager;
import com.kingbbode.game.GameMessengerInterface;
import com.kingbbode.game.domain.Dealer;
import com.kingbbode.game.domain.Mc;
import com.kingbbode.game.domain.Player;
import com.kingbbode.game.enums.Batting;
import com.kingbbode.game.enums.Scenario;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by YG on 2017-07-20.
 */
public class McTest {

    Mc mc;
    Dealer dealer;
    List<Player> players;
    Scenario scenario;
    
    @Before
    public void setUp() throws Exception {
        GameMessengerInterface gameMessengerInterface = new GameConsoleMessager();
        mc = new Mc(gameMessengerInterface);
        dealer = new Dealer(gameMessengerInterface, new HashMap<>());
        players = new ArrayList<>();
        players.add(new Player("1", "권용근", 10000));
        players.add(new Player("2", "강홍구", 10000));
        players.add(new Player("3", "김영재", 10000));
        players.add(new Player("4", "이동욱", 10000));
        dealer.introduce(players);
        scenario = mc.introduce(dealer, players);
    }

    @Test
    public void 소개_테스트_진행상태로_변경되면서_최초_플레이어를_잘_가져오는가() throws Exception {
        assertThat(scenario, is(Scenario.DOING));
        assertThat(mc.whoIsCurrentPlayer(), is(players.get(0)));
    }

    @Test
    public void STEP_진행이_잘_되는가_시나리오_유지_플레이어_변경() throws Exception {
        scenario = mc.notifyBatting(mc.whoIsCurrentPlayer(), Batting.CALL);
        assertThat(scenario, is(Scenario.DOING));
        assertThat(mc.whoIsCurrentPlayer(), is(players.get(1)));
    }

    @Test
    public void 턴_종료시_패를_돌리고_순서가_보장되는가() throws Exception {
        scenario = mc.notifyBatting(mc.whoIsCurrentPlayer(), Batting.CALL);
        scenario = mc.notifyBatting(mc.whoIsCurrentPlayer(), Batting.CALL);
        scenario = mc.notifyBatting(mc.whoIsCurrentPlayer(), Batting.CALL);
        scenario = mc.notifyBatting(mc.whoIsCurrentPlayer(), Batting.CALL);
        assertThat(scenario, is(Scenario.DOING));
        assertThat(mc.whoIsCurrentPlayer(), is(players.get(0)));
        players.forEach(player -> {
            assertThat(player.getDeck().size(), is(2));
        });
    }

    @Test
    public void 게임_종료시_시나리오가_잘_변경되는가() throws Exception {
        scenario = mc.notifyBatting(mc.whoIsCurrentPlayer(), Batting.CALL);
        scenario = mc.notifyBatting(mc.whoIsCurrentPlayer(), Batting.CALL);
        scenario = mc.notifyBatting(mc.whoIsCurrentPlayer(), Batting.CALL);
        scenario = mc.notifyBatting(mc.whoIsCurrentPlayer(), Batting.CALL);
        scenario = mc.notifyBatting(mc.whoIsCurrentPlayer(), Batting.CALL);
        scenario = mc.notifyBatting(mc.whoIsCurrentPlayer(), Batting.CALL);
        scenario = mc.notifyBatting(mc.whoIsCurrentPlayer(), Batting.CALL);
        scenario = mc.notifyBatting(mc.whoIsCurrentPlayer(), Batting.CALL);
        scenario = mc.notifyBatting(mc.whoIsCurrentPlayer(), Batting.CALL);
        scenario = mc.notifyBatting(mc.whoIsCurrentPlayer(), Batting.CALL);
        scenario = mc.notifyBatting(mc.whoIsCurrentPlayer(), Batting.CALL);
        scenario = mc.notifyBatting(mc.whoIsCurrentPlayer(), Batting.CALL);
        assertThat(scenario, is(Scenario.CHOICE));
    }

    @Test
    public void 죽은_유저가_있을_때도_순서가_보장되는가() throws Exception {
        players.get(1).batting(Batting.DIE);
        scenario = mc.notifyBatting(mc.whoIsCurrentPlayer(), Batting.CALL);
        assertThat(scenario, is(Scenario.DOING));
        assertThat(mc.whoIsCurrentPlayer(), is(players.get(2)));
    }

    @Test
    public void 올인한_유저가_있을_때도_순서가_보장되는가() throws Exception {
        players.get(1).batting(Batting.HALF);
        players.get(1).batting(Batting.HALF);
        players.get(1).batting(Batting.HALF);
        players.get(1).batting(Batting.HALF);
        players.get(1).batting(Batting.HALF);
        players.get(1).batting(Batting.HALF);
        players.get(1).batting(Batting.HALF);
        players.get(1).batting(Batting.HALF);
        players.get(1).batting(Batting.HALF);
        players.get(1).batting(Batting.HALF);
        players.get(1).batting(Batting.HALF);
        players.get(1).batting(Batting.HALF);
        assertThat(players.get(1).isAllIn(), is(true));
        scenario = mc.notifyBatting(mc.whoIsCurrentPlayer(), Batting.CALL);
        assertThat(scenario, is(Scenario.DOING));
        assertThat(mc.whoIsCurrentPlayer(), is(players.get(2)));
    }

    @Test
    public void 한명을_제외하고_모두_올인이_되었을_때_시나리오가_바로_결과_시나리오로_변경되는가() throws Exception {
        players.get(0).batting(Batting.CALL);
        scenario = mc.notifyBatting(mc.whoIsCurrentPlayer(), Batting.CALL);
        players.get(1).batting(Batting.DIE);
        scenario = mc.notifyBatting(mc.whoIsCurrentPlayer(), Batting.DIE);
        players.get(2).batting(Batting.DIE);
        scenario = mc.notifyBatting(mc.whoIsCurrentPlayer(), Batting.DIE);
        players.get(3).batting(Batting.DIE);
        scenario = mc.notifyBatting(mc.whoIsCurrentPlayer(), Batting.DIE);
        assertThat(scenario, is(Scenario.RESULT));
    }
}
