package client;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientStub extends Thread { //заглушка клиентской чатси - для тестовых целей

    private String nick;

    ClientStub(String nick) {
        this.nick = nick;
    }

    @Override
    public void run() {
        System.out.println("Инициализация подключения к серверу");

        try (Socket socket = new Socket("localhost", 10000);
             BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             // BufferedReader fileReader = new BufferedReader(new FileReader("./httpPackage"));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

            System.out.println("Соединение установлено");

            PrintWriter pw = new PrintWriter(bw, true);
            pw.println(nick);
            Thread.sleep(1000);
            String[] messages = {"Message1" + nick, "Message2" + nick, "exit"};//сообщения от клиента, последнее -команда выхода/завершенимя сеанса
            while (true) {
                for (String clientMessage : messages) {
                    pw.println(clientMessage);
                }
                String messageServer = "";
                while ((messageServer = br.readLine()) != null) {
                    System.out.println(messageServer);
                }
                Thread.sleep(50);
            }
        } catch (UnknownHostException | FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Соединение разорвано");
        }
    }
}


