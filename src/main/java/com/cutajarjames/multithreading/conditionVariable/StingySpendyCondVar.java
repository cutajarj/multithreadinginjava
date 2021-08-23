package com.cutajarjames.multithreading.conditionVariable;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class StingySpendyCondVar {
    int money = 100;
    Lock mutex = new ReentrantLock();
    Condition condition = mutex.newCondition();

    public void stingy() {
        for (int i = 0; i < 1000000; i++) {
            mutex.lock();
            this.money += 10;
            condition.signal();
            mutex.unlock();
        }
        System.out.println("Stingy Done");
    }

    public void spendy() {
        for (int i = 0; i < 500000; i++) {
            mutex.lock();
            try {
                while (this.money < 20)
                    condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.money -= 20;
            if (this.money < 0)
                System.out.println("Money in the bank: " + this.money);
            mutex.unlock();
        }
        System.out.println("Spendy Done");
    }

    public static void main(String[] args) throws InterruptedException {
        var stingySpendy = new StingySpendyCondVar();
        new Thread(stingySpendy::stingy).start();
        new Thread(stingySpendy::spendy).start();
        TimeUnit.SECONDS.sleep(2);
        System.out.println("Money in the end is: " + stingySpendy.money);
    }
}
