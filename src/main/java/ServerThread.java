import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

    public class ServerThread extends Thread {

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
                    System.out.println(String.format("messageClient - %s", messageClient));

                    //север что-то выполнил

                    String serverMessage = "";
                    if ((serverMessage = fileReader.readLine()) != null) {
                        pw.println(serverMessage);//ответ клиенту
                    }
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            } finally {
                System.out.println("Соединение разорвано");
            }
        }
    }

