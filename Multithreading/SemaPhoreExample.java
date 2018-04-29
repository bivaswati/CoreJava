package Multithreading;

import java.util.concurrent.Semaphore;

public class SemaPhoreExample {
    public static void main(String[] args) throws InterruptedException {
        Semaphore semaphore = new Semaphore(0);
        /*System.out.println("Available permits : " + semaphore.availablePermits());
        //Increment the available permits
        semaphore.release();
        System.out.println("Available permits after Release : " + semaphore.availablePermits());
        //decrement the permits
        semaphore.acquire();
        System.out.println("Available permits after Acquire : " + semaphore.availablePermits());*/
    }
}
