package Entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class Person<Public> implements Serializable {
    private String username;
    private String password;
    private Map<String, List<String>> senderToMessages;
    private List<String> storedMessages;
    private List<String> archivedMessages;
    protected boolean isSpeaker;
    protected boolean isAttendee;
    protected boolean isOrganizer;
    protected boolean isVip;

    /**
     * Constructor for entity class that creates a Person object
     *
     * @param username The Person's username as a String
     * @param password The Person's password as a String
     */
    public Person(String username, String password) {
        this.username = username;
        this.password = password;
        this.senderToMessages = new HashMap<>();
        this.storedMessages = new ArrayList<>();
        this.archivedMessages = new ArrayList<>();
    }

    /**
     * Returns whether the Person is a Speaker
     *
     * @return a boolean that returns true if Person is a Speaker and false otherwise
     */
    public boolean isSpeakerType() {
        return this.isSpeaker;
    }

    /**
     * Returns whether the Person is an Attendee
     *
     * @return a boolean that returns true if Person is an Attendee and false otherwise
     */
    public boolean isAttendeeType() {
        return this.isAttendee;
    }

    /**
     * Returns whether the Person is an Organizer
     *
     * @return a boolean that returns true if Person is an Organizer and false otherwise
     */
    boolean isOrganizerType() {
        return this.isOrganizer;
    }

    /**
     * Returns whether the Person is a Vip
     *
     * @return a boolean that returns true if Person is an Vip and false otherwise
     */
    public boolean isVipType() {
        return this.isVip;
    }

    /**
     * Returns whether a Person is a Person
     * always true
     *
     * @return true as a Person is always a Person
     */
    boolean isPersonType() {
        return true;
    }

    /**
     * Returns the Person's username
     *
     * @return The username of Person as a String
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Returns the Person's password
     *
     * @return The password of Person as a String
     */
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    // returns messages that has been sent to Person
//    public String getStoredMessages() {
//        String delim = " ,";
//        return String.join(delim, storedMessages);
//    }

    /**
     * Returns variable senderToMessages
     *
     * @return A Map containing the senders as keys and the messages they sent as values
     */
    public Map<String, List<String>> getSenderToMessages() {
        return this.senderToMessages;
    }

    /**
     * Returns variable storedMessages
     *
     * @return A List containing all the Messages Person has been sent as Strings
     */
    public List<String> getStoredMessagesList() {
        return this.storedMessages;
    }

    /**
     * Returns variable archivedMessages
     *
     * @return A List containing all the archives messages Person has been sent as Strings
     */
    public List<String> getArchivedMessages() {
        return this.archivedMessages;
    }

    /**
     * Changes the variable storedMessages
     *
     * @param messages The new List that replaces the current storedMessages
     */
    public void setStoredMessagesList(List<String> messages) {
        this.storedMessages = messages;
    }


    /**
     * Changes the variable archivedMessages
     *
     * @param messages The new List that replaces the current archivedMessages
     */
    public void addToArchivedMessagesList(List<String> messages) {
        this.archivedMessages.addAll(messages);
    }

    /**
     * Adds a message and it's sender to variable storedMessages and to senderToMessages
     *
     * @param messageContent The message that was sent to Person as a String
     * @param messageSender  The username of the Person who sent the message to Person as a String
     */
    public void addToMessageStorage(String messageContent, String messageSender) {
        if (senderToMessages.containsKey(messageSender)) {
            senderToMessages.get(messageSender).add(messageContent);
            for (String archived : this.archivedMessages) {
                if(archived.contains(messageSender + ":"))
                {
                    storedMessages.add(archived);
                }
            }
        } else {
            List<String> messages = new ArrayList<>();
            messages.add(messageContent);
            senderToMessages.put(messageSender, messages);
        }
        storedMessages.add(storedMessages.size() + 1 + ". " + messageSender + ": " + messageContent + "---Sent");
    }
}



