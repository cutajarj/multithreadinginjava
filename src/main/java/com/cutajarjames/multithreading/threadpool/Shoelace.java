package com.cutajarjames.multithreading.threadpool;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class Shoelace {
    private record Point2D(int x, int y) {
    }
    //                                   ( X   ,  Y  )
    final static String POINTS_REG = "\\((\\d*),(\\d*)\\)";

    //(229,150),(169,150),(26,171),(61,200),(290,264)
    private void findArea(String polygon) {
        var matcher = Pattern.compile(POINTS_REG).matcher(polygon);
        var points = new ArrayList<Point2D>();
        while (matcher.find())
            points.add(new Point2D(parseInt(matcher.group(1)), parseInt(matcher.group(2))));

        var area = 0.0;
        for (int i = 0; i < points.size(); i++) {
            Point2D a = points.get(i);
            Point2D b = points.get((i + 1) % points.size());
            area += a.x * b.y - a.y * b.x;
        }
        //System.out.println(Math.abs(area / 2.0));
    }

    public static void main(String[] args) throws IOException {
        var shoelace = new Shoelace();
        var allPolygons = Files.readAllLines(Path.of("./src/main/resources/polygons.txt"));
        var start = System.currentTimeMillis();
        for (var polygon : allPolygons)
            shoelace.findArea(polygon);
        var end = System.currentTimeMillis();
        System.out.println("Time taken: " +  (end - start));
    }
}
