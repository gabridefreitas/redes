package com.wordfindr.commom;

import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;

import lombok.Data;

@Data
public class Player implements Cloneable, Serializable {

    private String name;
    private int numberErrors;
    private int requestedHints;
    private Socket connection;
    private ArrayList<Character> guesses;

    public Player(String name, Socket connection) {
        this.name = name;
        this.numberErrors = 0;
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

    public String toStringGuesses() {
        StringBuilder sb = new StringBuilder("[");

        for (Character guess : guesses) {
            sb.append(guess);
            sb.append(" ");
        }

        sb.append("]");

        return sb.toString();
    }
}
