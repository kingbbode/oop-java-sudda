package com.kingbbode.game;

/**
 * Created by YG on 2017-07-18.
 */
public interface GameMessengerInterface {
    void broadcast(String message);
    void send(String userId, String message);
}
