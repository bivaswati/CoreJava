package Searching;

public class SequentialSearch {
    public static void main(String[] args) {
        int[] arr= {300,344,34,34,10,34,34,9000};
        int searchKey = 34;
        System.out.println("Key " + searchKey + " found at index: " + linerSearch(arr, searchKey));
        System.out.println("Count of number is : " + countDuplicate(arr,searchKey));
        System.out.println("The Peak number is : " + peakElement(arr));
    }
    private static int linerSearch(int[] arr, int searchKey) {
        for (int i=0;i< arr.length;i++) {
            if(searchKey == arr[i]){
                return i;
            }
        }return -1;
    }
    private static int countDuplicate(int[] arr, int searchKey) {
        int count=0;
        for (int anArr : arr) {
            if (searchKey == anArr) {
                count++;
            }
        }return count;
    }
    private static int peakElement(int[] arr) {
        int peak=arr[0];
        for (int i=1;i<arr.length;i++) {
            if(peak < arr[i]){
                peak = arr[i];
            }
        }return peak;
    }
}