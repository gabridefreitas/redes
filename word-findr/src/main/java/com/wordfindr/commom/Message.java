package com.wordfindr.commom;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

import lombok.Data;

@Data
public class Message implements Serializable {

    private Commands type;
    private String body;
    private Player player;

    public Message() {
    }

    public Message(Commands type, String body, Player player) {
        this.type = type;
        this.body = body;
        this.player = player;
    }

    public static Message readMessage(Socket connection) {
        try {
            ObjectInputStream input = new ObjectInputStream(connection.getInputStream());
            Message message = (Message) input.readObject();
            message.getPlayer().setConnection(connection);
            return message;
        } catch (Exception e) {
            System.out.println("Error to read message");
        }
        return new Message();
    }

    public static void sendMessage(Commands type, String body, Player player) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(player.getConnection().getOutputStream());
            outputStream.writeObject(new Message(type, body, player.clone()));
            outputStream.flush();
        } catch (Exception e) {
            System.out.println("Error to send message");
        }
    }
}
