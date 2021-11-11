package com.cutajarjames.multithreading.threadpool;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

public class ShoelaceExecutor {
    private record Point2D(int x, int y) {
    }

    final static String POINTS_REG = "\\((\\d*),(\\d*)\\)";

    private double findArea(String polygon) {
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
        return Math.abs(area / 2.0);
    }

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        var shoelace = new ShoelaceExecutor();
        var allPolygons = Files.readAllLines(Path.of("./src/main/resources/polygons.txt"));
        var executor = Executors.newFixedThreadPool(8);
        var futures = allPolygons.stream()
                .map(polygon -> executor.submit(() -> shoelace.findArea(polygon)))
                .collect(Collectors.toList());
        var totalArea = 0.0;
        for (var future: futures)
            totalArea += future.get();

        System.out.printf("Total Area: %.0f \n", totalArea);
    }
}
