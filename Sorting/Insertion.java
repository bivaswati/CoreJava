package Sorting;

import java.util.Arrays;

public class Insertion {
    public static void main(String[] args) {
        int finalArray [] = doInsertionSort(new int[]{4,7,9,2,13,1,6,3,4});
        System.out.println("Sorted Array : " + Arrays.toString(finalArray));
    }

    private static int[] doInsertionSort(int[] arr) {
        for (int i = 1 ; i < arr.length; i++) {
            int element = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > element) {
                arr[j+1] = arr[j];
                j--;
            }
            arr[j+1] = element;
        }
        return arr;
    }
}
