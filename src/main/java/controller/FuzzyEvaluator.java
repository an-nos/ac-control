package controller;

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

    public synchronized void evaluate(){

        fuzzyRuleSet.setVariable("temperature_level", fuzzyStats.getTemperatureLevel());
        fuzzyRuleSet.setVariable("fan_speed", fuzzyStats.getFanSpeed());
        fuzzyRuleSet.setVariable("air_humidity", fuzzyStats.getAirHumidity());

        fuzzyRuleSet.evaluate();

        fuzzyStats.setFanAcceleration((float) fuzzyRuleSet.getVariable("fan_acceleration").getValue());

        fuzzyStats.recalculate();

    }

    public synchronized float getFanSpeed(){
        return fuzzyStats.getFanSpeed();
    }

    public synchronized void printStats(){
        System.out.println("================ CURRENT STATS ================");
        System.out.println(fuzzyStats.toString());
        System.out.println("\n");
    }
}
