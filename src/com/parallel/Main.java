package com.parallel;

public class Main {

    public static void main(String[] args) throws Exception {
        int threads = Integer.parseInt(args[0]);
        int iterations = Integer.parseInt(args[1]);
        int resolution = Integer.parseInt(args[2]);
        System.out.println("Running task with " + threads + " threads");
        new Mandelbrot(threads, iterations, resolution);
    }
}
