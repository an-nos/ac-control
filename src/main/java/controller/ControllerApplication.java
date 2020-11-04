package controller;

import fuzzy.FuzzyEvaluator;
import fuzzy.FuzzyStats;
import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

    private ImageView getImageView(int size, String path) throws FileNotFoundException {
        FileInputStream inputStream = new FileInputStream(path);
        Image image = new Image(inputStream);

        ImageView fanImageView = new ImageView(image);

        fanImageView.setFitHeight(size);
        fanImageView.setFitWidth(size);
        fanImageView.setPreserveRatio(true);

        return fanImageView;
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {

        fuzzySetup();

        primaryStage.setTitle("AC Control");

        BorderPane borderPane = new BorderPane();
        VBox vBox = new VBox();
        SideBar sideBar = new SideBar(100, 600);

        borderPane.setCenter(vBox);
        borderPane.setRight(sideBar);

        ImageView fanImageView = getImageView(300, "src/main/resources/fan.png");

        fanImageView.setX(100);
        fanImageView.setY(100);

        ThermometerVisualization thermometer = new ThermometerVisualization(575, 400, Color.web("0xC42021"), 6);
        ThermometerVisualization humidity = new ThermometerVisualization(700, 400, Color.web("0x1C448E"), 2);

        Button showChartsButton = new Button("Show charts");

        showChartsButton.setOnAction(value ->
                fuzzyEvaluator.showChart()
        );

        Group root = new Group(fanImageView,
                thermometer.getThermometerGroup(),
                humidity.getThermometerGroup(),
                showChartsButton
        );

        vBox.getChildren().addAll(root);
        vBox.setStyle("-fx-background-color: #6F8695;");
        vBox.setPrefWidth(800);

        Scene scene = new Scene(borderPane, 900, 600);


        primaryStage.setScene(scene);

        primaryStage.show();

        ScheduledService<Void> changeFlowService = new ScheduledService<>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<>() {
                    @Override
                    protected Void call() throws Exception {
                        fuzzyEvaluator.nextFlowParameters();
                        System.out.println("Parameters changed!");
                        return null;
                    }
                };
            }
        };

        changeFlowService.setPeriod(Duration.seconds(5));

        changeFlowService.start();


        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(rotationTime), e -> {
//                new KeyValue(imageView.rotateProperty(), 360, Interpolator.LINEAR)
                    RotateTransition rotateTransition = new RotateTransition();

                    rotateTransition.setDuration(Duration.millis(rotationTime));
                    rotateTransition.setNode(fanImageView);
                    rotateTransition.setByAngle(angle);
                    rotateTransition.setInterpolator(Interpolator.LINEAR);
                    rotateTransition.setAutoReverse(false);
                    rotateTransition.play();

                    fuzzyEvaluator.evaluate();


                    int timeDelta = 1;

                    angle = (int) fuzzyEvaluator.getFanAngleInTime(timeDelta);
                    fuzzyEvaluator.printStats();

                    thermometer.setLevel((int) fuzzyEvaluator.getTemperature());
                    humidity.setLevel((int) fuzzyEvaluator.getAirHumidity());

                    System.out.println("The fan turned " + angle + " degrees.");
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();


    }

    //TODO: add thermomether ticks
    //TODO: add gauge
    //TODO: add sliders to sidebar

}
