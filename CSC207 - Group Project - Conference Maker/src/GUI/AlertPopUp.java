package GUI;

import GUI.Interfaces.AlertInterface;
import javafx.scene.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class AlertPopUp implements AlertInterface {

    /**
     * Creates an alert pop up window with a close button and an alert message.
     * @param title The title of the window.
     * @param message The message of the alert
     */
    public void display(String title, String message){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        window.setMinHeight(150);

        Label errorMessage = new Label(message);
        Button exitButton = new Button("Close");
        errorMessage.setWrapText(true);
        errorMessage.setAlignment(Pos.CENTER);

        exitButton.setOnAction(e-> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(errorMessage, exitButton);
        layout.setAlignment(Pos.CENTER);
        layout.setFillWidth(true);

        Scene scene = new Scene(layout, 600, 300);
        errorMessage.prefWidthProperty().bind(scene.widthProperty());
        window.setScene(scene);
        window.showAndWait();
    }
}
