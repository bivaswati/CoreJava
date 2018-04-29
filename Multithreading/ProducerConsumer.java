package Multithreading;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerConsumer {
    private static BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {Produce();} catch (InterruptedException e) {}
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {Consume();} catch (InterruptedException e) {}
            }
        });

        t1.start();t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {}
    }

    private static void Produce() throws InterruptedException {
       Random random = new Random();
       while (true) {
           queue.put(random.nextInt(100));
       }
    }
    private static void Consume() throws InterruptedException {
        Random random = new Random();
        while (true) {
            Thread.sleep(100);
            if(random.nextInt(10) == 0) {
                int value = queue.take();
                System.out.println("Queue size : " + queue.size() + ", Value : " + value);}}}}