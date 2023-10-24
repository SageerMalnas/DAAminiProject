package com.DAA;

import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

public class SortingComparison {

    public static void merge(int[] arr, int left, int mid, int right) {
        // Merge logic as shown in the previous example
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

    public static void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;

            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);

            merge(arr, left, mid, right);
        }
    }

    public static class MultithreadedMergeSort extends RecursiveAction {
        // Multithreaded Merge Sort implementation (same as in the previous example)
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

                invokeAll(new com.DAA.MultithreadedMergeSort(arr, left, mid), new com.DAA.MultithreadedMergeSort(arr, mid + 1, right));

                merge(arr, left, mid, right);
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);


        int choice;
        do {
            System.out.println("Choose a sorting method:");
            System.out.println("1. Merge Sort");
            System.out.println("2. Multithreaded Merge Sort");
            System.out.println("3. Exit");

            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Enter the size of the array");
                    int n = sc.nextInt();
                    int[] arr = new int[n];
                    System.out.println("Enter elements of the array");
                    for (int i = 0; i < n; i++) {
                        arr[i] = sc.nextInt();
                    }
                    System.out.println("Original array: " + Arrays.toString(arr));

                    long startTime = System.currentTimeMillis();
                    mergeSort(arr, 0, arr.length - 1);
                    long endTime = System.currentTimeMillis();

                    System.out.println("Sorted array (Merge Sort): " + Arrays.toString(arr));
                    System.out.println("Time taken for Merge Sort: " + (endTime - startTime) + " ms");
                    break;

                case 2:
                    System.out.println("Enter the size of the array");
                    n = sc.nextInt();
                    int[] arrMultithreaded = new int[n];
                    System.out.println("Enter elements of the array");
                    for (int i = 0; i < n; i++) {
                        arrMultithreaded[i] = sc.nextInt();
                    }
                    System.out.println("Original array: " + Arrays.toString(arrMultithreaded));

                    ForkJoinPool pool = new ForkJoinPool();
                    startTime = System.currentTimeMillis();
                    pool.invoke(new MultithreadedMergeSort(arrMultithreaded, 0, arrMultithreaded.length - 1));
                    endTime = System.currentTimeMillis();

                    System.out.println("Sorted array (Multithreaded Merge Sort): " + Arrays.toString(arrMultithreaded));
                    System.out.println("Time taken for Multithreaded Merge Sort: " + (endTime - startTime) + " ms");
                    break;

                case 3:
                    System.out.println("Exiting the program.");
                    break;

                default:
                    System.out.println("Invalid choice");
                    break;
            }

        } while (choice != 3);

    }
}
