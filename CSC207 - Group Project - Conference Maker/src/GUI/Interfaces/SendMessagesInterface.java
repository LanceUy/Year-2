package GUI.Interfaces;

import java.util.List;

public interface SendMessagesInterface {
    public void display(List<String> names);
    public String getMessage();
    public List<String> getReceivers();
}
