package model.flows;

import java.util.ArrayList;
import java.util.Arrays;

public class HumidityFlow extends FlowIterable{

    public HumidityFlow(){
        parameterFlow = new ArrayList<>(Arrays.asList(
                30.0f,
                30.0f,
                30.0f,
                35.0f,
                40.0f,
                45.0f
        ));
    }

}
