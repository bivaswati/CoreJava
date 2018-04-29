package Multithreading;

public class Test {
    public static void main(String[] args) {
        final StringBuffer a = new StringBuffer();
        final StringBuffer b = new StringBuffer();

        new Thread(){
            @Override
            public void run() {
                System.out.println("->"+a.append("A"));
                synchronized (b){
                    System.out.println("->>"+b.append("B"));
                }
            }
        }.start();

        new Thread(){
            @Override
            public void run() {
                System.out.println("<-"+b.append("C"));
                synchronized (a){
                    System.out.println("<<-"+a.append("D"));
                }
            }
        }.start();

        System.out.println("==============================");

        String s = "hello";
      /*  Object o = s;
        if (o.equals(s)){
            System.out.println("A");
        } else {
            System.out.println("B");
        }
        if (s.equals(o)){
            System.out.println("C");
        } else {
            System.out.println("D");
        }*/
    }
}
