package by.korziuk.web_chat_by_vaadin;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.shared.Registration;

@Route("")
@Push
public class MainView extends VerticalLayout implements AppShellConfigurator {

    private final Storage storage;
    private Registration registration;
    private Grid<Storage.ChatMessage> grid;
    private VerticalLayout chat;
    private HorizontalLayout login;
    private String user = "";

    public MainView(Storage storage) {
        this.storage = storage;
        
        buildLogin();
        buildChat();
    }

    private void buildLogin() {
        login = new HorizontalLayout() {{
            TextField field = new TextField("", "Please identify yourself");
            add(
                    field,
                    new Button("➜"){{
                        addClickListener(click -> {
                            login.setVisible(false);
                            chat.setVisible(true);
                            user = (field.getValue());
                            storage.addRecordJoined(user);
                        });
                        addClickShortcut(Key.ENTER);
                    }}
            );
        }};
        add(login);
    }

    private void buildChat() {
        chat = new VerticalLayout();
        add(chat);
        chat.setVisible(false);

        grid = new Grid<>();
        grid.setItems(storage.getMessages());
        grid.addColumn(new ComponentRenderer<>(this::renderRow))
                .setAutoWidth(true);

        TextField field = new TextField();
        Button button = new Button("➜"){{
            addClickListener(click -> {
                storage.addRecord(user, field.getValue());
                field.clear();
                grid.getDataProvider().refreshAll();
            });
            addClickShortcut(Key.ENTER);
        }};

        chat.add(
                new H3("Web chat"),
                grid,
                new HorizontalLayout(field, button)
        );
    }

    public void onMessage(Storage.ChatEvent event) {
        if (getUI().isPresent()) {
            UI ui = getUI().get();
            ui.getSession().lock();
            ui.beforeClientResponse(grid,ctx -> grid.scrollToEnd());
            ui.access(() -> grid.getDataProvider().refreshAll());
            ui.getSession().unlock();
        }
    }

    private Html renderRow(Storage.ChatMessage message) {
        if (message.getUser().isEmpty()) {
            return new Html("<span>" + "User " + message.getMessage() + " joined the chat room ..." + "</span>");
        } else {
            return new Html("<span>" + message.getUser() + " : " + message.getMessage() + "</span>");
        }
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        registration = storage.attachListener(this::onMessage);
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        registration.remove();
    }
}
