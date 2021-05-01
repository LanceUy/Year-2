package ControllerLayer.GUIControllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class ManageEventController {
    private boolean cancelEvent = false;
    private boolean addEvent = false;
    private boolean downloadEvent = false;
    private String id;
    private int time;
    private List<String> listSpeakerUsername;
    private String roomNum;
    private boolean isVip;
    private int duration;
    private int capacity;

    /**
     * The empty constructor for ManageEventController.
     */
    public ManageEventController() {
    }

    /**
     * The setter method for cancelEvent.
     * @param bool The boolean value of whether or not the user clicked the cancel event button.
     */
    public void setCancelEvent(boolean bool) {
        cancelEvent = bool;
    }

    /**
     * The getter method for cancelEvent.
     * @return Returns the boolean of whether or not the user clicked the cancel event button.
     */
    public boolean getCancelEvent() {
        return cancelEvent;
    }

    /**
     * The setter method for id.
     * @param id The ID of the new event.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * The setter method for time.
     * @param t The integer value of when this event begins.
     */
    public void setTime(int t) {
        this.time = t;
    }

    /**
     * The setter method for listSpeakerUsername.
     * @param speakers The string representing all the speakers that will be at this event.
     */
    public void setListSpeakerUsername(String speakers) {
        if(!speakers.equals("")) {
            listSpeakerUsername = Arrays.asList(speakers.split(", "));
        }
        else{
            listSpeakerUsername = new ArrayList<>();
        }
    }

    /**
     * The setter for roomNum.
     * @param n The string representing the room of this new event.
     */
    public void setRoomNum(String n) {
        roomNum = n;
    }

    /**
     * The setter method for isVip.
     * @param b The boolean of whether of not this event is VIP only.
     */
    public void setVip(boolean b) {
        isVip = b;
    }

    /**
     * The setter method for duration.
     * @param d The integer duration of this event.
     */
    public void setDuration(int d) {
        duration = d;
    }

    /**
     * The setter method for capacity.
     * @param c The integer capacity of this event.
     */
    public void setCapacity(int c) {
        capacity = c;
    }

    /**
     * The getter method for id.
     * @return Returns the String ID of the new event's ID.
     */
    public String getId() {
        return id;
    }

    /**
     * The getter method for time.
     * @return Returns the String value of the start time of this new event.
     */
    public int getTime() {
        return time;
    }

    /**
     * The getter method for listSpeakerUsername.
     * @return Returns the list of strings representing usernames of the speakers of this new event.
     */
    public List<String> getListSpeakerUsername() {
        return listSpeakerUsername;
    }

    /**
     * The getter method for roomNum.
     * @return The String value representing the new event's room number.
     */
    public String getRoomNum() {
        return roomNum;
    }

    /**
     * The getter method for isVip.
     * @return Returns the boolean of whether or not this event is VIP only.
     */
    public boolean getVIP() {
        return isVip;
    }

    /**
     * The getter method for duration.
     * @return Returns the integer value of how long this event lasts. In hours.
     */
    public int getDuration() {
        return duration;
    }

    /**
     * The getter for capacity.
     * @return Returns the integer value of the capacity of this new event.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * The setter method for addEvent
     * @param b The boolean representing whether or not the add event button was clicked.
     */
    public void setAddEvent(boolean b){
        addEvent = b;
    }
    /**
     * The getter method for addEvent.
     * @return Returns the boolean of whether or not the user clicked the add event button.
     */
    public boolean getAddEvent(){
        return  addEvent;
    }
    /**
     * The setter method for downloadEvent.
     * @param b The boolean representing whether or not the download event button was clicked.
     */
    public void setDownloadEvent(boolean b){
        downloadEvent = b;
    }
    /**
     * The getter method for downloadEvent.
     * @return Returns the boolean of whether or not the user clicked the download event button.
     */
    public boolean getDownloadEvent(){
        return downloadEvent;
    }
}
