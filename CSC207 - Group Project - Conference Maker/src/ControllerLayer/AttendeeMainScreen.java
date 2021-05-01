package ControllerLayer;

import GUI.*;
import GUI.Interfaces.AlertInputInterface;
import GUI.Interfaces.AlertInterface;
import GUI.Interfaces.ManageEventsViewInterface;
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

public class AttendeeMainScreen implements MainScreen{
    @FXML
    private Button viewEventsButton;
    @FXML
    private Button logOutButton;
    @FXML
    private Text welcomeText;
    private Scene mainScene;
    AttendeeSystem as;
    String username;
    AttendeeManager am;
    EventScheduler es;
    OrganizerManager om;
    SpeakerManager sm;
    VipManager vm;
    EventsToHtml eth;

    /**
     * Constructor for the presenter showing all of the Actions Entities.Attendee can do
     * @param username The username of the Entities.Attendee as a String
     * @param am Instance of UseCases.AttendeeManager with loaded information
     * @param es Instance of UseCases.EventScheduler with loaded information
     * @param om Instance of UseCases.OrganizerManager with loaded information
     * @param sm Instance of UseCases.SpeakerManager with loaded information
     * @param vm Instance of UseCases.VipManager with loaded information
     */
    public AttendeeMainScreen(String username, AttendeeManager am, EventScheduler es, OrganizerManager om,
                              SpeakerManager sm, VipManager vm, EventsToHtml eth){
        setInstances(username,am, es, om, sm, vm, eth);
    }

    /**
     * Sets the parameters of Attendee.
     * @param username The username of the Entities.Attendee as a String
     * @param am Instance of UseCases.AttendeeManager with loaded information
     * @param es Instance of UseCases.EventScheduler with loaded information
     * @param om Instance of UseCases.OrganizerManager with loaded information
     * @param sm Instance of UseCases.SpeakerManager with loaded information
     * @param vm Instance of UseCases.VipManager with loaded information
     */
    public void setInstances(String username, AttendeeManager am, EventScheduler es, OrganizerManager om,
                        SpeakerManager sm, VipManager vm, EventsToHtml eth){
        this.as = new AttendeeSystem(am, es, om, sm, vm);
        this.username = username;
        this.am = am;
        this.es = es;
        this.om = om;
        this.sm = sm;
        this.vm = vm;
        this.eth = eth;
    }

    /**
     * The empty constructor for AttendeeMainScreen. Required for FXML.
     */
    public AttendeeMainScreen(){

    }

    /**
     * Creates the screen and its respective controller for an Attendee's mainscreen but does not open the screen.
     * @return A list of size 2 representing the scene, and controller of this screen.
     * @throws IOException If the FXML file does not exist at that location.
     */
    @Override
    public List<Object> openMainScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GUI/AttendeeScreen.fxml"));
        Parent root = fxmlLoader.load();
        AttendeeMainScreen myPresenter = fxmlLoader.getController();
        myPresenter.setInstances(username, am, es, om, sm, vm ,eth);
        myPresenter.setMainScene(mainScene);
        List<Object> map = new ArrayList<>();
        map.add(new Scene(root));
        map.add(myPresenter);
        return map;
    }

    /**
     * Calls the ReadMessageScreen presenter.
     */
    public void viewMessages() {
        ReadMessageScreen messageScreen = new ReadMessageScreen(am, om, sm, vm, username);
        messageScreen.showMessages();
    }

    /**
     * The getter method for welcomeText.
     * @return The text label object representing the welcome text of this screen.
     */
    @Override
    public Text getWelcomeText(){
        return welcomeText;
    }

    /**
     * Changes the current scene back to the log in screen.
     */
    public void logOut(){
        Stage stage = (Stage) logOutButton.getScene().getWindow();
        stage.setScene(mainScene);
    }

    /**
     * Creates the screen that allows an Attendee to add a new contact.
     */
    public void addContact() {
        AlertInputInterface alertInterface = new AlertPopUpInput();
        alertInterface.display("Add a Contact", "Enter the name of the Contact you wish you add");
        String contactID = alertInterface.getInput();
        System.out.println(contactID);
        if (this.as.canAddContact(username, contactID)) {
            this.as.addContact(username, contactID);
            AlertInterface alert = new AlertPopUp();
            alert.display("Alert Message", "Contact added!");
        } else {
            AlertInterface alert = new AlertPopUp();
            alert.display("Error", "Error occured.\n Please try again.");
        }
    }

    /**
     * Calls AttendeeMessageScreen.
     */
    public void sendMessage(){
        AttendeeMessageScreen messageScreen = new AttendeeMessageScreen(am, om, sm, vm, username);
        messageScreen.sendMessage();
        }

    /**
     * Setter method for mainScene.
     * @param mainScene The scene that represents the login screen.
     */
    public void setMainScene(Scene mainScene){
        this.mainScene = mainScene;
    }

    /**
     * Creates the screen that allows the user to view the events they signed up for.
     */
    public void viewEvents(){
        ManageEventsViewInterface manageEventsScreen = new ManageEventsView();
        List<String> events = this.as.getScheduleIds(username);
        manageEventsScreen.display("My Events", events);
        AlertInterface alert = new AlertPopUp();
        if(manageEventsScreen.getEventController() != null && manageEventsScreen.getEventController().getCancel()){
            if(as.canCancelEnrollment(username, manageEventsScreen.getEventController().getEventID())){
                as.cancelEnrollment(username, manageEventsScreen.getEventController().getEventID());
                alert.display("Success!", "Event Cancelled");
            }
            else{
                alert.display("Error", "Event can not be cancelled");
            }
        }
        if(manageEventsScreen.getController().getAddEvent()){
            if(as.canAddEvent(username, manageEventsScreen.getController().getEventID())){
                as.addEvent(username, manageEventsScreen.getController().getEventID());
                alert.display("Success!", "Event Added!");
            }
            else{
                alert.display("An Error Has Occurred", "Event could not be added.");
            }
        }
        if(manageEventsScreen.getController().getSaveEvents()){
            eth.saveToHtml(es);
            alert.display("Downloaded", "Events downloaded as HTML.");
        }
    }
}
