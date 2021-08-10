package com.cutajarjames.multithreading.deadlock.train.strategies;

import com.cutajarjames.multithreading.deadlock.train.Crossing;
import com.cutajarjames.multithreading.deadlock.train.Intersection;
import com.cutajarjames.multithreading.deadlock.train.Train;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HierarchyTrain extends Train {

    public HierarchyTrain(int id, List<Crossing> crossings) {
        super(id, crossings);
    }

    private void lockIntersectionsInDistance(int reserveStart, int reserveEnd) {
        crossings.stream()
                .filter(c -> reserveStart <= c.getPos() && c.getPos() <= reserveEnd && c.getIntersection().getLockedBy() != id)
                .map(Crossing::getIntersection)
                .sorted(Comparator.comparingInt(Intersection::getId))
                .forEach(i -> {
                    i.getMutex().lock();
                    i.setLockedBy(id);
                    try {
                        TimeUnit.MILLISECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
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
