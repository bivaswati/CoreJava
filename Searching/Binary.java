package Searching;

public class Binary {
    public static void main(String[] args) {
        int arr[] = {3,5,9,11,67,87,98,99};
        int element = 11;
        int index = myBinarySearch(arr,element);
        System.out.println("Element found at : " + index + " index.");
    }

    private static int myBinarySearch(int[] arr, int element) {
        int p =0;
        int r = arr.length -1;

        while (p <= r) {
            int midPosiion = ( p + r )/2;
            if (element < arr[midPosiion]) r = midPosiion - 1;
            else if (element > arr[midPosiion]) p = midPosiion + 1;
            else return midPosiion;
        }
        return -1;
    }
}
