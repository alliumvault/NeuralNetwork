package network.activationfunctions;

import java.io.Serializable;

public class Sigmoid implements ActivationFunction, Serializable {
    @Override
    public float activation(float input) {
        return (float) (1f / (1f + Math.pow(Math.E, -input)));
    }

    @Override
    public float derivative(float input) {
        float sigmoid = activation(input);
        return sigmoid * (1 - sigmoid);
    }
}
