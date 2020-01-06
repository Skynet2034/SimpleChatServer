import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicInteger;

public class ClientStub extends Thread { //заглушка клиентской чатси - для тестовых целей
    public String nick;
    ClientStub(String nick)
    {
        this.nick=nick;
    }

    public void run() {

        System.out.println("Инициализация подключения к серверу");

        try (Socket socket = new Socket("localhost", 10000);
             BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // BufferedReader fileReader = new BufferedReader(new FileReader("./httpPackage"));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

            System.out.println("Соединение установлено");

            PrintWriter pw = new PrintWriter(bw, true);
            pw.println(nick);
            String[] messages = {"Message1", "Message2", "exit"};//сообщения от клиента, последнее -команда выхода/завершенимя сеанса
            AtomicInteger i=new AtomicInteger(0);
            while (true) {
                String clientMessage = messages[i.get()];
                pw.println(clientMessage);
                String messageServer ="";
                String st=" ";
                while ((st!=null)&&br.ready()) {
                    st=br.readLine();
                    messageServer+=st+"\n";
                    }
                System.out.println(messageServer);//ответ сервера
                i.getAndIncrement();
                if (i.intValue()>2) break;
                Thread.sleep(50);
            }
        }
        catch (UnknownHostException | FileNotFoundException e) {
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


