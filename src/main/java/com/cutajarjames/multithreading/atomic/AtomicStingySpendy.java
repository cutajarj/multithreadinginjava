package com.cutajarjames.multithreading.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicStingySpendy {
    AtomicInteger money = new AtomicInteger(100);

    public void stingy() {
        for (int i = 0; i < 1000000; i++) {
            this.money.addAndGet(10);
        }
        System.out.println("Stingy Done");
    }

    public void spendy() {
        for (int i = 0; i < 1000000; i++) {
            this.money.addAndGet(-10);
        }
        System.out.println("Spendy Done");
    }

    public static void main(String[] args) throws InterruptedException {
        var stingySpendy = new AtomicStingySpendy();
        new Thread(stingySpendy::stingy).start();
        new Thread(stingySpendy::spendy).start();
        TimeUnit.SECONDS.sleep(2);
        System.out.println("Money in the end is: " + stingySpendy.money);
    }
}
