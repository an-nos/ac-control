package view;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.text.Font;

public class ThermometerVisualization {

    private Group thermometerGroup;
    private int zeroLevel;
    private Rectangle thermometerLevel;
    private int xPos;
    private int yPos;
    private int multiplier = 1;
    private Color color;
    private Label textLabel;

    private Shape outlineShape;


    public ThermometerVisualization(int xPos, int yPos, Color color, int multiplier){
        int circleRadius = 50;
        int rectangleHeight = 300;
        this.xPos = xPos;
        this.yPos = yPos;
        this.zeroLevel = circleRadius + rectangleHeight;
        this.color = color;
        this.multiplier = multiplier;

        this.textLabel = new Label();
        textLabel.setFont(new Font("Cambria", 35));
        textLabel.setStyle("-fx-text-fill : white;");

        textLabel.setTranslateX(xPos-25);
        textLabel.setTranslateY(yPos-15);

        Circle circle = createThermometerCircle(circleRadius);

        Rectangle rectangle = new Rectangle(50, rectangleHeight);
        rectangle.setX(xPos - circleRadius/2);
        rectangle.setY(100);

        Shape thermometerShape = Shape.union(circle, rectangle);

        outlineShape = createThermometerOutline(circle, rectangle);
        thermometerLevel = createThermometerLevel(circleRadius, rectangleHeight, thermometerShape);

        thermometerGroup = new Group(thermometerLevel, outlineShape, textLabel);
    }

    public Circle createThermometerCircle(int circleRadius){
        Circle circle = new Circle();
        circle.setRadius(circleRadius);
        circle.setCenterX(xPos);
        circle.setCenterY(yPos);
        return circle;
    }


    public Rectangle createThermometerLevel(int circleRadius, int rectangleHeight, Shape thermometerShape){
        Rectangle thermometerLevel = new Rectangle(200, circleRadius + rectangleHeight);
        thermometerLevel.setX(xPos - 100);
        thermometerLevel.setY(zeroLevel);
        thermometerLevel.setFill(color);
        thermometerLevel.setClip(thermometerShape);
        return thermometerLevel;
    }

    public Shape createThermometerOutline(Circle thermometerCircle, Rectangle thermometerRectangle){
        Shape thermometerOutline = Shape.union(thermometerCircle, thermometerRectangle);
        thermometerOutline.setStroke(Color.web("0x2F2504"));
        thermometerOutline.setStrokeWidth(4);
        thermometerOutline.setFill(Color.TRANSPARENT);
        thermometerOutline.setStrokeLineJoin(StrokeLineJoin.BEVEL);
        return thermometerOutline;
    }

    public Group getThermometerGroup(){
        return thermometerGroup;
    }

    public void setMultiplier(int multiplier){
        this.multiplier = multiplier;
    }

    public void setLevel(int level){
        thermometerLevel.setY(zeroLevel - level * multiplier);
        textLabel.setText(String.valueOf(level));
    }


}
