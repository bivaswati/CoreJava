package Searching;
import java.util.Scanner;
public class BinarySearch {
    public static void main(String[] args) {
        int numTobeSearched, index, arr[] = {1,2,3,5, 7, 8, 90, 120, 456, 987, 3460};
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number to be Searhed : ");
        numTobeSearched = sc.nextInt();
        //index = recursiveBinarySearch(arr, 0, arr.length-1, numTobeSearched);
        index = binarySearch(arr, 0, arr.length-1, numTobeSearched);
        if(index > -1) {
            System.out.println("The number " + numTobeSearched + " Found at index : " + index);
        } else {
            System.out.println("The number Not Fund");
        }

    }

    private static int recursiveBinarySearch(int[] arr, int start, int end, int numTobeSearched) {
        int middle = (start + end) / 2;
        if (end < start) {
            return -1;
        }
        if (numTobeSearched == arr[middle]) {
            return middle;
        } else if (numTobeSearched < arr[middle]) {
            return recursiveBinarySearch(arr, start, middle - 1, numTobeSearched);
        } else {
            return recursiveBinarySearch(arr, middle + 1, end, numTobeSearched);
        }
    }

    private static int binarySearch(int arr[], int start,int end, int numTobeSearched) {
        while (start <= end) {
            int middle = (start + end)/2;
            if (numTobeSearched == arr[middle])
                return middle;
            if (numTobeSearched > arr[middle])
                start = middle + 1;
            else
                end = middle - 1;
        }
        return -1;
    }
}
