package com.kingbbode.game.domain;

import com.kingbbode.game.GameMessengerInterface;
import com.kingbbode.game.enums.Batting;
import com.kingbbode.game.enums.Card;
import com.kingbbode.game.enums.Pedigree;
import lombok.Getter;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by YG on 2017-07-18.
 */
@Getter
public class Dealer {

    private GameMessengerInterface messenger;
    private Queue<Card> deck;
    private Pane pane;
    private List<Player> players;
    private Map<Player, Pedigree> results;


    public Dealer(GameMessengerInterface messenger, Map<Player, Pedigree> results) {
        this.messenger = messenger;
        this.deck = Card.generateGameDeck();
        this.results = results;
        this.pane = new Pane();
    }

    public void introduce(Collection<Player> players) {
        this.players = new ArrayList<>(players);
        this.players.forEach(player -> {
            player.introduce(this);
            messenger.send(player.getId(),  "딜러 : " + player.getName() + "님 반갑습니다.");
        });
    }

    public void distributeCard(){
        players.stream().filter(Player::isLive).forEach(player -> {
            Card card = deck.poll();
            player.receiveCard(card);
            messenger.send(player.getId(), "딜러 : 새로 뽑은 카드는 [" + card.getName() + "] 입니다.");
        });
    }

    public void announceBatTableInfo(Player player){
        StringBuilder info = new StringBuilder("베팅 정보");
        info.append("\n")
                .append("***********")
                .append("\n")
                .append(getBatTableInfo())
                .append("\n")
                .append("소유 금액 : ")
                .append(player.getGameMoney())
                .append("\n");
        messenger.send(player.getId(), info.toString());
    }

    public int getBattingCost(Batting batting){
        return pane.batTable.get(batting);
    }

    public void batting(int batMoney){
        pane.batting(batMoney);
    }

    public String getBatTableInfo(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("총 배팅액 : ").append(pane.totalBatMoney).append("\n");
        pane.batTable.forEach((key, value) -> stringBuilder.append(key.getName()).append(" - ").append(value).append("\n"));
        return stringBuilder.toString();
    }

    public void award(Player winner) {
        award(winner, this.pane.totalBatMoney);
    }
    
    private void award(Player player, int batMoney) {
        player.receiveMoney(batMoney);
        messenger.send(player.getId(), "딜러 : 게임 보상으로 " + batMoney + "을(를) 획득했습니다.");
    }

    public void distributeResult() {
        for(Player player : players) {
            if(!player.isLive()) {
                results.put(player, Pedigree.DIE);
                continue;
            }
            StringBuilder stringBuilder = new StringBuilder();
            player.getAvailableResult().forEach(pedigree -> stringBuilder.append("[").append(pedigree.getName()).append("]"));
            messenger.send(player.getId(), "딜러 : 제출 가능한 결과는 " + stringBuilder.toString() + " 입니다.");
        }
    }

    public void distributeBatMoney() {
        this.players.stream().filter(Player::isLive).forEach(player -> award(player, this.pane.totalBatMoney/((int) this.players.stream().filter(Player::isLive).count())));
    }
    
    public void receiveResult(Player player, Pedigree result) {
        if(this.results.containsKey(player)){
            return;
        }
        this.results.put(player, result);
        messenger.send(player.getId(), "딜러 : 결과를 제출 받았습니다.");
    }

    public boolean hasAllResult() {
        return this.results.size() == this.players.size();
    }

    public Player chooseVictor() {
        Match match = new Match();
        for(Map.Entry<Player, Pedigree> challenger : results.entrySet()){
            match.challengeByScore(challenger);
        }
        for(Map.Entry<Player, Pedigree> challenger : results.entrySet()){
            match.challengeBySpecialPedigree(challenger);
        }
        return match.isDraw()?null:match.player;
    }
    
    public class Match {
        private Pedigree drawerPedigree;
        private Player player;
        private Pedigree winnerPedigree;
        
        boolean isDraw() {
            return drawerPedigree != null && drawerPedigree == winnerPedigree;
        }
        
        void challengeByScore(Map.Entry<Player, Pedigree> challenger){
            if(Pedigree.isSpecialPedigree(challenger.getValue())){
                return;
            }
            this.challenge(challenger);
        }

        void challengeBySpecialPedigree(Map.Entry<Player, Pedigree> challenger){
            if(!Pedigree.isSpecialPedigree(challenger.getValue())){
                return;
            }
            this.challenge(challenger);
        }
        
        private void challenge(Map.Entry<Player, Pedigree> challenger){
            if(this.winnerPedigree == null){
                this.player = challenger.getKey();
                this.winnerPedigree = challenger.getValue();
                return;
            }

            if(this.winnerPedigree == challenger.getValue()){
                this.drawerPedigree = challenger.getValue();
                return;
            }
            if(this.winnerPedigree.verifyVictoryConditions(challenger.getValue())){
                return;
            }
            this.player = challenger.getKey();
            this.winnerPedigree = challenger.getValue();
        }
    }

    @Getter
    public class Pane {
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
