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
    private GameMessengerInterface messenger;

    public Game() {
        this.messenger = new GameMessengerInterface() {
            @Override
            public void broadcast(String message) {

            }

            @Override
            public void send(String userId, String message) {

            }
        };
        this.players = new HashMap<>();
    }

    private Map<String, Player> players;
    public synchronized void join(Player player){
        if(this.players.size() == MAX_PLAYER_COUNT){
            throw new TooManyPlayerException();
        }
        this.players.put(player.getId(), player);
    }

    public void ready() {
        if(this.players.size() <2) {
            throw new NotEnoughPlayerException();
        }
        this.mc = new Mc(messenger);
        this.dealer = new Dealer(messenger);
        this.mc.introduce(dealer, players.values());
        this.dealer.introduce(mc, players.values());
    }

    public void receive(String playerId, String message) {
        //this.players.get(playerId).batting(Batting.valueOf(message));
        //this.dealer.announceBatTableInfo(this.mc.whoIsCurrentPlayer());
        //this.dealer.distributeCard();
        //this.mc.nextStep();
    }
}
