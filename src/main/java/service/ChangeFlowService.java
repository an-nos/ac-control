package service;

import model.fuzzy.FuzzyEvaluator;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;

public class ChangeFlowService extends ScheduledService<Void> {

    FuzzyEvaluator fuzzyEvaluator;

    public ChangeFlowService(FuzzyEvaluator fuzzyEvaluator){
        this.fuzzyEvaluator = fuzzyEvaluator;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<>() {
            @Override
            protected Void call() throws Exception {
                fuzzyEvaluator.nextFlowParameters();
                System.out.println("Parameters changed!");
                return null;
            }
        };
    }
}
