package UseCases;

import Entities.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventScheduler {

    private List<String> ListOfEvents;
    private Map<String, Event> idToEvent;

    /**
     * Creates an UseCases.EventScheduler object that stores the list of all Events and stores the nap where the item is the
     * name of the Entities.Event and the value is the event object corresponding to the name.
     */
    public EventScheduler(){
        ListOfEvents =  new ArrayList<String>();
        idToEvent = new HashMap<>();

    }

    /**
     * Precondition: The Entities.Speaker with speakerUsername exists
     *
     * Updates the list of all Events by creating a new Entities.Event object that stores the room number, the name,
     * the time and the speaker's username and then adding it to the list.
     *
     * @param roomNum The room number of where the event is
     * @param id The name of the event
     * @param time The time the event starts
     * @param speakerUsername The username of the speaker
     * @param isVip true if the event is for VIP's only, false otherwise
     * @param duration number of hours this event lasts
     * @return true if Entities.Event was updated into main list/map
     */
    public boolean updateEvents(String roomNum, String id, int time, List<String> speakerUsername, boolean isVip,
                                int duration, int max){
        if(validEvent(roomNum, id, time, speakerUsername, duration)){
            Event s = new Event(roomNum, id, time, speakerUsername, isVip, duration, max);//, speakerPassword);
            ListOfEvents.add(id);
            idToEvent.put(id, s);
            System.out.println(idToEvent);
            return true;
        }
        return false;
    }

    /**
     * Adds event's name as the key and event object as the value to this map idToEvent.
     *
     * @param e the event object want added into the map
     */
    public void updateEventObjects(Event e){
        ListOfEvents.add(e.getID());
        idToEvent.put(e.getID(), e);
    }

    /**
     * Return true if the event is removed from the list of all events using the name of the event. Otherwise, returns
     * false.
     *
     * @param id The name of the event want removed
     * @return true if the event is removed from list of events, false if the event isn't
     */
    public boolean removeEvent(String id){
        if (ListOfEvents.contains(id)){
            ListOfEvents.remove(id);
            idToEvent.remove(id);
            return true;
        }
        return false;
    }

    /**
     * Returns the map where the key is the name of the event and the value corresponded is the event object.
     *
     * @return the map where the key is the name of the event and the value corresponded is the event object
     */
    public Map<String, Event> getIdToEvent(){
        return idToEvent;
    }

    /**
     * Returns true if an event object created using room number, name, time and speaker's username has overlapping
     * times with any events in the list of all events, has name not identical with any other and whether the speaker's
     * username can join. Otherwise, returns false.
     *
     * @param roomNum The room number of where the Entities.Event is.
     * @param id The name of the Entities.Event.
     * @param time The time the Entities.Event starts.
     * @param speakerUsername The username of the Entities.Speaker.
     * @param duration number of hours this event lasts.
     */
    public boolean validEvent(String roomNum, String id, int time, List<String> speakerUsername, int duration){
        if(time < 9 || time > 16){
            return false;
        }
        for(String i : ListOfEvents){
            // Check for double booking room
            Event e = idToEvent.get(i);
            if(time >= e.getTime() && time < e.getTime() + e.getDuration() &&
                    roomNum.equals(idToEvent.get(i).getRoomNum())){
                return false;
            }
            if (time + duration > e.getTime() && time + duration <= e.getTime() + e.getDuration() &&
                    roomNum.equals(idToEvent.get(i).getRoomNum())){
                return false;
            }
            // ID for each event must be unique
            if(id.equals(e.getID())){
                return false;
            }
            // Check for speaker at two places at same time
            if(time == e.getTime() &&
                    speakerUsername.equals(e.getSpeaker()) && !speakerUsername.equals(new ArrayList<>())){
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the list of all events.
     *
     * @return the list of all events
     */
    public List<String> getEventList(){
        return ListOfEvents;
    }

}
