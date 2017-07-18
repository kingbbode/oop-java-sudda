package com.kingbbode.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.kingbbode.domain.Pedigree.Condition.*;


/**
 * Created by YG on 2017-07-17.
 */
public enum Pedigree {
    DDANG_GWANG_38("38광땡", CONDITION_BY_SCORE),
    DDANG_GWANG_13("13광땡", CONDITION_BY_SCORE, CONDITION_BY_AMHENG),
    DDANG_GWANG_18("18광땡", CONDITION_BY_SCORE, CONDITION_BY_AMHENG),
    DDANG10("장땡", CONDITION_BY_SCORE),
    DDANG9("9땡", CONDITION_BY_SCORE),
    DDANG8("8땡", CONDITION_BY_SCORE, CONDITION_BY_DDANG_KILLER),
    DDANG7("7땡", CONDITION_BY_SCORE, CONDITION_BY_DDANG_KILLER),
    DDANG6("6땡", CONDITION_BY_SCORE, CONDITION_BY_DDANG_KILLER),
    DDANG5("5땡", CONDITION_BY_SCORE, CONDITION_BY_DDANG_KILLER),
    DDANG4("4땡", CONDITION_BY_SCORE, CONDITION_BY_DDANG_KILLER),
    DDANG3("3땡", CONDITION_BY_SCORE, CONDITION_BY_DDANG_KILLER),
    DDANG2("2땡", CONDITION_BY_SCORE, CONDITION_BY_DDANG_KILLER),
    DDANG1("삥땡", CONDITION_BY_SCORE, CONDITION_BY_DDANG_KILLER),
    ALLI("알리", CONDITION_BY_SCORE),
    DOGSA("독사", CONDITION_BY_SCORE),
    BBING_9("구삥", CONDITION_BY_SCORE),
    BBING_10("장삥", CONDITION_BY_SCORE),
    JANGSA("장사", CONDITION_BY_SCORE),
    SERUEG("세륙", CONDITION_BY_SCORE),
    DDANG_KILLER("땡잡이", CONDITION_BY_DDANG_KILLER),
    AMHENG("암행어사", CONDITION_BY_AMHENG),
    KKEUT9("갑오", CONDITION_BY_SCORE),
    KKEUT8("8끗", CONDITION_BY_SCORE),
    KKEUT7("7끗", CONDITION_BY_SCORE),
    KKEUT6("6끗", CONDITION_BY_SCORE),
    KKEUT5("5끗", CONDITION_BY_SCORE),
    KKEUT4("4끗", CONDITION_BY_SCORE),
    KKEUT3("3끗", CONDITION_BY_SCORE),
    KKEUT2("2끗", CONDITION_BY_SCORE),
    KKEUT1("1끗", CONDITION_BY_SCORE),
    KKEUT0("망통", CONDITION_BY_SCORE),
    ERROR("시스템 에러", CONDITION_ALWAYS_WINDER);

    private String name;
    private List<Pedigree.Condition> victoryConditions;

    Pedigree(String name, Pedigree.Condition... victoryConditions) {
        this.name = name;
        this.victoryConditions = Arrays.asList(victoryConditions);
    }

    public int getScore() {
        return Card.values().length - this.ordinal() - 1;
    }

    public String getName() {
        return name;
    }

    public boolean verifyVictoryConditions(Pedigree challenger) {
        return this.victoryConditions.stream().allMatch(condition -> condition.isVictory(this, challenger));
    }

    public static Optional<Pedigree> findByScore(int score) {
        return Arrays.stream(Pedigree.values()).filter(pedigree -> pedigree.getScore() == score).findFirst();
    }

    enum Condition {
        CONDITION_BY_SCORE((self, challenger) -> self.getScore() > challenger.getScore()),
        CONDITION_BY_DDANG_KILLER((self, challenger) -> !contains(self, challenger, Pedigree.DDANG_KILLER) || self == Pedigree.DDANG_KILLER && challenger.getScore() < Pedigree.DDANG10.getScore() && challenger.getScore() > Pedigree.ALLI.getScore()),
        CONDITION_BY_AMHENG((self, challenger) -> !contains(self, challenger, Pedigree.AMHENG) || self == Pedigree.AMHENG && (challenger == Pedigree.DDANG_GWANG_18 || challenger == Pedigree.DDANG_GWANG_13)),
        CONDITION_ALWAYS_WINDER((self, challenger) -> false);

        private ConditionFormal formal;

        Condition(ConditionFormal formal) {
            this.formal = formal;
        }

        boolean isVictory(Pedigree self, Pedigree challenger) {
            return this.formal.apply(self, challenger);
        }

        private interface ConditionFormal {
            boolean apply(Pedigree self, Pedigree challenger);
        }

        private static boolean contains(Pedigree self, Pedigree challenger, Pedigree pedigree) {
            return self == pedigree || challenger == pedigree;
        }
    }
}