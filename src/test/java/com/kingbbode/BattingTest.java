package com.kingbbode;

import com.kingbbode.game.enums.Batting;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by YG on 2017-07-20.
 */
public class BattingTest {
    @Test
    public void 배팅_테스트() throws Exception {
        assertThat(Batting.DIE.calculate(300, 1000), is(0));
        assertThat(Batting.CALL.calculate(300, 1000), is(300));
        assertThat(Batting.HALF.calculate(300, 1000), is(500));
        assertThat(Batting.QUETER.calculate(300, 1000), is(250));
        assertThat(Batting.DDADANG.calculate(300, 1000), is(600));
        assertThat(Batting.CHECK.calculate(300, 1000), is(0));
        assertThat(Batting.BBING.calculate(300, 1000), is(100));
    }
}
