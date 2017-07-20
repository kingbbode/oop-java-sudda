package com.kingbbode;

import com.kingbbode.game.GameConsoleMessager;
import com.kingbbode.game.domain.Dealer;
import com.kingbbode.game.domain.Player;
import com.kingbbode.game.enums.Batting;
import com.kingbbode.game.enums.Card;
import com.kingbbode.game.enums.Pedigree;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by YG on 2017-07-20.
 */
public class DealerTest {

    Dealer dealer;
    List<Player> players;
    
    @Before
    public void setUp() throws Exception {
        dealer = new Dealer(new GameConsoleMessager(), new HashMap<>());
        players = new ArrayList<>();
        players.add(new Player("1", "권용근", 10000));
        players.add(new Player("2", "강홍구", 10000));
        players.add(new Player("3", "김영재", 10000));
        players.add(new Player("4", "이동욱", 10000));
        dealer.introduce(players);
        dealer.batting(dealer.getBatMoney(Batting.CALL));
        dealer.batting(dealer.getBatMoney(Batting.CALL));
        dealer.batting(dealer.getBatMoney(Batting.CALL));
    }

    @Test
    public void 소개_테스트() throws Exception {
        players.forEach(player -> assertThat(player.getDealer().equals(dealer), is(true)));
    }

    @Test
    public void 카드_분배_테스트() throws Exception {
        dealer.distributeCard();
        List<Card> cards = new ArrayList<>();
        players.forEach(player -> {
            assertThat(player.getDeck().size(), is(1));
            assertThat(cards.contains(player.getDeck().get(0)), is(false));
            cards.add(player.getDeck().get(0));
        });
    }

    @Test
    public void 배팅_테스트() throws Exception {
        assertThat(dealer.getPane().getTotalBatMoney(), is(400));
        assertThat(dealer.getPane().getBeforeBatMoney(), is(100));
        players.get(0).batting(Batting.HALF);
        assertThat(dealer.getPane().getTotalBatMoney(), is(600));
        assertThat(dealer.getPane().getBeforeBatMoney(), is(200));
        assertThat(players.get(0).getGameMoney(), is(9800));
        System.out.println(dealer.getBatTableInfo());
    }

    @Test
    public void 결과_수집_전_DIE_사전세팅_테스트() throws Exception {
        dealer.distributeCard();
        dealer.distributeCard();
        dealer.distributeCard();
        players.get(1).batting(Batting.DIE);
        dealer.distributeResult();
        assertThat(dealer.getResults().get(players.get(1)), is(Pedigree.DIE));
    }

    @Test
    public void 승리자_도출_테스트() throws Exception {
        Map<Player, Pedigree> results = new HashMap<>();
        results.put(players.get(0), Pedigree.DDANG_GWANG_38);
        results.put(players.get(1), Pedigree.DDANG3);
        results.put(players.get(2), Pedigree.KKEUT3);
        results.put(players.get(3), Pedigree.AMHENG);
        dealer = new Dealer(new GameConsoleMessager(), results);
        dealer.introduce(players);
        dealer.batting(dealer.getBatMoney(Batting.CALL));
        dealer.batting(dealer.getBatMoney(Batting.CALL));
        dealer.batting(dealer.getBatMoney(Batting.CALL));
        assertThat(dealer.chooseVictor(), is(players.get(0)));
        results = new HashMap<>();
        results.put(players.get(0), Pedigree.DDANG_GWANG_18);
        results.put(players.get(1), Pedigree.DDANG3);
        results.put(players.get(2), Pedigree.KKEUT3);
        results.put(players.get(3), Pedigree.AMHENG);
        dealer = new Dealer(new GameConsoleMessager(), results);
        dealer.introduce(players);
        dealer.batting(dealer.getBatMoney(Batting.CALL));
        dealer.batting(dealer.getBatMoney(Batting.CALL));
        dealer.batting(dealer.getBatMoney(Batting.CALL));
        assertThat(dealer.chooseVictor(), is(players.get(3)));
    }

    @Test
    public void 단일_보상_테스트() throws Exception {
        dealer.award(players.get(0));
        assertThat(players.get(0).getGameMoney(), is(10400));
    }

    @Test
    public void 공통_분배_테스트() throws Exception {
        dealer.distributeBatMoney();
        assertThat(players.get(0).getGameMoney(), is(10100));
    }
    
    @Test
    public void 무승부_테스트_스코어() throws Exception {
        Map<Player, Pedigree> results = new HashMap<>();
        results.put(players.get(0), Pedigree.DDANG3);
        results.put(players.get(1), Pedigree.DDANG3);
        results.put(players.get(2), Pedigree.KKEUT3);
        results.put(players.get(3), Pedigree.AMHENG);
        dealer = new Dealer(new GameConsoleMessager(), results);
        assertThat(dealer.chooseVictor(), nullValue());
    }

    @Test
    public void 무승부_테스트_특수_케이스() throws Exception {
        Map<Player, Pedigree> results = new HashMap<>();
        results.put(players.get(0), Pedigree.DDANG_GWANG_13);
        results.put(players.get(1), Pedigree.KKEUT3);
        results.put(players.get(2), Pedigree.AMHENG);
        results.put(players.get(3), Pedigree.AMHENG);
        dealer = new Dealer(new GameConsoleMessager(), results);
        assertThat(dealer.chooseVictor(), nullValue());
    }
}
