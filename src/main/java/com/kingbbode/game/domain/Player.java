package com.kingbbode.game.domain;

import com.kingbbode.game.enums.Batting;
import com.kingbbode.game.enums.Card;
import com.kingbbode.game.enums.Pedigree;
import com.kingbbode.game.enums.Rule;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by YG on 2017-07-18.
 */
@Getter
public class Player {
    
    private String id;
    private String name;
    private int gameMoney;
    private boolean live;
    private List<Card> deck;
    private Dealer dealer;

    public Player(String playerId, String name, int gameMoney) {
        this.id = playerId;
        this.name = name;
        this.gameMoney = gameMoney;
        this.deck = new ArrayList<>();
        this.live = true;
    }

    public void introduce(Dealer dealer) {
        this.dealer = dealer;
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
    
    public void batting(Batting batting){
        if(batting == Batting.DIE){
            die();
            return;
        }
        int cost = dealer.batting(batting);
        if(cost > this.gameMoney){
            this.gameMoney = 0;
            return;
        }
        this.gameMoney -= cost;
    }

    private void die(){
        this.live = false;
    }

    public boolean isSamePlayer(Player player) {
        return Objects.equals(this.id, player.id);
    }

    public boolean canBatting() {
        return isLive() && !isAllIn();
    }
    
    public boolean isAllIn() {
        return this.gameMoney == 0;
    }

    public String getStatus() {
        return isLive()?"ALLIN":"DIE";
    }

    public void receiveMoney(int totalBatMoney) {
        this.gameMoney += totalBatMoney;
    }
}
