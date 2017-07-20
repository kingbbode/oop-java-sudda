package com.kingbbode.game;

import java.util.*;

/**
 * Created by YG on 2017-07-20.
 */
public class GameConsoleMessager implements GameMessengerInterface {
    private Queue<String> colorQueue;
    private Map<String, String> colorMap;
    private static final String COLOR_DEFAULT = "\033[0m";

    public GameConsoleMessager() {
        colorMap = new HashMap<>();
        this.colorQueue = new ArrayDeque<>();
        this.colorQueue.add("\033[41m");
        this.colorQueue.add("\033[42m");
        this.colorQueue.add("\033[43m");
        this.colorQueue.add("\033[44m");
    }

    @Override
    public void broadcast(String message) {
        System.out.println(message);
    }

    @Override
    public void send(String userId, String message) {
        if(!colorMap.containsKey(userId)){
            this.colorMap.put(userId, colorQueue.poll());
        }
        System.out.println(this.colorMap.get(userId) + message + COLOR_DEFAULT);
    }
}
