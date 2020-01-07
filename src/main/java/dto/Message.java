package dto;

import java.util.Date;

public class Message { //Структура сообщения -  отправитель, время отправления, список получателей, тектс сообщения
    private String sender;
    private Date date;
    private String text;

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

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
