package com.wordfindr.server;

public enum Commands {
    CONNECT_PLAYER("01"),
    SET_SECRET("02"),
    SECRET_GUESS("03"),
    GET_HINT("04"),
    FORCE_GAME_START("99");

    private final String value;

    Commands(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
