package Multithreading;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


class Processorr implements Runnable {
    private CountDownLatch latch;

    public Processorr(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        System.out.println("Started .... ");
        try {Thread.sleep(3000);} catch (InterruptedException e) {}
        //Here the count is get decremented by CountDownLatch.countDown() method.
        latch.countDown();
    }
}


public class CountDownlatchesBasic {
    public static void main(String[] args) throws InterruptedException {

    CountDownLatch countDownLatch = new CountDownLatch(3);
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 3; i++) {
             executorService.submit(new Processorr(countDownLatch));
        }
        //The Thread which calls the await() method will wait until the initial count reaches to zero.
        countDownLatch.await(1, TimeUnit.DAYS);
        System.out.println("Completed ... ");
    }
}


    /*Another typical usage would be to divide a problem into N parts,describe each part with a Runnable that executes
    that portion and counts down on the latch, and queue all the Runnables to an Executor.  When all sub-parts are
    complete, the coordinating thread will be able to pass through await.

    Consider a scenario where we have requirement where we have three threads "A", "B" and "C" and we want to start
    thread "C" only when "A" and "B" threads completes or partially completes their task.

    CountDownLatch in Java is a type of synchronizer which allows one Thread to wait for one or more Threads before it starts processing.

    CountDownLatch works on latch principle, thread will wait until gate is open. One thread waits for n number of threads specified
    while creating CountDownLatch.e.g. final CountDownLatch latch = new CountDownLatch(3);Here we set the counter to 3.

    Any thread, usually main thread of application, which calls CountDownLatch.await() will wait until count reaches zero
    or it's interrupted by another Thread. All other threads are required to do count down by calling CountDownLatch.countDown()
    once they are completed or ready to the job. as soon as count reaches zero, the Thread awaiting starts running.

    Here the count is get decremented by CountDownLatch.countDown() method.

    The Thread which calls the await() method will wait until the initial count reaches to zero.
    To make count zero other threads need to call the countDown() method. Once the count become zero the thread which invoked the await() method will resume (start its execution).
    The disadvantage of CountDownLatch is that it's not reusable: once the count become zero it is no longer usable.
    */

/*
Generally, you would have many threads blocking on "await()" that would all start simultaneously when the countown reached zero.

Classical example of using CountDownLatch in Java is any server side core Java application which uses
services architecture, where multiple services are provided by multiple threads and application can not start
processing until all services have started successfully.

final CountDownLatch countdown = new CountDownLatch(1);
for (int i = 0; i < 10; ++ i){
   Thread racecar = new Thread() {
      public void run()    {
         countdown.await(); //all threads waiting
         System.out.println("Vroom!");
      }
   };
   racecar.start();
}
System.out.println("Go");
countdown.countDown();   //all threads start now!
You could also use this as an MPI-style "barrier" that causes all threads to wait for other threads to catch up to a
certain point before proceeding.

final CountDownLatch countdown = new CountDownLatch(num_thread);
for (int i = 0; i < num_thread; ++ i){
   Thread t= new Thread() {
      public void run()    {
         doSomething();
         countdown.countDown();
         System.out.printf("Waiting on %d other threads.",countdown.getCount());
         countdown.await();     //waits until everyone reaches this point
         finish();
      }
   };
   t.start();
}
*/