package Multithreading;

public class ReEntrantLock {
    public static void main(String[] args) {
        Runner runner = new Runner();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {runner.firstThread();} catch (InterruptedException e) {}
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {runner.secondThread();} catch (InterruptedException e) {}
            }
        });

        t1.start();
        t2.start();
        try {t1.join();t2.join();} catch (InterruptedException e) {}
        runner.finished();
    }
}
