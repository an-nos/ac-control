package fuzzy;

import fuzzy.FuzzyStats;
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
//        fuzzyRuleSet.chart();
        evaluate();

    }

    public synchronized void evaluate(){

        fuzzyRuleSet.setVariable("temperature_level", fuzzyStats.getTemperatureLevel());
        fuzzyRuleSet.setVariable("fan_speed", fuzzyStats.getFanSpeed());
        fuzzyRuleSet.setVariable("air_humidity", fuzzyStats.getAirHumidity());

        fuzzyRuleSet.evaluate();

        float newAccValue = (float) fuzzyRuleSet.getVariable("fan_acceleration").getLatestDefuzzifiedValue();

        fuzzyStats.setFanAcceleration(newAccValue);
        System.out.println("Recounted acceleration: " + newAccValue);

        fuzzyStats.nextFlowParameters();
        fuzzyStats.recalculate();

    }

    public synchronized float getTemperature() { return fuzzyStats.getTemperatureLevel(); }

    public synchronized float getAirHumidity() { return fuzzyStats.getAirHumidity(); }

    public synchronized float getFanSpeed(){
        return fuzzyStats.getFanSpeed();
    }

    public synchronized void printStats(){
        System.out.println("================ CURRENT STATS ================");
        System.out.println(fuzzyStats.toString());
        System.out.println("\n");
    }
}
