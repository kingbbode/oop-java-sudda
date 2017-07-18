package com.kingbbode.domain;

import java.util.*;

/**
 * Created by YG on 2017_07_13.
 */
public enum Card {
    ONE_1(1, true),
    ONE_2(1, false),
    ONE_3(1, false),
    ONE_4(1, false),
    TWO_1(2, true),
    TWO_2(2, false),
    TWO_3(2, false),
    TWO_4(2, false),
    THREE_1(3, true),
    THREE_2(3, false),
    THREE_3(3, false),
    THREE_4(3, false),
    FORE_1(4, true),
    FORE_2(4, false),
    FORE_3(4, false),
    FORE_4(4, false),
    FIVE_1(5, true),
    FIVE_2(5, false),
    FIVE_3(5, false),
    FIVE_4(5, false),
    SIX_1(6, true),
    SIX_2(6, false),
    SIX_3(6, false),
    SIX_4(6, false),
    SEVEN_1(7, true),
    SEVEN_2(7, false),
    SEVEN_3(7, false),
    SEVEN_4(7, false),
    EIGHT_1(8, true),
    EIGHT_2(8, false),
    EIGHT_3(8, false),
    EIGHT_4(8, false),
    NINE_1(9, true),
    NINE_2(9, false),
    NINE_3(9, false),
    NINE_4(9, false),
    TEN_1(10, false),
    TEN_2(10, true),
    TEN_3(10, false),
    TEN_4(10, false);

    private int number;
    private boolean isKing;

    Card(int number, boolean isKing) {
        this.number = number;
        this.isKing = isKing;
    }

    public int getNumber() {
        return number;
    }

    public boolean isKing() {
        return isKing;
    }

    public static Queue<Card> generateGameDeck() {
        List<Card> cards = Arrays.asList(Card.values());
        for (int i = 0; i < 100; i++) {
            Collections.shuffle(cards);
        }
        return new LinkedList<>(cards);
    }

    public String getName() {
        return number + (isKing?"광":"끗");
    }
}

