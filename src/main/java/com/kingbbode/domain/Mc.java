package com.kingbbode.domain;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by YG on 2017-07-18.
 */
public class Mc {
    private static final int MAX_TURN_COUNT=3;
    
    private List<Player> players;
    private int turnCount;
    private Dealer dealer;
    private GameMessengerInterface messenger;

    Mc(GameMessengerInterface messenger) {
        this.turnCount = 0;
        this.messenger = messenger;
    }

    void introduce(Dealer dealer, Collection<Player> players) {
        this.dealer = dealer;
        this.players = (List<Player>) players;
        StringBuilder stringBuilder = new StringBuilder();
        this.players.forEach(player -> stringBuilder.append("[").append(player.getName()).append("]"));
        stringBuilder.append("님 반갑습니다. 저는 MC 입니다");
        messenger.broadcast(stringBuilder.toString());
    }

    public boolean isFin(){
        return players.stream().filter(Player::isLive).count() ==1 || (turnCount+1/players.size()) >= MAX_TURN_COUNT;
    }
    
    public void nextStep() {
        dealer.distributeCard();
    }
    
    public Player whoIsCurrentPlayer() {
        Player player = players.get(turnCount%players.size());
        while(!player.isLive()){
            turnCount++;
            player = players.get(turnCount);
        }
        return player;
    }

    public void turnOff() {
        if(isFin()){
            messenger.broadcast("모든 턴이 종료되었습니다. 플레이어들은 결과를 제출해주세요.");
            return;
        }
        messenger.broadcast(whoIsCurrentPlayer().getName() + "님의 턴이 종료되었습니다.");
        turnCount++;
        messenger.broadcast(whoIsCurrentPlayer().getName() + "님의 차례입니다.");
    }

    public void notify(Player player, Batting batting) {
        messenger.broadcast(player.getName() + "님 " + batting.getName() + "!!");
    }
}
