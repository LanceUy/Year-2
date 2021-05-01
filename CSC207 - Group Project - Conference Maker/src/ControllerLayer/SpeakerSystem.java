package ControllerLayer;

import UseCases.SpeakerManager;

import java.util.List;
import java.util.Scanner;

public class SpeakerSystem {
    // Controller class (gets input from user)
    SpeakerManager sm;

    /**
     * Creates a ControllerLayer.SpeakerSystem object and sets its variable sm to the one passed in the constructor.
     * @param sm UseCases.SpeakerManager object
     */
    public SpeakerSystem(SpeakerManager sm){
        this.sm = sm;
    }

    /**
     * Get the schedule of Entities.Speaker with speakerUsername.
     * @param speakerUsername String representing Entities.Speaker's username
     * @return List representing the schedule of Entities.Speaker with speakerUsername
     */
    public List<String> getSchedule(String speakerUsername){
        return sm.getSchedule(speakerUsername);
    }

    /**
     * Reads user input and returns it as a String
     * @return String representing user input
     */
    public String readString(){
        Scanner scan = new Scanner(System.in);
        return scan.nextLine();
    }
}
