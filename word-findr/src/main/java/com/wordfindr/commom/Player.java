package com.wordfindr.commom;

import lombok.Data;

import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;

@Data
public class Player implements Cloneable, Serializable {

    private int score;
    private String name;
    private int requestedHints;
    private Socket connection;
    private ArrayList<String> guesses;

    public Player(String name, Socket connection) {
        this.score = 0;
        this.name = name;
        this.requestedHints = 0;
        this.connection = connection;
        this.guesses = new ArrayList<>();
    }

    @Override
    public Player clone() throws CloneNotSupportedException {
        Player clone = (Player) super.clone();
        clone.setConnection(null);
        return clone;
    }
}
