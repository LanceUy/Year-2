package ControllerLayer.GUIControllers;

public class ManageEventsViewController {
    private boolean addEvent = false;
    private String eventID;
    private boolean saveEvents = false;

    /**
     * The empty constructor for ManageEventsViewController.
     */
    public ManageEventsViewController(){}

    /**
     * The setter method for addEvent
     * @param bool The boolean representing whether or not the add event button was clicked.
     */
    public void setAddEvent(boolean bool){
        addEvent = bool;
    }
    /**
     * The getter method for addEvent.
     * @return Returns the boolean of whether or not the user clicked the add event button.
     */
    public boolean getAddEvent(){
        return addEvent;
    }

    /**
     * The setter method for saveEvents
     * @param bool The boolean representing whether or not the save events button was clicked.
     */
    public void setSaveEvents(boolean bool){
        saveEvents = bool;
    }
    /**
     * The getter method for saveEvents.
     * @return Returns the boolean of whether or not the user clicked the save events button.
     */
    public boolean getSaveEvents(){
        return saveEvents;
    }

    /**
     * The getter method for eventID.
     * @return The eventID of the chosen event.
     */
    public String getEventID(){
        return eventID;
    }

    /**
     * The setter method for eventID.
     * @param id The ID of the chosen event.
     */
    public void setEventID(String id){
        eventID = id;
    }

}
