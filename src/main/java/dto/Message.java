package dto;

import java.util.Date;

public class Message { //Структура сообщения -  отправитель, время отправления, текст сообщения
   public String sender;
    public Date date;
    public String text;

    @Override
    public String toString() {
        return "from: " + sender + '\n' +
                date + '\n' +
                text;
    }

    public Message(String sender, Date date, String text) {
        this.sender = sender;
        this.date = date;
        this.text = text;
    }

}
