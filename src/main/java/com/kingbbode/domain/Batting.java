package com.kingbbode.domain;

/**
 * Created by YG on 2017-07-18.
 */
public enum Batting {
    DIE("다이", (beforeBatMoney, totalBatMoney) -> 0),
    CALL("콜", (beforeBatMoney, totalBatMoney) -> beforeBatMoney),
    HALF("하프", (beforeBatMoney, totalBatMoney) -> totalBatMoney/2),
    QUETER("쿼터", (beforeBatMoney, totalBatMoney) -> totalBatMoney/4),
    DDADANG("따당", (beforeBatMoney, totalBatMoney) -> beforeBatMoney*2),
    CHECK("체크", (beforeBatMoney, totalBatMoney) -> 0),
    BBING("삥", (beforeBatMoney, totalBatMoney) -> 100);
    
    private String name;
    private BattingFormal formal;
    
    Batting(String name, BattingFormal formal) {
        this.name = name;
        this.formal = formal;
    }

    public int calculate(int beforeBatMoney, int totalBatMoney){
        return this.formal.apply(beforeBatMoney, totalBatMoney);
    }

    public String getName() {
        return name;
    }

    interface BattingFormal {
        int apply(int beforeBatMoney, int totalBatMoney);
    }
}
