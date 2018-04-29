package Sorting;

import java.util.Scanner;

public class Hi {
    private static int array[] ;
    public static void main(String[] args) {
        int num_ofelements_to_be_sorted;
        System.out.println("Enter number of elements to form array : ");
        Scanner sc= new Scanner(System.in);
        num_ofelements_to_be_sorted = sc.nextInt();
        array = new int[num_ofelements_to_be_sorted];
        System.out.println("Enter the array elements : ");
        for (int i = 0; i < num_ofelements_to_be_sorted ; i++) {
            array[i] = sc.nextInt();
        }
        //doBubbleSorting(array);
        //doInsertionSorting(array);
        doQuickSort(0,array.length - 1);
        System.out.println("After sorting : ");
        for (int i = 0; i < array.length ; i++) {
            System.out.print(array[i] + ", ");
        }

    }

    private static void doBubbleSorting(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] > array[j+1]){
                    int temp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = temp;
                }
            }
        }
    }

    private static int[] doInsertionSorting(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            int j = i-1;
            while (j>=0 && array[j] > key) {
                array[j+1] = array[j];
                j = j-1;
            }
            array[j+1] = key;
        }
        return array;
    }

    private static void doQuickSort(int low,int high) {
        int i = low,j=high;
        int pivot = array[low + (high - low)/2];
        while (i <= j){
            while (array[i] < pivot) {
                i++;
            }
            while (array[j] > pivot) {
                j--;
            }
            while (i<=j){
                exchange(i,j);
                i++;
                j--;
            }
        }
        if(low<j){
            doQuickSort(low,j);
        }
        if(i<high){
            doQuickSort(i,high);
        }
    }

    private static void exchange(int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

}