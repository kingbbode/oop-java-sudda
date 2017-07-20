package com.kingbbode.game.enums;

import com.kingbbode.game.domain.Dealer;
import com.kingbbode.game.domain.Mc;
import com.kingbbode.game.domain.Player;
import com.kingbbode.game.exceptions.NotEnoughPlayerException;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by YG on 2017-07-20.
 */
public enum Scenario {
    READY(Collections.singletonList("시작"), (self, mc, dealer, players, player, message) -> {
        if(players.size() <2) {
            throw new NotEnoughPlayerException();
        }
        dealer.introduce(players.values());
        return mc.introduce(dealer, players.values());
    }),
    DOING(Batting.getNameList(), (self, mc, dealer, players, player, message) -> {
        if(!mc.isCurrentPlayer(player)){
            return self;
        }
        Batting batting = Batting.resolve(message);
        mc.whoIsCurrentPlayer().batting(batting);
        return mc.notifyBatting(player, batting);
    }),
    CHOICE(Pedigree.getNameList(), (self, mc, dealer, players, player, message) -> {
        Pedigree result = Pedigree.resolve(message);
        if(!player.getAvailableResult().contains(result)){
            return self;
        }
        dealer.receiveResult(player, result);
        return mc.notifyResult(player, result);
    }),
    RESULT(Collections.EMPTY_LIST, (self, mc, dealer, players, player, message) -> mc.announceWinner()),
    END(Collections.EMPTY_LIST, (self, mc, dealer, players, player, message) -> self);
    
    private ScenarioFormal scenarioFormal;
    private List<String> commands;

    Scenario(List<String> commands, ScenarioFormal scenarioFormal) {
        this.scenarioFormal = scenarioFormal;
        this.commands = commands;
    }

    public boolean verify(String message) {
        return Collections.EMPTY_LIST.equals(this.commands) || this.commands.contains(message);
    }
    
    public Scenario play(Mc mc, Dealer dealer, Map<String, Player> players, Player player, String message) {
        if(!verify(message)){
            return this;
        }
        return this.scenarioFormal.apply(this, mc, dealer, players, player, message);
    }

    interface ScenarioFormal {
        Scenario apply(Scenario self, Mc mc, Dealer dealer, Map<String, Player> players, Player player, String message);
    }
}
