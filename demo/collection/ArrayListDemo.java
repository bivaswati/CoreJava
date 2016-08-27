package demo.collection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ArrayListDemo {
    public static void main(String[] args) throws IOException {
        ArrayList<String> arrayList = new ArrayList<>();
        boolean flag = true;
        while (flag){
            System.out.println("Press 1 to continue 0 to abort");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int choice = Integer.parseInt(br.readLine());
            if (choice == 1){
                System.out.println("Enter String values");
                BufferedReader value = new BufferedReader(new InputStreamReader(System.in));
                String data = value.readLine();
                arrayList.add(data);
            }else {
                flag = false;
            }
        }
        System.out.println("The Containts of ArrayList are : " +arrayList.toString());
    }
}
