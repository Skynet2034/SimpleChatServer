package server;

import event.EventManager;
import event.Events;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp {//основной класс для запуска серверной части

    private EventManager events;

    public ServerApp() {
        this.events = new EventManager(Events.BROADCAST);
    }

    public void broadcastMessageToAllClientsExcludingOriginator(ServerThread originatorServerThread) {
        this.events.notify(Events.BROADCAST, originatorServerThread);
    }

    public void execute() {
        System.out.println("Попытка запуска сервера");
        try (ServerSocket serverSocket = new ServerSocket(10000)) {
            System.out.println("Сервер запущен и ждет соединений");

            while (true) {
                Socket socket = serverSocket.accept();
                ServerThread newClient = new ServerThread(socket, this);
                this.events.subscribe(Events.BROADCAST, newClient);
                newClient.start();
                Thread.sleep(50);
            }

            // System.out.println("Сервер завершил работу");
        } catch (IOException | InterruptedException ex) {
            System.err.println(ex.getMessage());
        } finally {
            System.out.println("Сервер выключен");
        }
    }

    public static void main(String[] args) {
        ServerApp serverApp = new ServerApp();
        serverApp.execute();
    }
}
