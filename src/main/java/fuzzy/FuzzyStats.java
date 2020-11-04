package fuzzy;

import flows.HumidityFlow;
import flows.TemperatureFlow;

import java.util.Iterator;

public class FuzzyStats {

    private float temperatureLevel;
    private float airHumidity;
    private Fan fan;
    private Iterator temperatureFlowIterator;
    private Iterator humidityFlowIterator;

    public FuzzyStats(float temperatureLevel, float airHumidity, float fanSpeed, float timeDelta){
        this.temperatureLevel = temperatureLevel;
        this.airHumidity = airHumidity;
        this.fan = new Fan(fanSpeed, timeDelta);
        this.temperatureFlowIterator = (new TemperatureFlow()).iterator();
        this.humidityFlowIterator = (new HumidityFlow()).iterator();
    }


    public float getTemperatureLevel() {
        return temperatureLevel;
    }

    public void setTemperatureLevel(float temperatureLevel) {
        this.temperatureLevel = temperatureLevel;
    }

    public float getAirHumidity() {
        return airHumidity;
    }

    public void setAirHumidity(float airHumidity) {
        this.airHumidity = airHumidity;
    }

    public void setFanSpeed(float fanSpeed) {
        this.fan.setSpeed(fanSpeed);
    }

    public float getFanSpeed(){ return this.fan.getSpeed(); }

    public void setFanAcceleration(float fanAcceleration){
        this.fan.setAcceleration(fanAcceleration);
    }

    public float getFanAcceleration() { return this.fan.getAcceleration(); }

    public void nextFlowParameters(){
        this.temperatureLevel = (float) temperatureFlowIterator.next();
        this.airHumidity = (float) humidityFlowIterator.next();
    }

    public float getFanAngleInTime(float time) { return fan.getAngleInTime(time); }

    public void recalculate(){ this.fan.recalculateSpeed(); }

    @Override
    public String toString() {
        return "FAN SPEED: " + this.getFanSpeed() +
                "\n TEMPERATURE: " + this.temperatureLevel +
                "\n AIR HUMIDITY: " + this.airHumidity +
                "\n FAN ACCELERATION: " + this.getFanAcceleration();
    }
}
