import controller.Controller;
import controller.SideBar;
import fuzzy.FuzzyEvaluator;
import fuzzy.FuzzyStats;
import javafx.animation.*;
import javafx.application.Application;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import view.ThermometerVisualization;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class ControllerApplication extends Application {

    private static FuzzyEvaluator fuzzyEvaluator;


    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("view/mainView.fxml"));

        BorderPane borderPane = loader.load();

        Controller controller = loader.getController();

        controller.fuzzySetup("src/main/resources/fuzzy_speed.fcl");

        controller.setupFanImage("src/main/resources/fan.png");

        controller.setupFlow();



        configureStage(primaryStage, borderPane);
        primaryStage.show();


    }

    private static void configureStage(Stage primaryStage, BorderPane borderPane){
        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("AC Control");
    }

    //TODO: add thermomether ticks
    //TODO: add gauge
    //TODO: add sliders to sidebar

}
