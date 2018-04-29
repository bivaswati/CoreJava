package StringManipulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StringReverse {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter a string : ");
        String line = br.readLine();
        System.out.println("After Word Reversal : " + wordReverse(line.trim()));
        System.out.println("After String Reverse : " + stringReversal(line.trim()));
    }

    private static String wordReverse(String line) {
        String word[] = line.split(" ");
        StringBuffer sb = new StringBuffer();
        for (int i = word.length -1 ; i >= 0; i--) {
            sb.append(word[i]).append(" ");
        }
        return String.valueOf(sb);
    }

    private static String stringReversal(String line) {
        int l = line.length();
        char ch[] = new char[l];
        char linarr[] = line.toCharArray();
        for(int i=0 ; i < l; i++) {
            ch[i] = linarr[l-1-i];
        }
        return String.valueOf(ch);
    }
}

