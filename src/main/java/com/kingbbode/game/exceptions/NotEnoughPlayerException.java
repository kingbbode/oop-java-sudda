package com.kingbbode.game.exceptions;

/**
 * Created by YG on 2017-07-18.
 */
public class NotEnoughPlayerException extends RuntimeException {
    public NotEnoughPlayerException() {
        super("참여 인원이 충분하지 않습니다.");
    }
}
