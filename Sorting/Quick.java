package Sorting;

import java.util.Arrays;

public class Quick {
    public static void main(String[] args) {
        int arr[] = {3,56,9,101,67,8,98,2};
        quicksort(arr,0,arr.length-1);
        System.out.println("After Quick sort arr is : " + Arrays.toString(arr));
    }

    private static void quicksort(int[] arr, int start, int end) {
        if (start < end) {
            int pp = partion(arr,start,end);
            quicksort(arr,start,pp - 1);
            quicksort(arr,pp + 1,end);
        }
    }

    private static int partion(int[] arr, int start, int end) {
        int pivot = arr[end];
        int i = start - 1;

        for (int j = start; j <= end-1; j++) {

            if (arr[j] <= pivot){
                i++;
                int ival = arr[i];
                int jval = arr[j];
                arr[j] = ival;
                arr[i] = jval;
            }
        }

        int ival = arr[i+1];
        arr[end] = ival;
        arr[i+1] = pivot;
        return i+1;
    }
}
