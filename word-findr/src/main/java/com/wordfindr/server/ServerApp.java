package com.wordfindr.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerApp {
    private static final int MIN_CONNECTED_PLAYERS = 2;
    private static final int MAX_CONNECTED_PLAYERS = 4;
    private static final String FORCE_GAME_START = "01";

    private static boolean shouldStartGame = false;
    private static ServerSocket serverSocket;
    private static ArrayList<Socket> connections;
    private static ArrayList<String> playersName;

    public static void main(String[] args) throws Exception {
        serverSocket = new ServerSocket(6789);
        connections = new ArrayList<>();

        while (true) {
            if (!shouldStartGame) {
                System.out.println("Server: Connections: " + connections.size());

                int connectedPlayers = connections.size();

                connect(connectedPlayers);
            } else {
                System.out.println("Server: Starting game...");

                ArrayList<Player> players = new ArrayList<>();

                for (int i = 0; i < playersName.size(); i++) {
                    int playerId = connections.get(i).hashCode();
                    String playerName = playersName.get(i);

                    players.add(new Player(playerId, playerName));
                }

                Game game = new Game(players);

                Player challenger = game.setChallenger();

                System.out.printf("Server: %s is challenger\n", challenger.getName());

                String secret = requestSecret(challenger);

                game.start(secret, connections);

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

    private static String requestSecret(Player challenger) throws IOException {
        Socket challengerConnection = connections.stream()
                .filter(connection -> connection.hashCode() == challenger.getId()).findFirst().get();

        DataOutputStream serverStream = new DataOutputStream(challengerConnection.getOutputStream());

        serverStream.writeBytes("You are challenger. Define secret word:\n");

        BufferedReader clientBuffer = new BufferedReader(
                new InputStreamReader(challengerConnection.getInputStream()));

        String secretWord = clientBuffer.readLine();

        System.out.printf("Server: Secret word is %s\n", secretWord);

        return secretWord;
    }

    private static void connect(int connectedPlayers) throws IOException, Exception {
        if (connectedPlayers < MAX_CONNECTED_PLAYERS) {
            System.out.println("Server: Waiting for connection...");

            connections.add(serverSocket.accept());

            Socket current = connections.get(connectedPlayers);

            shouldStartGame = handleConnection(current, connectedPlayers);
        } else {
            shouldStartGame = true;
        }
    }

    private static boolean handleConnection(Socket connection, int connectedPlayers) throws Exception {
        BufferedReader clientBuffer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        DataOutputStream serverStream = new DataOutputStream(connection.getOutputStream());

        String clientInput = clientBuffer.readLine();

        if (clientInput.equals(FORCE_GAME_START) && connectedPlayers >= MIN_CONNECTED_PLAYERS) {
            serverStream.writeBytes("Starting game...\n");
            return true;
        } else {
            System.out.printf("Server: Player %s connected. Waiting for more players...\n", clientInput);

            playersName.add(clientInput);
            serverStream.writeBytes("Connected to server. Waiting for more players...\n");
            return false;
        }
    }
}
