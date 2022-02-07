package network;

import network.activationfunctions.ActivationFunction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork implements Serializable {
    List<InputNeuron> inputNeurons = new ArrayList<>();
    List<WorkingNeuron> hiddenNeurons = new ArrayList<>();
    List<WorkingNeuron> hiddenNeuronsTwo = new ArrayList<>();
    List<WorkingNeuron> outputNeurons = new ArrayList<>();

    private int trainingSample = 0;

    public InputNeuron createNewInputNeuron() {
        InputNeuron in = new InputNeuron();
        inputNeurons.add(in);
        return in;
    }

    public void createHiddenNeurons(int amount) {
        for (int i = 0; i < amount; i++) {
            WorkingNeuron workingNeuron = new WorkingNeuron();
            workingNeuron.setActivationFunction(ActivationFunction.ReLu);
            hiddenNeurons.add(workingNeuron);
        }
    }

    public void createHiddenNeuronsTwo(int amount) {
        for (int i = 0; i < amount; i++) {
            WorkingNeuron workingNeuron = new WorkingNeuron();
            workingNeuron.setActivationFunction(ActivationFunction.ReLu);
            hiddenNeuronsTwo.add(workingNeuron);
        }
    }

    public WorkingNeuron createNewOutputNeuron() {
        WorkingNeuron wn = new WorkingNeuron();
        outputNeurons.add(wn);
        return wn;
    }


    public void createFullMesh(float... weights) {
        if (hiddenNeurons.size() == 0 && hiddenNeuronsTwo.size() == 0) {
            if (weights.length != inputNeurons.size() * outputNeurons.size()) {
                throw new RuntimeException();
            }

            int index = 0;

            for (WorkingNeuron wn : outputNeurons) {
                for (InputNeuron in : inputNeurons) {
                    wn.addConnection(new Connection(in, weights[index++]));
                }
            }

        } else if (hiddenNeuronsTwo.size() == 0) {
            if (weights.length != inputNeurons.size() * hiddenNeurons.size() + hiddenNeurons.size() * outputNeurons.size()) {
                throw new RuntimeException();
            }

            int index = 0;

            for (WorkingNeuron hidden : hiddenNeurons) {
                for (InputNeuron in : inputNeurons) {
                    hidden.addConnection(new Connection(in, weights[index++]));
                }
            }

            for (WorkingNeuron out : outputNeurons) {
                for (WorkingNeuron hidden : hiddenNeurons) {
                    out.addConnection(new Connection(hidden, weights[index++]));
                }
            }

        } else {
            if (weights.length != inputNeurons.size() * hiddenNeurons.size() + hiddenNeurons.size() * hiddenNeuronsTwo.size() +
                    hiddenNeuronsTwo.size() * outputNeurons.size()) {
                throw new RuntimeException();
            }

            int index = 0;

            for (WorkingNeuron hidden : hiddenNeurons) {
                for (InputNeuron in : inputNeurons) {
                    hidden.addConnection(new Connection(in, weights[index++]));
                }
            }

            for (WorkingNeuron hidden2 : hiddenNeuronsTwo) {
                for (WorkingNeuron hidden1 : hiddenNeurons) {
                    hidden2.addConnection(new Connection(hidden1, weights[index++]));
                }
            }

            for (WorkingNeuron out : outputNeurons) {
                for (WorkingNeuron hidden2 : hiddenNeuronsTwo) {
                    out.addConnection(new Connection(hidden2, weights[index++]));
                }
            }

        }
    }


    public void backpropagation(float[] shoulds, float epsilon) {
        if (shoulds.length != outputNeurons.size()) {
            throw new IllegalArgumentException();
        }

        reset();

        for (int i = 0; i < shoulds.length; i++) {
            outputNeurons.get(i).calculateOutputDelta(shoulds[i]);
        }

        if (hiddenNeurons.size() > 0) {
            for (int i = 0; i < outputNeurons.size(); i++) {
                outputNeurons.get(i).backpropagateSmallDelta();
            }
        }

        if (hiddenNeuronsTwo.size() > 0) {
            for (WorkingNeuron workingNeuron : hiddenNeuronsTwo) {
                workingNeuron.backpropagateSmallDelta();
            }
        }

        for (int i = 0; i < shoulds.length; i++) {
            outputNeurons.get(i).deltaLearning(epsilon);
        }

        for (WorkingNeuron neuron : hiddenNeuronsTwo) {
            neuron.deltaLearning(epsilon);
        }

        for (WorkingNeuron neuron : hiddenNeurons) {
            neuron.deltaLearning(epsilon);
        }

        //if (trainingSample % 7 == 0) {
        //    for (WorkingNeuron outputNeuron : outputNeurons) {
        //        outputNeuron.applyBatch();
        //    }

        //    for (WorkingNeuron workingNeuron : hiddenNeuronsTwo) {
        //        workingNeuron.applyBatch();
        //    }

        //    for (WorkingNeuron hiddenNeuron : hiddenNeurons) {
        //        hiddenNeuron.applyBatch();
        //    }
        //}

        //trainingSample++;
    }


    public void resetAllWeights() {
        for (WorkingNeuron wn : hiddenNeurons) {
            wn.deleteAllConnections();
        }

        for (WorkingNeuron wn : hiddenNeuronsTwo) {
            wn.deleteAllConnections();
        }

        for (WorkingNeuron wn : outputNeurons) {
            wn.deleteAllConnections();
        }
    }

    public void reset() {
        for (WorkingNeuron wn : outputNeurons) {
            wn.reset();
        }
        for (WorkingNeuron wn : hiddenNeuronsTwo) {
            wn.reset();
        }
        for (WorkingNeuron wn : hiddenNeurons) {
            wn.reset();
        }
    }


    public float[] getWeights() {
        float[] weights;
        if (hiddenNeuronsTwo.size() > 0) {
            weights = new float[inputNeurons.size() * hiddenNeurons.size() + hiddenNeurons.size() * hiddenNeuronsTwo.size() +
                    hiddenNeuronsTwo.size() * outputNeurons.size()];
        } else if (hiddenNeurons.size() > 0) {
            weights = new float[inputNeurons.size() * hiddenNeurons.size() + hiddenNeurons.size() * outputNeurons.size()];
        } else {
            weights = new float[inputNeurons.size() * outputNeurons.size()];
        }

        int indexWeights = 0;

        for (WorkingNeuron hidden : hiddenNeurons) {
            List<Connection> connectionsHidden = hidden.getConnections();

            for (Connection connection : connectionsHidden) {
                weights[indexWeights] = connection.getWeight();
                indexWeights++;
            }

        }

        for (WorkingNeuron hiddenTwo : hiddenNeuronsTwo) {
            List<Connection> connectionsHidden = hiddenTwo.getConnections();

            for (Connection connection : connectionsHidden) {
                weights[indexWeights] = connection.getWeight();
                indexWeights++;
            }

        }

        for (WorkingNeuron out : outputNeurons) {
            List<Connection> connectionsHidden = out.getConnections();

            for (Connection connection : connectionsHidden) {
                weights[indexWeights] = connection.getWeight();
                indexWeights++;
            }

        }

        return weights;
    }
}
