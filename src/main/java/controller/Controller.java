package controller;

import javafx.concurrent.Worker;
import javafx.scene.control.Button;
import model.fuzzy.FuzzyEvaluator;
import model.fuzzy.FuzzyStats;
import javafx.animation.*;
import javafx.concurrent.ScheduledService;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import service.ChangeFlowService;
import view.ThermometerVisualization;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Controller {

    private FuzzyEvaluator fuzzyEvaluator;

    private ThermometerVisualization temperatureVisualization;

    private ThermometerVisualization humidityVisualization;

    private ScheduledService<Void> changeFlowService;


    @FXML
    private ImageView fanImageView;

    @FXML
    private Group temperatureGroup;

    @FXML
    private Group humidityGroup;

    @FXML
    private Button userControlButton;

    private int rotationTime = 1000;
    private int angle = 360;


    @FXML
    public void initialize(){

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(rotationTime), e -> {
            RotateTransition rotateTransition = new RotateTransition();

            rotateTransition.setDuration(Duration.millis(rotationTime));
            rotateTransition.setNode(fanImageView);
            rotateTransition.setByAngle(angle);
            rotateTransition.setInterpolator(Interpolator.LINEAR);
            rotateTransition.setAutoReverse(false);
            rotateTransition.play();

            fuzzyEvaluator.evaluate();

            angle = (int) fuzzyEvaluator.getFanAngleInTime(1);
            fuzzyEvaluator.printStats();

            temperatureVisualization.setLevel((int) fuzzyEvaluator.getTemperature());
            humidityVisualization.setLevel((int) fuzzyEvaluator.getAirHumidity());

            System.out.println("The fan turned " + angle + " degrees.");
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }

    public void setupFlowChange(){
        changeFlowService = new ChangeFlowService(fuzzyEvaluator);
        changeFlowService.setPeriod(Duration.seconds(5));
        changeFlowService.start();
    }

    private void stopFlowChange(){
        changeFlowService.cancel();
    }

    private void startFlowChange(){
        changeFlowService.restart();
    }

    public void fuzzySetup(String path){
        FuzzyStats fuzzyStats = new FuzzyStats(10, 40, 0, 1);
        fuzzyEvaluator = new FuzzyEvaluator(path, fuzzyStats);
    }

    public void setupFanImage(String path) throws FileNotFoundException {
        FileInputStream inputStream = new FileInputStream(path);
        Image image = new Image(inputStream);

        fanImageView.imageProperty().set(image);

    }

    public void setupFlow(){

        temperatureVisualization = new ThermometerVisualization(575, 400, Color.web("0xC42021"), 6);
        humidityVisualization = new ThermometerVisualization(700, 400, Color.web("0x1C448E"), 2);

        temperatureGroup.getChildren().addAll(temperatureVisualization.getThermometerGroup());
        humidityGroup.getChildren().add(humidityVisualization.getThermometerGroup());

    }

    public void onShowChartsButtonClicked(){
        fuzzyEvaluator.showChart();
    }

    public void onStartControllingButtonClicked(){
        if(changeFlowService.getState() == Worker.State.CANCELLED){
            startFlowChange();
            userControlButton.textProperty().set("Start controlling!");
        }
        else{
            stopFlowChange();
            userControlButton.textProperty().set("Start auto control");
        }
    }
}
