package GUI;

import GUI.Interfaces.CreateAccountInterface;
import javafx.scene.*;
import javafx.scene.input.KeyCode;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class CreatingAccountGUI implements CreateAccountInterface {
    private String usernameValue;
    private String passwordValue;
    private String p1;
    private String p2;

    /**
     * Creates the account creation GUI for an Organizer.
     * @param title The title of the window.
     * @param message The instruction for the user.
     */
    public void display(String title, String message){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(300);
        window.setMinHeight(400);

        Label msg = new Label(message);
        Button exitButton = new Button("Close");
        Button createButton = new Button("Create Account");

        TextField username = new TextField();
        username.setPromptText("Username");
        Label usernameLabel = new Label("Username:");

        PasswordField pass1 = new PasswordField();
        pass1.setPromptText("Enter password");
        Label passwordLabel1 = new Label("Password:");

        PasswordField pass2 = new PasswordField();
        pass2.setPromptText("Enter password again");
        Label passwordLabel2 = new Label("Password again:");


        username.setOnKeyReleased(e ->{
            if(e.getCode().equals(KeyCode.ENTER)) {
                usernameValue = username.getText();
            }
        });

        pass1.setOnKeyReleased(e ->{
            if(e.getCode().equals(KeyCode.ENTER)) {
                p1 = pass1.getText();
            }
        });

        pass2.setOnKeyReleased(e ->{
            if(e.getCode().equals(KeyCode.ENTER)) {
                p2 = pass2.getText();
            }
        });

        exitButton.setOnAction(e-> window.close());

        createButton.setOnAction(e -> {
            if(p1==null || p2 == null || usernameValue==null){
                AlertPopUp alert = new AlertPopUp();
                alert.display("Error", "Must fill out all fields.");
            }
            else if(p1.equals(p2)){
                passwordValue = p1;
                window.close();
            }
            else{
                AlertPopUp alert = new AlertPopUp();
                alert.display("Error", "Passwords do not match");
                pass1.clear();
                pass2.clear();
            }
        });

        HBox usernameLayout = new HBox(8);
        usernameLayout.getChildren().addAll(usernameLabel, username);
        usernameLayout.setAlignment(Pos.CENTER);

        HBox pass1Layout = new HBox(8);
        usernameLayout.getChildren().addAll(passwordLabel1, pass1);
        usernameLayout.setAlignment(Pos.CENTER);

        HBox pass2Layout = new HBox(8);
        usernameLayout.getChildren().addAll(passwordLabel2, pass2);
        usernameLayout.setAlignment(Pos.CENTER);


        HBox buttonLayout = new HBox(8);
        buttonLayout.getChildren().addAll(createButton, exitButton);
        buttonLayout.setAlignment(Pos.CENTER);

        VBox layout = new VBox(20);
        layout.getChildren().addAll(msg, usernameLayout, pass1Layout, pass2Layout, buttonLayout);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 1000, 300);
        window.setScene(scene);
        window.showAndWait();
    }
    /**
     * A get method for passwordValue
     * @return Returns the passwordValue input taken.
     */
    public String getPasswordValue(){
        return passwordValue;
    }
    /**
     * A get method for usernameValue
     * @return Returns the usernameValue input taken.
     */
    public String getUsernameValue(){
        return usernameValue;
    }
}
