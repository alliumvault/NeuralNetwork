package models;

public class Image {
    public int label;
    public float[][] data;

    public Image(int label, float[][] data) {
        this.label = label;
        this.data = data;
    }
}
