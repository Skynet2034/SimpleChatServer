import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp {

    private static final Logger log = LoggerFactory.getLogger(ServerApp.class);

    public static void main(String[] args) {

        log.info("Попытка запуска сервера");
        try (ServerSocket serverSocket = new ServerSocket(10000)) {
            log.info("Сервер запущен и ждет соединений");

            while (true) {
                Socket socket = serverSocket.accept();
                ServerThread newClient = new ServerThread(socket);
                newClient.start();
                Thread.sleep(50);
            }

        } catch (IOException | InterruptedException ex) {
            log.error("Произошла ошибка {}", ex.getMessage());
        } finally {
            log.info("Сервер выключен");
        }
    }
}
