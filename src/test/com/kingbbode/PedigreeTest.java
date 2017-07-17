package com.kingbbode.sudda;

import com.kingbbode.ultron.sudda.Pedigree;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by YG on 2017-07-17.
 */
public class PedigreeTest {
    
    @Test
    public void 섰다_승리_테스트_일반_스코어() throws Exception {
        assertThat(Pedigree.DDANG_GWANG_38.verifyVictoryConditions(Pedigree.DDANG_GWANG_18), is(true));
        assertThat(Pedigree.DDANG_GWANG_13.verifyVictoryConditions(Pedigree.DDANG_GWANG_18), is(true));
        assertThat(Pedigree.DDANG_GWANG_18.verifyVictoryConditions(Pedigree.DDANG10), is(true));
        assertThat(Pedigree.DDANG10.verifyVictoryConditions(Pedigree.DDANG9), is(true));
        assertThat(Pedigree.DDANG9.verifyVictoryConditions(Pedigree.DDANG8), is(true));
        assertThat(Pedigree.DDANG8.verifyVictoryConditions(Pedigree.DDANG7), is(true));
        assertThat(Pedigree.DDANG7.verifyVictoryConditions(Pedigree.DDANG6), is(true));
        assertThat(Pedigree.DDANG6.verifyVictoryConditions(Pedigree.DDANG5), is(true));
        assertThat(Pedigree.DDANG5.verifyVictoryConditions(Pedigree.DDANG4), is(true));
        assertThat(Pedigree.DDANG4.verifyVictoryConditions(Pedigree.DDANG3), is(true));
        assertThat(Pedigree.DDANG3.verifyVictoryConditions(Pedigree.DDANG2), is(true));
        assertThat(Pedigree.DDANG2.verifyVictoryConditions(Pedigree.DDANG1), is(true));
        assertThat(Pedigree.DDANG1.verifyVictoryConditions(Pedigree.ALLI), is(true));
        assertThat(Pedigree.ALLI.verifyVictoryConditions(Pedigree.DOGSA), is(true));
        assertThat(Pedigree.DOGSA.verifyVictoryConditions(Pedigree.BBING_9), is(true));
        assertThat(Pedigree.BBING_9.verifyVictoryConditions(Pedigree.BBING_10), is(true));
        assertThat(Pedigree.BBING_10.verifyVictoryConditions(Pedigree.JANGSA), is(true));
        assertThat(Pedigree.JANGSA.verifyVictoryConditions(Pedigree.SERUEG), is(true));
        assertThat(Pedigree.SERUEG.verifyVictoryConditions(Pedigree.KKEUT9), is(true));
        assertThat(Pedigree.KKEUT9.verifyVictoryConditions(Pedigree.KKEUT8), is(true));
        assertThat(Pedigree.KKEUT8.verifyVictoryConditions(Pedigree.KKEUT7), is(true));
        assertThat(Pedigree.KKEUT7.verifyVictoryConditions(Pedigree.KKEUT6), is(true));
        assertThat(Pedigree.KKEUT6.verifyVictoryConditions(Pedigree.KKEUT5), is(true));
        assertThat(Pedigree.KKEUT5.verifyVictoryConditions(Pedigree.KKEUT4), is(true));
        assertThat(Pedigree.KKEUT4.verifyVictoryConditions(Pedigree.KKEUT3), is(true));
        assertThat(Pedigree.KKEUT3.verifyVictoryConditions(Pedigree.KKEUT2), is(true));
        assertThat(Pedigree.KKEUT2.verifyVictoryConditions(Pedigree.KKEUT1), is(true));
        assertThat(Pedigree.KKEUT1.verifyVictoryConditions(Pedigree.KKEUT0), is(true));
        assertThat(Pedigree.KKEUT0.verifyVictoryConditions(Pedigree.ERROR), is(true));

        assertThat(Pedigree.DDANG_GWANG_13.verifyVictoryConditions(Pedigree.DDANG_GWANG_38), is(false));
        assertThat(Pedigree.DDANG_GWANG_18.verifyVictoryConditions(Pedigree.DDANG_GWANG_13), is(false));
        assertThat(Pedigree.DDANG10.verifyVictoryConditions(Pedigree.DDANG_GWANG_18), is(false));
        assertThat(Pedigree.DDANG9.verifyVictoryConditions(Pedigree.DDANG10), is(false));
        assertThat(Pedigree.DDANG8.verifyVictoryConditions(Pedigree.DDANG9), is(false));
        assertThat(Pedigree.DDANG7.verifyVictoryConditions(Pedigree.DDANG8), is(false));
        assertThat(Pedigree.DDANG6.verifyVictoryConditions(Pedigree.DDANG7), is(false));
        assertThat(Pedigree.DDANG5.verifyVictoryConditions(Pedigree.DDANG6), is(false));
        assertThat(Pedigree.DDANG4.verifyVictoryConditions(Pedigree.DDANG5), is(false));
        assertThat(Pedigree.DDANG3.verifyVictoryConditions(Pedigree.DDANG4), is(false));
        assertThat(Pedigree.DDANG2.verifyVictoryConditions(Pedigree.DDANG3), is(false));
        assertThat(Pedigree.DDANG1.verifyVictoryConditions(Pedigree.DDANG2), is(false));
        assertThat(Pedigree.ALLI.verifyVictoryConditions(Pedigree.DDANG1), is(false));
        assertThat(Pedigree.DOGSA.verifyVictoryConditions(Pedigree.ALLI), is(false));
        assertThat(Pedigree.BBING_9.verifyVictoryConditions(Pedigree.DOGSA), is(false));
        assertThat(Pedigree.BBING_10.verifyVictoryConditions(Pedigree.BBING_9), is(false));
        assertThat(Pedigree.JANGSA.verifyVictoryConditions(Pedigree.BBING_10), is(false));
        assertThat(Pedigree.SERUEG.verifyVictoryConditions(Pedigree.JANGSA), is(false));
        assertThat(Pedigree.KKEUT9.verifyVictoryConditions(Pedigree.SERUEG), is(false));
        assertThat(Pedigree.KKEUT8.verifyVictoryConditions(Pedigree.KKEUT9), is(false));
        assertThat(Pedigree.KKEUT7.verifyVictoryConditions(Pedigree.KKEUT8), is(false));
        assertThat(Pedigree.KKEUT6.verifyVictoryConditions(Pedigree.KKEUT7), is(false));
        assertThat(Pedigree.KKEUT5.verifyVictoryConditions(Pedigree.KKEUT6), is(false));
        assertThat(Pedigree.KKEUT4.verifyVictoryConditions(Pedigree.KKEUT5), is(false));
        assertThat(Pedigree.KKEUT3.verifyVictoryConditions(Pedigree.KKEUT4), is(false));
        assertThat(Pedigree.KKEUT2.verifyVictoryConditions(Pedigree.KKEUT3), is(false));
        assertThat(Pedigree.KKEUT1.verifyVictoryConditions(Pedigree.KKEUT2), is(false));
        assertThat(Pedigree.KKEUT0.verifyVictoryConditions(Pedigree.KKEUT1), is(false));
    }

    @Test
    public void 특수규칙_승리_테스트() throws Exception {
        assertThat(Pedigree.AMHENG.verifyVictoryConditions(Pedigree.DDANG_GWANG_18), is(true));
        assertThat(Pedigree.AMHENG.verifyVictoryConditions(Pedigree.DDANG_GWANG_13), is(true));
        assertThat(Pedigree.DDANG_KILLER.verifyVictoryConditions(Pedigree.DDANG9), is(true));
        assertThat(Pedigree.DDANG_KILLER.verifyVictoryConditions(Pedigree.DDANG8), is(true));
        assertThat(Pedigree.DDANG_KILLER.verifyVictoryConditions(Pedigree.DDANG7), is(true));
        assertThat(Pedigree.DDANG_KILLER.verifyVictoryConditions(Pedigree.DDANG6), is(true));
        assertThat(Pedigree.DDANG_KILLER.verifyVictoryConditions(Pedigree.DDANG5), is(true));
        assertThat(Pedigree.DDANG_KILLER.verifyVictoryConditions(Pedigree.DDANG4), is(true));
        assertThat(Pedigree.DDANG_KILLER.verifyVictoryConditions(Pedigree.DDANG3), is(true));
        assertThat(Pedigree.DDANG_KILLER.verifyVictoryConditions(Pedigree.DDANG2), is(true));
        assertThat(Pedigree.DDANG_KILLER.verifyVictoryConditions(Pedigree.DDANG1), is(true));

        assertThat(Pedigree.AMHENG.verifyVictoryConditions(Pedigree.DDANG_GWANG_38), is(false));
        assertThat(Pedigree.AMHENG.verifyVictoryConditions(Pedigree.KKEUT1), is(false));
        assertThat(Pedigree.DDANG_KILLER.verifyVictoryConditions(Pedigree.DDANG10), is(false));
        assertThat(Pedigree.DDANG_KILLER.verifyVictoryConditions(Pedigree.KKEUT1), is(false));
    }
}
