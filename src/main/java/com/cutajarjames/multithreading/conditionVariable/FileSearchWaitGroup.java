package com.cutajarjames.multithreading.conditionVariable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileSearchWaitGroup {
    List<File> matches = new ArrayList<>();

    public void find(File root, String filename, WaitGroup waitGroup) {
        System.out.println("Searching in " + root.getAbsolutePath());
        for (var f: root.listFiles()) {
            if (f.getName().contains(filename)) {
                matches.add(f);
            }
            if (f.isDirectory()) {
                waitGroup.add(1);
                var t = new Thread(() -> find(f, filename, waitGroup));
                t.start();
            }
        }
        waitGroup.done();
    }

    public static void main(String[] args) throws InterruptedException {
        var fileSearch = new FileSearchWaitGroup();
        var waitGroup = new WaitGroup();
        fileSearch.find(new File("c:/tools"), "README.md", waitGroup);
        waitGroup.waitUntilAllDone();
        fileSearch.matches.forEach(System.out::println);
    }
}
