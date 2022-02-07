package network;

import java.io.Serializable;

public class Connection implements Serializable {
    Neuron neuron;
    float weight;
    float momentum = 0;
    float weightAdd = 0;
    float alpha = 0.9f;

    public Connection(Neuron neuron, float weight) {
        this.neuron = neuron;
        this.weight = weight;
    }

    public float getValue() {
        return neuron.getValue() * weight;
    }

    public void addWeight(float weightDelta) {
        weightDelta = weightDelta + momentum * alpha;
        momentum = weightDelta;

        //momentum = momentum + weightDelta;
        //momentum *= alpha;
        //weightDelta = weightDelta + momentum;

        weight += weightDelta;
    }

    public void applyBatch() {
        //momentum += weightAdd;
        //momentum *= alpha;
        //weightAdd += momentum;
        //weight += weightAdd;

        weightAdd += momentum * alpha;
        momentum = weightAdd;
        weight += weightAdd;

        weightAdd = 0;
    }

    public Neuron getNeuron() {
        return neuron;
    }

    public float getWeight() {
        return weight;
    }
}
