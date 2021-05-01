package GUI;

import ControllerLayer.GUIControllers.EventViewController;
import javafx.scene.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class EventView{
    private final EventViewController controller = new EventViewController();

    /**
     * Creates the screen for when a user looks at the details of a single even they signed up for.
     * @param title The title of the window.
     * @param message The instruction for the user.
     */
    public void display(String title, String message){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(300);
        window.setMinHeight(150);

        Label msg = new Label(message);
        Button exitButton = new Button("Close");
        Button cancelButton = new Button("Cancel event");
        msg.setWrapText(true);

        cancelButton.setOnAction(e ->{
            controller.setCancel(true);
            window.close();
        });
        exitButton.setOnAction(e-> window.close());

        HBox buttonLayout = new HBox(8);
        buttonLayout.getChildren().addAll(cancelButton, exitButton);
        cancelButton.prefWidthProperty().bind(buttonLayout.widthProperty());
        exitButton.prefWidthProperty().bind(buttonLayout.widthProperty());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(msg, buttonLayout);
        layout.setAlignment(Pos.CENTER);
        layout.setFillWidth(true);

        Scene scene = new Scene(layout, 250, 150);
        buttonLayout.prefWidthProperty().bind(scene.widthProperty());
        msg.prefWidthProperty().bind(scene.widthProperty());
        window.setScene(scene);
        window.showAndWait();
    }

    /**
     * A get method for controller.
     * @return Returns the controller that takes the input of this class
     */
    public EventViewController getController(){
        return controller;
    }
}
