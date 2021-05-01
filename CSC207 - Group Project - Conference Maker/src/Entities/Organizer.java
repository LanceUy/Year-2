package Entities;

public class Organizer extends Person{
    /**
     * Creates an Entities.Organizer object that inherits from Entities.Person and sets variables isSpeaker and isAttendee to false,
     * while isOrganizer is set to true.
     * @param username String representing Entities.Organizer's username
     * @param password String representing Entities.Organizer's password
     */
    public Organizer(String username, String password) {
        super(username, password);
        this.isSpeaker = false;
        this.isAttendee = false;
        this.isOrganizer = true;
        this.isVip = false;
    }
    // Since Entities.Organizer has every Entities.Person in its contact list,
    // the methods that check if a certain Entities.Person is in its contact list
    // should just check that if it is a type Entities.Person then its in its contact list
    // instead of using method getContactList which are present in Entities.Speaker and Entities.Attendee
}
