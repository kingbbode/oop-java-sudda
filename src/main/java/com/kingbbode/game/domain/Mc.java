package com.kingbbode.game.domain;

import com.kingbbode.game.GameMessengerInterface;
import com.kingbbode.game.enums.Batting;
import com.kingbbode.game.enums.Pedigree;
import com.kingbbode.game.enums.Scenario;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by YG on 2017-07-18.
 */
@Getter
public class Mc {
    private static final int MAX_TURN_COUNT=3;
    
    private List<Player> players;
    private int playerTurnCount;
    private Dealer dealer;
    private GameMessengerInterface messenger;

    public Mc(GameMessengerInterface messenger) {
        this.playerTurnCount = 0;
        this.messenger = messenger;
    }

    public Scenario introduce(Dealer dealer, Collection<Player> players) {
        this.dealer = dealer;
        this.players = new ArrayList<>(players);
        StringBuilder stringBuilder = new StringBuilder("MC : ");
        this.players.forEach(player -> stringBuilder.append("[").append(player.getName()).append("]"));
        this.messenger.broadcast(stringBuilder.append("님 반갑습니다.").toString());
        return this.nextTurn();
    }
    
    public boolean isCurrentPlayer(Player player){
        return whoIsCurrentPlayer().isSamePlayer(player);
    }
    
    public Player whoIsCurrentPlayer() {
        return  players.get(getCurrentPlayerOrder());
    }

    private int getCurrentPlayerOrder() {
        return this.playerTurnCount % this.players.size();
    }


    public Scenario notifyBatting(Player player, Batting batting) {
        this.messenger.broadcast(player.getName() + "님 " + batting.getName() + "!!");
        playerTurnCount++;
        System.out.println(playerTurnCount);
        Scenario scenario = checkDoingStatus();
        if (scenario != null) {
            return scenario;
        }
        return nextStep();
    }

    private boolean isTurnOver() {
        return getCurrentPlayerOrder() == 0;
    }

    public Scenario notifyResult(Player player, Pedigree result) {
        this.messenger.send(player.getId(), player.getName() + "님 " + result.getName() + "!!");
        if(!this.dealer.hasAllResult()){
            return Scenario.CHOICE;
        }
        return Scenario.RESULT;
    }

    public Scenario announceWinner() {
        award(chooseVictor());
        return Scenario.END;
    }

    private Player chooseVictor() {
        if(isAllPlayerDie()){
            return findSoleSurvivor();
        }
        return this.dealer.chooseVictor();
    }
    
    private Player findSoleSurvivor() {
        return this.players.stream().filter(Player::isLive).findAny().orElseGet(null);
    }

    private boolean isAllPlayerDie(){
        return this.players.stream().filter(Player::isLive).count() == 1;
    }

    private void award(Player winner){
        if(winner == null){
            dealer.distributeBatMoney();
            messenger.broadcast("무승부가 발생하여 배팅 머니를 살아있는 플레이어에게 분배합니다.");
            return;
        }
        dealer.award(winner);
        messenger.broadcast(winner.getName() + "님이 승리하였습니다!");
    }

    

    private Scenario nextTurn() {
        this.messenger.broadcast("MC : 패 돌리기를 시작하겠습니다.");
        this.dealer.distributeCard();
        return this.nextStep();
    }

    private Scenario nextStep() {
        Player player = whoIsCurrentPlayer();
        while(!player.canBatting()){
            this.messenger.broadcast(player.getName() + "님은 [" + player.getStatus() + "] 상태이므로 차례를 넘깁니다.");
            playerTurnCount++;
            System.out.println(playerTurnCount);
            player = whoIsCurrentPlayer();
            Scenario scenario = checkDoingStatus();
            if (scenario != null) {
                return scenario;
            }
        }
        this.messenger.broadcast(player.getName() + "님의 차례입니다.");
        this.messenger.broadcast(this.dealer.getBatTableInfo());
        this.dealer.announceBatTableInfo(player);
        return Scenario.DOING;
    }

    private Scenario checkDoingStatus() {
        if(isAllPlayerDie()){
            return Scenario.RESULT;
        }
        if(isGameOver()){
            return gameOver();
        }
        if(isTurnOver()){
            return nextTurn();
        }
        return null;
    }

    private Scenario gameOver() {
        this.messenger.broadcast("모든 턴이 종료되었습니다. 플레이어들은 결과를 제출해주세요.");
        this.dealer.distributeResult();
        return Scenario.CHOICE;
    }



    private boolean isGameOver() {
        return getCurrentTurnCount() >= MAX_TURN_COUNT;
    }

    private int getCurrentTurnCount(){
        return (this.playerTurnCount ) / this.players.size();
    }
}
