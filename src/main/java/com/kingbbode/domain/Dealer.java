package com.kingbbode.domain;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by YG on 2017-07-18.
 */
public class Dealer {

    private GameMessengerInterface messenger;
    private Queue<Card> deck;
    private Pane pane;
    private List<Player> players;
    private Mc mc;

    Dealer(GameMessengerInterface messenger) {
        this.messenger = messenger;
        this.deck = Card.generateGameDeck();
        this.pane = new Pane();
    }

    void introduce(Mc mc, Collection<Player> players) {
        this.mc = mc;
        this.players = (List<Player>) players;
        this.players.forEach(player -> {
            player.introduce(this);
            messenger.send(player.getId(),  player.getName() + "님 반갑습니다. 저는 딜러입니다.");
        });
    }
    
    public void distributeCard(){
        players.stream().filter(Player::isLive).forEach(player -> {
            Card card = deck.poll();
            player.receiveCard(card);
            messenger.send(player.getId(), "새로 뽑은 카드는 [" + card.getName() + "] 입니다.");
        });
    }

    public void announceBatTableInfo(Player player){
        String info = "베팅 정보" + "\n" +
                "***********" + "\n" +
                getBatTableInfo() +
                "\n" +
                "소유 금액 : " + player.getGameMoney() + "\n";
        messenger.send(player.getId(), info);
    }

    public int batting(Player player, Batting batting){
        int batMoney = pane.batTable.get(batting);
        pane.batting(batMoney);
        mc.notify(player, batting);

        return batMoney;
    }

    public String getBatTableInfo(){
        StringBuilder stringBuilder = new StringBuilder();
        pane.batTable.forEach((key, value) -> stringBuilder.append(key.getName()).append(" - ").append(value).append("\n"));
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
