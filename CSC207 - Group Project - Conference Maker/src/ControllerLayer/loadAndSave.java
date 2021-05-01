package ControllerLayer;

import UseCases.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class loadAndSave{

    /**
     * Method to load all events and users login that are stored in the txt file to the program
     *
     * @param scheduler Instance of scheduler in order to add the loaded events
     * @param speakerManager Instance of UseCases.SpeakerManager in order to add the list of speakers
     * @param attendeeManager Instance of UseCases.AttendeeManager in order to add the list of attendees
     * @param organizerManager Instance of UseCases.OrganizerManager in order to add the list of organizers
     */
    public void loadAll(EventScheduler scheduler, SpeakerManager speakerManager,
                        AttendeeManager attendeeManager, OrganizerManager organizerManager,
                        VipManager vp) {
        List<List<String>> arrEvents = new ArrayList<List<String>>();
        List<List<String>> arrLogins = new ArrayList<List<String>>();
        BufferedReader inputReader;

        try {
            inputReader = new BufferedReader(new FileReader(new File("phase2/src/Files/data.txt")));

            String line;
            // Load events
            line = inputReader.readLine();
            if(line.equalsIgnoreCase("Events")){
                while ((line = inputReader.readLine()) != null){
                    if(!line.isEmpty()){
                        List<String> tempEvents = new ArrayList<String>();
                        tempEvents.add(line);
                        arrEvents.add(tempEvents);
                    }
                    else{
                        break;
                    }
                }
            }
            // Load logins
            line = inputReader.readLine();
            if(line.equalsIgnoreCase("Logins")){
                while ((line = inputReader.readLine()) != null){
                    if(!line.isEmpty()){
                        List<String> tempLogins = new ArrayList<String>();
                        tempLogins.add(line);
                        arrLogins.add(tempLogins);
                    }
                    else{
                        break;
                    }
                }
            }
            inputReader.close();
            System.out.println("--Successfully Loaded File--");
        } catch (IOException e) {
            System.out.println("--Invalid file--");
        }
        updateLogins(arrLogins, attendeeManager, organizerManager, speakerManager, vp);
        updateEvents(arrEvents, scheduler, speakerManager);
    }

    /**
     * Helper method to add the generated events and its speaker from the txt file to UseCases.EventScheduler
     *
     * @param arrEvents Current list of events loaded
     * @param scheduler Instance of UseCases.EventScheduler in order to access its methods
     * @param speakerManager Instance of UseCases.SpeakerManager in order to update the list of speakers
     */
    public void updateEvents(List<List<String>> arrEvents, EventScheduler scheduler,
                             SpeakerManager speakerManager){
        for(List<String> i : arrEvents){
            for (String l : i){
                List<String> tempPeople = new ArrayList<>();
                String[] splitText = l.split(",");
                //System.out.println(splitText.length);
                if(splitText.length > 7){ // Check for empty speakers
                    for(int j = 0; j < Integer.valueOf(splitText[6]); j++){
                        tempPeople.add(splitText[7+j]);
                    }
                }

                boolean isVip = Boolean.parseBoolean(splitText[5]);
                boolean update = scheduler.updateEvents(splitText[0],
                        splitText[1], Integer.valueOf(splitText[2]), tempPeople, isVip,
                        Integer.valueOf(splitText[3]), Integer.valueOf(splitText[4]));
                if(update){
                    //speakerManager.createSpeaker(splitText[3], splitText[4]);
                    for(String k : tempPeople){
                        speakerManager.updateSchedule(k, splitText[1]);
                    }

                }

            }
        }
    }

    /**
     * Helper method to add the generated logins from the txt file to its respective type
     * (Entities.Attendee, Entities.Organizer or Entities.Speaker)
     *
     * @param arrLogins Current list of logins loaded
     * @param attendeeManager Instance of UseCases.AttendeeManager in order to update the list of attendees
     * @param organizerManager Instance of UseCases.OrganizerManager in order to update the list of organizers
     * @param speakerManager Instance of UseCases.SpeakerManager in order to update the list of speakers
     */
    public void updateLogins(List<List<String>> arrLogins, AttendeeManager attendeeManager,
                             OrganizerManager organizerManager, SpeakerManager speakerManager,
                             VipManager vp){
        for(List<String> i : arrLogins){
            for (String l : i){
                String[] splitText = l.split(",");
                if(splitText[0].equalsIgnoreCase("speaker")){
                    if(!attendeeManager.getUsernameToAttendee().containsKey(splitText[1]) &&
                            !organizerManager.getUsernameToOrganizer().containsKey(splitText[1])&&
                            !vp.getUsernameToVip().containsKey(splitText[1])){
                        speakerManager.createSpeaker(splitText[1], splitText[2]);
                    }
                }
                else if (splitText[0].equalsIgnoreCase("attendee")){
                    if(!speakerManager.getUsernameToSpeaker().containsKey(splitText[1]) &&
                            !organizerManager.getUsernameToOrganizer().containsKey(splitText[1])&&
                            !vp.getUsernameToVip().containsKey(splitText[1])){
                        attendeeManager.createAttendee(splitText[1], splitText[2]);
                    }

                }
                else if (splitText[0].equalsIgnoreCase("organizer")){
                    if(!attendeeManager.getUsernameToAttendee().containsKey(splitText[1]) &&
                            !speakerManager.getUsernameToSpeaker().containsKey(splitText[1])&&
                            !vp.getUsernameToVip().containsKey(splitText[1])){
                        organizerManager.createOrganizer(splitText[1], splitText[2]);
                    }
                }
                else if (splitText[0].equalsIgnoreCase("vip")){
                    if(!attendeeManager.getUsernameToAttendee().containsKey(splitText[1]) &&
                            !speakerManager.getUsernameToSpeaker().containsKey(splitText[1])&&
                            !organizerManager.getUsernameToOrganizer().containsKey(splitText[1])){
                        vp.createVip(splitText[1], splitText[2]);
                    }
                }

            }

        }
        System.out.println(speakerManager.getUsernameToSpeaker());
    }
}
