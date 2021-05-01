package GUI.Interfaces;
import ControllerLayer.GUIControllers.EventViewController;
import ControllerLayer.GUIControllers.ManageEventsViewController;

import java.util.List;

public interface ManageEventsViewInterface {
    public void display(String title, List<String> events);
    public ManageEventsViewController getController();
    public EventViewController getEventController();
}
