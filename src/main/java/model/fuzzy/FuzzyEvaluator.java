package model.fuzzy;

import javafx.beans.property.FloatProperty;
import model.fuzzy.FuzzyStats;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.rule.FuzzyRuleSet;

public class FuzzyEvaluator {

    private FIS fis;
    private FuzzyStats fuzzyStats;
    private FuzzyRuleSet fuzzyRuleSet;


    public FuzzyEvaluator(String fileName, FuzzyStats stats){

        fuzzyStats = stats;

        fis = FIS.load(fileName, false);

        fuzzyRuleSet = fis.getFuzzyRuleSet();
        evaluate();

    }

    public void showChart(){
        fuzzyRuleSet.chart();
    }

    public synchronized void evaluate(){

        fuzzyRuleSet.setVariable("temperature_level", fuzzyStats.getTemperatureLevel());
        fuzzyRuleSet.setVariable("fan_speed", fuzzyStats.getFanSpeed());
        fuzzyRuleSet.setVariable("air_humidity", fuzzyStats.getAirHumidity());

        fuzzyRuleSet.evaluate();

        float newAccValue = (float) fuzzyRuleSet.getVariable("fan_acceleration").getLatestDefuzzifiedValue();

        fuzzyStats.setFanAcceleration(newAccValue);
        System.out.println("Recounted acceleration: " + newAccValue);

        fuzzyStats.recalculate();

    }

    public synchronized void nextFlowParameters(){ fuzzyStats.nextFlowParameters(); }

    public synchronized float getTemperature() { return fuzzyStats.getTemperatureLevel(); }

    public synchronized float getAirHumidity() { return fuzzyStats.getAirHumidity(); }

    public synchronized float getFanSpeed(){
        return fuzzyStats.getFanSpeed();
    }

    public synchronized float getFanAcceleration() { return fuzzyStats.getFanAcceleration(); }

    public synchronized float getFanAngleInTime(float time) { return fuzzyStats.getFanAngleInTime(time);}

    public synchronized FloatProperty getTemperatureProperty(){ return fuzzyStats.getTemperatureProperty(); }

    public synchronized FloatProperty getAirHumidityProperty(){ return fuzzyStats.getAirHumidityProperty(); }

    public synchronized void printStats(){
        System.out.println("================ CURRENT STATS ================");
        System.out.println(fuzzyStats.toString());
        System.out.println("\n");
    }
}
