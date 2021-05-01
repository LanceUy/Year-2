package Entities;

import java.util.ArrayList;
import java.util.List;

public class Vip extends Person{
    private List<String> contactList;
    private List<String> schedule;

    /**
     * Creates a VIP object that extends Entities.Person and initializes the VIPs contactList and schedule as an
     * empty ArrayList, sets variables isAttendee, isSpeaker and isOrganizer to false, while isVip is set to true.
     * @param username The VIP's username as a String
     * @param password The VIP's password as a String
     */
    public Vip(String username, String password) {
        super(username, password);
        this.contactList = new ArrayList<>();
        this.isSpeaker = false;
        this.isAttendee = false;
        this.isOrganizer = false;
        this.isVip = true;
        this.schedule = new ArrayList<>();
    }
    /**
     * returns the usernames of all the people that the VIP can message
     * @return A List containing each contacts username as a String
     */
    public List<String> getContactList() {
        return this.contactList;
    }

    /**
     * Adds another persons username to VIP's contactList
     * @param contact The username of the person the VIP wants to add to their contactList
     */
    public void addToContact(String contact){
        this.contactList.add(contact);
    }

    /**
     * returns the ids of all the events that the VIP has signed up for
     * @return An Arraylist containing the event ids as a String
     */
    public List<String> getSchedule(){
        return this.schedule;
    }

    /**
     * Adds an event's ID to the VIP Schedule
     * @param eventID The ID of the event the VIP is adding to their schedule
     */
    public void addToSchedule(String eventID){
        this.schedule.add(eventID);
    }

    /**
     * Removes an event's ID from the VIP's Schedule
     * @param eventID The ID of the event the VIP is removing from their schedule
     */
    public void removeFromSchedule(String eventID){
        this.schedule.remove(eventID);
    }

}
