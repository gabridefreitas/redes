package com.wordfindr.server;

import com.wordfindr.commom.Message;
import com.wordfindr.commom.Player;
import lombok.Data;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.wordfindr.commom.Commands.*;

@Data
public class Game {

    private int round;
    private String secret;
    private boolean finished;
    private Player challenger;
    private ArrayList<String> hints;
    private ArrayList<Player> players;
    private ArrayList<String> guesses;
    private Player winner;

    public Game(ArrayList<Player> players) {
        this.round = 0;
        this.finished = false;
        this.players = players;
        this.hints = new ArrayList<>();
        this.guesses = new ArrayList<>();
    }

    public void start() {

        setChallenger();
        System.out.printf("Challenger: %s\n", challenger.getName());

        requestSecret();
        System.out.printf("Secret: %s\n", secret);

        while (!finished) {
            System.out.println("Round: " + round++);

            for (Player player : players) {
                if (!player.equals(challenger)) {
                    while (!requestGuess(player)){}
                    if (player.getNumberErrors() == 2){
                        showRequestTip(player, requestTip(player));
                    }

                    if (searchLetter(player) > -1){
                        Message.sendMessage(SUCESS_GUESS, "", player);
                    } else{
                        Message.sendMessage(UNSUCESSFULLY_GUESS, "", player);
                        player.setNumberErrors(player.getNumberErrors() + 1);
                    }
                }
            }

            for (Player player: players) {
                if (!player.equals(challenger)) {
                    if (wordComplet(player)) {
                        finished = true;
                        winner = player;
                    }
                }
            }


        }

        Message.sendMessage(WINNER, "", winner);
    }

    public void setChallenger() {
        int randomPlayer = (int) (Math.random() * players.size());
        challenger = players.get(randomPlayer);
    }

    private void requestSecret() {
        Message.sendMessage(SET_SECRET, "", challenger);
        secret = Message.readMessage(challenger.getConnection()).getBody();
    }

    private boolean requestGuess(Player player) {
        Message.sendMessage(SECRET_GUESS, "", player);
        String letter = Message.readMessage(player.getConnection()).getBody();
        if (validateLetter(letter)) {
            player.getGuesses().add(letter);
            return true;
        } else {
            Message.sendMessage(INVALID_VALUE, "", player);
            return false;
        }
    }

    private boolean validateLetter(String letter){
        String regex = "[a-zA-Z]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(letter);
        if (letter.length() == 1 && matcher.matches()){
            return true;
        } else return false;
    }

    private int searchLetter(Player player){
        int position = secret.indexOf(player.getGuesses().get(round));
        if (position > -1){
            return position;
        }else{
            return -1;
        }
    }

    private boolean wordComplet(Player player){
        ArrayList<String> letters  = new ArrayList<>();
        ArrayList<String> playerGuesses = player.getGuesses();
        String letter = new String();

        for (int i = 0; i < secret.length(); i++) {
           letter = String.valueOf(secret.charAt(i));

           if (letters.contains(letter)){
               letters.remove(letter);
           } else { return false;}
        }
        return true;
    }

    private String requestTip(Player player){
        Message.sendMessage(REQUEST_TIP, "", player);
        String tip = Message.readMessage(player.getConnection()).getBody();
        return tip;
    }

    private void showRequestTip(Player player, String tip){
        Message.sendMessage(SHOW_REQUEST_TIP, "", player);
    }
}
