package ControllerLayer;

import GUI.Interfaces.AlertInterface;
import GUI.AlertPopUp;
import UseCases.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SpeakerMainScreen implements MainScreen {
    @FXML
    private Button logOutButton;
    @FXML
    private Text welcomeText;
    private Scene mainScene;
    String username;
    SpeakerSystem ss;
    SpeakerManager sm;
    EventScheduler es;
    SpeakerMessageScreen sms;
    AttendeeManager am;
    OrganizerManager om;
    VipManager vm;
    EventsToHtml eth;

    /**
     * Create a ControllerLayer.SpeakerMainScreen object and sets its variables username, am, es, om, and sm to the ones passed in
     * the constructor.
     *
     * @param username String representing a Entities.Person's username.
     * @param am UseCases.AttendeeManager object
     * @param es UseCases.EventScheduler object
     * @param om UseCases.OrganizerManager object
     * @param sm UseCases.SpeakerManager object
     * @param vm UseCases.VipManager object
     */
    public SpeakerMainScreen(String username, AttendeeManager am, EventScheduler es, OrganizerManager om,
                             SpeakerManager sm, VipManager vm, EventsToHtml eth) {
        setInstances(username, am, es, om, sm, vm, eth);
    }
    /**
     * Sets the parameters of Speaker.
     * @param username The username of the Entities.Attendee as a String
     * @param am Instance of UseCases.AttendeeManager with loaded information
     * @param es Instance of UseCases.EventScheduler with loaded information
     * @param om Instance of UseCases.OrganizerManager with loaded information
     * @param sm Instance of UseCases.SpeakerManager with loaded information
     * @param vm Instance of UseCases.VipManager with loaded information
     */
    public void setInstances(String username, AttendeeManager am, EventScheduler es, OrganizerManager om,
                             SpeakerManager sm, VipManager vm, EventsToHtml eth){
        this.ss = new SpeakerSystem(sm);
        this.username = username;
        this.sm = sm;
        this.es = es;
        this.am = am;
        this.om = om;
        this.vm = vm;
        this.eth = eth;
    }
    /**
     * The empty constructor for SpeakerMainScreen. Required for FXML.
     */
    public SpeakerMainScreen(){}

    /**
     * Calls the ReadMessageScreen presenter.
     */
    public void viewMessages() {
        ReadMessageScreen messageScreen = new ReadMessageScreen(am, om, sm, vm, username);
        messageScreen.showMessages();
    }

    /**
     * Calls the SpeakerMainScreen Presenter.
     */
    public void viewTalks() {
        SpeakerMessageScreen messageScreen = new SpeakerMessageScreen(am, om, sm, vm, username);
        messageScreen.sendMessage();
    }

    /**
     * Changes the current screen back to the log in screen.
     */
    public void logOut() {
        Stage stage = (Stage) logOutButton.getScene().getWindow();
        stage.setScene(mainScene);
    }

    /**
     * Creates the screen and its respective controller for an Speakers' main screen but does not open the screen.
     * @return A list of size 2 representing the scene, and controller of this screen.
     * @throws IOException If the FXML file does not exist at that location.
     */
    @Override
    public List<Object> openMainScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GUI/SpeakerScreen.fxml"));
        Parent root = fxmlLoader.load();
        SpeakerMainScreen myPresenter = fxmlLoader.getController();
        myPresenter.setInstances(username, am, es, om, sm, vm ,eth);
        myPresenter.setMainScene(mainScene);
        List<Object> map = new ArrayList<>();
        map.add(new Scene(root));
        map.add(myPresenter);
        return map;
    }

    /**
     * Setter method for mainScene.
     * @param mainScene The scene that represents the login screen.
     */
    public void setMainScene(Scene mainScene){
        this.mainScene = mainScene;
    }

    /**
     * The getter method for welcomeText.
     * @return The text label object representing the welcome text of this screen.
     */
    @Override
    public Text getWelcomeText() {
        return welcomeText;
    }

    /**
     * Saves all events to an HTML file and calls a pop up message.
     */
    public void getAllEvents() {
        eth.saveToHtml(es);
        AlertInterface alert = new AlertPopUp();
        alert.display("Downloaded", "Events saved as HTML file.");
    }
}