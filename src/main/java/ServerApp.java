import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp {

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

            // System.out.println("Сервер завершил работу");
        } catch (IOException | InterruptedException ex) {
            System.err.println(ex.getMessage());
        } finally {
            System.out.println("Сервер выключен");
        }
    }
}
