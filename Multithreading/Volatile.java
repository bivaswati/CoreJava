package Multithreading;

import java.util.Scanner;

public class Volatile {
    public static void main(String[] args) {
        Processor processor1 = new Processor();
        processor1.start();

        System.out.println("Enter return key to Exit : ");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        processor1.shutdown();

    }
}

class Processor extends Thread {
    /*Here in this program there are 2 threads.one main one processor and generally one thread don't expect other
    thread to interfere it's activity/modify its data .
    So processor thread may decide to cache running value as true bcoz it sees the value is not changed by processor
    thread and it not gonna bother checking running variable.so in while loop it may be alwz true.

    To prevent this volatile keyword is there.it's used to prevent thread caching of variable in one thread while
    it's changing from other thred in case.

    We may use synchronization also.
    */

    private volatile boolean running = true;
    @Override
    public void run() {

        while (running) {
            System.out.println("Hello");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void shutdown() {
        running = false;
    }
}