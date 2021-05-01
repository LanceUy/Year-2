package UseCases;

import Entities.Organizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrganizerManager{
    private List<Organizer> allOrganizers;
    private Map<String, Organizer> usernameToOrganizer;

    /**
     * Creates an UseCases.OrganizerManager object and initializes allOrganizers as an empty ArrayList and
     * usernameToOrganizer as an empty HashMap.
     */
    public OrganizerManager(){
        allOrganizers = new ArrayList<>();
        usernameToOrganizer = new HashMap<>();
    }

    /**
     * Precondition: username does not already exist
     *
     * Creates an Entities.Organizer object, if the username does not already exist.
     * @param username String representing Entities.Organizer's username
     * @param password String representing Entities.Organizer's password
     */
    public void createOrganizer(String username, String password){
        Organizer organizer = new Organizer(username, password);
        allOrganizers.add(organizer);
        usernameToOrganizer.put(username, organizer);
    }

    /**
     * Adds Entities.Organizer object to List allOrganizers, and Map usernameToOrganizer.
     * @param o Entities.Organizer object
     */
    public void updateOrganizerObjects(Organizer o){
        allOrganizers.add(o);
        usernameToOrganizer.put(o.getUsername(), o);
    }

    /**
     * Get the List allOrganizers.
     * @return List allOrganizers
     */
    public List<Organizer> getAllOrganizers(){
        return allOrganizers;
    }

    /**
     * Get the Map usernameToOrganizer.
     * @return Map usernameToOrganizer
     */
    public Map<String, Organizer> getUsernameToOrganizer() {
        return usernameToOrganizer;
    }

    /**
     * Checks if the username exists and if it's password is correct.
     * @param username String representing Entities.Organizer's username
     * @param password String representing Entities.Organizer's password
     * @return boolean; true if username exists and it's password is correct, false if otherwise.
     */
    public boolean checkLogin(String username, String password){
        boolean valid = false;
        for (Organizer user: allOrganizers){
            if (user.getUsername().equals(username) && user.checkPassword(password)) {
                valid = true;
                break;
            }
        }
        return valid;
    }

    /**
     * Returns true if a Organizer with this username already exists, false otherwise
     * @param username The Entities.Organizer's username as a String
     * @return true if a Organizer with this username already exists, false otherwise
     */
    public boolean organizerExists(String username){
        return usernameToOrganizer.containsKey(username);
    }
}
