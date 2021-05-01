package ControllerLayer.GUIControllers;

public class MessageScreenController {
    boolean openSent = false;

    /**
     * The empty constructor for MessageScreenController.
     */
    public MessageScreenController(){}

    /**
     * The setter method for openSent.
     * @param bool The boolean representing whether or not the view sent messages button was clicked.
     */
    public void setOpenSent(boolean bool){
        openSent = bool;
    }

    /**
     * The getter method for openSent.
     * @return Returns the boolean of whether or not the view sent messages button was clicked.
     */
    public boolean getOpenSent(){
        return openSent;
    }
}
