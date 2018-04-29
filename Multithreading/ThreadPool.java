package Multithreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class processor implements Runnable {
    private int id;

    public processor(int id) {
        this.id = id;
    }
    @Override
    public void run() {
        System.out.println("Starting  .... " + id);
        try {Thread.sleep(5000);} catch (InterruptedException e) {}
        System.out.println("Completed  .... " + id);
    }
}

public class ThreadPool {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        for (int i = 0; i < 5; i++) {
            //5 tasks are given to TWO workers
            executorService.submit(new processor(i));
        }

        /*
           It make shudown to executor service.if commented out then program won't stop.

         * Initiates an orderly shutdown in which previously submitted
         * tasks are executed, but no new tasks will be accepted.
         * Invocation has no additional effect if already shut down.
         * This method does not wait for previously submitted tasks to
         * complete execution.
         */
        executorService.shutdown();
        System.out.println("All tasks Submitted");

        /*Blocks until all tasks have completed execution after a shutdown
        request, or the timeout occurs, or the current thread is
        interrupted, whichever happens first.*/

        executorService.awaitTermination(1, TimeUnit.DAYS);
        System.out.println("All tasks Completed");
    }
}
