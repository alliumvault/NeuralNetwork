package handlers;

import models.Image;
import network.NeuralNetwork;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;


public class FileManager {
    static String path = System.getProperty("user.home") + "/Desktop";

    public static Image getImageOfCategory(String category, String networkTitle, int res) {
        try {
            File folder = new File(path + "/" + networkTitle + "/" + "categories" + "/" + category);
            String[] files = folder.list();

            if (files != null) {
                int index = (int) (Math.random() * files.length);

                BufferedImage bufferedUnscaledImage = ImageIO.read(new File(path + "/" + networkTitle
                        + "/" + "categories" + "/" + category + "/"
                        + files[index]));

                BufferedImage bufferedImage = getScaledImage(bufferedUnscaledImage, res, res);

                float[][] pixels = new float[res][res];
                for (int i = 0; i < res; i++) {
                    for (int k = 0; k < res; k++) {

                        int pixel = bufferedImage.getRGB(i, k);

                        int red = (pixel >>> 16) & 0xFF;
                        int green = (pixel >>> 8) & 0xFF;
                        int blue = (pixel) & 0xFF;

                        float luminance = (red * 0.2126f + green * 0.7152f + blue * 0.0722f) / 255;

                        pixels[i][k] = luminance;
                    }
                }

                return new Image(1, pixels);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Image getColorImageOfCategory(String category, String networkTitle, int res) {
        try {
            File folder = new File(path + "/" + networkTitle + "/" + "categories" + "/" + category);
            String[] files = folder.list();

            if (files != null) {
                int index = (int) (Math.random() * files.length);

                BufferedImage bufferedUnscaledImage = ImageIO.read(new File(path + "/" + networkTitle
                        + "/" + "categories" + "/" + category + "/"
                        + files[index]));

                //BufferedImage bufferedImage = new BufferedImage(res, res, BufferedImage.TYPE_INT_ARGB);
                //AffineTransform at = new AffineTransform();
                //at.scale(2.0, 2.0);
                //AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
                //bufferedImage = scaleOp.filter(bufferedUnscaledImage, bufferedImage);


                BufferedImage bufferedImage = getScaledImage(bufferedUnscaledImage, res, res);


                float[][] pixels = new float[res][res * 3];
                for (int i = 0; i < res; i++) {
                    int kk = 0;
                    for (int k = 0; k < res*3; k++) {

                        int pixel = bufferedImage.getRGB(i, kk);
                        int red = (pixel & 0x00ff0000) >> 16;
                        int green = (pixel & 0x0000ff00) >> 8;
                        int blue = pixel & 0x000000ff;

                        //System.out.println(pixel + "  " + (float) red / 255 + "  " + (float) green / 255 + "  " + (float) blue / 255);

                        pixels[i][k] = (float) red / 255;
                        pixels[i][k + 1] = (float) green / 255;
                        pixels[i][k + 2] = (float) blue / 255;

                        //System.out.println(pixels[i][k] + "  " + pixels[i][k + 1] + "  " + pixels[i][k + 2]);

                        if (k % 3 == 0) {
                            kk++;
                        }
                        k++;
                        k++;
                    }
                }

                return new Image(1, pixels);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static BufferedImage getScaledImage(BufferedImage srcImg, int w, int h) {

        //Create a new image with good size that contains or might contain arbitrary alpha values between and including 0.0 and 1.0.
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TRANSLUCENT);

        //Create a device-independant object to draw the resized image
        Graphics2D g2 = resizedImg.createGraphics();

        //This could be changed, Cf. http://stackoverflow.com/documentation/java/5482/creating-images-programmatically/19498/specifying-image-rendering-quality
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        //Finally draw the source image in the Graphics2D with the desired size.
        g2.drawImage(srcImg, 0, 0, w, h, null);

        //Disposes of this graphics context and releases any system resources that it is using
        g2.dispose();

        //Return the image used to create the Graphics2D
        return resizedImg;
    }


    public static Image getTestImageAtPosition(String category, String networkTitle, int position, int res) {
        try {
            File folder = new File(path + "/" + networkTitle + "/" + "testCategories" + "/" + category);
            String[] files = folder.list();

            BufferedImage bufferedUnscaledImage = ImageIO.read(new File(path + "/" + networkTitle
                    + "/" + "testCategories" + "/" + category + "/"
                    + files[position]));

            BufferedImage bufferedImage = getScaledImage(bufferedUnscaledImage, res, res);

            float[][] pixels = new float[res][res];
            for (int i = 0; i < res; i++) {
                for (int k = 0; k < res; k++) {

                    int pixel = bufferedImage.getRGB(i, k);

                    //System.out.println(pixel);

                    int red = (pixel >>> 16) & 0xFF;
                    int green = (pixel >>> 8) & 0xFF;
                    int blue = (pixel) & 0xFF;

                    pixels[i][k] = (float) red / 255;

                    float luminance = (red * 0.2126f + green * 0.7152f + blue * 0.0722f) / 255;


                    pixels[i][k] = luminance;
                }
            }

            return new Image(1, pixels);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Image getTestColorImageAtPosition(String category, String networkTitle, int position, int res) {
        try {
            File folder = new File(path + "/" + networkTitle + "/" + "testCategories" + "/" + category);
            String[] files = folder.list();

            BufferedImage bufferedUnscaledImage = ImageIO.read(new File(path + "/" + networkTitle
                    + "/" + "testCategories" + "/" + category + "/"
                    + files[position]));

            BufferedImage bufferedImage = getScaledImage(bufferedUnscaledImage, res, res);

            float[][] pixels = new float[res][res * 3];
            for (int i = 0; i < res; i++) {
                int kk = 0;
                for (int k = 0; k < res * 3; k++) {

                    int pixel = bufferedImage.getRGB(i, kk);
                    int red = (pixel & 0x00ff0000) >> 16;
                    int green = (pixel & 0x0000ff00) >> 8;
                    int blue = pixel & 0x000000ff;

                    //System.out.println(pixel + "  " + (float) red / 255 + "  " + (float) green / 255 + "  " + (float) blue / 255);

                    pixels[i][k] = (float) red / 255;
                    pixels[i][k + 1] = (float) green / 255;
                    pixels[i][k + 2] = (float) blue / 255;

                    //System.out.println(pixels[i][k] + "  " + pixels[i][k + 1] + "  " + pixels[i][k + 2]);

                    if (k % 3 == 0) {
                        kk++;
                    }
                    k++;
                    k++;
                }
            }

            return new Image(1, pixels);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static boolean getLearningStatus(String netName) {
        File weightsFile = new File(path + "/" + netName + "/" + "learningStatus" + "/" +
                "learningStatus" + ".status");

        try {
            Scanner myReader = new Scanner(weightsFile);
            String data = myReader.nextLine();
            return data.trim().equals("true");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static void saveWeights(NeuralNetwork net, String netName) throws IOException {

        File weightsFile = new File(path + "/" + netName + "/" + "weights" + "/" + "num" + ".weights");

        ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(weightsFile)
        );
        out.writeObject(net.getWeights());
        out.flush();
        out.close();
    }

}
