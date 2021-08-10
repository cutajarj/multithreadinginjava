package com.cutajarjames.multithreading.deadlock.train;

public class Crossing {
    int pos;
    Intersection intersection;

    public Crossing(int pos, Intersection intersection) {
        this.pos = pos;
        this.intersection = intersection;
    }

    public int getPos() {
        return pos;
    }

    public Intersection getIntersection() {
        return intersection;
    }
}
