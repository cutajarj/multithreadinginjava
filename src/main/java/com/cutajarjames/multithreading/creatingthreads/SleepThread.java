package com.cutajarjames.multithreading.creatingthreads;

import java.util.concurrent.TimeUnit;

public class SleepThread extends Thread{
    public void run() {
        System.out.println("Starting work");
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Finished Work");
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            new SleepThread().start();
        }
    }
}
