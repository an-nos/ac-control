package model.fuzzy;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;

public class Fan {

    private FloatProperty speed;
    private float acceleration;
    private float timeDelta = 1.0f;

    public Fan(float speed, float timeDelta){
        this.speed = new SimpleFloatProperty(speed);
        this.timeDelta = timeDelta;
        this.acceleration = 0.0f;
    }

    public Fan(float speed){
        this.speed = new SimpleFloatProperty(speed);
        this.acceleration = 0.0f;
    }

    public float getSpeed() { return speed.getValue(); }

    public FloatProperty getSpeedProperty(){ return this.speed; }

    public void setSpeed(float speed) { this.speed.setValue(speed); }

    public float getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    public void setTimeDelta(float timeDelta){
        this.timeDelta = timeDelta;
    }

    public float getAngleInTime(float time) {
        return getSpeed() * time + acceleration * time * time / 2;
    }

    public void recalculateSpeed(){
        setSpeed(getSpeed() + acceleration * timeDelta);
        if(getSpeed() < 0) setSpeed(0);
    }
}
