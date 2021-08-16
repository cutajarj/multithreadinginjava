package com.cutajarjames.multithreading.barriers;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class BarrierExample {
    public void waitOnBarrier(String name, int timeToSleep, CyclicBarrier barrier) {
        for (int i = 0; i < 10; i++) {
            try {
                System.out.printf("%s running\n", name);
                TimeUnit.SECONDS.sleep(timeToSleep);
                System.out.printf("%s is waiting on barrier\n", name);
                barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        var barrier = new CyclicBarrier(2);
        var barrierExample = new BarrierExample();
        new Thread(() -> barrierExample.waitOnBarrier("red", 4, barrier)).start();
        new Thread(() -> barrierExample.waitOnBarrier("blue", 10, barrier)).start();
        TimeUnit.SECONDS.sleep(8);
        barrier.reset();
    }
}
