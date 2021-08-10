package com.cutajarjames.multithreading.deadlock.train.strategies;

import com.cutajarjames.multithreading.deadlock.train.Crossing;
import com.cutajarjames.multithreading.deadlock.train.Train;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ArbitratorTrain extends Train {
    public ArbitratorTrain(int id, List<Crossing> crossings) {
        super(id, crossings);
    }

    private void lockIntersectionsInDistance(int reserveStart, int reserveEnd) {
        var intersectionsToLock = crossings.stream()
                .filter(c -> reserveStart <= c.getPos() && c.getPos() <= reserveEnd && c.getIntersection().getLockedBy() != id)
                .map(Crossing::getIntersection)
                .collect(Collectors.toList());
        synchronized (ArbitratorTrain.class) {
            while (!intersectionsToLock.stream().allMatch(i -> i.getLockedBy() == -1)) {
                try {
                    ArbitratorTrain.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            intersectionsToLock.forEach(i -> i.setLockedBy(id));
        }
    }

    public void run() {
        while (frontPos < DISTANCE) {
            frontPos += 1;
            crossings.forEach(crossing -> {
                if (frontPos == crossing.getPos()) {
                    lockIntersectionsInDistance(crossing.getPos(), crossing.getPos() + TRAIN_LENGTH);
                }
                int backPos = frontPos - TRAIN_LENGTH;
                if (backPos == crossing.getPos()) {
                    synchronized (ArbitratorTrain.class) {
                        crossing.getIntersection().setLockedBy(-1);
                        ArbitratorTrain.class.notifyAll();
                    }
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
