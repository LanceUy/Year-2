package ControllerLayer;

import GUI.Interfaces.AlertInterface;
import GUI.AlertPopUp;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;


public class MainPresenter {
    @FXML
    private Button exitButton;
    @FXML
    private TextField usernameInput;
    @FXML
    private TextField passwordInput;
    private final LoginType loginType = new LoginType();

    @FXML
    private Button logInButton;

    private Scene mainScene;

    /**
     * The empty constructor for MainPresenter.
     * @throws IOException If the file to be saved to does not exist at that location.
     */
    public MainPresenter() throws IOException {
    }

    /**
     * Checks the credentials of the logged in user and changes the current screen to the screen of a
     * corresponding logged in user.
     * @throws IOException If an FXML file representing a user main screen does not exist at that location.
     */
    public void clickedLogInButton() throws IOException {
        usernameInput.setText("");
        passwordInput.setText("");
        loginType.setMainScene(mainScene);
        List<Object> newMap = loginType.checkLogin();

        if(newMap == null){
            AlertInterface alertPopUp = new AlertPopUp();
            alertPopUp.display("Log in Error", "Incorrect password/username.");
        }
        else{
            Scene newScene = (Scene) newMap.get(0);
            MainScreen presenter = (MainScreen) newMap.get(1);
            Stage stage = (Stage) logInButton.getScene().getWindow();
            presenter.getWelcomeText().setText("Welcome " + loginType.getUsername());
            stage.setScene(newScene);
        }
    }

    /**
     * Changes the current screen back to the log in screen.
     */
    public void clickedExitButton(){
        Stage stage = (Stage) exitButton.getScene().getWindow();
        closeProgram(stage);
    }

    /**
     * Closes the program.
     * @param stage The stage object that represents a window.
     */
    public void closeProgram(Stage stage){
        try {
            loginType.save();
        } catch (IOException ioException) {
            AlertInterface alertPopUp = new AlertPopUp();
            alertPopUp.display("Error", "File to save to does not exist.");
        }
        stage.close();
    }

    /**
     * Sets the username of the current user by calling loginType.
     */
    public void setUsername() {
        loginType.setUsername(usernameInput.getText());
    }

    /**
     * The the password of the current user by calling loginType.
     */
    public void setPassword() {
        loginType.setPassword(passwordInput.getText());
    }

    /**
     * Setter for mainScene
     * @param mainScene The scene representing the login screen.
     */
    public void setMainScene(Scene mainScene){
        this.mainScene = mainScene;
    }
}
