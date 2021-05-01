package GUI.Interfaces;

import ControllerLayer.GUIControllers.MessageScreenController;
import ControllerLayer.GUIControllers.SingleMessageScreenController;

import java.util.List;

public interface ListScreenInterface {
    public void display(List<String> names, List<String> messages);
    public SingleMessageScreenController getController();
    public MessageScreenController getThisController();
}
