package ControllerLayer;

import GUI.Interfaces.AlertInterface;
import GUI.AlertPopUp;
import GUI.Interfaces.SendMessagesInterface;
import GUI.SendMessagesScreen;
import UseCases.AttendeeManager;
import UseCases.OrganizerManager;
import UseCases.SpeakerManager;
import UseCases.VipManager;
import java.util.List;

public class VipMessageScreen {
    private final AttendeeManager am;
    private final OrganizerManager om;
    private final SpeakerManager sm;
    private final VipManager vm;
    private final String username;

    /**
     * Creates instance of AttendeeMessageScreen, the presenter class for the messaging hub of an attendee user.
     *
     * @param am Instance of AttendeeManager with loaded information
     * @param om Instance of OrganizerManager with loaded information
     * @param sm Instance of SpeakerManager with loaded information
     * @param vm Instance of VipManager with loaded information
     * @param username String of the username of current user logged in
     */
    public VipMessageScreen(AttendeeManager am, OrganizerManager om, SpeakerManager sm, VipManager vm,
                            String username){
        this.am = am;
        this.om = om;
        this.sm = sm;
        this.vm = vm;
        this.username = username;
    }

    /**
     * Calls the message Screen that allows the Vip to send a message to another user and creates the message.
     */
    public void sendMessage(){
        MessageSystem messageSystem = new MessageSystem(am, om, sm, vm, username);
        SendMessagesInterface messagesScreen = new SendMessagesScreen();
        List<String> contactList = vm.getContactList(username);
        messagesScreen.display(contactList);
        String message = messagesScreen.getMessage();
        String receiver = messagesScreen.getReceivers().get(0);
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

