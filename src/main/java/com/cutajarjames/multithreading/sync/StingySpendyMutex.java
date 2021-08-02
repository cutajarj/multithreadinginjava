package com.cutajarjames.multithreading.sync;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class StingySpendyMutex {
    int money = 100;
    Lock mutex = new ReentrantLock();

    public void stingy() {
        for (int i = 0; i < 1000000; i++) {
            mutex.lock();
            this.money += 10;
            mutex.unlock();
        }
        System.out.println("Stingy Done");
    }

    public void spendy() {
        for (int i = 0; i < 1000000; i++) {
            mutex.lock();
            this.money -= 10;
            mutex.unlock();
        }
        System.out.println("Spendy Done");
    }

    public static void main(String[] args) throws InterruptedException {
        var stingySpendy = new StingySpendyMutex();
        new Thread(stingySpendy::stingy).start();
        new Thread(stingySpendy::spendy).start();
        TimeUnit.SECONDS.sleep(2);
        System.out.println("Money in the end is: " + stingySpendy.money);
    }
}
