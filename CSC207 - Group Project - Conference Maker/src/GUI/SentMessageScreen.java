package GUI;

import GUI.Interfaces.SentMessageInterface;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.List;

public class SentMessageScreen implements SentMessageInterface {

    /**
     * Creates the screen for a user to see the messages they sent.
     * @param names A list of the current user's username for each message they sent out.
     * @param messages The list of messages that this user has sent out.
     */
    @Override
    public void display(List<String> names, List<String> messages) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Sent Messages");
        window.setMinWidth(300);
        window.setMinHeight(500);

        Label message = new Label("Choose a message to view:");


        VBox layout = new VBox(10);
        layout.getChildren().add(message);
        layout.setAlignment(Pos.CENTER);
        int i = 0;
        for(String name:names){
            Button nameButton = new Button(i+1 + name);
            layout.getChildren().add(nameButton);
            String m = messages.get(i);
            nameButton.setOnAction(e -> {
                AlertPopUp msgScreen = new AlertPopUp();
                msgScreen.display("Message from " + name, m);
            });
            i++;
        }
        Button backButton = new Button("Close");
        backButton.setOnAction(e -> window.close());
        layout.getChildren().add(backButton);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(layout);
        scrollPane.setFitToWidth(true);

        Scene scene = new Scene(scrollPane, 300, 500);
        window.setScene(scene);
        window.showAndWait();
    }
}
