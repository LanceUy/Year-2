package GUI;

import GUI.Interfaces.AlertInputInterface;
import javafx.scene.*;
import javafx.scene.input.KeyCode;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class AlertPopUpLargeInput implements AlertInputInterface {
    private String inputValue;

    /**
     * Creates an alert pop up window with a close button and an alert message and a multi-line input.
     * @param title The title of the window.
     * @param message The message of the alert
     */
    public void display(String title, String message){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        window.setMinHeight(150);

        Label msg = new Label(message);
        Button exitButton = new Button("Close");
        TextArea input = new TextArea();
        input.setPromptText("Enter here");

        input.setOnKeyReleased(e ->{
            if(e.getCode().equals(KeyCode.ENTER)){
                inputValue = input.getText();
                window.close();
            }
            else if(e.getCode().equals(KeyCode.TAB)){
                String newLine = "\n";
                input.appendText(newLine);
            }
        });
        exitButton.setOnAction(e-> window.close());
        VBox layout = new VBox(10);
        layout.getChildren().addAll(msg, input, exitButton);
        layout.setAlignment(Pos.CENTER);
        layout.setFillWidth(false);

        Scene scene = new Scene(layout, 250, 400);
        window.setScene(scene);
        window.showAndWait();
    }

    /**
     * A get method for inputValue
     * @return Returns the input of the user.
     */
    public String getInput(){
        return inputValue;
    }
}
