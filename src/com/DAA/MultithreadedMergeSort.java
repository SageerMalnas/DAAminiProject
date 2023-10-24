package com.DAA;
import java.util.*;
import java.util.Arrays;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

public class MultithreadedMergeSort extends RecursiveAction {
    private int[] arr;
    private int left;
    private int right;

    public MultithreadedMergeSort(int[] arr, int left, int right) {
        this.arr = arr;
        this.left = left;
        this.right = right;
    }

    public static void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];

        for (int i = 0; i < n1; i++) {
            leftArray[i] = arr[left + i];
        }
        for (int j = 0; j < n2; j++) {
            rightArray[j] = arr[mid + 1 + j];
        }

        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2) {
            if (leftArray[i] <= rightArray[j]) {
                arr[k] = leftArray[i];
                i++;
            } else {
                arr[k] = rightArray[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            arr[k] = leftArray[i];
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = rightArray[j];
            j++;
            k++;
        }
    }

    @Override
    protected void compute() {
        if (left < right) {
            int mid = (left + right) / 2;

            invokeAll(new MultithreadedMergeSort(arr, left, mid), new MultithreadedMergeSort(arr, mid + 1, right));

            merge(arr, left, mid, right);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter size of the array");
        int n = sc.nextInt();
        int[] arr = new int[n];
        System.out.println("Enter elements of the array");
        for (int i = 0;i<n;i++){
            arr[i] = sc.nextInt();
        }
//        int[] arr = {12, 11, 13, 5, 6, 7};
        System.out.println("Original array: " + Arrays.toString(arr));

        ForkJoinPool pool = new ForkJoinPool();
        long startTime = System.currentTimeMillis();
//        mergeSort(arr, 0, arr.length - 1);
        pool.invoke(new MultithreadedMergeSort(arr, 0, arr.length - 1));

        long endTime = System.currentTimeMillis();

        System.out.println("Sorted array: " + Arrays.toString(arr));
    }
}
