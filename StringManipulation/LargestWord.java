package StringManipulation;
import java.util.Scanner;
public class LargestWord {
    public static void main(String[] args) {
        System.out.println("Enter The Sentence : ");
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        System.out.println("The Largest Word : " + largestWord(line));
    }

    private static String largestWord(String line) {
        String words[] = line.split(" ");
        String largest = words[0];
        for(int i =1;i<words.length;i++){
           if(largest.length()<words[i].length())
               largest = words[i];
        }
        return largest;
    }
}
