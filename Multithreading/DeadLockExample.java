package Multithreading;

public class DeadLockExample {
    public static Object lock1 = new Object();
    public static Object lock2 = new Object();

    public static void main(String[] args) {
        ThreadDemo1 demo1 = new ThreadDemo1();
        ThreadDemo2 demo2 = new ThreadDemo2();
        demo1.start();
        demo2.start();

    }
    private static class ThreadDemo1 extends Thread {
        public void run(){
            synchronized (lock1){
                System.out.println("T1 holds Lock1 ...");
                try {Thread.sleep(10);} catch (InterruptedException e) {}
                System.out.println("T1 waiting for Lock2 ...");
                synchronized (lock2){
                    System.out.println("T1 holds Lock1 and Lock2 ...");
                }
            }
        }

    }
    //Deadlock code
    /*private static class ThreadDemo2 extends Thread {
        public void run(){
            synchronized (lock2){
                System.out.println("T2 holds Lock2 ...");
                try {Thread.sleep(10);} catch (InterruptedException e) {}
                System.out.println("T2 waiting for Lock1 ...");
                synchronized (lock1){
                    System.out.println("T2 holds Lock2 and Lock1 ...");
                }
            }
        }
    }*/

    //Soluton to DeadLock Code
    private static class ThreadDemo2 extends Thread {
        public void run(){
            synchronized (lock1){
                System.out.println("T2 holds Lock1 ...");
                try {Thread.sleep(10);} catch (InterruptedException e) {}
                System.out.println("T2 waiting for Lock2 ...");
                synchronized (lock2){
                    System.out.println("T2 holds Lock1 and Lock2 ...");
                }
            }
        }
    }
}

