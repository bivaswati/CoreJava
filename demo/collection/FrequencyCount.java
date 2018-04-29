package demo.collection;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FrequencyCount {
    public static void main(String[] args) {
        int arr[] = {5,10,6,10,4,6};
        countFrequency(arr);
    }

    private static void countFrequency(int[] arr) {
        HashMap<Integer,Integer> map = new HashMap<>();

        for (int i = 0; i < arr.length; i++) {
            Integer c = map.get(arr[i]);
            if (map.get(arr[i]) == null)
                map.put(arr[i],1);
            else
                map.put(arr[i],++c); // c++ will not work all count will be 1
        }

        for (Map.Entry s : map.entrySet()){
            System.out.println("Number : " + s.getKey() +", Frequency : " + s.getValue());
        }
    }

}
