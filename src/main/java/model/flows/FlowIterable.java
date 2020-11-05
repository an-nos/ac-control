package model.flows;

import java.util.Iterator;
import java.util.List;

public abstract class FlowIterable implements Iterable{


    protected List parameterFlow;
    private Iterator parameterFlowIterator;

    private void createIterator(){
        this.parameterFlowIterator = new ParameterFlowIterator();
    }

    public Iterator<Float> iterator() {
        if(parameterFlowIterator == null) parameterFlowIterator = new ParameterFlowIterator();
        return parameterFlowIterator;
    }


    protected class ParameterFlowIterator implements Iterator<Float> {

        private Iterator outerIterator;

        public ParameterFlowIterator(){
            outerIterator = parameterFlow.iterator();
        }

        @Override
        public boolean hasNext() {
            if(!outerIterator.hasNext()) outerIterator = parameterFlow.iterator();
            return outerIterator.hasNext();
        }

        @Override
        public Float next() {
            if(!outerIterator.hasNext()) outerIterator = parameterFlow.iterator();
            return (Float) outerIterator.next();
        }
    }


}
