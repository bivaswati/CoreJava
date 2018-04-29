package Multithreading;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class LowLevelProducerConsumer {

    public static void main(String[] args) {
        Procesor proces = new Procesor();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {proces.produce();} catch (InterruptedException e) {}
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {proces.consume();} catch (InterruptedException e) {}
            }
        });

        t1.start();t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {}
    }
}

class Procesor {
    private LinkedList<Integer> list = new LinkedList<>();
    private final int LIMIT = 10;
    Object lock = new Object();
    public void produce() throws InterruptedException {
        int value = 0;

        while (true) {
            synchronized (lock) {
                while (list.size() == LIMIT) {
                    lock.wait();
                }
                list.add(value++);
                lock.notify();
            }
        }

    }
    public void consume() throws InterruptedException {
        Random random = new Random();
        while (true) {
            synchronized (lock) {
                while (list.size() == 0) {
                    lock.wait();
                }
                System.out.print("List size is : " + list.size());
                int value = list.removeFirst();
                System.out.println(", Values is : " + value);
                lock.notify();
            }
            Thread.sleep(random.nextInt(1000));
        }
    }
}