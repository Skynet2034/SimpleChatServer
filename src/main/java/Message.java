import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

public class Message { //Структура сообщения -  отправитель, время отправления, список получателей, тектс сообщения
    public String sender;
    public CopyOnWriteArraySet<String> recepients =new CopyOnWriteArraySet<>();
    public Date date;
    public String text;

    @Override
    public String toString() {
        return "from: "+sender + '\n' +
                date+'\n'+
                 text;
    }

    public Message(String sender, CopyOnWriteArraySet<String> recepients, Date date, String text) {
        this.sender = sender;
        this.recepients = recepients;
        this.date = date;
        this.text = text;
    }
}
