package Multithreading;

import java.util.Scanner;

public class WaitAndNotify {
    public static void main(String[] args) {
     Processes processes = new Processes();
        Thread t1 = new Thread(new Runnable() {
         @Override
         public void run() {
             try {
                 processes.produces();
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
         }
     }) ;

    Thread t2 = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                processes.consumes();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }) ;

    t1.start();t2.start();
    try {
        t1.join();
        t2.join();
    } catch (InterruptedException e) {}
    }
}

class Processes {
    public void produces() throws InterruptedException {
        synchronized (this) {
            System.out.println("Producer Thread running .... ");
            wait();
            System.out.println("Producer Thread Resumed");
        }
    }
    public void consumes() throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        Thread.sleep(2000);

        synchronized (this) {
            System.out.println("Press Enter Key for Return ");
            sc.nextLine();
            System.out.println("Return key Pressed");
            notify();
            Thread.sleep(4000);
            //System.out.println("Test");
        }
    }
}