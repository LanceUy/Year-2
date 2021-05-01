package ControllerLayer;

import Entities.*;
import UseCases.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MessageSystem {
    private final AttendeeManager am;
    private final OrganizerManager om;
    private final SpeakerManager sm;
    private final VipManager vm;
    private final String username;

    /**
     * Constructor for controller class, reads user input and decides if messaging can be done
     *
     * @param am       Instance of UseCases.AttendeeManager with loaded information
     * @param om       Instance of UseCases.OrganizerManager with loaded information
     * @param sm       Instance of UseCases.SpeakerManager with loaded information
     * @param vm       Instance of UseCases.VipManager with loaded information
     * @param username String of the username of current user logged in
     */
    public MessageSystem(AttendeeManager am, OrganizerManager om, SpeakerManager sm, VipManager vm, String username) {
        this.am = am;
        this.om = om;
        this.sm = sm;
        this.vm = vm;
        this.username = username;
    }


    /**
     * Reads the keyboard input.
     *
     * @return The keyboard input as a string.
     */
    public String userInput() {
        Scanner scan = new Scanner(System.in);
        String m = scan.nextLine();
        return m;
    }

    /**
     * Calls the send message function and only requires a message and a receiver.
     *
     * @param message  The message to be sent.
     * @param receiver The username of the recipient.
     */
    public boolean createMessage(String message, String receiver) {
        if (am.getUsernameToAttendee().containsKey(receiver) ||
                sm.getUsernameToSpeaker().containsKey(receiver) ||
                om.getUsernameToOrganizer().containsKey(receiver) ||
                vm.getUsernameToVip().containsKey(receiver) ||
                receiver.equals("All Speakers") ||
                receiver.equals("All Attendees")) {
            this.sendMessage(message, receiver, username);
            return true;
        }
        return false;
    }

    /**
     * Overloaded method: for speaker messaging
     *
     * @param message Content of the message being sent
     * @param talks   the talks that the speaker wants to send messages
     */
    public boolean createMessage(String message, List<String> talks) {
        boolean validTalks = true;

        if (talks.contains("All")) {
            List<String> allTalks = sm.getSchedule(username);
            this.sendMessage(message, allTalks, username);
        } else {
            for (String talk : talks) {
                if (!sm.getSchedule(username).contains(talk)) {
                    validTalks = false;
                }
            }
            if (validTalks) {
                this.sendMessage(message, talks, username);
            }
        }

        return validTalks;
    }

    /**
     * Reads multiple user inputs and adds them to a list
     *
     * @return A list containing each input from user
     */
    public List<String> readList() {
        Scanner scan = new Scanner(System.in);
        String e = "";
        List<String> events = new ArrayList<>();
        while (!e.equals("done")) {
            e = scan.nextLine();
            events.add(e);
        }
        events.remove("done");
        return events;
    }

    /**
     * Returns all the messages sent to currPerson.
     *
     * @param currPerson The username of the user that is currently logged in.
     * @return A string of the messages sent to currPerson in the form
     * sender1: message1, sender2: message2, ...
     */
    public List<String> readMessage(String currPerson) {
        List<Attendee> attendees = am.getAllAttendees();
        List<Organizer> organizers = om.getAllOrganizers();
        List<Speaker> speakers = sm.getAllSpeakers();
        List<Vip> vips = vm.getAllVips();

        UserText uText = new UserText();

        if (am.getUsernameToAttendee().containsKey(currPerson)) {
            uText.addPeopleToList(attendees);
            return uText.readMessage(currPerson);
        } else if (om.getUsernameToOrganizer().containsKey(currPerson)) {
            uText.addPeopleToList(organizers);
            return uText.readMessage(currPerson);
        } else if (sm.getUsernameToSpeaker().containsKey(currPerson)) {
            uText.addPeopleToList(speakers);
            return uText.readMessage(currPerson);
        } else if (vm.getUsernameToVip().containsKey(currPerson)) {
            uText.addPeopleToList(vips);
            return uText.readMessage(currPerson);
        } else {
            throw new NullPointerException();
        }

    }

    /**
     * Returns the messages sent by the currently logged in user as a string.
     *
     * @return Returns the string of messages sent by the current user.
     */
    public List<String> seeSentMessages(String currPerson) {
        List<Attendee> attendees = am.getAllAttendees();
        List<Organizer> organizers = om.getAllOrganizers();
        List<Speaker> speakers = sm.getAllSpeakers();
        List<Vip> vips = vm.getAllVips();

        UserText uText = new UserText();
        uText.addPeopleToList(attendees);
        uText.addPeopleToList(organizers);
        uText.addPeopleToList(speakers);
        uText.addPeopleToList(vips);
        return uText.seeSentMessages(currPerson);
    }

    /**
     * Marks the messages selected as unread: ("Sent" in this design")
     *
     * @param currPerson Current person logged in
     * @param messages   The messages they want to mark as unread
     */
    public void markUnread(String currPerson, List<String> messages) {
        List<Attendee> attendees = am.getAllAttendees();
        List<Organizer> organizers = om.getAllOrganizers();
        List<Speaker> speakers = sm.getAllSpeakers();
        List<Vip> vips = vm.getAllVips();
        UserText uText = new UserText();
        uText.addPeopleToList(attendees);
        uText.addPeopleToList(organizers);
        uText.addPeopleToList(speakers);
        uText.addPeopleToList(vips);
        uText.markUnread(currPerson, messages);
    }

    /**
     * Deletes the messages selected
     *
     * @param currPerson Current person logged in
     * @param messages   The messages they want to mark as unread
     */
    public void deleteMessage(String currPerson, List<String> messages, boolean archive) {
        List<Attendee> attendees = am.getAllAttendees();
        List<Organizer> organizers = om.getAllOrganizers();
        List<Speaker> speakers = sm.getAllSpeakers();
        List<Vip> vips = vm.getAllVips();
        UserText uText = new UserText();
        uText.addPeopleToList(attendees);
        uText.addPeopleToList(organizers);
        uText.addPeopleToList(speakers);
        uText.addPeopleToList(vips);
        if(archive){
            uText.deleteMessages(currPerson, messages, true);
        }
        else{
            uText.deleteMessages(currPerson, messages, false);
        }
    }

    /**
     * Sends a message to other other user(s).
     *
     * @param message    The message to be sent.
     * @param receiver   The recipient of the message.
     * @param currPerson The sender of the message.
     */
    private void sendMessage(String message, String receiver, String currPerson) {
        List<Attendee> attendees = am.getAllAttendees();
        List<Speaker> speakers = sm.getAllSpeakers();
        List<Vip> vips = vm.getAllVips();
        if (om.getUsernameToOrganizer().containsKey(currPerson)) {
            UserText text = new UserText();
            ;
            if (receiver.equals("All Speakers")) {
                text.addPeopleToList(speakers);
                text.messageAllSpeakers(message, currPerson);
            } else if (receiver.equals("All Attendees")) {
                text.addPeopleToList(attendees);
                text.messageAllAttendees(message, currPerson);
            } else {
                text.addPeopleToList(attendees);
                text.addPeopleToList(speakers);
                text.addPeopleToList(vips);
                if (am.getUsernameToAttendee().containsKey(receiver) ||
                        sm.getUsernameToSpeaker().containsKey(receiver) ||
                        vm.getUsernameToVip().containsKey(receiver)) {
                    text.sendSingleMessage(message, currPerson, receiver);
                } else {
                    throw new NullPointerException();
                }
            }
        } else if (am.getUsernameToAttendee().containsKey(currPerson)) {
            UserText text = new UserText();
            text.addPeopleToList(attendees);
            text.addPeopleToList(speakers);
            text.addPeopleToList(vips);
            text.sendSingleMessage(message, currPerson, receiver);
        } else if (sm.getUsernameToSpeaker().containsKey(currPerson)) {
            UserText text = new UserText();
            text.addPeopleToList(attendees);
            text.addPeopleToList(vips);
            text.sendSingleMessage(message, receiver, currPerson);
        } else if (vm.getUsernameToVip().containsKey(currPerson)) {
            UserText text = new UserText();
            text.addPeopleToList(attendees);
            text.addPeopleToList(speakers);
            text.addPeopleToList(vips);
            text.sendSingleMessage(message, currPerson, receiver);
        }
    }

    /**
     * Overloaded method for speaker messaging
     *
     * @param message    The message to be sent.
     * @param talks      The talk(s) the speaker is giving
     * @param currPerson The sender of the message.
     */
    private void sendMessage(String message, List<String> talks, String currPerson) {
        List<Attendee> attendees = am.getAllAttendees();
        List<Vip> vips = vm.getAllVips();
        if (sm.getUsernameToSpeaker().containsKey(currPerson)) {
            UserText st = new UserText();
            st.addPeopleToList(attendees);
            st.addPeopleToList(vips);
            st.messageAllAttendeesInEvents(talks, message, currPerson);
        }
    }
}
