package controller;

import fuzzy.FuzzyEvaluator;
import fuzzy.FuzzyStats;
import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import visualization.ThermometerVisualization;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class ControllerApplication extends Application {

    private FuzzyEvaluator fuzzyEvaluator;
    static int rotationTime = 1000;
    static int angle = 360;

    public static void main(String[] args) {
        launch(args);
    }

    private void fuzzySetup(){
        FuzzyStats fuzzyStats = new FuzzyStats(10, 40, 100, 1);
        fuzzyEvaluator = new FuzzyEvaluator("src/main/resources/fuzzy_speed.fcl", fuzzyStats);
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {

        fuzzySetup();

        primaryStage.setTitle("AC Control");

        FileInputStream inputStream = new FileInputStream("src/main/resources/fan.png");
        Image image = new Image(inputStream);

        ImageView imageView = new ImageView(image);

        imageView.setX(100);
        imageView.setY(100);

        imageView.setFitHeight(300);
        imageView.setFitWidth(300);
        imageView.setPreserveRatio(true);

        ThermometerVisualization thermometer = new ThermometerVisualization(575, 400, Color.web("0xC42021"));
        ThermometerVisualization humidity = new ThermometerVisualization(700, 400, Color.web("0x1C448E"));

        Group root = new Group(imageView, thermometer.getThermometerGroup(), humidity.getThermometerGroup());
        Scene scene = new Scene(root, 800, 600, Color.web("0x6F8695"));


        primaryStage.setScene(scene);
        primaryStage.show();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(rotationTime), (e) -> {
//                new KeyValue(imageView.rotateProperty(), 360, Interpolator.LINEAR)
                    RotateTransition rotateTransition = new RotateTransition();

                    rotateTransition.setDuration(Duration.millis(rotationTime));
                    rotateTransition.setNode(imageView);
                    rotateTransition.setByAngle(angle);
                    rotateTransition.setInterpolator(Interpolator.LINEAR);
                    rotateTransition.setAutoReverse(false);
                    rotateTransition.play();

                    fuzzyEvaluator.evaluate();

                    int timeDelta = 1;

                    angle = (int) fuzzyEvaluator.getFanSpeed() * timeDelta +
                            (int) (fuzzyEvaluator.getFanAcceleration() * timeDelta * timeDelta) / 2;
                    fuzzyEvaluator.printStats();

                    thermometer.setLevel((int) fuzzyEvaluator.getTemperature());
                    humidity.setLevel((int) fuzzyEvaluator.getAirHumidity());

                    System.out.println("The fan turned " + angle + " degrees.");
                }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }

    //TODO: add thermomether ticks and text labels
    //TODO: add gauge

}
