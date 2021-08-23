package com.cutajarjames.multithreading.conditionVariable;

import java.util.concurrent.TimeUnit;

public class StingySpendySyncWait {
    int money = 100;

    public void stingy() {
        for (int i = 0; i < 1000000; i++) {
            synchronized (this) {
                this.money += 10;
                this.notify();
            }
        }
        System.out.println("Stingy Done");
    }

    public void spendy() {
        for (int i = 0; i < 500000; i++) {
            synchronized (this) {
                try {
                    while (this.money < 20)
                        this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.money -= 20;
                if (this.money < 0)
                    System.out.println("Money in the bank: " + this.money);
            }
        }
        System.out.println("Spendy Done");
    }

    public static void main(String[] args) throws InterruptedException {
        var stingySpendy = new StingySpendySyncWait();
        new Thread(stingySpendy::stingy).start();
        new Thread(stingySpendy::spendy).start();
        TimeUnit.SECONDS.sleep(2);
        System.out.println("Money in the end is: " + stingySpendy.money);
    }
}
