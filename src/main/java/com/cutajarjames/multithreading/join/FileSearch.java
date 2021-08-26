package com.cutajarjames.multithreading.join;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileSearch {
    List<File> matches = new ArrayList<>();

    public void find(File root, String filename) {
        System.out.println("Searching in " + root.getAbsolutePath());
        var childThreads = new ArrayList<Thread>();
        for (var f : root.listFiles()) {
            if (f.getName().contains(filename))
                synchronized (this) {
                    matches.add(f);
                }
            if (f.isDirectory()) {
                var t = new Thread(() -> find(f, filename));
                childThreads.add(t);
                t.start();
            }
        }
        for (var t : childThreads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        var fileSearch = new FileSearch();
        fileSearch.find(new File("c:/tools"), "README.md");
        fileSearch.matches.forEach(System.out::println);
    }
}
