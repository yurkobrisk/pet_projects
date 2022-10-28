package by.korziuk.web_chat_by_vaadin;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("")
public class MainView extends VerticalLayout {
    private VerticalLayout listVerticalLayout;
    private TextField textField;
    private Button button;

    public MainView() {
        listVerticalLayout = new VerticalLayout(new Text("Text in verticalLayout"));
        textField = new TextField();
        button = new Button("Just button");

        add(
                listVerticalLayout,
                textField,
                button
        );
    }
}
