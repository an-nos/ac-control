package controller;

import javafx.animation.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

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
        FuzzyStats fuzzyStats = new FuzzyStats(10, 40, 10);
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

        Group root = new Group(imageView);
        Scene scene = new Scene(root, 500, 500, Color.BLACK);


        primaryStage.setScene(scene);
        primaryStage.show();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(rotationTime), (e) ->
//                new KeyValue(imageView.rotateProperty(), 360, Interpolator.LINEAR)
                {
                    RotateTransition rotateTransition = new RotateTransition();

                    rotateTransition.setDuration(Duration.millis(rotationTime));

                    rotateTransition.setNode(imageView);

                    rotateTransition.setByAngle(angle);

                    rotateTransition.setInterpolator(Interpolator.LINEAR);

                    rotateTransition.setAutoReverse(false);

                    rotateTransition.play();

                    fuzzyEvaluator.evaluate();

                    angle = (int) fuzzyEvaluator.getFanSpeed() * rotationTime/1000;
                    fuzzyEvaluator.printStats();

                    System.out.println(angle);
                }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }
//TODO: edit speed
//TODO: add thermometer and show air humidity


}
