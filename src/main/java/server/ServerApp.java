package server;

import event.EventManager;
import event.Events;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp {//основной класс для запуска серверной части

    private static EventManager events;

   static {
        events = new EventManager(Events.BROADCAST);
    }
    public static void subscribe(ServerThread client)
    {
        events.subscribe(Events.BROADCAST, client);
    }

    public static void unsubscribe(ServerThread client)
    {
        events.unsubscribe(Events.BROADCAST, client);
    }

    public static void send(ServerThread originatorServerThread) {
        events.notify(Events.BROADCAST, originatorServerThread);
    }

   public static void main(String[] args) {
            System.out.println("Попытка запуска сервера");
            try (ServerSocket serverSocket = new ServerSocket(10000)) {
                System.out.println("Сервер запущен и ждет соединений");

                while (true) {
                    Socket socket = serverSocket.accept();
                    ServerThread newClient = new ServerThread(socket);
                    newClient.start();
                    Thread.sleep(50);
                }


            } catch (IOException | InterruptedException ex) {
                System.err.println(ex.getMessage());
            } finally {
                System.out.println("Сервер выключен");
            }
        }
    }

