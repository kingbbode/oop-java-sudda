package com.kingbbode.game.exceptions;

/**
 * Created by YG on 2017-07-18.
 */
public class TooManyPlayerException extends RuntimeException {
    public TooManyPlayerException() {
        super("더 이상 참여할 수 없습니다.");
    }
}
