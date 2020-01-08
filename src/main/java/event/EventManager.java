package event;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class EventManager {

    //паттерн наблюдатель

    Map<Events, CopyOnWriteArraySet<EventListener>> listeners = new ConcurrentHashMap<>();

    public EventManager(Events... operations) {
        for (Events operation : operations) {
            listeners.put(operation, new CopyOnWriteArraySet<>());
        }
    }

    public void subscribe(Events eventType, EventListener listener) {
        CopyOnWriteArraySet<EventListener> users = this.listeners.get(eventType);
        users.add(listener);
    }

    public void unsubscribe(Events eventType, EventListener listener) {
        CopyOnWriteArraySet<EventListener> users = this.listeners.get(eventType);
        users.remove(listener);
    }

    public void notify(Events eventType, EventListener exclude) {
        CopyOnWriteArraySet<EventListener> users = this.listeners.get(eventType);
        for (EventListener eventListener : users) {
            if (!eventListener.equals(exclude)) {
                eventListener.update(eventType, exclude.getMessage());
            }
        }
    }
}
