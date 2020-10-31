package controller;

import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineJoin;
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
        FuzzyStats fuzzyStats = new FuzzyStats(10, 40, 100);
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

        int circleRadius = 50;

        Circle thermometerCircle = new Circle();
        thermometerCircle.setRadius(circleRadius);
        thermometerCircle.setCenterX(600);
        thermometerCircle.setCenterY(400);


        int rectangleHeight = 300;
        Rectangle rectangle = new Rectangle(50, rectangleHeight);
        rectangle.setX(575);
        rectangle.setY(100);

        Shape thermometer = Shape.union(thermometerCircle, rectangle);

        Shape thermometerOutline = Shape.union(thermometerCircle, rectangle);
        thermometerOutline.setStroke(Color.DARKGRAY);
        thermometerOutline.setStrokeWidth(4);
        thermometerOutline.setFill(Color.TRANSPARENT);
        thermometerOutline.setStrokeLineJoin(StrokeLineJoin.BEVEL);


        int zeroTemperatureLevel = 350;

        Rectangle thermometerLevel = new Rectangle(200, circleRadius + rectangleHeight);
        thermometerLevel.setX(500);
        thermometerLevel.setY(zeroTemperatureLevel);
        thermometerLevel.setFill(Color.RED);
        thermometerLevel.setClip(thermometer);



        Group root = new Group(imageView, thermometerLevel, thermometerOutline);
        Scene scene = new Scene(root, 800, 600, Color.DIMGREY);


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

                    angle = (int) fuzzyEvaluator.getFanSpeed() * rotationTime/100;
                    fuzzyEvaluator.printStats();

                    thermometerLevel.setY(zeroTemperatureLevel - fuzzyEvaluator.getTemperature()*5);

                    System.out.println(angle);
                }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }


    //TODO: add thermometer and show air humidity


}
