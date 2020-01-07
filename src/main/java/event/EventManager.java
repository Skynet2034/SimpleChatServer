package event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventManager {

    //паттерн наблюдатель

    Map<Events, List<EventListener>> listeners = new HashMap<>();

    public EventManager(Events... operations) {
        for (Events operation : operations) {
            listeners.put(operation, new ArrayList<>());
        }
    }

    public void subscribe(Events eventType, EventListener listener) {
        List<EventListener> users = this.listeners.get(eventType);
        users.add(listener);
    }

    public void unsubscribe(Events eventType, EventListener listener) {
        List<EventListener> users = this.listeners.get(eventType);
        users.remove(listener);
    }

    public void notify(Events eventType, EventListener exclude) {
        List<EventListener> users = this.listeners.get(eventType);
        for (EventListener eventListener : users) {
            if (!eventListener.equals(exclude)) {
                eventListener.update(eventType, exclude.getMessage());
            }
        }
    }
}
