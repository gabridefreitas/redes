package com.wordfindr.commom;

public enum Commands {

    CONNECT("01"),
    SECRET("02"),
    HINT("03"),
    GUESS("04"),
    FINISH("05");

    public final String value;

    Commands(String value) {
        this.value = value;
    }
}
