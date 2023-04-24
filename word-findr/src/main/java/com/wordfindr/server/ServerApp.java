package com.wordfindr.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import static com.wordfindr.server.Commands.FORCE_GAME_START;

public class ServerApp {

    private static final int MIN_CONNECTED_PLAYERS = 2;
    private static final int MAX_CONNECTED_PLAYERS = 4;

    private static boolean shouldStartGame = false;
    private static ServerSocket serverSocket;
    private static ArrayList<Player> players;

    public static void main(String[] args) throws Exception {

        serverSocket = new ServerSocket(6789);
        players = new ArrayList<>();

        while (true) {
            if (!shouldStartGame) {
                System.out.println("Server: Connections: " + players.size());
                connect();
            } else {
                System.out.println("Server: Starting game...");

                Game game = new Game(players);
                game.start();

                // if (finished) {
                // System.out.println("Server: Game finished. Closing connections...");
                // for (Socket connection : connections) {
                // connection.close();
                // }

                // break;
                // }
            }
        }
    }

    private static void connect() throws Exception {
        if (players.size() < MAX_CONNECTED_PLAYERS) {
            System.out.println("Server: Waiting for connection...");

            shouldStartGame = handleConnection(serverSocket.accept());
        } else {
            shouldStartGame = true;
        }
    }

    private static boolean handleConnection(Socket connection) throws Exception {
        BufferedReader clientBuffer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        DataOutputStream serverStream = new DataOutputStream(connection.getOutputStream());

        String clientInput = clientBuffer.readLine();

        if (clientInput.equals(FORCE_GAME_START.getValue()) && players.size() >= MIN_CONNECTED_PLAYERS) {
            serverStream.writeBytes("Starting game...\n");
            return true;
        } else {
            System.out.printf("Server: Player %s connected. Waiting for more players...\n", clientInput);

            players.add(new Player(clientInput, connection));
            serverStream.writeBytes("Connected to server. Waiting for more players...\n");
            return false;
        }
    }
}
