package Sorting;//Sort method of Arrays class to sort integers in ascending order which uses a variation of Quick sort
/*
Quicksort is a divide and conquer algorithm. In a divide and conquer sorting algorithm the original data is separated
into two parts "divide" which are individually sorted and "conquered" and then combined.

If the array contains only one element or zero elements than the array is sorted.
If the array contains more than one element than:
Select an element from the array. This element is called the "pivot element". For example select the element in the middle of the array.
All elements which are smaller then the pivot element are placed in one array and all elements which are larger are placed in another array.
Sort both arrays by recursively applying Quicksort to them.
Combine the arrays.
Quicksort can be implemented to sort "in-place". This means that the sorting takes place in the array and that no
additional array needs to be created.
=======================================================================
Quicksort is a fast, recursive, non-stable sort algorithm which works by the divide and conquer principle. Quicksort will in the
best case divide the array into almost two identical parts. It the array contains n elements then the first run will need O(n).
Sorting the remaining two sub-arrays takes 2* O(n/2). This ends up in a performance of O(n log n).

In the worst case quicksort selects only one element in each iteration. So it is O(n) + O(n-1) + (On-2).. O(1)
which is equal to O(n^2).
The average case of quicksort is O(n log n).
1.Get the pivot element from the middle of the list
2.Divide into two lists
3.If the current value from the left list is smaller then the pivot element then get the next element from the left list
4.If the current value from the right list is larger then the pivot element then get the next element from the right list
5.If we have found a values in the left list which is larger then the pivot element and if we have found a value in
  the right list which is smaller then the pivot element then we exchange the values.As we are done we can increase i and j
*/

import java.util.Scanner;

public class QuickSort {
    private static int[] numbers;

    public static void main(String[] args) {
        int num_Of_integer_to_Sort, size;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Number of Integers for Sorting : ");
        num_Of_integer_to_Sort = sc.nextInt();
        numbers = new int[num_Of_integer_to_Sort];
        System.out.println("Enter " + num_Of_integer_to_Sort + " Integers now : ");

        for (size = 0; size < num_Of_integer_to_Sort; size++) {
            numbers[size] = sc.nextInt();
        }
        if (numbers == null || numbers.length ==0) {
            return;
        }
        quicksort(0,numbers.length -1);
        printNumbers(numbers);
    }

    private static void quicksort(int low, int high) {
        int i = low, j = high;
        int pivot = numbers[low + (high - low) / 2];
        while (i <= j) {
            while (numbers[i] < pivot) {
                i++;
            }
            while (numbers[j] > pivot) {
                j--;
            }
            if (i <= j) {
                exchange(i, j);
                i++;
                j--;
            }
        }
        if (low < j)
            quicksort(low, j);
        if (i < high)
            quicksort(i, high);
    }
    private static void exchange(int i, int j) {
        int temp = numbers[i];
        numbers[i] = numbers[j];
        numbers[j] = temp;
    }

    private static void printNumbers(int[] input) {
        for (int anInput : input) {
            System.out.print(anInput + ", ");
        }
        System.out.println("\n");
    }
}


 