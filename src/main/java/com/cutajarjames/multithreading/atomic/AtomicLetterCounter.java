package com.cutajarjames.multithreading.atomic;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicLetterCounter {

    public void countLetters(URL url, HashMap<Character, AtomicInteger> frequencyDict) {
        try {
            var stream = url.openStream();
            var txt = new String(stream.readAllBytes());
            for (char c : txt.toCharArray()) {
                var letter = Character.toLowerCase(c);
                if (frequencyDict.containsKey(letter))
                    frequencyDict.get(letter).incrementAndGet();
            }
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws MalformedURLException, InterruptedException {
        var letterCounter = new AtomicLetterCounter();
        var frequencyDict = new HashMap<Character, AtomicInteger>();
        for (char c : "abcdefghijklmnopqrstuvwxyz".toCharArray()) {
            frequencyDict.put(c, new AtomicInteger(0));
        }
        var start = System.currentTimeMillis();
        var workers = new ArrayList<Thread>();
        for (int i = 1000; i < 1050; i++) {
            var url = new URL("https://www.rfc-editor.org/rfc/rfc%s.txt".formatted(i));
            var worker = new Thread(() -> letterCounter.countLetters(url, frequencyDict));
            workers.add(worker);
            worker.start();
        }
        for (Thread worker : workers) {
            worker.join();
        }

        var end = System.currentTimeMillis();
        System.out.println("Done, timetaken: " + (end - start));
        frequencyDict.forEach((k, v) -> System.out.println(k + ", " + v));
    }
}
