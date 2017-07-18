package com.kingbbode.domain;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by YG on 2017-07-18.
 */
public class Dealer {
    
    private Queue<Card> deck;
    private final Pane pane;
    private List<Player> players;
    private Mc mc;

    Dealer() {
        this.deck = Card.generateGameDeck();
        this.pane = new Pane();
    }

    void introduce(Mc mc, Collection<Player> players) {
        this.mc = mc;
        this.players = (List<Player>) players;
    }
    
    public void distributeCard(){
        players.stream().filter(Player::isLive).forEach(player -> player.receiveCard(deck.poll()));
    }

    public void batting(int batMoney){
        pane.batting(batMoney);
        mc.turnOff();
    }

    public int guideBatMoney(Batting batting){
        return pane.batTable.get(batting);
    }

    public String getBatTableInfo(){
        StringBuilder stringBuilder = new StringBuilder();
        pane.batTable.forEach((key, value) -> stringBuilder.append(key.getName()).append(" - ").append(value));
        return stringBuilder.toString();
    }

    private class Pane {
        private int totalBatMoney = 100;
        private int beforeBatMoney = 100;
        private Map<Batting, Integer> batTable = Arrays.stream(Batting.values()).collect(Collectors.toMap(Function.identity(), batting -> batting.calculate(100, 100)));
        
        void batting(int batMoney) {
            this.totalBatMoney += batMoney;
            this.beforeBatMoney = batMoney;
            batTable.keySet().forEach(batting -> batTable.put(batting, batting.calculate(this.beforeBatMoney, this.totalBatMoney)));
        }
    }
}
