package com.cutajarjames.multithreading.letterfrequencies;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class LetterCounter {

    int finishedCount = 0;

    public void countLetters(URL url, HashMap<Character, Integer> frequencyTable) {
        try {
            var stream = url.openStream();
            var txt = new String(stream.readAllBytes());
            for (char c : txt.toCharArray()) {
                var letter = Character.toLowerCase(c);
                synchronized (this) {
                    if (frequencyTable.containsKey(letter))
                        frequencyTable.put(letter, frequencyTable.get(letter) + 1);
                }
            }
            stream.close();
            finishedCount++;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws MalformedURLException, InterruptedException {
        var letterCounter = new LetterCounter();
        var freqTable = new HashMap<Character, Integer>();
        for (char c : "abcdefghijklmnopqrstuvwxyz".toCharArray()) {
            freqTable.put(c, 0);
        }
        var start = System.currentTimeMillis();
        for (int i = 1000; i < 1020; i++) {
            var url = new URL("https://www.rfc-editor.org/rfc/rfc%s.txt".formatted(i));
            new Thread(() -> letterCounter.countLetters(url, freqTable)).start();
        }
        while (letterCounter.finishedCount < 20) {
            TimeUnit.MILLISECONDS.sleep(500);
        }
        var end = System.currentTimeMillis();
        System.out.println("Done, timetaken: " + (end - start));
        freqTable.forEach((k, v) -> System.out.println(k + ", " + v));
    }
}
