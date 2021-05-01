package ControllerLayer;

import ControllerLayer.GUIControllers.ManageEventController;
import GUI.*;
import GUI.Interfaces.AlertInterface;
import GUI.Interfaces.CreateAccountInterface;
import GUI.Interfaces.ManageEventsInterface;
import UseCases.*;
import javafx.event.ActionEvent;
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

public class OrganizerMainScreen implements MainScreen{
    public Button viewEventsButton;
    @FXML
    private Button backButton;
    @FXML
    private Button logOutButton;
    @FXML
    private Text welcomeText;
    private Scene mainScene;
    private Scene organizerScene;
    AttendeeManager am;
    EventScheduler es;
    OrganizerManager om;
    OrganizerSystem os;
    SpeakerManager sm;
    VipManager vm;
    String username;
    EventsToHtml eth;

    /**
     * Create an ControllerLayer.OrganizerMainScreen object and sets its variables username, am, es, om, and sm to the ones passed in
     * the constructor.
     * @param username String representing a Entities.Person's username.
     * @param am UseCases.AttendeeManager object
     * @param es UseCases.EventScheduler object
     * @param om UseCases.OrganizerManager object
     * @param sm UseCases.SpeakerManager object
     * @param vm UseCases.VipManager object
     */
    public OrganizerMainScreen(String username, AttendeeManager am, EventScheduler es, OrganizerManager om,
                               SpeakerManager sm, VipManager vm, EventsToHtml eth){
        setInstances(username, am, es, om, sm, vm, eth);
    }
    /**
     * Sets the parameters of Organizer.
     * @param username The username of the Entities.Attendee as a String
     * @param am Instance of UseCases.AttendeeManager with loaded information
     * @param es Instance of UseCases.EventScheduler with loaded information
     * @param om Instance of UseCases.OrganizerManager with loaded information
     * @param sm Instance of UseCases.SpeakerManager with loaded information
     * @param vm Instance of UseCases.VipManager with loaded information
     */
    public void setInstances(String username, AttendeeManager am, EventScheduler es, OrganizerManager om,
                        SpeakerManager sm, VipManager vm, EventsToHtml eth){
        this.username = username;
        this.am = am;
        this.es = es;
        this.om = om;
        this.sm = sm;
        this.vm = vm;
        this.eth = eth;
        this.os = new OrganizerSystem(am, es, om, sm, vm);
    }
    /**
     * The empty constructor for AttendeeMainScreen. Required for FXML.
     */
    public OrganizerMainScreen(){}

    /**
     * Calls the ReadMessageScreen presenter.
     */
    public void viewMessages() {
        ReadMessageScreen messageScreen = new ReadMessageScreen(am, om, sm, vm, username);
        messageScreen.showMessages();
    }

    /**
     *Creates the screen that allows the Organizer to manage events.
     */
    public void viewEvents() {
        List<String> events = os.getEventList();

        ManageEventsInterface screen = new ManageEventsScreen();
        screen.display(events);
        ManageEventController controller = screen.getController();
        AlertInterface alert = new AlertPopUp();
        if(controller.getAddEvent()){
            boolean created = os.createEvent(controller.getRoomNum(), controller.getId(), controller.getTime(),
                    controller.getListSpeakerUsername(), controller.getVIP(), controller.getDuration(),
                    controller.getCapacity());
            if(created){
                alert.display("Success!", "Event Created.");
            }
            else{
                alert.display("Error", "Event could not be created.");
            }
        }
        if(controller.getCancelEvent()){
            boolean created = os.cancelEvent(controller.getId());
            if(created){
                alert.display("Success!", "Event Cancelled.");
            }
            else{
                alert.display("Error", "Event could not be cancelled.");
            }
        }
        if(controller.getDownloadEvent()){
            eth.saveToHtml(es);
            alert.display("Success!", "Events downloaded as HTML");
        }
    }

    /**
     * Creates the screen that allows an organizer to choose which type of account to create.
     * @throws IOException If the FXML file for this screen is not at the specified location.
     */
    public void createAccount() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GUI/CreateAccountScreen.fxml"));
        Parent root = fxmlLoader.load();
        OrganizerMainScreen myPresenter = fxmlLoader.getController();
        myPresenter.setInstances(username, am, es, om, sm, vm ,eth);
        myPresenter.setMainScene(mainScene);
        Stage window = (Stage) logOutButton.getScene().getWindow();
        myPresenter.setOrganizerScene(window.getScene());
        window.setScene(new Scene(root));
    }

    /**
     * Calls OrganizerMessageScreen.
     */
    public void sendMessage() {
        OrganizerMessageScreen messageScreen = new OrganizerMessageScreen(username, am, om, sm, vm);
        messageScreen.sendMessage();
    }

    /**
     * Sets the current screen back to the login screen.
     */
    public void logOut() {
        Stage stage = (Stage) logOutButton.getScene().getWindow();
        stage.setScene(mainScene);
    }

    /**
     * Creates the screen and its respective controller for an Organizer's main screen but does not open the screen.
     * @return A list of size 2 representing the scene, and controller of this screen.
     * @throws IOException If the FXML file does not exist at the specified location.
     */
    @Override
    public List<Object> openMainScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GUI/OrganizerScreen.fxml"));
        Parent root = fxmlLoader.load();
        OrganizerMainScreen myPresenter = fxmlLoader.getController();
        myPresenter.setInstances(username, am, es, om, sm, vm ,eth);
        myPresenter.setMainScene(mainScene);
        List<Object> map = new ArrayList<>();
        map.add(new Scene(root));
        map.add(myPresenter);
        return map;
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
     * Setter method for mainScene.
     * @param mainScene The scene that represents the login screen.
     */
    public void setMainScene(Scene mainScene){
        this.mainScene = mainScene;
    }

    /**
     * Calls the screen that allows the organizer to create a new VIP user.
     */
    public void createVIP() {
        CreateAccountInterface createScreen = new CreatingAccountGUI();
        createScreen.display("Creating VIP User", "Creating VIP user");
        String user = createScreen.getUsernameValue();
        String pass = createScreen.getPasswordValue();
        if(user != null && pass != null){
            boolean created = os.createVip(user, pass);
            AlertInterface alert = new AlertPopUp();
            if(created){
                alert.display("Success!", "Account created!");
            }
            else{
                alert.display("Error", "Account could not be created.");
            }
        }
    }
    /**
     * Calls the screen that allows the organizer to create a new Attendee user.
     */
    public void createAttendee() {
        CreateAccountInterface createScreen = new CreatingAccountGUI();
        createScreen.display("Creating Attendee User", "Creating Attendee user");
        String user = createScreen.getUsernameValue();
        String pass = createScreen.getPasswordValue();
        if(user != null && pass != null){
            boolean created = os.createAttendee(user, pass);
            AlertInterface alert = new AlertPopUp();
            if(created){
                alert.display("Success!", "Account created!");
            }
            else{
                alert.display("Error", "Account could not be created.");
            }
        }
    }
    /**
     * Calls the screen that allows the organizer to create a new Speaker user.
     */
    public void createSpeaker() {
        CreateAccountInterface createScreen = new CreatingAccountGUI();
        createScreen.display("Creating Speaker User", "Creating Speaker user");
        String user = createScreen.getUsernameValue();
        String pass = createScreen.getPasswordValue();
        if(user != null && pass != null){
            boolean created = os.createSpeaker(user, pass);
            AlertInterface alert = new AlertPopUp();
            if(created){
                alert.display("Success!", "Account created!");
            }
            else{
                alert.display("Error", "Account could not be created.");
            }
        }
    }
    /**
     * Calls the screen that allows the organizer to create a new Organizer user.
     */
    public void createOrganizer() {
        CreateAccountInterface createScreen = new CreatingAccountGUI();
        createScreen.display("Creating Organizer User", "Creating Organizer user");
        String user = createScreen.getUsernameValue();
        String pass = createScreen.getPasswordValue();
        if(user != null && pass != null){
            boolean created = os.createOrganizer(user, pass);
            AlertInterface alert = new AlertPopUp();
            if(created){
                alert.display("Success!", "Account created!");
            }
            else{
                alert.display("Error", "Account could not be created.");
            }
        }
    }

    /**
     * Changes the scene back to the the Organizer main screen.
     */
    public void backButton() {
        Stage window =  (Stage) backButton.getScene().getWindow();
        window.setScene(organizerScene);
    }

    /**
     * Setter method for the organizer's main Screen
     * @param scene The scene representing the Organizer's main scene.
     */
    public void setOrganizerScene(Scene scene){
        organizerScene = scene;
    }
}
