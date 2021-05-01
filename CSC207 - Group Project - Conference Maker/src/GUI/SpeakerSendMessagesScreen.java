package GUI;

import GUI.Interfaces.SendMessagesInterface;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class SpeakerSendMessagesScreen implements SendMessagesInterface {
    private String message;
    private final List<String> checkedTalks = new ArrayList<>();

    /**
     * Creates the screen that allows a speaker to choose which talks they would like to message.
     * @param talks The list of the event IDs that this speaker is speaking at.
     */
    @Override
    public void display(List<String> talks) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Send a Message");
        window.setMinWidth(350);
        window.setMinHeight(500);

        Label message = new Label("Check the Talks that you would like to message, then click \"Send Message\"");
        message.setWrapText(true);


        VBox layout = new VBox(10);
        layout.getChildren().add(message);
        layout.setAlignment(Pos.CENTER);
        List<CheckBox> checked = new ArrayList<>();
        List<CheckBox> allBox = new ArrayList<>();
        for(String talk:talks){
            CheckBox checkBox = new CheckBox(talk);
            allBox.add(checkBox);
            layout.getChildren().add(checkBox);
            checkBox.setOnAction(e -> {
                if(checkBox.isSelected()) {
                    checked.add(checkBox);
                    checkedTalks.add(checkBox.getText());
                }
                else{
                    checked.remove(checkBox);
                    checkedTalks.remove(checkBox.getText());
                }
                System.out.println(checkedTalks);
            });
        }
        Button sendMessageButton = new Button("Send Message");
        Button checkAllButton = new Button(("Check/Uncheck All"));
        Button closeButton = new Button("Close");


        closeButton.setOnAction(e -> window.close());
        checkAllButton.setOnAction(e ->{
            boolean allSame = true;
            if((allBox.size() != checked.size()) && (checked.size() != 0)){
                allSame = false;
            }
            if(allSame) {
                for (CheckBox b : allBox) {
                    b.setSelected(!b.isSelected());
                    if(b.isSelected()){
                        checked.add(b);
                        checkedTalks.add(b.getText());
                    }
                    else{
                        checked.remove(b);
                        checkedTalks.remove(b.getText());
                    }
                }
            }
            else{
                for (CheckBox b : allBox) {
                    b.setSelected(true);
                    if(!checked.contains(b)){
                        checked.add(b);
                        checkedTalks.add(b.getText());
                    }
                }
            }
            System.out.println(checkedTalks);
        });

        sendMessageButton.setOnAction(e -> {
            if(checkedTalks.equals(new ArrayList<>())){
                AlertPopUp alert = new AlertPopUp();
                alert.display("Error", "No talks selected!");
            }
            else {
                AlertPopUpLargeInput alert = new AlertPopUpLargeInput();
                alert.display("Sending Message", "Type your message.\n Press \"Enter\" when done.");
                this.message = alert.getInput();
                window.close();
            }
        });

        HBox buttonLayout = new HBox(8);
        buttonLayout.setAlignment(Pos.CENTER);
        buttonLayout.getChildren().addAll(checkAllButton, sendMessageButton, closeButton);
        layout.getChildren().add(buttonLayout);


        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(layout);
        scrollPane.setFitToWidth(true);

        Scene scene = new Scene(scrollPane, 350, 500);
        message.prefWidthProperty().bind(scene.widthProperty());
        window.setScene(scene);
        window.showAndWait();
    }

    /**
     * A get method for message.
     * @return Returns the message the speaker wants to send.
     */
    @Override
    public String getMessage() {
        return message;
    }

    /**
     * A get method for checkedTalks.
     * @return Returns a list of event IDs of the talks the speaker likes to message.
     */
    @Override
    public List<String> getReceivers(){
        return checkedTalks;
    }
}
