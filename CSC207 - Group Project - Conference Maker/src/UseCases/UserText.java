package UseCases;

import Entities.Attendee;
import Entities.Person;
import Entities.Vip;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserText {
    private List<Person> people = new ArrayList<>();
    private List<String> sentMessages = new ArrayList<>();

    /**
     * Precondition: T is a subtype of Person
     * <p>
     * Extends a list of Person to people.
     *
     * @param list The list of Person to add into people.
     * @param <T>  The type of list. It is assumed to be a subtype of Person.
     */
    public <T> void addPeopleToList(List<T> list) {
        for (T person : list) {
            people.add((Person) person);
        }
    }

    /**
     * Sends a message to a user
     * <p>
     * Extends a list of Person to people.
     *
     * @param message   The content of the message being sent.
     * @param sender    The attendee that is being replied to.
     * @param recipient The user that is replying
     */
    public void sendSingleMessage(String message, String sender, String recipient) {
        for (Person user : people) {
            if (user.getUsername().equals(recipient)) {
                user.addToMessageStorage(message, sender);
            }
        }
    }

    /**
     * Creates a message object and stores in the every Speaker's message list, and the sender's message list.
     *
     * @param message       The String message to be sent.
     * @param currentPerson The sender of the message.
     */
    public void messageAllSpeakers(String message, String currentPerson) {
        for (Person user : people) {
            if (user.isSpeakerType()) {
                user.addToMessageStorage(message, currentPerson);
                // defined in Person Class
            }
        }
    }

    /**
     * Creates a message object and stores in the every Attendee's message list, and the sender's message list.
     *
     * @param message       The String message to be sent.
     * @param currentPerson The sender of the message.
     */
    public void messageAllAttendees(String message, String currentPerson) {
        for (Person user : people) {
            if (user.isAttendeeType()) {
                user.addToMessageStorage(message, currentPerson);
            }
        }
    }

    /**
     * Creates a message object and stores in the every Attendee's message list, and the sender's message list.
     *
     * @param events  The events the speakers want to message
     * @param message The message being sent.
     * @param sender  The sender of the message.
     */
    public void messageAllAttendeesInEvents(List<String> events, String message, String sender) {
        for (Person user : people) {
            if (user.isAttendeeType()) {
                boolean sent = false; //so a message is not sent twice if an attendee is signed up for multiple talks
                for (String talk : events) {
                    if (((Attendee) user).getSchedule().contains(talk) && !sent) {
                        user.addToMessageStorage(message, sender);
                        sent = true;
                    }
                }
            } else if (user.isVipType()) {
                boolean sent = false;
                for (String talk : events) {
                    if (((Vip) user).getSchedule().contains(talk) && !sent) {
                        user.addToMessageStorage(message, sender);
                        sent = true;
                    }
                }
            }
            if (user.isVipType()) {
                boolean sent = false; //so a message is not sent twice if an attendee is signed up for multiple talks
                for (String talk : events) {
                    if (((Vip) user).getSchedule().contains(talk) && !sent) {
                        user.addToMessageStorage(message, sender);
                        sent = true;
                    }
                }
            }
        }
    }

    /**
     * Returns the messages sent to the currently logged in user as a string. It takes the form
     * "sender1: message, sender2: message, ..."
     *
     * @return Returns the string of messages sent to the current user.
     */
    public List<String> readMessage(String currPerson) {
        for (Person person : people) {
            if (person.getUsername().equals(currPerson)) {
                List<String> list = person.getStoredMessagesList();
                List<String> newList = new ArrayList<>();
                for (String messages : list) {
                    newList.add(replaceLast("---Sent", "---Seen", messages));
                }
                person.setStoredMessagesList(newList);
                return newList;
            }
        }
        return Collections.emptyList();
    }

    /**
     * Returns the messages sent by the currently logged in user as a string.
     *
     * @return Returns the string of messages sent by the current user.
     */
    public List<String> seeSentMessages(String currPerson) {
        for (Person person : people) {
            List<String> list = person.getStoredMessagesList();
            for (String messages : list) {
                if (messages.contains(currPerson + ":")) {
                    this.sentMessages.add(messages.replaceFirst("[0-9]+\\.", ""));
                }
            }
        }
        return this.sentMessages;
    }

    /**
     * Marks the messages selected as unread: ("Sent" in this design")
     *
     * @param currPerson Current person logged in
     * @param messages   The messages they want to mark as unread
     */
    public void markUnread(String currPerson, List<String> messages) {
        for (Person person : people) {
            if (person.getUsername().equals(currPerson)) {
                List<String> list = person.getStoredMessagesList();
                List<String> newList = new ArrayList<>();
                if (messages.contains("All")) {
                    for (String msg : list) {
                        newList.add(replaceLast("---Seen", "---Sent", msg));
                    }
                } else {
                    for (String msg : list) {
                        if (stringContainsItemFromList(msg + ".", messages))
                            newList.add(replaceLast("---Seen", "---Sent", msg));
                        else {
                            newList.add(msg);
                        }
                    }
                }
                person.setStoredMessagesList(newList);
            }
        }

    }

    /**
     * Deletes the messages selected
     *
     * @param currPerson Current person logged in
     * @param messages   The messages they want to mark as unread
     */
    public void deleteMessages(String currPerson, List<String> messages, boolean archived) {
        for (Person person : people) {
            if (person.getUsername().equals(currPerson)) {
                List<String> list = person.getStoredMessagesList();
                List<String> newList = new ArrayList<>();
                List<String> archivedList = new ArrayList<>();
                if (messages.contains("All")) {
                    person.setStoredMessagesList(newList);
                    if (archived) {
                        person.addToArchivedMessagesList(list);
                    }
                } else {
                    for (String msg : list) {
                        if (!stringContainsItemFromList(msg + ".", messages))
                            newList.add(msg);
                        else {
                            archivedList.add(msg);
                        }
                    }
                }
                if (archived) {
                    person.addToArchivedMessagesList(archivedList);
                }
                person.setStoredMessagesList(newList);
            }
        }
    }


    //Helper method change strings... Credit goes to https://bytenota.com/java-replace-last-occurrence-of-a-string/
    //replaces last occurrence of "find" with "replace" in "string"
    private static String replaceLast(String find, String replace, String string) {
        int lastIndex = string.lastIndexOf(find);

        if (lastIndex == -1) {
            return string;
        }

        String beginString = string.substring(0, lastIndex);
        String endString = string.substring(lastIndex + find.length());

        return beginString + replace + endString;
    }

    //Helper method to check if a string contains any strings from a list.
    //Credit goes to https://stackoverflow.com/questions/8992100/test-if-a-string-contains-any-of-the-strings-from-an-array
    private static boolean stringContainsItemFromList(String inputStr, List<String> items) {
        for (int i = 0; i < items.size(); i++) {
            if (inputStr.contains(items.get(i))) {
                return true;
            }
        }
        return false;
    }
}

