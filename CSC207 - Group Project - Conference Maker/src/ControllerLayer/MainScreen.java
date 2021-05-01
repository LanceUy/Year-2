package ControllerLayer;

import javafx.scene.text.Text;

import java.io.IOException;
import java.util.List;

public interface MainScreen {
    public List<Object> openMainScreen() throws IOException;
    public Text getWelcomeText();
}
