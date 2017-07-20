package com.kingbbode.game.exceptions;

/**
 * Created by YG on 2017-07-20.
 */
public class AlreadyJoinException extends RuntimeException {
    public AlreadyJoinException() {
        super("이미 참여했습니다.");
    }
}
