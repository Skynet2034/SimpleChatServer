package server;

import dto.Message;
import event.EventListener;
import event.Events;

import java.io.*;
import java.net.Socket;
import java.util.Date;

public class ServerThread extends Thread implements EventListener { //поток для каждого клиента

    private final Socket socket;
    private String nick;
    private BufferedReader reader;
    private PrintWriter writer;
    private Message message;
    private ServerApp server;

    public ServerThread(Socket socket, ServerApp serverApp) {
        this.socket = socket;
        this.server = serverApp;
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
            reader = br;
            PrintWriter pw = new PrintWriter(bw, true);
            writer = pw;
            this.nick = reader.readLine();//первое сообщение от клиента - его логин/никнейм, для идентификации юзера

            String messageText = "";
            while (!messageText.equalsIgnoreCase("exit")) {
                messageText = reader.readLine();
                message = new Message(this.nick, new Date(), messageText);
                //посылаем сообщения всем кроме текущего
                this.server.broadcastMessageToAllClientsExcludingOriginator(this);
                Thread.sleep(50);
                writer.println("from server to " + nick + ": your message has been sent");
            }

            writer.println("shutdown");
            socket.close();

        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            System.out.println("Соединение разорвано");
        }
    }

    @Override
    public void update(Events eventType, Message message) {
        if (eventType == Events.BROADCAST) {
            this.writer.println(String.format("######\nto:%s\n%s\n######", nick, message));
        }
    }

    @Override
    public Message getMessage() {
        return this.message;
    }
}

