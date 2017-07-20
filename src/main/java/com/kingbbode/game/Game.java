package com.kingbbode.game;

import com.kingbbode.game.domain.Dealer;
import com.kingbbode.game.domain.Mc;
import com.kingbbode.game.domain.Player;
import com.kingbbode.game.enums.Scenario;
import com.kingbbode.game.exceptions.AlreadyJoinException;
import com.kingbbode.game.exceptions.TooManyPlayerException;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by YG on 2017-07-18.
 */
@Getter
public class Game {
    private static final int MAX_PLAYER_COUNT=4;

    private Dealer dealer;
    private Mc mc;
    private Map<String, Player> players;
    private Scenario suddaScenario;

    public Game(GameMessengerInterface messenger) {
        this.players = new HashMap<>();
        this.mc = new Mc(messenger);
        this.dealer = new Dealer(messenger, new HashMap<>());
        this.suddaScenario = Scenario.READY;
    }

    
    public void join(String playerId, String name, int gameMoney){
        if(this.players.size() == MAX_PLAYER_COUNT){
            throw new TooManyPlayerException();
        }
        if(this.players.containsKey(playerId)){
           throw new AlreadyJoinException(); 
        }
        this.players.put(playerId, new Player(playerId, name, gameMoney));
    }
    
    public boolean isEnd() { 
        return suddaScenario == Scenario.END;
    }

    public synchronized void play(String playerId, String message) {
        suddaScenario = suddaScenario.play(this.mc, this.dealer, this.players, this.players.get(playerId), message);
        if(suddaScenario == Scenario.RESULT){
            suddaScenario = suddaScenario.play(this.mc, this.dealer, this.players, null, "");
        }
    }
}
