package ControllerLayer;

import Entities.*;
import UseCases.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class AttendeeSystem {
    AttendeeManager am;
    EventScheduler es;
    OrganizerManager om;
    SpeakerManager sm;
    VipManager vm;

    /**
     * Constructor for controller that creates ControllerLayer.AttendeeSystem object
     * @param am Instance of UseCases.AttendeeManager with loaded information
     * @param es Instance of UseCases.EventScheduler with loaded information
     * @param om Instance of UseCases.OrganizerManager with loaded information
     * @param sm Instance of UseCases.SpeakerManager with loaded information
     */
    public AttendeeSystem(AttendeeManager am, EventScheduler es, OrganizerManager om, SpeakerManager sm, VipManager vm){
        this.am = am;
        this.es = es;
        this.om = om;
        this.sm = sm;
        this.vm = vm;
    }

    /**
     * Reads String input from User
     * @return The String that the User inputted
     */
    public String readString(){
        String str;
        Scanner scan = new Scanner(System.in);
        str = scan.nextLine();

        return str;
    }

    /**
     * Creates an Entities.Attendee Account
     * @param username The Entities.Attendee's username as a String
     * @param password The Entities.Attendee's password as a String
     */
    public void createAccount(String username, String password){
        am.createAttendee(username, password);
    }

    /**
     * Checks whether contact can be added to Attendees contact list
     * @param username The Entities.Attendee's username as a String
     * @param contact The potential contact's username as a String
     * @return a boolean that returns true if contact can be added to Entities.Attendee contact list and false if contact cannot
     * be added
     */
    public boolean canAddContact(String username, String contact){
        boolean userCheck = false;
        if (am.getUsernameToAttendee().containsKey(contact)){
            userCheck = true;
        } else if (om.getUsernameToOrganizer().containsKey(contact)){
            userCheck = true;
        } else if (sm.getUsernameToSpeaker().containsKey(contact)){
            userCheck = true;
        } else if (vm.getUsernameToVip().containsKey(contact)){
            userCheck = true;
        }
        return userCheck && this.am.canAddToContactList(username, contact);
    }

    /**
     * Adds a contact to Entities.Attendee's contact list
     * @param username The Entities.Attendee's username as a String
     * @param contact The contact's username as a String
     */
    public void addContact(String username, String contact){
        this.am.addToContactList(username, contact);
    }

    /**
     * Returns the variable IdToEvent in UseCases.EventScheduler
     * @return A Map pointing Entities.Event IDS to their corresponding Entities.Event objects
     */
    private Map<String, Event> getEventMap(){
        return this.es.getIdToEvent();
    }

    /**
     * Returns Entities.Attendee's schedule using the Entities.Event objects
     * @param username The Entities.Attendee's username as a String
     * @return A List of all the Events the Entities.Attendee has signed up for
     */
    public List<Event> getSchedule(String username){
        List<String> idSchedule =  this.am.getSchedule(username);
        Map<String, Event> eventMap = getEventMap();
        List<Event> fullSchedule = new ArrayList<>();

        for (String i: idSchedule){
            if (eventMap.containsKey(i)){
                fullSchedule.add(eventMap.get(i));
            }
        }
        return fullSchedule;
    }

    /**
     * returns Entities.Attendee's Schedule with all the information about each Entities.Event
     * @param username The Entities.Attendee's username as a String
     * @return A List of Entities.Event information as Strings
     */
    public List<String> getScheduleIds(String username){
        List<Event> fullSchedule =  getSchedule(username);
        List<String> ScheduleString = new ArrayList<>();

        for (Event i: fullSchedule){
            ScheduleString.add("[Entities.Event: " + i.getID() + ", Room Number: " + i.getRoomNum() + ", Time: " + i.getTime()
                    + ", Entities.Speaker: " + i.getSpeaker() + "]");
        }
        return ScheduleString;
    }

    /**
     * Checks whether eventID exists
     * @param eventID The ID of the Entities.Event as a String
     * @return a boolean that returns true if the EventID exists and false otherwise
     */
    private boolean validEvent(String eventID) {
        return getEventMap().containsKey(eventID);
    }

    /**
     * Checks whether an Entities.Event can be added to Entities.Attendee's schedule
     * @param username The Entities.Attendee's username as a String
     * @param eventID The ID of the Entities.Event as a String
     * @return a boolean that returns true if Entities.Event can be added and false otherwise
     */
    public boolean canAddEvent(String username, String eventID){
        List<String> idSchedule =  this.am.getSchedule(username);
        List<Event> fullSchedule = getSchedule(username);
        boolean canAdd = true;
        int eventStartTime;
        int eventEndTime;
        boolean vipOnly;
        // Checks if Event ID exists
        if (validEvent(eventID)) {
            eventStartTime = getEventMap().get(eventID).getTime();
            vipOnly = getEventMap().get(eventID).getIsVip();
        }
        else{
            // Event ID does not exist
            return false;
        }

        // Checks if Event is for VIP's only
        if (vipOnly){
            return false;
        }
        for (Event event: fullSchedule){
            if(event.getTime() != eventStartTime && !idSchedule.contains(eventID)){
                canAdd = true;
            }
            else{
                canAdd = false;
                break;
            }
        }
        for (Event event: fullSchedule){
            // Checks if the event with eventID starts at same time/during any other event in the user's schedule
            if(event.getTime() <= eventStartTime && eventStartTime < event.getEndTime()) {
                canAdd = false;
                break;
            }

        }
        Event event = es.getIdToEvent().get(eventID);
        if(event.getCountInEvent() >= event.getCapacity()){
            canAdd = false;
        }
        return canAdd;
    }

    /**
     * Adds an event to the Entities.Attendee's schedule
     * @param username The Entities.Attendee's username as a String
     * @param eventID The ID of the Entities.Event as a String
     */
    public void addEvent(String username, String eventID){
        boolean canAdd = canAddEvent(username, eventID);
        Event event = getEventMap().get(eventID);
        List<String> speakersID = new ArrayList<>(event.getSpeaker());
        List<Speaker> speakers = new ArrayList<>();
        for(String id: speakersID){
            Speaker eventSpeaker = sm.getUsernameToSpeaker().get(id);
            speakers.add(eventSpeaker);
        }
        if(canAdd){
            this.am.eventSignUp(username,eventID);
            for (String id: speakersID){
                this.addContact(username, id);
            }
            event.updateInEvent(username);
            for(Speaker speaker: speakers){
                speaker.addToContact(username);
            }
        }
    }

    /**
     * Checks whether an Entities.Event can be removed from the Entities.Attendee's Schedule
     * @param username The Entities.Attendee's username as a String
     * @param eventID The ID of the Entities.Event as a String
     * @return a boolean that returns true if the event can be removed from Entities.Attendee's schedule and false otherwise
     */
    public boolean canCancelEnrollment(String username, String eventID){
        List<String> idSchedule =  this.am.getSchedule(username);
        boolean validEvent = validEvent(eventID);
        return (validEvent && idSchedule.contains(eventID));
    }

    /**
     * Removes an Entities.Event from the Entities.Attendee's schedule
     * @param username The Entities.Attendee's username as a String
     * @param eventID The ID of the Entities.Event as a String
     */
    public void cancelEnrollment(String username, String eventID){
        boolean canCancel = canCancelEnrollment(username, eventID);
        Event event = getEventMap().get(eventID);
        if (canCancel){
            this.am.eventCancel(username, eventID);
            event.removeInEvent(username);
        }
    }

}
