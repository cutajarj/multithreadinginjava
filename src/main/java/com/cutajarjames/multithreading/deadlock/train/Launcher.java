package com.cutajarjames.multithreading.deadlock.train;

import com.cutajarjames.multithreading.deadlock.train.strategies.DeadlockTrain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Launcher {

    public static void main(String[] args) throws InterruptedException {
        var intersections = new ArrayList<Intersection>();
        for (int i = 0; i < 4; i++)
            intersections.add(new Intersection(i));

        List<Train> trains = List.of(
                new DeadlockTrain(0, List.of(new Crossing(320, intersections.get(0)), new Crossing(460, intersections.get(1)))),
                new DeadlockTrain(1, List.of(new Crossing(320, intersections.get(1)), new Crossing(460, intersections.get(2)))),
                new DeadlockTrain(2, List.of(new Crossing(320, intersections.get(2)), new Crossing(460, intersections.get(3)))),
                new DeadlockTrain(3, List.of(new Crossing(320, intersections.get(3)), new Crossing(460, intersections.get(0))))
        );

        trains.forEach(Thread::start);

        TrainDrawing canvas = new TrainDrawing(trains, intersections);
        while (true) {
            canvas.render(canvas.getGraphics());
            TimeUnit.MILLISECONDS.sleep(20);
        }
    }
}
