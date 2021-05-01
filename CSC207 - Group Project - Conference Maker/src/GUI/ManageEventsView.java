package GUI;

import ControllerLayer.GUIControllers.EventViewController;
import ControllerLayer.GUIControllers.ManageEventsViewController;
import GUI.Interfaces.ManageEventsViewInterface;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.List;

public class ManageEventsView implements ManageEventsViewInterface {
    private EventViewController eventController;
    private final ManageEventsViewController controller = new ManageEventsViewController();

    /**
     * Creates the screen for a user to manage the events they are currently signed up for
     * @param title The title of the window.
     * @param events The list of event IDs that this user is currently signed up for.
     */
    @Override
    public void display(String title, List<String> events){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(300);
        window.setMinHeight(500);

        Label message = new Label("Here are your events.\n Click one to cancel it.");
        Button addEventButton = new Button("Register Event");
        Button saveEventButton = new Button("All Events");
        Button exitButton = new Button("Close");
        addEventButton.setWrapText(true);
        saveEventButton.setWrapText(true);
        exitButton.setWrapText(true);

        saveEventButton.setOnAction(e -> {
            controller.setSaveEvents(true);
            window.close();
        });

        addEventButton.setOnAction(e -> {
            AlertPopUpInput alert = new AlertPopUpInput();
            alert.display("Register For an Event", "Enter the ID of the event.");
            controller.setAddEvent(true);
            controller.setEventID(alert.getInput());
            window.close();
        });
        exitButton.setOnAction(e -> window.close());

        HBox buttonLayout = new HBox(8);
        buttonLayout.getChildren().addAll(addEventButton, saveEventButton,exitButton);
        buttonLayout.setAlignment(Pos.CENTER);
        addEventButton.prefWidthProperty().bind(buttonLayout.widthProperty());
        saveEventButton.prefWidthProperty().bind(buttonLayout.widthProperty());
        exitButton.prefWidthProperty().bind(buttonLayout.widthProperty());


        VBox layout = new VBox(10);
        layout.getChildren().add(message);
        layout.setAlignment(Pos.CENTER);
        for(String event:events){
            String id = event.substring(event.indexOf(": ")+2,event.indexOf(", Room"));
            Button nameButton = new Button(id);
            layout.getChildren().add(nameButton);
            nameButton.setOnAction(e -> {
                EventView eventScreen = new EventView();
                eventScreen.display("Event " + id, event);
                eventController = eventScreen.getController();
                eventController.setEventID(id);
                window.close();
            });
        }
        layout.getChildren().add(buttonLayout);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(layout);
        scrollPane.setFitToWidth(true);

        Scene scene = new Scene(scrollPane, 300, 500);

        window.setScene(scene);
        window.showAndWait();
    }

    /**
     * A get method for controller.
     * @return Returns the controller that takes the input of this UI.
     */
    public ManageEventsViewController getController(){
        return controller;
    }
    /**
     * A get method for eventController.
     * @return Returns the controller that takes the input of the EventView UI.
     */
    public EventViewController getEventController() {
        return eventController;
    }
}
