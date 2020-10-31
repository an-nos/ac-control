package flows;

import java.util.*;

public class TemperatureFlow extends FlowIterable {

    public TemperatureFlow(){
        parameterFlow = new ArrayList<>(Arrays.asList(
                10.0f,
                12.0f,
                13.0f,
                14.5f,
                20.0f,
                30.0f
        ));
    }
}
