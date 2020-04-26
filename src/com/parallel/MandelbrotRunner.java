package com.parallel;

import java.util.HashMap;
import java.util.Vector;

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

    public MandelbrotRunner(int thisThread, Mandelbrot mandelBrot){
        this.mb = mandelBrot;
        this.threadCount = mb.getThreadCount();
        this.thread = thisThread;
        width = mb.getWidth();
        height = mb.getHeight();
        ZOOM = mb.getZoom();
        MAX_ITER = mb.getMAX_ITER();
        thread++;
    }
    public void run(){
        int xBegin = 0;
        int xEnd = 0;
        int yBegin = 0;
        int yEnd = height;

        xEnd = (width / threadCount) * thread;
        xBegin = xEnd - width / threadCount;

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
