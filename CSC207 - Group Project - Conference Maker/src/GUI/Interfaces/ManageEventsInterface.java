package GUI.Interfaces;

import ControllerLayer.GUIControllers.ManageEventController;

import java.util.List;

public interface ManageEventsInterface {
    public void display(List<String> eventIDs);
    public ManageEventController getController();
}
