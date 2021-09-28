package com.cutajarjames.multithreading.messagepassing;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class WindDirection {
    final static String TAF_VALIDATION = ".*TAF.*";
    final static String COMMENT = "\\w*#.*";
    final static String METAR_CLOSE = ".*=";
    final static String WIND = "\\d* METAR.*EGLL \\d*Z [A-Z ]*(\\d{5}KT|VRB\\d{2}KT).*=";
    final static String VARIABLE_WIND = ".*VRB\\d{2}KT";
    final static String VALID_WIND = "\\d{5}KT";
    final static String WIND_DIRECTION_ONLY = "(\\d{3})\\d{2}KT";

    private List<String> parseReports(String strFile) {
        var lines = strFile.split("\n");
        var result = new LinkedList<String>();
        StringBuilder metarStr = new StringBuilder();
        for (var line : lines) {
            line = line.trim();
            if (line.matches(TAF_VALIDATION))
                break;
            if (!line.matches(COMMENT))
                metarStr.append(line);
            if (line.matches(METAR_CLOSE)) {
                result.add(metarStr.toString());
                metarStr = new StringBuilder();
            }
        }
        return result;
    }

    private List<String> extractWindDirection(List<String> reports) {
        var winds = new LinkedList<String>();
        for (var report : reports) {
            var matcher = Pattern.compile(WIND).matcher(report);
            if (matcher.matches())
                winds.add(matcher.group(1));
        }
        return winds;
    }

    private void mineWindDistribution(List<String> winds, int[] distribution) {
        for (var wind : winds) {
            if (wind.matches(VARIABLE_WIND))
                for (var i = 0; i < 8; i++)
                    distribution[i] += 1;
            else if (wind.matches(VALID_WIND)) {
                var matcher = Pattern.compile(WIND_DIRECTION_ONLY).matcher(wind);
                if (matcher.matches()) {
                    var windStr = matcher.group(1);
                    var dir = Float.parseFloat(windStr);
                    var dirIndex = ((int) Math.round(dir / 45.0)) % 8;
                    distribution[dirIndex] += 1;
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        var windDirection = new WindDirection();
        var metarFiles = new File("./src/main/resources/metarfiles").listFiles();
        var distribution = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
        var startTime = System.currentTimeMillis();
        for (var file : metarFiles) {
            String strFile = Files.readString(Paths.get(file.getAbsolutePath()));
            var reports = windDirection.parseReports(strFile);
            var winds = windDirection.extractWindDirection(reports);
            windDirection.mineWindDistribution(winds, distribution);
        }
        var endTime = System.currentTimeMillis();
        System.out.println(Arrays.toString(distribution));
        System.out.println("Time Taken: " + (endTime - startTime));
    }
}
