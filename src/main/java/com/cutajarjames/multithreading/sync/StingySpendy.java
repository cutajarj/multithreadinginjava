package com.cutajarjames.multithreading.sync;

import java.util.concurrent.TimeUnit;

public class StingySpendy {
    int money = 100;

    public void stingy() {
        for (int i = 0; i < 1000000; i++) {
            this.money += 10;
        }
        System.out.println("Stingy Done");
    }

    public void spendy() {
        for (int i = 0; i < 1000000; i++) {
            this.money -= 10;
        }
        System.out.println("Spendy Done");
    }

    public static void main(String[] args) throws InterruptedException {
        var stingySpendy = new StingySpendy();
        new Thread(stingySpendy::stingy).start();
        new Thread(stingySpendy::spendy).start();
        TimeUnit.SECONDS.sleep(2);
        System.out.println("Money in the end is: " + stingySpendy.money);
    }
}
