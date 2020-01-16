import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread {

    private final static Logger log = LoggerFactory.getLogger(ServerThread.class);

    private final Socket socket;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader fileReader = new BufferedReader(new FileReader("./Response"));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

            PrintWriter pw = new PrintWriter(bw, true);

            String messageClient = "";
            //диалог клиента-сервера
            while ((messageClient = br.readLine()) != null) {//вопрос/сообщение от клиента
                log.info("messageClient - {}", messageClient);

                //север что-то выполнил

                String serverMessage = "";
                if ((serverMessage = fileReader.readLine()) != null) {
                    pw.println(serverMessage);//ответ клиенту
                }
            }
        } catch (IOException e) {
            log.info("Произошла ошибка {}", e.getMessage());
        } finally {
            log.info("Соединение разорвано");
        }
    }
}
