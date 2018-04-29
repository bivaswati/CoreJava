package ConcurrencyMessages;

public class LambdaDemo {
    public static void main(String[] args) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Hiii");
            }
        });
        t.start();

        Thread t1 = new Thread(() -> System.out.println("Hello"));
        t1.start();
    }
}
