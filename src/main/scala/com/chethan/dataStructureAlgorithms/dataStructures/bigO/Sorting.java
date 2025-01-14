package com.chethan.dataStructureAlgorithms.dataStructures.bigO;

import java.util.Arrays;

/**
 * Created by $CapName on Jan 03, 2025.
 */

public class Sorting {


    public int[] bubbleSort(int[] input) {

        for (int i = 0; i < input.length - 1; i++) {
            for (int j = 0; j < input.length - 1 - i; j++) {
                int a = input[j];
                int b = input[j + 1];
                if (a > b) {
                    input[j + 1] = a;
                    input[j] = b;
                }
            }
        }
        return input;
    }

    public static int[] selectionSort(int[] input) {

        for (int i = 0; i < input.length - 1; i++) {
            int minIndex = i;
            int minValue = input[i];
            for (int j = i; j < input.length - 1; j++) {
                int c = input[j + 1];
                if (c < minValue) {
                    input[i] = c;
                    input[j + 1] = minValue;
                    minValue = c;
                }
            }
        }
        return input;
    }

    static int ccccc = 0;

    public static int[] insertionSort(int[] input) {

        for (int i = 1; i < input.length; i++) {
            for (int j = i - 1; j >= 0; j--) {
                int a = input[j + 1];
                int b = input[j];
                if (a < b) {
                    input[j + 1] = b;
                    input[j] = a;
                } else break;
                ccccc++;
            }
        }
        return input;
    }

//    public static void insertionSort(int[] array) {
//        for (int i = 1; i < array.length; i++) {
//            int temp = array[i];
//            int j    = i - 1;
//            while (j > -1 && temp < array[j]) {
//                array[j + 1] = array[j];
//                array[j] = temp;
//                j--;
//                ccccc++;
//            }
//        }
//    }


    public void mySelection(int[] nums) {

        for (int i = 1; i < nums.length - 1; i++) {
            int j    = i - 1;
            int temp = nums[i];
            while (j >= 0 && temp < nums[j]) {
                nums[j + 1] = nums[j];
                j--;
            }
            nums[j + 1] = temp;


        }

    }

    public static void main(String[] args) {
//        selectionSort(new int[]{3, 1, 2});
        int[] myArray = {4, 2, 6, 5, 1, 3};

        insertionSort(myArray);

        System.out.println(Arrays.toString(myArray));
        System.out.println(ccccc);

    }
}
