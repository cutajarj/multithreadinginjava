package com.cutajarjames.multithreading.deadlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RedBlue {
    Lock mutex1 = new ReentrantLock();
    Lock mutex2 = new ReentrantLock();

    public void blueRobot() {
        while (true) {
            System.out.println("Blue: Acquiring lock1");
            mutex1.lock();
            System.out.println("Blue: Acquiring lock2");
            mutex2.lock();
            System.out.println("Blue: Both locks Acquired");
            mutex1.unlock();
            mutex2.unlock();
            System.out.println("Blue: Locks Released");
        }
    }

    public void redRobot() {
        while (true) {
            System.out.println("Red: Acquiring lock2");
            mutex2.lock();
            System.out.println("Red: Acquiring lock1");
            mutex1.lock();
            System.out.println("Red: Both locks Acquired");
            mutex1.unlock();
            mutex2.unlock();
            System.out.println("Red: Locks Released");
        }
    }

    public static void main(String[] args) {
        var redBlue = new RedBlue();
        new Thread(redBlue::blueRobot).start();
        new Thread(redBlue::redRobot).start();
    }
}
