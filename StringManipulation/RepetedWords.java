package StringManipulation;

import java.util.*;

public class RepetedWords {
    public static void main(String[] args) {
        System.out.println("Enter The Sentence : ");
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        System.out.println("you Entered : " + line.trim());
        System.out.println("The Most Repeted Word : " + findRepetedWord(line));
    }

    private static String findRepetedWord(String line) {
        HashMap<String,Integer> hm = new HashMap<>();
        String sb[] = line.split(" ");
        for (String aSb : sb) {
            int count = 0;
            for (String aSb1 : sb) {
                if (aSb.equalsIgnoreCase(aSb1)) {
                    count++;
                }
            }
            hm.put(aSb, count);
        }

        System.out.println("HM is : " + hm );
        ArrayList al = new ArrayList();
        for (String key: hm.keySet()) {
            al.add(hm.get(key));
        }
        Collections.sort(al);
        String mostRepeat = null;
        for (String key: hm.keySet()) {
            if (hm.get(key) == al.get(al.size()-1)){
                mostRepeat = key;
            }
        }
        return mostRepeat;
    }
}
