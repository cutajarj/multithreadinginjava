package com.cutajarjames.multithreading.creatingthreads;

import java.util.concurrent.TimeUnit;

public class RunnableThread implements Runnable{
    @Override
    public void run() {
        System.out.println("Starting work");
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Finished work");
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(new RunnableThread()).start();
        }
    }
}
