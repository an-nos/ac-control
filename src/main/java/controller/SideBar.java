package controller;

import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


public class SideBar extends StackPane {

    public SideBar(int width, int height){
        this.setPrefWidth(width);
        this.setPrefHeight(height);
        this.setStyle("-fx-background-color: #000000;");

        Button button = new Button("USER PARAMETERS");
        button.setPrefWidth(80);
        this.getChildren().add(button);
    }

    public Slider createSlider(){
        return null;
    }

}
