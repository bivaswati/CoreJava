package Multithreading;

public class DeadLock {
    public static void main(String[] args) {
        final RunnerExample runner = new RunnerExample();

        Thread t1  = new Thread(new Runnable() {
            @Override
            public void run() {
                try {runner.firstThread();} catch (InterruptedException e) {}
            }
        });

        Thread t2  = new Thread(new Runnable() {
            @Override
            public void run() {
                try {runner.secondThread();} catch (InterruptedException e) {}
            }
        });

        t1.start();
        t2.start();
        try {t2.join();t1.join();} catch (InterruptedException e) {}
        runner.finished();
    }
}
