package network;

import java.io.Serializable;

public class InputNeuron  extends Neuron implements Serializable {
    private float value = 0;

    @Override
    public float getValue() {
        //System.out.println(value);
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
