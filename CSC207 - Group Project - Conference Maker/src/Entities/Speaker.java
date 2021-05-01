package Entities;

import java.util.ArrayList;
import java.util.List;

public class Speaker extends Person {
    private List<String> contactList;
    private List<String> schedule;

    /**
     * Creates a Entities.Speaker object that inherits from Entities.Person
     * @param username The Entities.Speaker's username as a String
     * @param password The Entities.Speaker's password as a String
     */
    public Speaker(String username, String password) {
        super(username, password);
        this.contactList = new ArrayList<>();
        this.isSpeaker = true;
        this.isAttendee = false;
        this.isOrganizer = false;
        this.isVip = false;
        this.schedule = new ArrayList<>();
    }

    /**
     * returns the usernames of all the people that the Entities.Speaker can message
     * @return A List containing each contacts username as a String
     */
    public List<String> getContactList() {
        return this.contactList;
    }

    /**
     * returns the ids of all the events that the Entities.Speaker is speaking at
     * @return An Arraylist containing the event ids as a String
     */
    public List<String> getSchedule(){
        return this.schedule;
    }

    /**
     * Adds another persons username to Entities.Speaker's contactList
     * @param contact The username of the person the Entities.Attendee wants to add to their contactList
     */
    public void addToContact(String contact){
        this.contactList.add(contact);
    }
}
