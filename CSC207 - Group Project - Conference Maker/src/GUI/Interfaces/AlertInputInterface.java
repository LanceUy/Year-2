package GUI.Interfaces;

import ControllerLayer.*;
import javafx.application.Application;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.awt.*;

public interface AlertInputInterface {
    public void display(String title, String message);
    public String getInput();
}
