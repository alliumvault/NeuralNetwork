package models;

public class ImageProbability implements Comparable<ImageProbability> {
    public final int category;
    public float probability;

    public ImageProbability(int category, float probability) {
        this.category = category;
        this.probability = probability;
    }

    @Override
    public int compareTo(ImageProbability other) {
        return Float.compare(probability, other.probability);
    }
}

