package flows;

import java.util.*;

public class TemperatureFlow implements Iterable {

    private List<Float> temperatures;
    private Iterator temperatureIterator;

    public TemperatureFlow(){
        temperatures = new ArrayList<>(Arrays.asList(
                10.0f,
                12.0f,
                13.0f,
                14.5f,
                20.0f,
                30.0f
        ));
        temperatureIterator = new TemperatureIterator();
    }

    public Iterator<Float> iterator() {
        return temperatureIterator;
    }

    private class TemperatureIterator implements Iterator<Float>{

        private Iterator tempIterator;

        public TemperatureIterator(){
            tempIterator = temperatures.iterator();
        }

        @Override
        public boolean hasNext() {
            if(!tempIterator.hasNext()) tempIterator = temperatures.iterator();
            return tempIterator.hasNext();
        }

        @Override
        public Float next() {
            if(!tempIterator.hasNext()) tempIterator = temperatures.iterator();
            return (Float) tempIterator.next();
        }
    }
}
