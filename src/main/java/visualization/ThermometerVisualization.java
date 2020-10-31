package visualization;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineJoin;

public class ThermometerVisualization {

    private Group thermometerGroup;
    private int zeroTemperatureLevel;
    private Rectangle thermometerLevel;


    public ThermometerVisualization(){
        int circleRadius = 50;
        int rectangleHeight = 300;
        zeroTemperatureLevel = 350;

        Circle circle = new Circle();
        circle.setRadius(circleRadius);
        circle.setCenterX(600);
        circle.setCenterY(400);

        Rectangle rectangle = new Rectangle(50, rectangleHeight);
        rectangle.setX(575);
        rectangle.setY(100);

        Shape thermometerShape = Shape.union(circle, rectangle);

        Shape thermometerOutline = createThermometerOutline(circle, rectangle);
        thermometerLevel = createThermometerLevel(circleRadius, rectangleHeight, thermometerShape);
        thermometerGroup = new Group(thermometerLevel, thermometerOutline);
    }


    private Rectangle createThermometerLevel(int circleRadius, int rectangleHeight, Shape thermometerShape){
        Rectangle thermometerLevel = new Rectangle(200, circleRadius + rectangleHeight);
        thermometerLevel.setX(500);
        thermometerLevel.setY(zeroTemperatureLevel);
        thermometerLevel.setFill(Color.RED);
        thermometerLevel.setClip(thermometerShape);
        return thermometerLevel;
    }

    private Shape createThermometerOutline(Circle thermometerCircle, Rectangle thermometerRectangle){
        Shape thermometerOutline = Shape.union(thermometerCircle, thermometerRectangle);
        thermometerOutline.setStroke(Color.DARKGRAY);
        thermometerOutline.setStrokeWidth(4);
        thermometerOutline.setFill(Color.TRANSPARENT);
        thermometerOutline.setStrokeLineJoin(StrokeLineJoin.BEVEL);
        return thermometerOutline;
    }

    public Group getThermometerGroup(){
        return thermometerGroup;
    }

    public void setLevel(int temperature){
        thermometerLevel.setY(zeroTemperatureLevel - temperature*5);
    }


}
