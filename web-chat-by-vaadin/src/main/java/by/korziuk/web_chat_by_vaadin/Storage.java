package by.korziuk.web_chat_by_vaadin;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventBus;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.shared.Registration;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class Storage {

    private Queue<ChatMessage> messages = new ConcurrentLinkedQueue<>();
    private ComponentEventBus eventBus = new ComponentEventBus(new Span());

    public Queue<ChatMessage> getMessages() {
        return messages;
    }

    public static class ChatMessage {
        String user;
        String message;

        public ChatMessage(String name, String content) {
            this.user = name;
            this.message = content;
        }

        public String getUser() {
            return user;
        }

        public String getMessage() {
            return message;
        }
    }

    public static class ChatEvent extends ComponentEvent<Span> {
        public ChatEvent() {
            super(new Span(), false);
        }
    }

    public void addRecord(String name, String message) {
        messages.add(new ChatMessage(name, message));
        eventBus.fireEvent(new ChatEvent());
    }

    public void addRecordJoined(String user) {
        messages.add(new ChatMessage("", user));
        eventBus.fireEvent(new ChatEvent());
    }

    public Registration attachListener(ComponentEventListener<ChatEvent> messageListener) {
        return eventBus.addListener(ChatEvent.class, messageListener);
    }

    public int size() {
        return messages.size();

    }
}
