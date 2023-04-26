package com.wordfindr.client;

import com.wordfindr.commom.Message;
import com.wordfindr.commom.Player;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import static com.wordfindr.commom.Commands.*;

public class ClientApp {

    public static void main(String[] args) throws Exception {

        Socket socket = new Socket("127.0.0.1", 6789);
        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("What is your name? ");
        Player player = new Player(buffer.readLine(), socket);
        Message.sendMessage(CONNECT_PLAYER, player.getName(), player);

        while (true) {
            Message message = Message.readMessage(socket);
            player = message.getPlayer();

            switch (message.getType()) {
                case CONNECT_PLAYER:
                    System.out.println("Connected to server. Waiting for more players...");
                    break;
                case SET_SECRET:
                    System.out.print("You are challenger. Define secret word: ");
                    Message.sendMessage(SET_SECRET, buffer.readLine(), player);
                    break;
                case GET_HINT:
                    break;
                case SECRET_GUESS:
                    System.out.print("Take your guess: ");
                    Message.sendMessage(SECRET_GUESS, buffer.readLine(), player);
                    break;
                default:
                    break;
            }

            if (message.getType().equals(FINISH_GAME)) {
                socket.close();
                break;
            }
        }
    }
}
