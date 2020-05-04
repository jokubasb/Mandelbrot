package com.parallel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Mandelbrot {

    private int MAX_ITER;
    private final double ZOOM = 3000;
    private BufferedImage I;
    private double zx, zy, cX, cY, tmp;
    public int thread;
    public int width;
    public int height;

    int threadCount;


    public Mandelbrot(int threadCount, int iter, int resolution) throws Exception{
        //super("Mandelbrot Set");
        this.threadCount = threadCount;
        //setBounds(100, 100, 1000, 1000);
        MAX_ITER = iter;
        width = resolution;
        height = resolution;
        //setResizable(false);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        I = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        
        /*
        boolean finished = false;
        int left = 100;
        int[] c = splitIntoParts(width, 100);
        ArrayList<Integer> chunks = new ArrayList<Integer>();
        int begin = 0;
        for(int i : c){
            chunks.add(begin);
            begin += i;
        }
        */

        long time0 = System.currentTimeMillis();
        MandelbrotRunner[] threads = new MandelbrotRunner[threadCount];

        for(int i = 0; i < threadCount; i++){
            (threads[i] = new MandelbrotRunner(i, this)).start();
        }


        
        for(int i = 0; i < threadCount; i++) {
            while (!threads[i].finished){
                threads[i].join();
            }
        }

        //Graphics2D g2d = I.createGraphics();
        //printAll(g2d);
        //g2d.dispose();
        ImageIO.write(I, "png", new File("output.png"));
        long time1 = System.currentTimeMillis();
        double dtime = (time1-time0)/1000.;
        System.out.println("Task finished in " + dtime + " seconds");
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