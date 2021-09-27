package com.cutajarjames.multithreading.messagepassing;

import java.util.concurrent.*;

public class ProducerConsumer {
    public void consumer(BlockingQueue<String> queue) {
        try {
            while (true) {
                System.out.println(queue.take());
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void producer(BlockingQueue<String> queue) {
        try {
            while (true) {
                queue.put("Hello there");
                System.out.println("Message sent");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //var queue = new SynchronousQueue<String>();
        var queue = new ArrayBlockingQueue<String>(5);
        //var queue = new LinkedBlockingQueue<String>();
        var producerConsumer = new ProducerConsumer();
        new Thread(() -> producerConsumer.consumer(queue)).start();
        new Thread(() -> producerConsumer.producer(queue)).start();
    }
}
