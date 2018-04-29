package Sorting;

import java.util.Scanner;

public class SelectionSort {
    public static void main(String a[]){
        int num_Of_integer_to_Sort, size;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Number of Integers for Sorting : ");
        num_Of_integer_to_Sort = sc.nextInt();
        int inputArray[] = new int[num_Of_integer_to_Sort];
        System.out.println("Enter " + num_Of_integer_to_Sort + " Integers now : ");
        for (size = 0; size < num_Of_integer_to_Sort; size++) {
            inputArray[size] = sc.nextInt();
        }
        //int[] sortedArray = doSelectionSort(inputArray);
        int[] sortedArray = selectionSort(inputArray);
        for(int i:sortedArray){
            System.out.print(i);
            System.out.print(" ");
        }
    }
    private static int[] doSelectionSort(int[] arr){
        for (int i = 0; i < arr.length - 1; i++) {
            int index = i;
            for (int j = i + 1; j < arr.length; j++)
                if (arr[j] < arr[index])
                    index = j;

            int smallerNumber = arr[index];
            arr[index] = arr[i];
            arr[i] = smallerNumber;
        }
        return arr;
    }
    private static int[] selectionSort(int[] list) {
        int i, j, minValue, minIndex, temp = 0;
        for (i = 0; i < list.length; i++) {
            minValue = list[i];
            minIndex = i;
            for (j = i; j < list.length; j++) {
                if (list[j] < minValue) {
                    minValue = list[j];
                    minIndex = j;
                }
            }
            if (minValue < list[i]) {
                temp = list[i];
                list[i] = list[minIndex];
                list[minIndex] = temp;
            }
        }
        return list;
    }
}

