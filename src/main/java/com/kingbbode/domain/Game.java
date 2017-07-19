package com.kingbbode.domain;

import com.kingbbode.exceptions.NotEnoughPlayerException;
import com.kingbbode.exceptions.TooManyPlayerException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by YG on 2017-07-18.
 */
public class Game {
    private static final int MAX_PLAYER_COUNT=4;

    private Dealer dealer;
    private Mc mc;
    private boolean doing;

    public Game(GameMessengerInterface messenger) {
        this.players = new HashMap<>();
        this.mc = new Mc(messenger);
        this.dealer = new Dealer(messenger);
        this.doing = false;
    }

    private Map<String, Player> players;
    public synchronized void join(Player player){
        if(doing){
            return;
        }
        if(this.players.size() == MAX_PLAYER_COUNT){
            throw new TooManyPlayerException();
        }
        this.players.put(player.getId(), player);
    }

    public synchronized void ready() {
        if(doing){
            return;
        }
        if(this.players.size() <2) {
            throw new NotEnoughPlayerException();
        }
        doing = true;
        this.mc.introduce(dealer, players.values());
        this.dealer.introduce(mc, players.values());
    }

    public void receive(String playerId, String message) {
        if(!doing){
            //join or ready
        }
        if(!this.mc.whoIsCurrentPlayer().isSamePlayer(playerId)){
            return;
        }
        //this.players.get(playerId).batting(Batting.valueOf(message));
        //this.dealer.announceBatTableInfo(this.mc.whoIsCurrentPlayer());
        //this.dealer.distributeCard();
    }
}
