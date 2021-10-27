package com.cutajarjames.multithreading.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ExecutorExample {

    public static void doWork(int i) {
        try {
            Thread.sleep(1000);
            System.out.println("Finished work "+i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //var executor = Executors.newFixedThreadPool(3);
        //var executor = Executors.newSingleThreadExecutor();
        //var executor = Executors.newCachedThreadPool();
        var executor = new ThreadPoolExecutor(3, 5, 0L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(10));
        List<Future> futures = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            //executor.execute(() -> doWork(finalI));
            Future future = executor.submit(() -> doWork(finalI));
            futures.add(future);
        }
        for (var future: futures)
            future.get();
        System.out.println("Main thread done");
    }
}
