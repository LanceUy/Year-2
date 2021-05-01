package ControllerLayer;

import UseCases.*;

import java.util.List;
import java.util.Scanner;

public class OrganizerSystem {
    // Controller class (gets input from user)
    AttendeeManager am;
    EventScheduler es;
    OrganizerManager om;
    SpeakerManager sm;
    VipManager vm;

    /**
     * Creates an ControllerLayer.OrganizerSystem object and sets its variables am, es, om, and sm to the ones passed in the
     * constructor.
     * @param am UseCases.AttendeeManager object
     * @param es EvenScheduler object
     * @param om UseCases.OrganizerManager object
     * @param sm UseCases.SpeakerManager object
     */
    public OrganizerSystem(AttendeeManager am, EventScheduler es, OrganizerManager om, SpeakerManager sm, VipManager vm){
        this.am = am;
        this.es = es;
        this.om = om;
        this.sm = sm;
        this.vm = vm;
    }

    /**
     * Create an Entities.Event object and add it to the schedule of Entities.Speaker with speakerUsername, if the event is valid.
     * @param roomNum String representing the room number of where the event is
     * @param eventId String representing the id of the event
     * @param time int representing the time the event starts
     * @param speakers List of Strings representing the username of the speakers
     * @param isVip boolean; true if the Entities.Event is VIP only, false otherwise
     * @return boolean; true if the Entities.Event was created, false if it was not created.
     */
    public boolean createEvent(String roomNum, String eventId, int time, List<String> speakers, boolean isVip,
                               int duration, int maxPeople){
        boolean eventCreated;
        // Check if Speakers exists
        if(speakers.size() >=1){
            for (String speakerID: speakers) {
                if (!sm.getUsernameToSpeaker().containsKey(speakerID)) {
                    return false;
                }
            }
        }
        //create event
        eventCreated = es.updateEvents(roomNum, eventId, time, speakers, isVip, duration, maxPeople);
        if (eventCreated){
            // Add event to speaker's schedule
            for(String speakerID: speakers){
                sm.updateSchedule(speakerID, eventId);
            }
        }
        return eventCreated;
        }

    /**
     * Create a Entities.Attendee object.
     * @param attendeeUsername String representing the Entities.Attendee's username
     * @param password String representing the Entities.Attendee's password
     * @return boolean; true if the Entities.Attendee was created, false if it was not created.
     */
    public boolean createAttendee(String attendeeUsername, String password){
        if (personExists(attendeeUsername)){
            return false;
        } else {
            am.createAttendee(attendeeUsername, password);
            return true;
        }
    }

    /**
     * Create an Entities.Organizer object.
     * @param organizerUsername String representing Entities.Organizer's username
     * @param password String representing Entities.Organizer's password
     * @return boolean; true if Entities.Organizer object was created, false if it was not created
     */
    public boolean createOrganizer(String organizerUsername, String password){
        if (personExists(organizerUsername)){
            return false;
        } else {
            om.createOrganizer(organizerUsername, password);
            return true;
        }
    }

    /**
     * Create a Entities.Speaker object.
     * @param speakerUsername String representing the Entities.Speaker's username
     * @param password String representing the Entities.Speaker's password
     * @return boolean; true if the Entities.Speaker was created, false if it was not created.
     */
    public boolean createSpeaker(String speakerUsername, String password){
        if (personExists(speakerUsername)){
            return false;
        } else {
            sm.createSpeaker(speakerUsername, password);
            return true;
        }
    }

    /**
     * Create a VIP object.
     * @param vipUsername String representing the VIP's username
     * @param password String representing the VIP's password
     * @return boolean; true if the VIP was created, false if it was not created.
     */
    public boolean createVip(String vipUsername, String password){
        if (personExists(vipUsername)){
            return false;
        } else {
            vm.createVip(vipUsername, password);
            return true;
        }
    }

    /**
     * Removes the Entities.Event with eventId from UseCases.EventScheduler, Entities.Speaker's schedule, and all of its Attendees' schedule.
     * @param eventId String representing the id of the event
     * @return boolean; true if event was cancelled, false if it was not cancelled.
     */
    public boolean cancelEvent(String eventId){
        // Remove event from UseCases.EventScheduler
        try {
            List<String> speakerIDs = es.getIdToEvent().get(eventId).getSpeaker();
            List<String> attendeeList = es.getIdToEvent().get(eventId).getInEvent();
            boolean eventCancelled = es.removeEvent(eventId);
            if (eventCancelled) {
                // Remove event from speaker's schedule
                for(String speakerID: speakerIDs) {
                    sm.removeFromSchedule(speakerID, eventId);
                }
                // Remove event from all attendees' schedule
                for (String attendeeUsername : attendeeList) {
                    am.eventCancel(attendeeUsername, eventId);
                }
            }
            return eventCancelled;
        }
        catch(NullPointerException e){
            return false;
        }
    }

    /**
     * Returns true if a Person with this username already exists, false otherwise
     * @param username The Entities.Person's username as a String
     * @return true if a Person with this username already exists, false otherwise
     */
    public boolean personExists(String username){
        if (am.attendeeExists(username)){
            return true;
        } else if (om.organizerExists(username)){
            return true;
        } else if (sm.speakerExists(username)){
            return true;
        } else if (vm.vipExists(username)){
            return true;
        } else {
            // This username does not exist for any accounts
            return false;
        }

    }

    /**
     * Reads user input and returns it as a String
     * @return String representing user input
     */
    public String readString() {
        Scanner scan = new Scanner(System.in);

        return scan.nextLine();
    }

    /**
     * Reads user input and returns it as an int
     * @return int representing user input
     */
    public int readInt() {
        Scanner scan = new Scanner(System.in);
        String strInput = scan.nextLine();

        return Integer.parseInt(strInput);
    }

    /**
     * Precondition: The user input is either 'true' or 'false'
     *
     * Reads user input and returns it as a boolean
     * @return boolean representing user input
     */
    public boolean readBoolean() {
        Scanner scan = new Scanner(System.in);
        String strInput = scan.nextLine();

        // if input isn't 'true', Boolean.parseBoolean will return false
        return Boolean.parseBoolean(strInput);
    }
    public List<String> getEventList(){
        return es.getEventList();
    }

}

