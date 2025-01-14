package com.chethan.dataStructureAlgorithms.dataStructures.bigO;

/**
 * Created by Chethan on Nov 27, 2024.
 */

public class DoublyLinkedList {
    int  length;
    Node head;
    Node tail;

    class Node {
        int  value;
        Node next;
        Node prev;

        Node(int value) {
            this.value = value;
        }
    }


    public DoublyLinkedList(int value) {
        Node newNode = new Node(value);
        head = newNode;
        tail = newNode;
        length = 1;
    }

    public void append(int value) {
        Node newNode = new Node(value);
        if (this.head == null) this.head = newNode;

        else {
            newNode.prev = this.tail;
            this.tail.next = newNode;
        }
        this.tail = newNode;
        length++;
    }

    public void swapFirstLast() {
        if (length == 2) {
            Node temp = head;
            head = tail;
            tail = temp;
            head.prev = null;
            tail.next = null;
            head.next = tail;
            tail.prev = head;
        } else if (length > 2) {
            head.next.prev = tail;
            tail.next = head.next;
            Node tempTail = tail.prev;
            tail.prev = null;
            tempTail.next = head;
            head.prev = tempTail;
            head.next = null;
            Node tempHead = head;
            head = tail;
            tail = tempHead;
        }
    }

    public void reverse() {
        if (length == 2) swapFirstLast();
        else if (length > 2) {
            Node temp = head;
            for (int i = length - 1; i > 0; i--) {
                Node actTail = tail.prev;
                actTail.next = null;
                if (temp.prev == null) {
                    tail.next = temp;
                    temp.prev = tail;
                    head = tail;
                } else {
                    Node temp2 = temp.prev;
                    temp2.next = tail;
                    tail.prev = temp2;
                    tail.next = temp;
                    temp.prev = tail;
                }
                tail = actTail;
            }
        }
    }

    public boolean isPalindrome() {
        if (length > 1) {
            Node cHead = head;
            Node cTail = tail;
            for (int i = 0; i <= (length / 2) - 1; i++) {
                if (cHead.value != cTail.value) return false;
                cHead = cHead.next;
                cTail = cTail.prev;
            }
        }
        return true;
    }
    
    public static void main(String[] args) {
        DoublyLinkedList doublyLinkedList = new DoublyLinkedList(1);
        doublyLinkedList.append(2);
        doublyLinkedList.append(3);
        doublyLinkedList.append(4);
        doublyLinkedList.append(5);
        doublyLinkedList.append(6);
        doublyLinkedList.reverse();
        System.out.println();
//        doublyLinkedList

    }
}
