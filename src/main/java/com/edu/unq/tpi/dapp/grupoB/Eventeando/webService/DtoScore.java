package com.edu.unq.tpi.dapp.grupoB.Eventeando.webService;

public class DtoScore {

    private final Long userId;
    private final Integer rank;

    public DtoScore(Long userId, Integer rank) {

        this.userId = userId;
        this.rank = rank;
    }

    public Long userId() {
        return userId;
    }

    public Integer rank() {
        return rank;
    }
}
