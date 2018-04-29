package Multithreading;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RunnerExample {
    private Account account1 = new Account();
    private Account account2 = new Account();

    private Lock lock1 = new ReentrantLock();
    private Lock lock2 = new ReentrantLock();

    private void acquireLocks(Lock firstLock,Lock secondLock) throws InterruptedException{
        while (true){
            //Acquire lock
            boolean gotFirstlock = false;
            boolean gotSecondlock = false;
            try {
                gotFirstlock = firstLock.tryLock();
                gotSecondlock = secondLock.tryLock();
            } finally {
                if(gotFirstlock && gotSecondlock){
                    return;
                }
                if (gotFirstlock){
                    firstLock.unlock();
                }
                if (gotSecondlock){
                    secondLock.unlock();
                }
                //Lock not acquired
                Thread.sleep(1);
            }
        }
    }

    public void firstThread() throws InterruptedException {
        for (int i = 0; i < 10000; i++) {
            lock1.lock();
            lock2.lock();
            //acquireLocks(lock1,lock2);
            try {
                Account.transfer(account1,account2,new Random().nextInt(100));
            } finally {
                lock1.unlock();lock2.unlock();
            }
        }
    }
    public void secondThread() throws InterruptedException {
        for (int i = 0; i < 10000; i++) {
            /*DeadLock code
            lock2.lock();
            lock1.lock();*/

            lock1.lock();
            lock2.lock();

            //acquireLocks(lock2,lock1);

            try {
                Account.transfer(account2,account1,new Random().nextInt(100));
            } finally {
                lock1.unlock();lock2.unlock();
            }
        }
    }
    public void finished(){
        System.out.println("Account1 balance : " + account1.getBalance());
        System.out.println("Account2 balance : " + account2.getBalance());
        System.out.println("Total balance : " + (account1.getBalance() + account2.getBalance()));
    }
}
