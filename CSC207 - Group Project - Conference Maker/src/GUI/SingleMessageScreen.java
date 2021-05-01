package GUI;

import ControllerLayer.GUIControllers.SingleMessageScreenController;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class SingleMessageScreen{
    private final SingleMessageScreenController controller = new SingleMessageScreenController();

    /**
     * Creates the screen that allows the user to choose what to do with a single message sent to them.
     * @param title The title of the window.
     * @param msg The instruction for the user,
     */
    public void display(String title, String msg) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(400);
        window.setMinHeight(300);

        Label message = new Label(msg);
        Button exitButton = new Button("Close");
        Button unreadButton = new Button("Mark as unread");
        Button deleteMessageButton = new Button("Delete Message");
        Button archiveMessageButton = new Button("Archive Message");

        archiveMessageButton.setOnAction(e -> {
            controller.setArchive(true);
            window.close();
        });
        deleteMessageButton.setOnAction(e -> {
            AlertPopUpInput alert = new AlertPopUpInput();
            alert.display("Confirmation", "Are you sure? Enter Yes/No.");
            if(alert.getInput().equals("Yes")){
                controller.setDelete(true);
                window.close();
            }
            else if(alert.getInput().equals("No")){
                controller.setDelete(false);
            }
            else{
                alert.display("Confirmation", "Invalid input. Enter Yes/No.");
            }
        });
        unreadButton.setOnAction(e-> {
            controller.setRead(false);
            window.close();
        });
        exitButton.setOnAction(e-> window.close());

        HBox buttonLayout = new HBox(8);
        buttonLayout.getChildren().addAll(unreadButton, deleteMessageButton, archiveMessageButton, exitButton);
        buttonLayout.setAlignment(Pos.CENTER);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(message, buttonLayout);
        layout.setAlignment(Pos.CENTER);
        layout.setFillWidth(true);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(layout);
        scrollPane.setFitToWidth(true);

        Scene scene = new Scene(scrollPane, 500, 300);
        window.setScene(scene);
        window.showAndWait();
    }

    /**
     * A get method for controller.
     * @return Returns the controller that takes the input of this class.
     */
    public SingleMessageScreenController getController(){
        return controller;
    }
}
