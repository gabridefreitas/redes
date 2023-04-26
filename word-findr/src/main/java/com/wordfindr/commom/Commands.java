package com.wordfindr.commom;

public enum Commands {

    CONNECT_PLAYER("01"),
    SET_SECRET("02"),
    GET_HINT("03"),
    SECRET_GUESS("04"),
    FINISH_GAME("05");

    public final String value;

    Commands(String value) {
        this.value = value;
    }
}
