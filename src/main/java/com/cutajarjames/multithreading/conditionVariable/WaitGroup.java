package com.cutajarjames.multithreading.conditionVariable;

public class WaitGroup {
    private int waitCount = 0;

    public synchronized void add(int count) {
        waitCount += count;
    }

    public synchronized void done() {
        if (waitCount > 0)
            waitCount -= 1;
        if (waitCount == 0)
            this.notifyAll();
    }

    public synchronized void waitUntilAllDone() throws InterruptedException {
        while (waitCount > 0)
            this.wait();
    }
}
