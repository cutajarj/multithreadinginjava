package com.cutajarjames.multithreading.deadlock.train;

import java.util.List;

public abstract class Train extends Thread{
    public static int DISTANCE = 780;
    public static int TRAIN_LENGTH = 200;
    protected int frontPos = 0;
    protected int id;
    protected List<Crossing> crossings;

    public Train(int id, List<Crossing> crossings) {
        this.id = id;
        this.crossings = crossings;
    }

    public int getFrontPos() {
        return frontPos;
    }
}
