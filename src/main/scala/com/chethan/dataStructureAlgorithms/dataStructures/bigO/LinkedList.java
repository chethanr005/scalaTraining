package com.chethan.dataStructureAlgorithms.dataStructures.bigO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Chethan on Nov 23, 2024.
 */

public class LinkedList {
    private Node head;
    private Node tail;
    private int  length;

    class Node {
        int  value;
        Node next;

        Node(int value) {
            this.value = value;
        }
    }

    public LinkedList(int value) {
        Node newNode = new Node(value);
        head = newNode;
        tail = newNode;
        length = 1;
    }

    public Node getHead() {
        return head;
    }

    public Node getTail() {
        return tail;
    }

    public int getLength() {
        return length;
    }

    public void printList() {
        Node temp = head;
        while (temp != null) {
            System.out.println(temp.value);
            temp = temp.next;
        }
    }

    public void printAll() {
        if (length == 0) {
            System.out.println("Head: null");
            System.out.println("Tail: null");
        } else {
            System.out.println("Head: " + head.value);
            System.out.println("Tail: " + tail.value);
        }
        System.out.println("Length:" + length);
        System.out.println("\nLinked List:");
        if (length == 0) {
            System.out.println("empty");
        } else {
            printList();
        }
    }

    public void makeEmpty() {
        head = null;
        tail = null;
        length = 0;
    }

    public void append(int value) {
        Node newNode = new Node(value);
        if (length == 0) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        length++;
    }

    public Node removeLast() {
        if (length == 0) return null;
        Node temp = head;
        Node pre  = head;
        while (temp.next != null) {
            pre = temp;
            temp = temp.next;
        }
        tail = pre;
        tail.next = null;
        length--;
        if (length == 0) {
            head = null;
            tail = null;
        }
        return temp;
    }

    public void prepend(int value) {
        Node newNode = new Node(value);
        if (length == 0) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.next = head;
            head = newNode;
        }
        length++;
    }

    public Node removeFirst() {
        if (length == 0) return null;
        Node temp = head;
        head = head.next;
        temp.next = null;
        length--;
        if (length == 0) {
            tail = null;
        }
        return temp;
    }

    public Node get(int index) {
        if (index < 0 || index >= length) return null;
        Node temp = head;
        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }
        return temp;
    }

    public boolean set(int index, int value) {
        Node temp = get(index);
        if (temp != null) {
            temp.value = value;
            return true;
        }
        return false;
    }

    public boolean insert(int index, int value) {
        if (index < 0 || index > length) return false;
        if (index == 0) {
            prepend(value);
            return true;
        }
        if (index == length) {
            append(value);
            return true;
        }
        Node newNode = new Node(value);
        Node temp    = get(index - 1);
        newNode.next = temp.next;
        temp.next = newNode;
        length++;
        return true;
    }

    public Node remove(int index) {
        if (index < 0 || index >= length) return null;
        if (index == 0) return removeFirst();
        if (index == length - 1) return removeLast();

        Node prev = get(index - 1);
        Node temp = prev.next;

        prev.next = temp.next;
        temp.next = null;
        length--;
        return temp;
    }

    public void reverse() {
        System.out.println("starting length" + length);
        // if(!(length == 0 || length == 1)) {
        Node temp = tail;
        doit(tail, length - 1);
        this.head = temp;

        // }
    }

    private void doit(Node endNode, int index) {
        if (index == 0) {
            System.out.println("if" + endNode.value);
            endNode.next = null;
            this.tail = endNode;
        } else {
            System.out.println("else" + endNode.value);
            Node before = get(index - 1);
            endNode.next = before;
            doit(before, index - 1);
        }
    }

    public Node findMiddleNode() {
        int length = getLength(head, 0);
        if (length > 0) {
            System.out.println("current length" + length);
            int mid = length / 2;
            return getMiddle(head, 0, mid);
        } else return null;
    }

    private Node getMiddle(Node head, int c, int mid) {
        if (c == mid) return head;
        else return getMiddle(head.next, c + 1, mid);
    }

    private int getLength(Node head, int length) {
        if (head == null) return length;
        else return getLength(head.next, length + 1);
    }

    public boolean hasLoop() {
        if (head == null) return false;
        else if (head.next == null) return false;
        else if (head.next.next == null) return false;
        else if (head.next.next.toString() == head.toString()) return true;
        else return findLoop(head.next.next, head);
    }

    private boolean findLoop(Node fast, Node slow) {
        if (fast == null) return false;
        else if (fast.toString().equals(slow.toString())) return true;
        else if (fast.next == null) return false;
        else if (fast.next.toString() == slow.toString()) return true;
        else if (fast.next.next == null) return false;
        else return findLoop(fast.next.next, slow.next);
    }

    private void createLoop() {
        Node get2 = get(2);
        tail.next = get2;
    }

    public void partitionList(int x) {
        part(head, x, null, null, null, null);
    }

    private void part(Node head, int x, Node less, Node high, Node lessStart, Node highStart) {
        if (head == null) concatList(less, high, lessStart, highStart);
        else if (head.value < x) {
            if (less == null) part(head.next, x, head, high, head, highStart);
            else {
                less.next = head;
                part(head.next, x, head, high, lessStart, highStart);
            }
        } else {
            if (high == null) part(head.next, x, less, head, lessStart, head);
            else {
                high.next = head;
                part(head.next, x, less, head, lessStart, highStart);
            }
        }
    }

    private void concatList(Node less, Node high, Node lessStart, Node highStart) {
        if (less != null && high != null && lessStart != null && highStart != null) {
            this.head = lessStart;
            less.next = highStart;
            high.next = null;
        } else if (high != null && highStart != null) {
            this.head = highStart;
            high.next = null;
        } else if (less != null && lessStart != null) {
            this.head = lessStart;
            less.next = null;
        }
    }

    public void removeDuplicates() {
        System.out.println("before : " + length);
        Set<Integer> set = new HashSet<>();

        Node main     = head;
        Node previous = null;

        while (main != null) {
            if (!set.contains(main.value)) {
                if (previous != null)
                    previous.next = main;
                previous = main;
                set.add(main.value);
            } else length--;
            main = main.next;
        }
        System.out.println("after : " + length);
    }

    private Set<Integer> loop(Node head, Set<Integer> set) {
        if (head == null) return set;
        else {
            set.add(head.value);
            return loop(head.next, set);
        }
    }

    public int binaryToDecimal() {
        String binary = getBinary(head, "");
        int    length = binary.length() - 1;

        int num = 0;
        for (int i = length; i >= 0; i--) {
            int flag = Integer.parseInt(String.valueOf(binary.charAt(i)));
            if (flag == 1) {
                int pow = (int) Math.pow(2, length - i);
                num = num + pow;
            }
        }
        return num;
    }

    private String getBinary(Node node, String binary) {
        if (node == null) return binary;
        else return getBinary(node.next, binary + node.value);
    }


    public void reverseBetween(int m, int n) {

        Node head = getHead();
        if (head == null) return;
        ArrayList<Node> nodeList = new ArrayList<>();
        Node            tempHead = null;
        Node            tempTail = null;

        int itr = 0;
        while (head != null && itr <= n) {
            if (itr >= m) nodeList.add(head);
            if (itr == m - 1) tempHead = head;
            if (itr == n) tempTail = head.next;
            head = head.next;
            itr += 1;
        }

        for (int i = n - m; i >= 0; i--) {

            if (i == n - m) {
                head = nodeList.get(i);
                if (m == 0) this.head = head;
                if (tempHead != null) tempHead.next = head;
            } else if (i == 0) {
                Node c = nodeList.get(i);
                head.next = c;
                c.next = tempTail;
                if (tempTail != null) tempTail.next = null;
            } else {
                Node c = nodeList.get(i);
                head.next = c;
                head = c;
            }
        }
    }

    public void reverseBetweenV2(int startIndex, int endIndex) {
        if (head == null) return;

        Node dummyNode = new Node(0);
        dummyNode.next = head;
        Node previousNode = dummyNode;

        for (int i = 0; i < startIndex; i++) {
            previousNode = previousNode.next;
        }

        Node currentNode = previousNode.next;

        for (int i = 0; i < endIndex - startIndex; i++) {
            Node nodeToMove = currentNode.next;
            currentNode.next = nodeToMove.next;
            nodeToMove.next = previousNode.next;
            previousNode.next = nodeToMove;
        }
        head = dummyNode.next;
    }


//    public void bubbleSort() {
//        if (head != null && head.next != null) {
//            Node node  = head;
//            Node cTail = tail;
//            Node pNode = null;
//
//
//            while (head.next != cTail) {
//                boolean breakLoop = node.next == cTail;
//                while (node != cTail) {
//                    Node next = node.next;
//                    if (node.value > next.value) {
//                        if (pNode == null) head = next;
//                        if (pNode != null) pNode.next = next;
//                        pNode = next;
//                        node.next = next.next;
//                        next.next = node;
//                    } else {
//                        node = next;
//                        pNode = node;
//                    }
//                    if (node.next == null) cTail = node;

    /// /                    else if(node.next == cTail) cTail = node;
//                    if (breakLoop) {
//                        cTail = node;
//                        break;
//                    }
//                }
//                node = head;
//                pNode = null;
//
//            }
//        }
//    }
    public void bubbleSort() {
        if (length > 1) {
            Node exitNode = null;
            while (exitNode != this.head.next) {
                Node node = this.head;
                while (node.next != exitNode) {
                    Node next = node.next;
                    if (node.value > next.value) {
                        int temp = node.value;
                        node.value = next.value;
                        next.value = temp;
                    }
                    node = next;
                }
                exitNode = node;
            }
        }
    }

    public static int[] merge(int[] list1, int[] list2) {

        int[] result = new int[list1.length + list2.length];

        int l1 = 0;
        int l2 = 0;
        int r  = 0;

        while (l1 < list1.length && l2 < list2.length) {
            int i = list1[l1];
            int j = list2[l2];

            if (i < j) {
                result[r] = i;
                l1++;
                r++;
            } else {
                result[r] = j;
                l2++;
                r++;
            }
        }

        while (l1 < list1.length) {
            result[r] = list1[l1];
            l1++;
            r++;
        }

        while (l2 < list2.length) {
            result[r] = list1[l2];
            l2++;
            r++;
        }
        return result;
    }


    public int[] mergeSort(int[] array) {
        if (array.length == 1) return array;
        else {
            int   mid   = array.length / 2;
            int[] half1 = mergeSort(Arrays.copyOfRange(array, 0, mid));
            int[] half2 = mergeSort(Arrays.copyOfRange(array, mid, array.length));

            return merge(half1, half2);
        }
    }


//    public void merge(LinkedList otherList) {
//        Node currentHead = this.head;
//        Node otherHead   = otherList.head;
//
//        int count = 0;
//
//        LinkedList linkedList = new LinkedList(0);
//
//        while (currentHead != null && otherHead != null) {
//            if (currentHead.value < otherHead.value) {
//                linkedList.append(currentHead.value);
//                currentHead = currentHead.next;
//            } else {
//                linkedList.append(otherHead.value);
//                otherHead = otherHead.next;
//            }
//
//            count++;
//        }
//
//        while (currentHead != null) {
//            linkedList.append(currentHead.value);
//            currentHead = currentHead.next;
//            count++;
//        }
//
//        while (otherHead != null) {
//            linkedList.append(otherHead.value);
//            otherHead = otherHead.next;
//            count++;
//        }
//        System.out.println("this is the count");
//        System.out.println(count);
//        linkedList.head = linkedList.head.next;
//        this.head = linkedList.head;
//    }


    public void merge(LinkedList otherList) {
        Node otherHead = otherList.getHead();
        Node dummy     = new Node(0);
        Node current   = dummy;

        int count = 0;

        while (head != null && otherHead != null) {
            if (head.value < otherHead.value) {
                current.next = head;
                head = head.next;
            } else {
                current.next = otherHead;
                otherHead = otherHead.next;
            }
            current = current.next;
            count++;
        }

        if (head != null) {
            current.next = head;
        } else {
            current.next = otherHead;
            tail = otherList.getTail();
        }
        System.out.println(count);

        head = dummy.next;
        length += otherList.getLength();
    }

    private static void quickSortHelper(int[] array, int left, int right) {
        if (!(left < right)) return;
        else {
            if (array.length > 1) {
                int mid = array[0];


            }
        }
    }

    static int count = 0;

    static int[] stack = new int[1001];

    public static int fibonacci(int n) {
        count++;
        stack[0] = 0;
        stack[1] = 1;
        if (n == 0 || n == 1) return n;
        else {
            if (stack[n] > 0) return stack[n];
            else {
                int result = fibonacci(n - 1) + fibonacci(n - 2);
                stack[n] = result;
                return result;

            }
        }
    }

    public static long fibonacciV2(int n) {
        long[] stack = new long[1001];
        int    count = 0;

        stack[0] = 0;
        stack[1] = 1;

        for (int i = 2; i <= n; i++) {
            count++;
            stack[i] = stack[i - 1] + stack[i - 2];
        }
        System.out.println(count);
        return stack[n];
    }

    public static int removeDuplicates(int[] nums) {
        if (nums.length == 0) return 0;
        int uniqueLength = 1;
        int c            = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (c < nums[i]) {
                nums[uniqueLength] = nums[i];
                uniqueLength++;
                c = nums[i];
            }
        }
        return uniqueLength;
    }


    public static int removeElement(int[] nums, int val) {

        int handicap = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == val) handicap++;
            else if (handicap > 0) nums[i - handicap] = nums[i];
        }
        return nums.length - handicap;
    }


    public static String findLongestString(String[] stringList) {
        String longest = "";

        for (int i = 0; i < stringList.length; i++) {
            String s = stringList[i];
            if (longest.length() < s.length()) longest = s;
        }
        return longest;
    }

    public static int[] findMaxMin(int[] myList) {
        if (myList.length == 0) return myList;

        int small = myList[0];
        int big   = myList[0];
        for (int i : myList) {
            if (i < small) small = i;
            else if (i > big) big = i;
        }
        return new int[]{big, small};
    }


    public static int maxProfit(int[] prices) {
        int profit = 0;
        for (int i = 0; i < prices.length; i++) {
            int start = prices[i];
            for (int j = i + 1; j < prices.length; j++) {
                int end  = prices[j];
                int diff = end - start;
                if (diff > profit) profit = diff;
            }
        }
        return profit;
    }

//    public static void rotate(int[] nums, int k) {
//        int count = 0;
//        if (nums.length < 1) return;
//
//        while (k >= 0) {
//            int first = nums[0];
//
//            for (int i = 1; i < nums.length; i++) {
//                nums[i - 1] = nums[i];
//                count++;
//            }
//            nums[nums.length - 1] = first;
//            k--;
//        }
//        System.out.println("");
//    }

    public static void rotate(int[] nums, int k) {
        int count = 0;
        // Reduce k to its equivalent value within array length range.
        k = k % nums.length;

        // Reverse the first part of the array (from start to length-k)
        for (int start = 0, end = nums.length - k - 1; start < end; start++, end--) {
            // Swap elements at positions 'start' and 'end'
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            count++;
        }

        // Reverse the second part of the array (from length-k to end)
        for (int start = nums.length - k, end = nums.length - 1; start < end; start++, end--) {
            // Swap elements at positions 'start' and 'end'
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            count++;
        }

        // Reverse the whole array
        for (int start = 0, end = nums.length - 1; start < end; start++, end--) {
            // Swap elements at positions 'start' and 'end'
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            count++;
        }
    }

    public static int maxSubarray(int[] nums) {
        if (nums.length == 0) return 0;
        int cs  = nums[0];
        int max = nums[0];

        for (int i = 1; i < nums.length; i++) {
            cs = Math.max(nums[i], cs + nums[i]);
            max = Math.max(cs, max);
        }
        return max;
    }


    public static void main(String[] args) throws Exception {
        LinkedList list = new LinkedList(1);
        list.append(3);
        list.append(5);
        list.append(7);

        LinkedList otherList = new LinkedList(2);
        otherList.append(4);
        otherList.append(6);
        otherList.append(8);
//        list.append(5);
//        list.reverseBetween(4, 4);
//        list.reverseBetweenV2(1, 3);
//        list.append(1);
//        list.removeDuplicates();
//        System.out.println(list.binaryToDecimal());
//        list.bubbleSort();
        list.merge(otherList);

//        System.out.println(fibonacci(1000));
//        System.out.println(fibonacciV2(10));
//        System.out.println(count);

//        int[] input  = new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4};
//        int[] input1 = removeElement(input, 1);


//        int[] input  = new int[]{5, 3, 8, 1, 6, 9};
//        int[] maxMin = findMaxMin(input);
//        int[] input  = new int[]{0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
//        int   unique = removeDuplicates(input);

//        int[] input  = new int[]{7, 1, 5, 3, 6, 4};
//        int   unique = maxProfit(input);
//        int[] input = new int[]{1, 2, 3, 4, 5, 6, 7};
//        rotate(input, 3);
        int[] input = new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4};
        maxProfit(input);
        System.out.println("abc");
    }
}


