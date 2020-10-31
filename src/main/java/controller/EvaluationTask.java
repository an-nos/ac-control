package controller;


import javafx.application.Platform;
import javafx.concurrent.Task;

public class EvaluationTask implements Runnable {

    private final FuzzyEvaluator fuzzyEvaluator;

    public EvaluationTask(FuzzyEvaluator evaluator){ this.fuzzyEvaluator = evaluator; }

//    @Override
//    protected Object call() throws Exception {
//
////        while(true) {
////            if(isCancelled()) break;
//
//            Thread.sleep(1000);
//
//
//            return null;
////        }
//
//    }

    @Override
    public void run() {
        Runnable updater = new Runnable() {
            @Override
            public void run() {
                fuzzyEvaluator.evaluate();
                fuzzyEvaluator.printStats();
            }
        };

        while(true){
            try{ Thread.sleep(1000); }
            catch (InterruptedException e) { e.printStackTrace();}

            Platform.runLater(updater);
        }

    }
}
