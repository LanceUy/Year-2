package ControllerLayer.GUIControllers;

import java.util.ArrayList;
import java.util.List;

public class SingleMessageScreenController {
    private String message;
    private boolean read = true;
    private boolean delete = false;
    private boolean archive = false;

    /**
     * The empty constructor for SingleMessageScreenController.
     */
    public SingleMessageScreenController(){
    }

    /**
     * The setter method for read.
     * @param read The boolean of whether or not this message was marked as read.
     */
    public void setRead(boolean read){
        this.read = read;
    }

    /**
     * The setter method for delete.
     * @param delete The boolean of whether or not the delete button was clicked.
     */
    public void setDelete(boolean delete){
        this.delete = delete;
    }

    /**
     * The setter method for archive.
     * @param archive The boolean of whether or not the message was archived.
     */
    public void setArchive(boolean archive){
        this.archive = archive;
    }

    /**
     * The setter method for message.
     * @param msg The message content of the current message.
     */
    public void setMessage(String msg){
        message = msg;
    }

    /**
     * The getter method for read.
     * @return Returns the boolean of whether or not this message was marked as read.
     */
    public boolean getRead(){
        return read;
    }

    /**
     * The getter method for archive.
     * @return Returns the boolean of whether or not this message was marked as archived.
     */
    public boolean getArchive(){
        return archive;
    }

    /**
     * The getter method for delete.
     * @return Returns the boolean of whether or not the delete button was clicked.
     */
    public boolean getDelete(){
        return delete;
    }

    /**
     * The getter method for message.
     * @return Returns the message content of the message.
     */
    public String getMessage(){
        return message;
    }
}
