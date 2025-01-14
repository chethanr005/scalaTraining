package com.chethan.dataStructureAlgorithms.dataStructures.bigO;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Chethan on Dec 10, 2024.
 */

public class HashTable {

    private int    size;
    private Node[] dataMap;


    HashTable(int size) {
        this.size = size;
        dataMap = new Node[size];
    }


    class Node {
        private String key;
        private int    value;
        private Node   next;

        Node(String key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private int hashing(String key) {
        char[] charArray = key.toCharArray();

        int hash = 0;
        for (char ch : charArray) {
            int ascii = ch;
            hash = (hash + ascii * 23) % dataMap.length;
        }
        return hash;
    }

    public void set(String key, int value) {
        int  index = hashing(key);
        Node node  = new Node(key, value);

        if (dataMap[index] == null) dataMap[index] = node;
        else {
            Node head = dataMap[index];
            while (head.next != null) {
                head = head.next;
            }
            head.next = node;
        }
    }

    public int get(String key) {
        int  index = hashing(key);
        Node head  = dataMap[index];
        while (head != null) {
            if (head.key.equals(key)) return head.value;
            head = head.next;
        }
        return 0;
    }

    public List<String> keys() {
        List<String> allKeys = new ArrayList<>();

        for (Node node : dataMap) {
            while (node != null) {
                allKeys.add(node.key);
                node = node.next;
            }
        }
        return allKeys;
    }


    public boolean itemInCommon(int[] array1, int[] array2) {
        HashTable hashTable = new HashTable(7);
        for (int i : array1) {
            hashTable.set(String.valueOf(i), 1);
        }
        for (int i : array2) {
            int value = hashTable.get(String.valueOf(i));
            if (value == 1) return true;
        }
        return false;
    }

    public boolean itemInCommon2(int[] array1, int[] array2) {
        Map<Integer, Boolean> map = new HashMap<>();
        for (int i : array1) {
            map.put(i, true);
        }
        for (int i : array2) {
            if (map.containsKey(i)) return true;
        }
        return false;
    }

    public List<Integer> findDuplicates(int[] array1) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i : array1) {
            map.put(i, map.getOrDefault(i, 0) + 1);
        }
        List<Integer> list = map.entrySet().stream().filter(a -> a.getValue() > 1).map(a -> a.getKey()).toList();
        return list;
    }

    public static Character firstNonRepeatingChar(String string) {

        Map<Character, Integer> map = new LinkedHashMap<>();
        for (char i : string.toCharArray()) {
            map.put(i, map.getOrDefault(i, 0) + 1);
        }
        return map.entrySet().stream().filter(a -> a.getValue() <= 1).findFirst().map(a -> a.getKey()).orElse(null);
    }

    public static List<List<String>> groupAnagrams(String[] stringGroup) {
        Map<String, List<String>> map = new LinkedHashMap<>();

        for (String string : stringGroup) {
            char[]          charArray = string.toCharArray();
            List<Character> charList  = new ArrayList<>();
            for (char ch : charArray) {
                charList.add(ch);
            }
            charList.sort((o1, o2) -> (o1 < o2) ? -1 : 1);
            String sorted = charList.stream().map(Object::toString).collect(Collectors.joining());

            List<String> anagramList = map.getOrDefault(sorted, new ArrayList<>());
            anagramList.add(string);

            map.put(sorted, anagramList);
        }

        return map.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
    }

    public static int[] twoSum(int[] intArr, int target) {
        for (int i = 0; i < intArr.length; i++) {
            int main = intArr[i];

            for (int j = 0; j < intArr.length; j++) {
                if (i != j) {
                    int sub = intArr[j];
                    if (main + sub == target) return new int[]{i, j};
                }
            }
        }
        return new int[]{};
    }

    public static int[] twoSum2(int[] intArr, int target) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < intArr.length; i++) {
            int main       = intArr[i];
            int complement = target - main;

            if (map.containsKey(complement)) return new int[]{map.get(complement), i};
            map.put(main, i);
        }
        return new int[]{};
    }

    public static int[] subArray(int[] intArr, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);

        int currentSum = 0;

        for (int i = 0; i < intArr.length; i++) {
            currentSum += intArr[i];

            if (map.containsKey(currentSum - target)) return new int[]{map.get(currentSum - target) + 1, i};
            map.put(currentSum, i);
        }
        return new int[]{};
    }

    public boolean hasUniqueChars(String string) {
        char[]         chArr = string.toCharArray();
        Set<Character> set   = new HashSet<Character>();
        for (char ch : chArr) {
            set.add(ch);
        }
        return chArr.length == set.size();
    }

    public static List<int[]> findPairs(int[] arr1, int[] arr2, int target) {
        Set<Integer> set = new HashSet<>();
        for (int i : arr1) {
            set.add(i);
        }

        List<int[]> list = new ArrayList<>();
        for (int i : arr2) {
            int diff = target - i;
            if (set.contains(diff)) list.add(new int[]{diff, i});
        }
        return list;
    }


    public static int longestConsecutiveSequence(int[] arr) {
        Arrays.sort(arr);
        int result      = 0;
        int tempCounter = 1;
        for (int i = 0; i < arr.length; i++) {
            if (i == arr.length - 1) {
                if (tempCounter > result) result = tempCounter;
            } else if (arr[i] == arr[i + 1]) ;
            else if (arr[i] == arr[i + 1] - 1) tempCounter++;
            else {
                if (tempCounter > result) result = tempCounter;
                tempCounter = 1;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        HashTable hashTable = new HashTable(7);
//        hashTable.set("nails", 100);
//        hashTable.set("tile", 50);
//        hashTable.set("bolts", 999);
//        hashTable.set("screws", 190);
//        System.out.println(hashTable.get("soap"));
//        System.out.println(hashTable.get("bolts"));
//        List<String> keys = hashTable.keys();

//        System.out.println(hashTable.itemInCommon(new int[]{1, 3, 5}, new int[]{2, 4, 5}));


//        System.out.println(hashTable.findDuplicates(new int[]{}));
//        System.out.println(hashTable.firstNonRepeatingChar("hheelloo"));
//        System.out.println(hashTable.groupAnagrams(new String[]{"eat", "tea", "tan", "ate", "nat", "bat"}));
        System.out.println(hashTable.longestConsecutiveSequence(new int[]{1, 2, 2, 3, 4}));


    }
}

