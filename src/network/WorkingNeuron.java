package network;

import network.activationfunctions.ActivationFunction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WorkingNeuron extends Neuron implements Serializable {
    private final List<Connection> connections = new ArrayList<>();
    private ActivationFunction activationFunction = ActivationFunction.ActivationSigmoid;
    private float smallDelta = 0;
    private float value = 0;
    private boolean valueClean = false;

    public float getValue() {
        float sum = 0;
        if (!valueClean) {
            for (Connection c : connections) {
                sum += c.getValue();
            }
            value = activationFunction.activation(sum);

            valueClean = true;
        }
        return value;
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public void reset() {
        valueClean = false;
        smallDelta = 0;
    }

    public void addConnection(Connection c) {
        connections.add(c);
    }

    public void deleteAllConnections() {
        connections.clear();
    }

    public void deltaLearning(float epsilon) {
        float derivative = activationFunction.derivative(getValue());
        for (Connection connection : connections) {
            float bigDelta = epsilon * smallDelta *
                    connection.getNeuron().getValue() * derivative;
            connection.addWeight(bigDelta);
            //System.out.println(bigDelta + "   " + smallDelta + "   " + connection.getNeuron().getValue() + "   " + derivative + "   " + connections.size() +"   " + getValue());
        }
    }

    public void calculateOutputDelta(float should) {
        smallDelta = (should - getValue());
    }

    public void backpropagateSmallDelta() {
        for (Connection c : connections) {
            Neuron n = c.getNeuron();
            if (n instanceof WorkingNeuron) {
                WorkingNeuron wn = (WorkingNeuron) n;
                wn.smallDelta += this.smallDelta * c.getWeight();
            }
        }
    }

    public void setActivationFunction(ActivationFunction function) {
        this.activationFunction = function;
    }

    public void applyBatch() {
        for (Connection c : connections) {
            c.applyBatch();
        }
    }
}
