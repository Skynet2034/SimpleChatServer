import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerThread extends Thread { //поток для каждого клиента

        private final Socket socket;
        public String nick;
        public BufferedReader reader;
        public PrintWriter writer;

        public ServerThread(Socket socket) {
            this.socket = socket;
        }

public  synchronized void  send(Message message) //метод для рассылки клиентского сообщения всем остальным получателям
{
    Iterator it=message.recepients.iterator();
    while(it.hasNext())
    {
        String r=it.next().toString();
        ServerThread recepient=ServerApp.clients.get(r);
        recepient.writer.println("to:"+r);
        recepient.writer.println(message);
    }
}
        @Override
        public void run() {
              try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
reader=br;
                PrintWriter pw = new PrintWriter(bw, true);
                writer=pw;
                this.nick=reader.readLine();//первое сообщение от клиента - его логин/никнейм, для идентификации юзера
                ServerApp.clients.put(nick, this);

                    while (true) {
                        String messageText=reader.readLine();
                        if (messageText.equalsIgnoreCase("exit")) {
                            writer.println("shutdown");
                            break;
                        }
                        CopyOnWriteArraySet<String> recepients=new CopyOnWriteArraySet<>();
                        recepients.addAll(ServerApp.clients.keySet());
                        recepients.remove(this.nick); //список получателей сообщения - всем, кроме отправителя
                        Message messageClient=new Message(this.nick, recepients, new Date(), messageText);
                        send(messageClient);
                        writer.println("to: "+nick+" message sent");
                                      }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            } finally {
                       System.out.println("Соединение разорвано");
            }
        }
    }

