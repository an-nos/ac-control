package model.flows;

import java.util.*;

public class TemperatureFlow extends FlowIterable {

    public TemperatureFlow(){
        parameterFlow = new ArrayList<>(Arrays.asList(
                20.0f,
                22.0f,
                23.0f,
                24.5f,
                25.0f,
                30.0f,
                30.0f,
                30.0f
        ));
    }
}
