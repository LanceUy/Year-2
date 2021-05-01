package GUI;

import ControllerLayer.AttendeeMainScreen;
import ControllerLayer.MainPresenter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GUI.fxml"));
        Parent root = fxmlLoader.load();
        MainPresenter mainPresenter = fxmlLoader.getController();
        Scene mainScene = new Scene(root);
        mainPresenter.setMainScene(mainScene);
        primaryStage.setTitle("Event Manager");
        primaryStage.setScene(mainScene);
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> mainPresenter.closeProgram(primaryStage));
    }
    public static void main(String[] args) {
        launch(args);
    }
}
