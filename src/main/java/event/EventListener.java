package event;

import dto.Message;

public interface EventListener {

    void update(Events eventType, Message message);

    Message getMessage();
}
