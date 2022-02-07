import handlers.NeuralNetworkStarter;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        String percentageText = "";
        String resultText = "";

        String networkTitle = "testNet";


        ArrayList<String> categories = new ArrayList<>();
        //categories.add("Forest");
        //categories.add("Landscape");
        //categories.add("Document");
        //categories.add("Fire");
        //categories.add("Flower");
        //categories.add("Sunset");
        //categories.add("Window");
        //categories.add("Winter");

        categories.add("0");
        categories.add("1");
        categories.add("2");
        categories.add("3");
        categories.add("4");
        categories.add("5");
        categories.add("6");
        categories.add("7");
        categories.add("8");
        categories.add("9");

        int res = 28;
        int numHidden = 100;
        int numHiddenTwo = 0;

        //28bzw21/100/0.001 ist optimal

        boolean isColor = false;

        float epsilon = 0.001f;  //!!!!!!! hatte damit glaube super Ergebnisse auf lange sicht erzielt (durchschnittl.: 93; sehr oft: 96)
        // (0.1) ohne relu mit momentum    (0.001) mit ReLu    (0.015) mit ReLu und Batch
        float epsilonFactor = 0.99f;

        int times = 100;


        NeuralNetworkStarter neuralNetworkStarter = new NeuralNetworkStarter();
        neuralNetworkStarter.startLearning(percentageText, res, numHidden, numHiddenTwo, categories.size(), categories, networkTitle,
                resultText, isColor, epsilon, epsilonFactor, times);
    }
}
