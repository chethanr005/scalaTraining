package com.chethan.dataStructureAlgorithms.dataStructures.bigO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by $CapName on Dec 27, 2024.
 */

public class Heap {
    private List<Integer> heap;

    Heap() {
        this.heap = new ArrayList<>();
    }

    public List<Integer> getHeap() {
        return new ArrayList<>(heap);
    }

    public int leftChild(int index) {
        return 2 * index + 1;

    }

    public int rightChild(int index) {
        return 2 * index + 2;
    }

    public int parent(int index) {
        return (index - 1) / 2;
    }

    public void swap(int index1, int index2) {
        int temp = heap.get(index1);
        heap.set(index1, heap.get(index2));
        heap.set(index2, temp);
    }


    public void insert(int value) {
        heap.add(value);
        int latestIndex = heap.size() - 1;

        while (value > heap.get(parent(latestIndex))) {
            swap(latestIndex, parent(latestIndex));
            latestIndex = parent(latestIndex);
        }
    }


//    public Integer remove() {
//        if (!heap.isEmpty()) {
//            int     i      = 0;
//            Integer parent = heap.removeFirst();
//            if (heap.size() == 1) return parent;
//            else {
//                int lastChild = heap.removeLast();
//                heap.addFirst(lastChild);
//
//                while (i >= 0) {
//                    int     c1Index = leftChild(i);
//                    int     c2Index = rightChild(i);
//                    Integer c1      = heap.get(c1Index);
//                    Integer c2      = heap.get(c2Index);
//                    int     max     = (c1 > c2) ? c1Index : c2Index;
//                    if (heap.get(i) < max) {
//                        swap(i, max);
//                        i = max;
//                    } else i = -1;
//                }
//                return parent;
//            }
//        }
//        return 0;
//    }

    public Integer remove() {
        if (!heap.isEmpty()) {
            int     i      = 0;
            Integer parent = heap.remove(i);
            if (heap.isEmpty()) return parent;
            else {
                int lastChild = heap.remove(heap.size() - 1);
                heap.add(i, lastChild);

                sinkDown(i);
                return parent;
            }
        }
        return null;
    }

    public void sinkDown(int i) {
        while (i >= 0) {
            int c1Index = leftChild(i);
            int c2Index = rightChild(i);
            if (c1Index < heap.size()) {
                Integer c1 = heap.get(c1Index);
                if (c2Index < heap.size()) {
                    Integer c2       = heap.get(c2Index);
                    int     maxValue = (c1 > c2) ? c1 : c2;
                    int     maxIndex = (c1 > c2) ? c1Index : c2Index;
                    if (heap.get(i) < maxValue) {
                        swap(i, maxIndex);
                        i = maxIndex;
                    } else i = -1;
                } else {
                    if (heap.get(i) < c1) swap(i, c1Index);
                    i = -1;
                }
            } else i = -1;
        }
    }

    public void insertMin(int value) {
        heap.add(value);
        int latestIndex = heap.size() - 1;

        while (value < heap.get(parent(latestIndex))) {
            swap(latestIndex, parent(latestIndex));
            latestIndex = parent(latestIndex);
        }
    }

    public static List<Integer> streamMax(int[] nums) {
        List<Integer> result = new ArrayList<>();
        if (nums.length == 0) return result;
        int temp = nums[0];
        for (int i = 0; i < nums.length; i++) {
            int current = nums[i];
            if (temp > current) result.add(temp);
            else {
                result.add(current);
                temp = current;
            }
        }
        return result;
    }

    public int factorial(int num) {
        if (num == 1) return 1;

        else return num * factorial(num - 1);
    }



    public static void main(String[] args) {
        Heap heap = new Heap();

        heap.insert(100);
        heap.insert(75);
        heap.insert(80);
        heap.insert(45);
        heap.insert(42);
        heap.insert(21);
        heap.insert(19);

        System.out.println(heap.remove());
        System.out.println(heap.remove());
        System.out.println(heap.remove());
        System.out.println(heap.remove());
        System.out.println(heap.remove());
        System.out.println(heap.remove());
        System.out.println(heap.remove());
        System.out.println("");
    }

}
