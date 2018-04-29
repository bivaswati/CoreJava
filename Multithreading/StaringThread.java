package Multithreading;

public class StaringThread {
    public static void main(String[] args) {

        //Extending Thread Class
        Runner1 runner1 = new Runner1();
        Runner1 runner2 = new Runner1();
        runner1.start();
        runner2.start();

        Thread t1 = new Thread(new Runner2());
        Thread t2 = new Thread(new Runner2());
        t1.start();
        t2.start();


        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println("Hello Inner Class Runnable : " + i);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t3.start();

    }
}

class Runner1 extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Hello Thread class : " + i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Runner2 implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Hello Runnable interface : " + i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}