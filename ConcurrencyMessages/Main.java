package ConcurrencyMessages;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Message message = new Message();
        (new Thread(new Writer(message))).start();
        (new Thread(new Reader(message))).start();
    }
}

class Message {
    private String message;
    private boolean empty = true;

    public synchronized String read() {
        while(empty) {
            //System.out.println("Read's While: "+empty);
            try {wait();} catch(InterruptedException e) {}
        }
        empty = true;
        notifyAll();
        //System.out.println("Read : "+empty);
        return message;
    }

    public synchronized void write(String message) {
        while(!empty) {
            //System.out.println("Writer's While: "+empty);
            try {wait();} catch(InterruptedException e) {}
        }
        empty = false;
        this.message = message;
        //System.out.println("Write : "+empty);
        notifyAll();
    }
}

class Writer implements Runnable {
    private Message message;

    public Writer(Message message) {this.message = message;}

    public void run() {
        System.out.println("Executing Writer");
        String messages[] = {"Bivas","Bivas1","Bivas2","Bivas3"};
        Random random = new Random();
        for (String message1 : messages) {
            message.write(message1);
            try {Thread.sleep(random.nextInt(2000));} catch (InterruptedException e) {}
        }
        message.write("Finished");
    }
}

class Reader implements Runnable{
    private Message message;

    public Reader(Message message) {this.message = message;}

    @Override
    public void run() {
        System.out.println("Executing Reader");
        Random random = new Random();
        for(String latestMessage = message.read(); !latestMessage.equals("Finished"); latestMessage=message.read()) {
            System.out.println(latestMessage);
            try {Thread.sleep(random.nextInt(2000));} catch (InterruptedException e) {}
        }
    }
}