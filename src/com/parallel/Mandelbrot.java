package com.parallel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Mandelbrot extends JFrame {

    private final int MAX_ITER = 10000;
    private final double ZOOM = 3000;
    private BufferedImage I;
    private double zx, zy, cX, cY, tmp;
    public int thread;

    int threadCount;


    public Mandelbrot(int threadCount) throws Exception{
        //super("Mandelbrot Set");
        this.threadCount = threadCount;
        setBounds(100, 100, 1000, 1000);
        //setResizable(false);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        MandelbrotRunner[] threads = new MandelbrotRunner[threadCount];

        for(int i = 0; i < threadCount; i++){
            (threads[i] = new MandelbrotRunner(i, this)).start();
        }

        for(int i = 0; i < threadCount; i++) {
            while (!threads[i].finished)
                threads[i].join();
        }
        Graphics2D g2d = I.createGraphics();
        printAll(g2d);
        g2d.dispose();
        ImageIO.write(I, "png", new File("filename.png"));
    }


    public void setImage(int x, int y, int rgb){
        I.setRGB(x, y, rgb | (rgb << 8));
    }

    public int getThreadCount(){
        return threadCount;
    }

    public double getZoom(){
        return ZOOM;
    }

    public int getMAX_ITER(){
        return MAX_ITER;
    }


    /*
    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }

     */
}