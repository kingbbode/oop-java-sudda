package com.kingbbode.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by YG on 2017-07-18.
 */
public class Player {
    
    private String id;
    private String name;
    private int gameMoney;
    private boolean live;
    private List<Card> deck;
    private Dealer dealer;

    Player(String playerId, String name, int gameMoney) {
        this.id = playerId;
        this.name = name;
        this.gameMoney = gameMoney;
        this.deck = new ArrayList<>();
        this.live = true;
    }

    void introduce(Dealer dealer) {
        this.dealer = dealer;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Pedigree> getAvailableResult() {
        return Rule.findAvailableResult(this.deck);
    }

    public void receiveCard(Card card) {
        deck.add(card);
    }

    public boolean isLive() {
        return live;
    }
    
    public boolean isAllIn() {
        return this.gameMoney == 0;
    }
    
    public void die(){
        this.live = false;
    }
    
    public void batting(Batting batting){
        int cost = dealer.batting(this, batting);
        if(cost > this.gameMoney){
            this.gameMoney = 0;
            return;
        }
        this.gameMoney -= cost;
    }

    public int getGameMoney() {
        return gameMoney;
    }

    public boolean isSamePlayer(String playerId) {
        return Objects.equals(this.id, playerId);
    }
}
