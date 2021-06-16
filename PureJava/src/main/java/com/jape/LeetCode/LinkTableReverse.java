package com.jape.LeetCode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 链表反转
 */
public class LinkTableReverse {
    public static void main(String[] args) {

        //头插
        Node<Integer> node1 = new Node<>(5, null);
        Node<Integer> node2 = new Node<>(4, node1);
        Node<Integer> node3 = new Node<>(3, node2);
        Node<Integer> node4 = new Node<>(2, node3);
        Node<Integer> node5 = new Node<>(1, node4);
        Node<Integer> header = new Node<>(null, node5);
        printLinkTable(header);

        doReverse(header);
        printLinkTable(header);

        header.setNext(doRecursionRev(header.getNext()));
        printLinkTable(header);


    }

    /**
     * 打印链表
     */
    private static <T> void printLinkTable(Node<T> header){
        Node<T> temp = header;
        while (temp.hasNext()) {
            temp = temp.getNext();
            System.err.print(temp.getValue() + "\t");
        }
        System.err.println();
    }

    /**
     * while
     */
    private static <T> void doReverse(Node<T> header) {
        if (header == null || !header.hasNext() || header.getNext().getNext() == null) {
            return;
        }

        Node<T> first = header.getNext();
        Node<T> prev = first;
        Node<T> curr = null;
        while (first.hasNext()) {
            curr = first.getNext();
            first.setNext(curr.getNext());
            curr.setNext(prev);
            prev = curr;
        }
        header.setNext(curr);
    }


    /**
     * 递归 Recursion，利用递归的栈原理
     */
    private static <T> Node<T> doRecursionRev(Node<T> curr){
        if(curr == null || curr.getNext() == null){
            return curr;
        }
        Node<T> next = curr.getNext();
        Node<T> tail = doRecursionRev(next);
        next.setNext(curr);
        curr.setNext(null);
        //返回旧的尾巴，实际上是未来的头
        return tail;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Node<T> {
        T value;
        Node<T> next;

        public boolean hasNext() {
            return next != null;
        }
    }

}
