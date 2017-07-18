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
    public Game() {
        this.players = new HashMap<>();
        this.mc = new Mc();
        this.dealer = new Dealer();
    }
    private Mc mc;
    private Dealer dealer;

    private Map<String, Player> players;
    public synchronized void join(String playerId, int playerMoney){
        if(this.players.size() == MAX_PLAYER_COUNT){
            throw new TooManyPlayerException();
        }
        this.players.put(playerId, new Player(playerId, playerMoney));
    }

    public void start() {
        if(players.size() <2) {
            throw new NotEnoughPlayerException();
        }
        mc.introduce(dealer, players.values());
        dealer.introduce(mc, players.values());
        this.players.values().forEach(player -> player.introduce(dealer));
    }
}
