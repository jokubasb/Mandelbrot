package com.parallel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

//import sun.awt.image.XbmImageDecoder;

public class MandelbrotRunner extends Thread{
    public int thread;
    public int threadCount;
    volatile boolean finished = false;
    double ZOOM;
    int MAX_ITER;
    int width;
    int height;
    Mandelbrot mb;
    double zx, zy, cX, cY, tmp;
    //ArrayList<Integer> chunk = new ArrayList<Integer>();
    //int[] chunkSize;
    //int yBegin = 0;
    //int yEnd = 0;
    

    public MandelbrotRunner(int thisThread, Mandelbrot mandelBrot){
        this.mb = mandelBrot;
        this.threadCount = mb.getThreadCount();
        this.thread = thisThread;
        width = mb.width;
        height = mb.height;
        ZOOM = mb.getZoom();
        MAX_ITER = mb.getMAX_ITER();
        thread++;
        //this.yBegin = begin;
        //this.yEnd = end;
    }
    public void run(){
        int xBegin = 0;
        int xEnd = width;
        int yBegin = 0;
        int yEnd = 0;

        

        yEnd = (height / threadCount) * thread;
        yBegin = yEnd - height / threadCount;

        //System.out.println(thread + "," + begin + "," + end);


        for (int y = yBegin; y < yEnd; y++) {
            for (int x = xBegin; x < xEnd; x++) {
                zx = zy = 0;
                cX = (x - 2800) / ZOOM;
                cY = (y - 800) / ZOOM;
                int iter = MAX_ITER;
                while (zx * zx + zy * zy < 4 && iter > 0) {
                    tmp = zx * zx - zy * zy + cX;
                    zy = 2.0 * zx * zy + cY;
                    zx = tmp;
                    iter--;
                }
                mb.setImage(x, y, iter | (iter << 8));
                //I.setRGB(x, y, iter | (iter << 8));
            }
        }
        System.out.println("Thread " + thread + " finished");
        this.finished = true;
    }

}
