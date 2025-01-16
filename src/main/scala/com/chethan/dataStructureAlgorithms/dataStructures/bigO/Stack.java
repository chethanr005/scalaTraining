package com.chethan.dataStructureAlgorithms.dataStructures.bigO;

/**
 * Created by Chethan on Dec 03, 2024.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Stack<T> {

    private ArrayList<T> stackList = new ArrayList<>();

    public ArrayList<T> getStackList() {
        return stackList;
    }

    public void printStack() {
        for (int i = stackList.size() - 1; i >= 0; i--) {
            System.out.println(stackList.get(i));
        }
    }

    public boolean isEmpty() {
        return stackList.size() == 0;
    }

    public T peek() {
        if (isEmpty()) {
            return null;
        } else {
            return stackList.get(stackList.size() - 1);
        }
    }

    public int size() {
        return stackList.size();
    }

    public void push(T value) {
        stackList.add(value);
    }

    public T pop() {
        if (stackList.isEmpty()) return null;
        return stackList.remove(stackList.size() - 1);
    }

    public String reverseString(String string) {
        Stack<Character> stack = new Stack<>();
        for (char c : string.toCharArray()) {
            stack.push(c);
        }
        String rs = "";
        while (!stack.isEmpty()) {
            rs += stack.pop();
        }
        return rs;
    }

    public static boolean isBalancedParentheses(String parentheses) {
        Stack<Character>          stack = new Stack<>();
        Map<Character, Character> map   = new HashMap<>();
        map.put(')', '(');
        map.put('}', '{');
        map.put(']', '[');
        for (char c : parentheses.toCharArray()) {
            if (map.containsKey(c)) {
                Character ach = map.get(c);
                if (stack.isEmpty()) return false;
                Character ch = stack.pop();
                if (!ch.equals(ach)) return false;
            } else stack.push(c);
        }
        if (!stack.isEmpty()) return false;
        return true;
    }

    public static void sortStack(Stack<Integer> stack) {

        Stack<Integer> sortedStack = new Stack<>();

        while (!stack.isEmpty()) {
            int n = stack.pop();

            if (sortedStack.isEmpty()) sortedStack.push(n);
            else {
                if (n < sortedStack.peek()) {
                    int top = sortedStack.pop();
                    stack.push(top);
                    stack.push(n);
                } else sortedStack.push(n);
            }
        }

        while (!sortedStack.isEmpty()) {
            stack.push(sortedStack.pop());
        }
    }

    public class MyQueue {
        private Stack<Integer> stack1;
        private Stack<Integer> stack2;

        public MyQueue() {
            stack1 = new Stack<>();
            stack2 = new Stack<>();
        }

        // WRITE THE ENQUEUE METHOD HERE //
        //                               //
        //                               //
        //                               //
        //                               //
        ///////////////////////////////////

        public int peek() {
            return stack1.peek();
        }

        public boolean isEmpty() {
            return stack1.isEmpty();
        }


        public void enqueue(int value) {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
            stack1.push(value);
            while (!stack2.isEmpty()) {
                stack1.push(stack2.pop());
            }
        }

        public Integer dequeue() {
            if (stack1.isEmpty()) return null;
            else return stack1.pop();

        }


//        public static void main(String[] args) {
//            System.out.println(isBalancedParentheses("}{"));
//
//        }

    }
}