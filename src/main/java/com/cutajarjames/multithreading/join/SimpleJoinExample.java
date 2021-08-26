package com.cutajarjames.multithreading.join;

import java.util.concurrent.TimeUnit;

public class SimpleJoinExample extends Thread {
    @Override
    public void run() {
        System.out.println("Child thread doing work");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Child thread finished work");
    }

    public static void main(String[] args) throws InterruptedException {
        var childThread = new SimpleJoinExample();
        childThread.start();
        System.out.println("Parent Thread is waiting...");
        childThread.join();
        System.out.println("Parent Thread is unblocked...");
    }
}
