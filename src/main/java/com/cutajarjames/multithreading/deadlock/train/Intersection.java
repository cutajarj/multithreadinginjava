package com.cutajarjames.multithreading.deadlock.train;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Intersection {
    int id;
    Lock mutex = new ReentrantLock();
    int lockedBy = -1;

    public Intersection(int id) {
        this.id = id;
    }

    public void setLockedBy(int lockedBy) {
        this.lockedBy = lockedBy;
    }

    public Lock getMutex() {
        return mutex;
    }

    public int getLockedBy() {
        return lockedBy;
    }

    public int getId() {
        return id;
    }
}
