package Multithreading;

import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Runner {
    private int count = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private void increment(){
        for (int i = 0; i < 10000; i++) {
            count++;
        }
    }
    public void firstThread() throws InterruptedException {
        lock.lock();
        System.out.println("waiting .....");
        condition.await();
        System.out.println("Waken up ...");
        try {
            increment();
        } finally {
            lock.unlock();
        }
    }
    public void secondThread() throws InterruptedException {
        Thread.sleep(1000);
        lock.lock();

        System.out.println("Press Enter Key ...");
        new Scanner(System.in).nextLine();
        System.out.println("Got Return key ...");

        condition.signal();

        try {
            increment();
        } finally {
            lock.unlock();
        }
    }
    public void finished(){
        System.out.println("Finished ... Count is : " + count);
    }
}
