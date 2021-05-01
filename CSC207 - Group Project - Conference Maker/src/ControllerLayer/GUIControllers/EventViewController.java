package ControllerLayer.GUIControllers;

public class EventViewController {
    private boolean cancel = false;
    private String eventID;

    /**
     * Empty constructor for EventViewController
     */
    public  EventViewController(){
    }

    /**
     * The setter method for cancel.
     * @param cancel A boolean value for whether or not the user clicked the cancel button.
     */
    public void setCancel(boolean cancel){
        this.cancel = cancel;
    }

    /**
     * The getter method for cancel.
     * @return Returns the boolean of whether or not the user clicked the cancel button.
     */
    public boolean getCancel(){
        return cancel;
    }

    /**
     * The getter method for eventID.
     * @return Returns the eventID value.
     */
    public String getEventID(){
        return eventID;
    }

    /**
     * The setter method for event ID.
     * @param id The Event ID that eventID should be set to.
     */
    public void setEventID(String id){
        eventID = id;
    }
}
