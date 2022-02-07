package handlers;

import models.Image;
import models.ImageProbability;
import network.InputNeuron;
import network.NeuralNetwork;
import network.WorkingNeuron;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class NeuralNetworkStarter {
    NeuralNetwork nn = new NeuralNetwork();

    InputNeuron[][] inputs;
    WorkingNeuron[] outputs;


    public void startLearning(String percentageText, int res, int numHidden, int numHiddenTwo, int numOutputs,
                              ArrayList<String> categories, String networkTitle, String resultText,
                              boolean isColor,
                              float epsilon, double epsilonFactor, int times) {

        if (isColor) {

            inputs = new InputNeuron[res][res * 3];
            outputs = new WorkingNeuron[numOutputs];

            for (int i = 0; i < res; i++) {
                for (int k = 0; k < res * 3; k++) {
                    inputs[i][k] = nn.createNewInputNeuron();
                    inputs[i][k].setValue(0.5f);
                }
            }

            for (int i = 0; i < numOutputs; i++) {
                outputs[i] = nn.createNewOutputNeuron();
            }

            nn.createHiddenNeurons(numHidden);

            nn.createHiddenNeuronsTwo(numHiddenTwo);


            Random rand = new Random();
            float[] weights;

            if (numHidden == 0) {
                weights = new float[((res * res) * 3) * numOutputs];
                for (int i = 0; i < weights.length; i++) {
                    weights[i] = rand.nextFloat();
                }
            } else if (numHiddenTwo == 0) {
                weights = new float[((res * res) * 3) * numHidden + numHidden * numOutputs];
                for (int i = 0; i < weights.length; i++) {
                    weights[i] = rand.nextFloat();
                }
            } else {
                weights = new float[((res * res) * 3) * numHidden + numHidden * numHiddenTwo + numHiddenTwo * numOutputs];
                for (int i = 0; i < weights.length; i++) {
                    weights[i] = rand.nextFloat();
                }
            }

            nn.createFullMesh(weights);


            while (FileManager.getLearningStatus(networkTitle)) {
                testColorProgress(percentageText, res, networkTitle, categories);
                testColorLearnProgress(percentageText, res, networkTitle, categories);
                for (int i = 0; i < times; i++) {
                    for (int j = 0; j < categories.size(); j++) {
                        Image currentImage = FileManager.getColorImageOfCategory(categories.get(j), networkTitle, res);


                        //BufferedImage img = new BufferedImage(res, res, BufferedImage.TYPE_INT_RGB);
//
//
                        //int zz = 0;
                        //int zzz = 0;
                        for (int x = 0; x < res; x++) {
                            //int z = 0;
                            for (int y = 0; y < res * 3; y++) {
                                //float d1 = currentImage.data[x][y];
                                //float d2 = currentImage.data[x][y + 1];
                                //float d3 = currentImage.data[x][y + 2];
                                //if (d3 ==0) { //d1 == 0 || d2 == 0 ||
                                //    zz++;
                                //} else {
                                //    zzz++;
                                //}
                                inputs[x][y].setValue(currentImage.data[x][y]);
                                y++;
                                inputs[x][y].setValue(currentImage.data[x][y]);
                                y++;
                                inputs[x][y].setValue(currentImage.data[x][y]);
                                //System.out.println(inputs[x][y].getValue() + "   " + currentImage.data[x][y]);

                                //int r = (int) (inputs[x][y].getValue() * 255);
                                //int g = (int) (inputs[x][y + 1].getValue() * 255);
                                //int b = (int) (inputs[x][y + 2].getValue() * 255);
                                //int col = (r << 16) | (g << 8) | b;
//
                                //img.setRGB(x, z, col);
//
                                //if (y % 3 == 0) {
                                //    z++;
                                //}
                            }
                        }

                        //System.out.println(zz + "   " + zzz);

                        //if (i == 1 && j == 1) {
                        //    try {
                        //        ImageIO.write(img, "png", new File("/tmp/duke.png"));
                        //        System.out.println("WROTE");
                        //    } catch (IOException e) {
                        //        e.printStackTrace();
                        //    }
                        //}


                        float[] shoulds = new float[numOutputs];
                        shoulds[j] = 1;

                        nn.backpropagation(shoulds, epsilon);
                    }

                }

                epsilon *= epsilonFactor;

                try {
                    FileManager.saveWeights(nn, networkTitle);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        } else {
            inputs = new InputNeuron[res][res];
            outputs = new WorkingNeuron[numOutputs];

            for (int i = 0; i < res; i++) {
                for (int k = 0; k < res; k++) {
                    inputs[i][k] = nn.createNewInputNeuron();
                    inputs[i][k].setValue(0.5f);
                }
            }

            for (int i = 0; i < numOutputs; i++) {
                outputs[i] = nn.createNewOutputNeuron();
            }

            nn.createHiddenNeurons(numHidden);

            nn.createHiddenNeuronsTwo(numHiddenTwo);


            Random rand = new Random();
            float[] weights;

            if (numHidden == 0) {
                weights = new float[res * res * numOutputs];
                for (int i = 0; i < weights.length; i++) {
                    weights[i] = rand.nextFloat();
                }
            } else if (numHiddenTwo == 0) {
                weights = new float[res * res * numHidden + numHidden * numOutputs];
                for (int i = 0; i < weights.length; i++) {
                    weights[i] = rand.nextFloat();
                }
            } else {
                weights = new float[res * res * numHidden + numHidden * numHiddenTwo + numHiddenTwo * numOutputs];
                for (int i = 0; i < weights.length; i++) {
                    weights[i] = rand.nextFloat();
                }
            }

            nn.createFullMesh(weights);


            while (FileManager.getLearningStatus(networkTitle)) {
                testProgress(percentageText, res, networkTitle, categories);
                for (int i = 0; i < times; i++) {
                    for (int j = 0; j < categories.size(); j++) {
                        Image currentImage = FileManager.getImageOfCategory(categories.get(j), networkTitle, res);
                        for (int x = 0; x < res; x++) {
                            for (int y = 0; y < res; y++) {
                                inputs[x][y].setValue(currentImage.data[x][y]);
                            }
                        }

                        float[] shoulds = new float[numOutputs];
                        shoulds[j] = 1;

                        nn.backpropagation(shoulds, epsilon);
                    }
                }

                epsilon *= epsilonFactor;

                try {
                    FileManager.saveWeights(nn, networkTitle);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void testColorProgress(String percentageText, int res, String netName, ArrayList<String> categories) {
        int correct = 0;
        int incorrect = 0;

        for (int i = 0; i < categories.size(); i++) {
            for (int j = 0; j < 9; j++) {
                nn.reset();
                Image currentImage = FileManager.getTestColorImageAtPosition(categories.get(i), netName, j, res);
                for (int x = 0; x < res; x++) {
                    for (int y = 0; y < res * 3; y++) {
                        inputs[x][y].setValue(currentImage.data[x][y]);
                        y++;
                        inputs[x][y].setValue(currentImage.data[x][y]);
                        y++;
                        inputs[x][y].setValue(currentImage.data[x][y]);
                    }
                }

                ImageProbability[] imageProbabilities = new ImageProbability[categories.size()];
                for (int k = 0; k < imageProbabilities.length; k++) {
                    imageProbabilities[k] = new ImageProbability(k, outputs[k].getValue());
                }

                Arrays.sort(imageProbabilities, Collections.reverseOrder());

                if (i == imageProbabilities[0].category) {
                    correct++;
                } else {
                    incorrect++;
                }
            }
        }
        float percentage = (float) correct / (float) (correct + incorrect);
        percentageText = String.valueOf(percentage * 100);
        System.out.println("percentage: " + percentageText);
    }

    private void testColorLearnProgress(String percentageText, int res, String netName, ArrayList<String> categories) {
        int correct = 0;
        int incorrect = 0;

        for (int i = 0; i < categories.size(); i++) {
            for (int j = 0; j < 9; j++) {
                nn.reset();
                Image currentImage = FileManager.getColorImageOfCategory(categories.get(i), netName, res);
                for (int x = 0; x < res; x++) {
                    for (int y = 0; y < res * 3; y++) {
                        inputs[x][y].setValue(currentImage.data[x][y]);
                        y++;
                        inputs[x][y].setValue(currentImage.data[x][y]);
                        y++;
                        inputs[x][y].setValue(currentImage.data[x][y]);
                    }
                }

                ImageProbability[] imageProbabilities = new ImageProbability[categories.size()];
                for (int k = 0; k < imageProbabilities.length; k++) {
                    imageProbabilities[k] = new ImageProbability(k, outputs[k].getValue());
                }

                Arrays.sort(imageProbabilities, Collections.reverseOrder());

                if (i == imageProbabilities[0].category) {
                    correct++;
                } else {
                    incorrect++;
                }
            }
        }
        float percentage = (float) correct / (float) (correct + incorrect);
        percentageText = String.valueOf(percentage * 100);
        System.out.println("Learn: " + percentageText + "\n");
    }


    private void testProgress(String percentageText, int res, String netName, ArrayList<String> categories) {
        int correct = 0;
        int incorrect = 0;

        for (int i = 0; i < categories.size(); i++) {
            for (int j = 0; j < 9; j++) {
                nn.reset();
                Image currentImage = FileManager.getTestImageAtPosition(categories.get(i), netName, j, res);
                for (int x = 0; x < res; x++) {
                    for (int y = 0; y < res; y++) {
                        inputs[x][y].setValue(currentImage.data[x][y]);
                    }
                }

                ImageProbability[] imageProbabilities = new ImageProbability[categories.size()];
                for (int k = 0; k < imageProbabilities.length; k++) {
                    imageProbabilities[k] = new ImageProbability(k, outputs[k].getValue());
                }

                Arrays.sort(imageProbabilities, Collections.reverseOrder());

                if (i == imageProbabilities[0].category) {
                    correct++;
                } else {
                    incorrect++;
                }
            }
        }
        float percentage = (float) correct / (float) (correct + incorrect);
        percentageText = String.valueOf(percentage * 100);
        System.out.println("percentage: " + percentageText);
    }
}
