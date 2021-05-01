package ControllerLayer;

import ControllerLayer.GUIControllers.MessageScreenController;
import GUI.*;
import ControllerLayer.GUIControllers.SingleMessageScreenController;
import GUI.Interfaces.AlertInterface;
import GUI.Interfaces.ListScreenInterface;
import GUI.Interfaces.SentMessageInterface;
import UseCases.*;

import java.util.ArrayList;
import java.util.List;

public class ReadMessageScreen{
    private AttendeeManager am;
    private OrganizerManager om;
    private SpeakerManager sm;
    private VipManager vm;
    private String username;

    /**
     * The constructor for ReadMessageScreen. It takes 4 parameters.
     *
     * @param am       The AttendeeManager object that contains information from the file.
     * @param om       The OrganizerManager object that contains information from the file.
     * @param sm       The SpeakerManager object that contains information from the file.
     * @param vm       The VipManager object that contains information from the file.
     * @param username The username of the logged-in/current user.
     */
    public ReadMessageScreen(AttendeeManager am, OrganizerManager om, SpeakerManager sm, VipManager vm,
                             String username) {
        this.am = am;
        this.om = om;
        this.sm = sm;
        this.vm = vm;
        this.username = username;
    }

    /**
     * Creates the screen that shows the user all messages that are sent to them.
     */
    public void showMessages() {
        MessageSystem messageSystem = new MessageSystem(am, om, sm, vm, username);
        List<String> usernames = new ArrayList<>();
        List<String> sentMsgs = messageSystem.readMessage(username);
        List<String> reverseMsgs = new ArrayList<>();
        for (String msg : sentMsgs) {
            int startIndex = msg.indexOf(".");
            int endIndex = msg.indexOf(":");
            usernames.add(0, msg.substring(startIndex + 1, endIndex));
            reverseMsgs.add(0, msg);
        }
        ListScreenInterface messageScreen = new MessagesScreen();
        messageScreen.display(usernames, reverseMsgs);

        SingleMessageScreenController controller = messageScreen.getController();
        List<String> msg = new ArrayList<>();
        MessageScreenController thisController = messageScreen.getThisController();

        if(thisController.getOpenSent()){
            openSent();
        }

        if (controller != null) {
            msg.add(controller.getMessage());

            AlertInterface alert = new AlertPopUp();
            if (controller.getDelete()) {
                messageSystem.deleteMessage(username, msg, false);
                alert = new AlertPopUp();
                alert.display("Success!", "Message deleted.");
            }
            if (!controller.getRead()) {
                messageSystem.markUnread(username, msg);
                alert.display("Success!", "Message marked as unread.");
            }
            if (controller.getArchive()) {
                messageSystem.deleteMessage(username, msg, true);
                alert.display("Success!", "Message Archived.");
            }
        }
    }

    /**
     * Creates the screen that shows all messages the user sent.
     */
    public void openSent(){
        MessageSystem messageSystem = new MessageSystem(am, om, sm, vm, username);
        List<String> usernames = new ArrayList<>();
        List<String> sentMsgs = messageSystem.seeSentMessages(username);
        List<String> reverseMsgs = new ArrayList<>();
        for (String msg : sentMsgs) {
            usernames.add("");
            reverseMsgs.add(0, msg);
        }
        SentMessageInterface messageScreen = new SentMessageScreen() {
        };
        messageScreen.display(usernames, reverseMsgs);
    }

}

