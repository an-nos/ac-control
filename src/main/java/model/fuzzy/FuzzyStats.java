package model.fuzzy;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import model.flows.HumidityFlow;
import model.flows.TemperatureFlow;

import java.util.Iterator;

public class FuzzyStats {

    private FloatProperty temperatureLevel;
    private FloatProperty airHumidity;
    private Fan fan;
    private Iterator temperatureFlowIterator;
    private Iterator humidityFlowIterator;

    public FuzzyStats(float temperatureLevel, float airHumidity, float fanSpeed, float timeDelta){
        this.temperatureLevel = new SimpleFloatProperty(temperatureLevel);
        this.airHumidity = new SimpleFloatProperty(airHumidity);
        this.fan = new Fan(fanSpeed, timeDelta);
        this.temperatureFlowIterator = (new TemperatureFlow()).iterator();
        this.humidityFlowIterator = (new HumidityFlow()).iterator();
    }


    public float getTemperatureLevel() {
        return temperatureLevel.getValue();
    }

    public void setTemperatureLevel(float temperatureLevel) {
        this.temperatureLevel.set(temperatureLevel);
    }

    public float getAirHumidity() {
        return airHumidity.getValue();
    }

    public void setAirHumidity(float airHumidity) {
        this.airHumidity.setValue(airHumidity);
    }

    public void setFanSpeed(float fanSpeed) {
        this.fan.setSpeed(fanSpeed);
    }

    public FloatProperty getSpeedProperty(){ return fan.getSpeedProperty(); }

    public float getFanSpeed(){ return this.fan.getSpeed(); }

    public void setFanAcceleration(float fanAcceleration){
        this.fan.setAcceleration(fanAcceleration);
    }

    public float getFanAcceleration() { return this.fan.getAcceleration(); }

    public void nextFlowParameters(){
        setTemperatureLevel((float) temperatureFlowIterator.next());
        setAirHumidity((float) humidityFlowIterator.next());
    }

    public float getFanAngleInTime(float time) { return fan.getAngleInTime(time); }

    public void recalculate(){ this.fan.recalculateSpeed(); }

    public FloatProperty getTemperatureProperty(){
        return this.temperatureLevel;
    }

    public FloatProperty getAirHumidityProperty(){
        return this.airHumidity;
    }

    @Override
    public String toString() {
        return "FAN SPEED: " + this.getFanSpeed() +
                "\nTEMPERATURE: " + this.getTemperatureLevel() +
                "\nAIR HUMIDITY: " + this.getAirHumidity() +
                "\nFAN ACCELERATION: " + this.getFanAcceleration();
    }
}
