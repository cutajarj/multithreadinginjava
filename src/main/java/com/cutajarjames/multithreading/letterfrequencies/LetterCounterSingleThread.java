package com.cutajarjames.multithreading.letterfrequencies;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class LetterCounterSingleThread {
    public void countLetters(URL url, HashMap<Character, Integer> frequencyDict) {
        try {
            var stream = url.openStream();
            var txt = new String(stream.readAllBytes());
            for (char c : txt.toCharArray()) {
                var letter = Character.toLowerCase(c);
                if (frequencyDict.containsKey(letter))
                    frequencyDict.put(letter, frequencyDict.get(letter) + 1);
            }
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws MalformedURLException {
        var letterCounter = new LetterCounterSingleThread();
        var frequencyDict = new HashMap<Character, Integer>();
        for (char c : "abcdefghijklmnopqrstuvwxyz".toCharArray())
            frequencyDict.put(c, 0);
        var start = System.currentTimeMillis();
        for (int i = 1000; i < 1050; i++) {
            var url = new URL("https://www.rfc-editor.org/rfc/rfc%s.txt".formatted(i));
            letterCounter.countLetters(url, frequencyDict);
        }
        var end = System.currentTimeMillis();
        System.out.println("Done, timetaken: " + (end - start));
        frequencyDict.forEach((k, v) -> System.out.println(k + ", " + v));
    }
}
