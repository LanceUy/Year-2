package ControllerLayer;

import GUI.*;
import GUI.Interfaces.AlertInterface;
import GUI.Interfaces.SendMessagesInterface;
import UseCases.*;

import java.util.List;

public class SpeakerMessageScreen {
    private AttendeeManager am;
    private OrganizerManager om;
    private SpeakerManager sm;
    private VipManager vm;
    private String username;

    /**
     * Creates instance of ControllerLayer.SpeakerMessageScreen, the presenter class for the messaging hub of a speaker user.
     *
     * @param am       Instance of UseCases.AttendeeManager with loaded information
     * @param om       Instance of UseCases.OrganizerManager with loaded information
     * @param sm       Instance of UseCases.SpeakerManager with loaded information
     * @param vm       Instance of UseCases.VipManager with loaded information
     * @param username String of the username of current user logged in
     */
    public SpeakerMessageScreen(AttendeeManager am, OrganizerManager om, SpeakerManager sm, VipManager vm,
                                String username) {
        this.am = am;
        this.om = om;
        this.sm = sm;
        this.vm = vm;
        this.username = username;
    }

    /**
     * Calls the message Screen that allows the Speaker to send a message and creates the message.
     */
    public void sendMessage() {
        MessageSystem messageSystem = new MessageSystem(am, om, sm, vm, username);
        SendMessagesInterface messagesScreen = new SpeakerSendMessagesScreen();
        List<String> scheduleList = sm.getSchedule(username);
        messagesScreen.display(scheduleList);
        String message = messagesScreen.getMessage();
        List<String> receivers = messagesScreen.getReceivers();
        if(message != null) {
            boolean sent = messageSystem.createMessage(message, receivers);
            AlertInterface alert = new AlertPopUp();
            if (sent) {
                alert.display("Message Sent", "Message Sent!");
            } else {
                alert.display("Error", "Message not Sent.");
            }
        }
    }
}