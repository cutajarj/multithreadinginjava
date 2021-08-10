package com.cutajarjames.multithreading.deadlock.train;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class TrainDrawing extends Canvas {

    List<Train> trains;
    List<Intersection> intersections;

    public TrainDrawing(List<Train> trains, ArrayList<Intersection> intersections) {
        this.trains = trains;
        this.intersections = intersections;
        var frame = new JFrame("Deadlocks");
        setSize(800, 800);
        setBackground(Color.BLACK);
        frame.setLocation(400, 100);
        frame.add(this);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


    public void paint(Graphics gCanvas) {
        render(gCanvas);
    }

    public void render(Graphics gCanvas) {
        Image img = createImage(800,800);
        var g = img.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(10, 330, 780, 3);
        g.fillRect(10, 470, 780, 3);
        g.fillRect(330, 10, 3, 780);
        g.fillRect(470, 10, 3, 780);

        g.setColor(new Color(233, 33, 40));
        g.fillRect(10 + trains.get(0).getFrontPos() - Train.TRAIN_LENGTH, 325, Train.TRAIN_LENGTH, 12);

        g.setColor(new Color(78, 151, 210));
        g.fillRect(465, 10 + trains.get(1).getFrontPos() - Train.TRAIN_LENGTH, 12, Train.TRAIN_LENGTH);

        g.setColor(new Color(251, 170, 26));
        g.fillRect(790 - trains.get(2).getFrontPos(), 465, Train.TRAIN_LENGTH, 12);

        g.setColor(new Color(11, 132, 54));
        g.fillRect(325, 790 - trains.get(3).getFrontPos(), 12, Train.TRAIN_LENGTH);
        gCanvas.drawImage(img, 0,0, null);
    }
}
