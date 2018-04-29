package Multithreading;

public class SynchronizationBasics {
    //Here just volatile keyword also won't solve the problem
    private int count = 0;

    //So do synchronixation which is a shared resource(more threads try to modify it).
    // Multiple thread access shared data.

    /*Every object in java has an intrensic lock or monitor lock or mutex
    * when you call a synchronized method of an object you have to acquire the intrensic lock of that object.
    * only one thread can acquire intrensic lock at at time.other thread has to wait untill lock is being released.*/
    public synchronized void increment(){
        count++;
    }

    public static void main(String[] args) {
        SynchronizationBasics synchronizationBasics = new SynchronizationBasics();
        synchronizationBasics.doWork();
    }

    public void doWork() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    increment();
                }
            }
        });


        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    increment();
                }
            }
        });


        t1.start();
        t2.start();
        //here main thread is spawning the individual threads.
        // At this point count value is zero so we have to wait until two threads finises off their job.
        System.out.println("Count value before join is : " + count);

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Count value is after join() called: " + count);
    }

}
