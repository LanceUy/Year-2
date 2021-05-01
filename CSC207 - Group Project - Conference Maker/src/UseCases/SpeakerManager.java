package UseCases;

import Entities.Speaker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpeakerManager {
    private List<Speaker> allSpeakers;
    private Map<String, Speaker> usernameToSpeaker;

    /**
     * Creates an UseCases.OrganizerManager object and initializes allSpeakers as an empty ArrayList and
     * usernameToSpeaker as an empty HashMap.
     */
    public SpeakerManager(){
        allSpeakers = new ArrayList<>();
        usernameToSpeaker = new HashMap<>();
    }

    /**
     * Precondition: username does not already exist
     *
     * Creates a Entities.Speaker object, if the username does not already exist.
     * @param username String representing Entities.Speaker's username
     * @param password String representing Entities.Speaker's password
     */
    public void createSpeaker(String username, String password){
        Speaker speaker = new Speaker(username, password);
        allSpeakers.add(speaker);
        usernameToSpeaker.put(username, speaker);
    }

    /**
     * Adds Entities.Speaker object to List allSpeakers, and Map usernameToSpeaker.
     * @param s Entities.Speaker object
     */
    public void updateSpeakerObjects(Speaker s){
        allSpeakers.add(s);
        usernameToSpeaker.put(s.getUsername(), s);
    }

    /**
     * Get the List allSpeakers.
     * @return List allSpeakers
     */
    public List<Speaker> getAllSpeakers(){
        return allSpeakers;
    }

    /**
     * Get the Map usernameToSpeaker.
     * @return Map usernameToSpeaker.
     */
    public Map<String, Speaker> getUsernameToSpeaker() {
        return usernameToSpeaker;
    }

    /**
     * Checks if the username exists and if it's password is correct.
     * @param username String representing Entities.Speaker's username
     * @param password String representing Entities.Speaker's password
     * @return boolean; true if username exists and it's password is correct, false if otherwise.
     */
    public boolean checkLogin(String username, String password){
        boolean valid = false;
        for (Speaker user: allSpeakers){
            if (user.getUsername().equals(username) && user.checkPassword(password)) {
                valid = true;
                break;
            }
        }
        return valid;
    }

    /**
     * Get the schedule of Entities.Speaker with speakerUsername.
     * @param speakerUsername String representing Entities.Speaker's username
     * @return List representing the Entities.Speaker's schedule.
     */
    public List<String> getSchedule(String speakerUsername){
        return usernameToSpeaker.get(speakerUsername).getSchedule();
    }

    /**
     * Precondition: The Entities.Speaker with speakerUsername and the Entities.Event with evendId exist
     *
     * Add an Entities.Event with eventId to the schedule of Entities.Speaker with speakerUsername.
     * @param speakerUsername String representing Entities.Speaker's username
     * @param eventId String representing the id of the event
     */
    public void updateSchedule(String speakerUsername, String eventId){
        getSchedule(speakerUsername).add(eventId);
    }

    /**
     * Remove Entities.Event with eventId from the schedule of Entities.Speaker with speakerUsername.
     * @param speakerUsername String representing Entities.Speaker's username
     * @param eventId String representing the id of the event
     */
    public void removeFromSchedule(String speakerUsername, String eventId){
        getSchedule(speakerUsername).remove(eventId);
    }

    /**
     * Add Entities.Person with contact username to the contact list of Entities.Speaker with speakerUsername.
     * @param speakerUsername String representing Entities.Speaker's username
     * @param contact String representing the contact's username
     */
    public void addToContactList(String speakerUsername, String contact) {
        usernameToSpeaker.get(speakerUsername).getContactList().add(contact);
    }

    /**
     * Remove Entities.Person with contact username from the contact list of Entities.Speaker with speakerUsername.
     * @param speakerUsername String representing Entities.Speaker's username
     * @param contact String representing the contact's username
     */
    public void removeFromContactList(String speakerUsername, String contact){
        usernameToSpeaker.get(speakerUsername).getContactList().remove(contact);
    }

    /**
     * Check if Entities.Person with personUsername is in the contact list of Entities.Speaker with speakerUsername.
     * @param speakerUsername String representing Entities.Speaker's username
     * @param personUsername String representing Entities.Person's username
     * @return boolean; true if Entities.Person with personUsername is a contact, false if they are not.
     */
    public boolean isContact(String speakerUsername, String personUsername){
        return usernameToSpeaker.get(speakerUsername).getContactList().contains(personUsername);
    }

    /**
     * Returns true if a Speaker with this username already exists, false otherwise
     * @param username The Entities.Speaker's username as a String
     * @return true if a Speaker with this username already exists, false otherwise
     */
    public boolean speakerExists(String username){
        return usernameToSpeaker.containsKey(username);
    }
}
