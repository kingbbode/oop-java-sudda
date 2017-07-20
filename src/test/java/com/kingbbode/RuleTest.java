package com.kingbbode;

import com.kingbbode.game.enums.Card;
import com.kingbbode.game.enums.Pedigree;
import com.kingbbode.game.enums.Rule;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by YG on 2017-07-17.
 */
public class RuleTest {
    @Test
    public void 카드_특수조합_테스트() throws Exception {
        assertThat(Rule.findAvailableResult(Arrays.asList(Card.THREE_1, Card.SEVEN_2)).contains(Pedigree.DDANG_KILLER), is(true));
        assertThat(Rule.findAvailableResult(Arrays.asList(Card.FORE_2, Card.SEVEN_2)).contains(Pedigree.AMHENG), is(true));
    }

    @Test
    public void 일반_끗_조합_테스트() throws Exception {
        assertThat(Rule.findAvailableResult(Arrays.asList(Card.FIVE_1, Card.SIX_1, Card.SEVEN_2)).contains(Pedigree.KKEUT1), is(true));
        assertThat(Rule.findAvailableResult(Arrays.asList(Card.FIVE_1, Card.SIX_1, Card.SEVEN_2)).contains(Pedigree.KKEUT2), is(true));
        assertThat(Rule.findAvailableResult(Arrays.asList(Card.FIVE_1, Card.SIX_1, Card.SEVEN_2)).contains(Pedigree.KKEUT3), is(true));
    }

    @Test
    public void 상위족보_끗_조합_테스트() throws Exception {
        assertThat(Rule.findAvailableResult(Arrays.asList(Card.FORE_2, Card.FIVE_1, Card.SIX_1)).contains(Pedigree.SERUEG), is(true));
        assertThat(Rule.findAvailableResult(Arrays.asList(Card.FORE_2, Card.FIVE_1, Card.SIX_1)).contains(Pedigree.KKEUT9), is(true));
        assertThat(Rule.findAvailableResult(Arrays.asList(Card.FORE_2, Card.FIVE_1, Card.SIX_1)).contains(Pedigree.KKEUT1), is(true));
        assertThat(Rule.findAvailableResult(Arrays.asList(Card.FORE_2, Card.FIVE_1, Card.SIX_1)).contains(Pedigree.KKEUT0), is(false));

        assertThat(Rule.findAvailableResult(Arrays.asList(Card.ONE_2, Card.FIVE_1, Card.FORE_2)).contains(Pedigree.DOGSA), is(true));
        assertThat(Rule.findAvailableResult(Arrays.asList(Card.ONE_2, Card.FIVE_1, Card.FORE_2)).contains(Pedigree.KKEUT6), is(true));
        assertThat(Rule.findAvailableResult(Arrays.asList(Card.ONE_2, Card.FIVE_1, Card.FORE_2)).contains(Pedigree.KKEUT9), is(true));
        assertThat(Rule.findAvailableResult(Arrays.asList(Card.ONE_2, Card.FIVE_1, Card.FORE_2)).contains(Pedigree.KKEUT5), is(false));
    }
}
