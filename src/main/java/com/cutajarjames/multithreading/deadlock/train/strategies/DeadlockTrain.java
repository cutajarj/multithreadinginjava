package com.cutajarjames.multithreading.deadlock.train.strategies;

import com.cutajarjames.multithreading.deadlock.train.Crossing;
import com.cutajarjames.multithreading.deadlock.train.Train;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class DeadlockTrain extends Train {

    public DeadlockTrain(int id, List<Crossing> crossings) {
        super(id, crossings);
    }

    public void run() {
        while (frontPos < DISTANCE) {
            frontPos += 1;
            crossings.forEach(crossing -> {
                if (frontPos == crossing.getPos()) {
                    crossing.getIntersection().getMutex().lock();
                    crossing.getIntersection().setLockedBy(id);
                }
                int backPos = frontPos - TRAIN_LENGTH;
                if (backPos == crossing.getPos()) {
                    crossing.getIntersection().setLockedBy(-1);
                    crossing.getIntersection().getMutex().unlock();
                }
            });
            try {
                TimeUnit.MILLISECONDS.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
