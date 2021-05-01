package ControllerLayer;

import UseCases.*;

import java.io.IOException;
import java.util.List;

import javafx.scene.Scene;

public class LoginType {
    private String username;
    private String password;
    private loadAndSave las;
    private LoadAndSaveObjects laso;
    private SpeakerManager sm;
    private AttendeeManager am;
    private OrganizerManager om;
    private EventScheduler es;
    private VipManager vm;
    private EventsToHtml eth;
    private Scene mainScene;

    /**
     * Constructor, saves login information of current user
     *
     */
    public LoginType() throws IOException {
        username = "";
        password = "";
        las = new loadAndSave();
        laso = new LoadAndSaveObjects();
        sm = new SpeakerManager();
        am = new AttendeeManager();
        om = new OrganizerManager();
        es = new EventScheduler();
        vm = new VipManager();
        eth = new EventsToHtml();
        laso.loadAll(es, sm, am, om, vm);
        //las.loadAll(es, sm, am, om, vm);
    }


    /**
     * Calls the save method in LoadAndSaveObjects.
     * @throws IOException If the file to be saved to does not exist at that location.
     */
    public void save() throws IOException {
        laso.saveAll(es, sm, am, om, vm);
    }

    /**
     * The setter method for username.
     * @param username The usernmane of the logged in user.
     */
    public void setUsername(String username){
        this.username = username;
    }

    /**
     * The setter method for password.
     * @param password The password of the logged in user.
     */
    public void setPassword(String password){
        this.password = password;
    }

    /**
     * Reads user input and stores it in variable "password"
     *
     * @return variable username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * The setter method for mainScene.
     * @param mainScene The scene of the log in screen.
     */
    public void setMainScene(Scene mainScene){
        this.mainScene = mainScene;
    }

    /**
     * Matches username and password to an Entities.Attendee, Entities.Organizer or Entities.Speaker.
     * Shows their respective presenter.
     *
//     * @param am UseCases.AttendeeManager instance passed from Main method
//     * @param om UseCases.OrganizerManager instance passed from Main method
//     * @param sm UseCases.SpeakerManager instance passed from Main method
//     * @param es UseCases.EventScheduler instance passed from Main method
//     * @param vm UseCases.VipManager instance passed from Main method
     */
    public List<Object> checkLogin() throws IOException {
        if (am.checkLogin(username, password)) {
            AttendeeMainScreen ams = new AttendeeMainScreen(username, am, es, om, sm, vm, eth);
            ams.setMainScene(mainScene);
            return ams.openMainScreen();
        }
        else if (sm.checkLogin(username, password)) {
            SpeakerMainScreen sms = new SpeakerMainScreen(username, am, es, om, sm, vm, eth); // assuming its implemented like other presenters
            sms.setMainScene(mainScene);
            return sms.openMainScreen();
        }
        else if (om.checkLogin(username, password)) {
            OrganizerMainScreen oms = new OrganizerMainScreen(username, am, es, om ,sm, vm, eth);
            oms.setMainScene(mainScene);
            return oms.openMainScreen();
        }
        else if(vm.checkLogin(username, password)){
            VipMainScreen vms = new VipMainScreen(username, am, es, om, sm, vm, eth);
            vms.setMainScene(mainScene);
            return vms.openMainScreen();
        }
        else{
            return null;
        }
    }
}
