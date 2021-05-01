package ControllerLayer;

import GUI.*;
import GUI.Interfaces.AlertInputInterface;
import GUI.Interfaces.AlertInterface;
import UseCases.*;

public class
        OrganizerMessageScreen {
    private AttendeeManager am;
    private OrganizerManager om;
    private SpeakerManager sm;
    private VipManager vm;
    private String username;

    /**
     * Creates instance of ControllerLayer.OrganizerMessageScreen, the presenter class for the messaging hub of an organizer user.
     *
     * @param username String of the username of current user logged in
     * @param am Instance of UseCases.AttendeeManager with loaded information
     * @param om Instance of UseCases.OrganizerManager with loaded information
     * @param sm Instance of UseCases.SpeakerManager with loaded information
     * @param vm Instance of UseCases.VipManager with loaded information
     */
    public OrganizerMessageScreen(String username, AttendeeManager am, OrganizerManager om,
                                  SpeakerManager sm, VipManager vm){
        this.am = am;
        this.om = om;
        this.sm = sm;
        this.vm = vm;
        this.username = username;
    }

    /**
     * Calls the message Screen that allows the Organizer to send a message and creates the message.
     */
    public void sendMessage(){
        MessageSystem messageSystem = new MessageSystem(am, om, sm, vm, username);
        AlertInputInterface input = new AlertPopUpInput();
        input.display("Choose Recipient", "Enter the username of the recipient.\n " +
                "Enter \"All Attendees\" to message all Attendes.\nEnter \"All Speakers\" to message all Speakers ");
        String receiver = input.getInput();
        
        AlertInputInterface messageInput = new AlertPopUpLargeInput();
        messageInput.display("Send Message", "Enter your message.\nPress \"Enter\" when finished.");
        String message = messageInput.getInput();

        if(message == null || receiver == null){
            message = "";
            receiver = "";
        }
        boolean sent = messageSystem.createMessage(message, receiver);
        AlertInterface alert = new AlertPopUp();
        if(sent){
            alert.display("Message Sent", "Message Sent!");
        }
        else{
            alert.display("Error", "Message not Sent.");
        }
    }
}
