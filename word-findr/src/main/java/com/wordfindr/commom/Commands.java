package com.wordfindr.commom;

public enum Commands {

    CONNECT_PLAYER("01"),
    SET_SECRET("02"),
    GET_HINT("03"),
    SECRET_GUESS("04"),
    FINISH_GAME("05"),
    INVALID_VALUE("06"),
    SUCESS_GUESS("07"),
    UNSUCESSFULLY_GUESS("08"),
    WINNER("09"),
    REQUEST_TIP("10"),
    SHOW_REQUEST_TIP("11");
    public final String value;

    Commands(String value) {
        this.value = value;
    }
}
