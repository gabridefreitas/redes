package com.wordfindr;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerApp {

    public static void main(String[] args) throws Exception {

        String fraseCliente;
        String fraseMaiusculas;
        ArrayList<Socket> socketConexaoList = new ArrayList<>();

        ServerSocket socketRecepcao = new ServerSocket(6789);

        while (true) {
            System.out.println("Size: " + socketConexaoList.size());

            if (socketConexaoList.size() < 3) {
                System.out.println("Servidor disponivel");

                int index = socketConexaoList.size();

                socketConexaoList.add(socketRecepcao.accept());

                Socket current = socketConexaoList.get(index);

                BufferedReader doCliente = new BufferedReader(new InputStreamReader(current.getInputStream()));
                DataOutputStream paraCliente = new DataOutputStream(current.getOutputStream());
                fraseCliente = doCliente.readLine();
                fraseMaiusculas = fraseCliente.toUpperCase() + '\n';
                paraCliente.writeBytes(fraseMaiusculas);
            } else {
                System.out.println("Servidor cheio");
                break;
            }
        }
    }
}
