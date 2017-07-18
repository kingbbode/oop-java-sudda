package com.kingbbode.exceptions;

/**
 * Created by YG on 2017-07-18.
 */
public class NotReadyGameException extends RuntimeException {
    public NotReadyGameException() {
        super("게임이 아직 시작되지 않았습니다.");
    }
}
