package com.wordfindr.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientApp {

    public static void main(String[] args) throws Exception {

        Socket socket = new Socket("127.0.0.1", 6789);
        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
        DataOutputStream user = new DataOutputStream(socket.getOutputStream());
        BufferedReader server = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        System.out.print("Digite uma frase: ");
        user.writeBytes(buffer.readLine() + '\n');

        System.out.println(server.readLine());

        while (true) {
            System.out.println(server.readLine());
            user.writeBytes(buffer.readLine() + '\n');
        }

        // socket.close();
    }
}
