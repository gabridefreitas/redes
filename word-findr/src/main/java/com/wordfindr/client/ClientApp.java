package com.wordfindr.client;

import static com.wordfindr.commom.Commands.CONNECT;
import static com.wordfindr.commom.Commands.FINISH;
import static com.wordfindr.commom.Commands.GUESS;
import static com.wordfindr.commom.Commands.HINT;
import static com.wordfindr.commom.Commands.SECRET;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import com.wordfindr.commom.Message;
import com.wordfindr.commom.Player;

public class ClientApp {
    private static final String EMPTY_TEXT = "";

    public static void main(String[] args) throws Exception {

        Socket socket = new Socket("127.0.0.1", 6789);
        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
        boolean isChallenger = false;

        Utils.log("Welcome to Word Findr!");
        Utils.input("What is your name? ");

        Player player = new Player(buffer.readLine().toUpperCase(), socket);

        Message.sendMessage(CONNECT, player.getName(), player);

        String hint = EMPTY_TEXT;

        while (true) {
            Message message = Message.readMessage(socket);
            player = message.getPlayer();

            Utils.renderHeader(player, hint, isChallenger);

            switch (message.getType()) {
                case CONNECT:
                    Utils.log("Connected to server. Waiting for more players...");
                    break;
                case SECRET:
                    Utils.input("You are challenger. Define secret word: ");
                    Message.sendMessage(SECRET, buffer.readLine().toUpperCase(), player);
                    isChallenger = true;
                    break;
                case HINT:
                    if (message.getBody().equals(EMPTY_TEXT)) {
                        Utils.input("Provide a hint for secret: ");
                        Message.sendMessage(HINT, buffer.readLine().toUpperCase(), player);
                    } else {
                        Utils.log(message.getBody());
                    }
                    break;
                case GUESS:
                    if (message.getBody().equals(EMPTY_TEXT)) {
                        Utils.input("Take your guess (Insert a letter): ");

                        String guess = buffer.readLine().toUpperCase();
                        String errorMessage = Utils.validateGuess(guess);

                        if (!errorMessage.isBlank()) {
                            Utils.log(errorMessage);
                        } else {
                            if (guess.equals("1")){
                                Message.sendMessage(HINT, "", player);
                            } else {
                                Message.sendMessage(GUESS, guess, player);
                            }
                        }

                    } else {
                        Utils.log(message.getBody());
                    }
                    break;
                default:
                    break;
            }

            if (message.getType().equals(FINISH)) {
                Utils.log(message.getBody());
                socket.close();
                break;
            }
        }
    }
}
