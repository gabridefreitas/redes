package com.wordfindr.server;

import com.wordfindr.commom.Message;
import com.wordfindr.commom.Player;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import static com.wordfindr.commom.Commands.CONNECT_PLAYER;

public class ServerApp {

    private static final int MAX_CONNECTED_PLAYERS = 2;

    private static ArrayList<Player> players;

    public static void main(String[] args) throws Exception {

        ServerSocket serverSocket = new ServerSocket(6789);
        players = new ArrayList<>();

        while (true) {
            System.out.println("Server: Connections: " + players.size());

            if (players.size() < MAX_CONNECTED_PLAYERS) {
                System.out.println("Server: Waiting for connection...");
                connect(serverSocket.accept());
            } else {
                System.out.println("Server: Starting game...");

                Game game = new Game(players);
                game.start();

                System.out.println("Server: Finishing game...");
                break;
            }
        }
    }

    private static void connect(Socket connection) {
        Message message = Message.readMessage(connection);

        if (message.getType().equals(CONNECT_PLAYER)) {
            Player player = new Player(message.getBody(), connection);
            Message.sendMessage(CONNECT_PLAYER, "", player);

            players.add(player);
            System.out.printf("Server: Player %s connected...\n", message.getBody());
        } else {
            System.out.printf("Server: Player %s cannot be connected...\n", message.getBody());
        }
    }
}
