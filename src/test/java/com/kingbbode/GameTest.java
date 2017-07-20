package com.kingbbode;

import com.kingbbode.game.Game;
import com.kingbbode.game.GameConsoleMessager;
import com.kingbbode.game.exceptions.AlreadyJoinException;
import com.kingbbode.game.exceptions.TooManyPlayerException;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by YG on 2017-07-20.
 */
public class GameTest {
    Game game;

    @Before
    public void setUp() throws Exception {
        game = new Game(new GameConsoleMessager());
    }

    @Test(expected = AlreadyJoinException.class)
    public void 이미_참여한_인원이_또_참여시_Exception이_발생하는가() throws Exception {
        game.join("1", "권용근", 1000);
        game.join("1", "권용근", 1000);
    }

    @Test(expected = TooManyPlayerException.class)
    public void 너무_많은_인원이_참여시_Exception이_발생하는가() throws Exception {
        game.join("1", "권용근", 1000);
        game.join("2", "강홍구", 1000);
        game.join("3", "김영재", 1000);
        game.join("4", "이동욱", 1000);
        game.join("5", "김태기", 1000);
    }

    @Test
    public void 풀_시나리오_테스트() throws Exception {
        game.join("1", "권용근", 1000);
        game.join("2", "강홍구", 1000);
        game.join("3", "김영재", 1000);
        game.join("4", "이동욱", 1000);
        game.play("1", "시작");
        
        game.play("1", "콜");
        game.play("2", "콜");
        game.play("3", "콜");
        game.play("4", "콜");

        game.play("1", "콜");
        game.play("2", "콜");
        game.play("3", "콜");
        game.play("4", "콜");

        game.play("1", "콜");
        game.play("2", "콜");
        game.play("3", "콜");
        game.play("4", "콜");

        game.play("1", game.getPlayers().get("1").getAvailableResult().get(new Random().nextInt(game.getPlayers().get("1").getAvailableResult().size())).getName());
        game.play("2", game.getPlayers().get("2").getAvailableResult().get(new Random().nextInt(game.getPlayers().get("2").getAvailableResult().size())).getName());
        game.play("3", game.getPlayers().get("3").getAvailableResult().get(new Random().nextInt(game.getPlayers().get("3").getAvailableResult().size())).getName());
        game.play("4", game.getPlayers().get("4").getAvailableResult().get(new Random().nextInt(game.getPlayers().get("4").getAvailableResult().size())).getName());
        
        assertThat(game.isEnd(), is(true));
    }
}
