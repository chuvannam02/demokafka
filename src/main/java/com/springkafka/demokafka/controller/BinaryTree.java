package com.springkafka.demokafka.controller;

import com.springkafka.demokafka.entity.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * @Project: demokafka
 * @Package: com.springkafka.demokafka.controller  *
 * @Author: ChuVanNam
 * @Date: 8/31/2025
 * @Time: 3:34 PM
 */

public class BinaryTree {
    public static void main(String[] args) {
        // Initialize and allocate memory for tree nodes
        Node firstNode = new Node(2);
        Node secondNode = new Node(3);
        Node thirdNode = new Node(4);
        Node fourthNode = new Node(5);

        firstNode.setLeft(secondNode);  // 2's left child is 3
        firstNode.setRight(thirdNode); // 2's right child is 4
        secondNode.setLeft(fourthNode); // 3's left child is 5

        System.out.println("Pre-order traversal of binary tree is:");
        preOrder(firstNode); // Output: 2 3 5 4

        System.out.println("\nIn-order traversal of binary tree is:");
        inOrder(firstNode); // Output: 5 3 2 4

        System.out.println("\nPost-order traversal of binary tree is:");
        postOrder(firstNode); // Output: 5 3 4 2

        System.out.println("\nLevel-order traversal of binary tree is:");
        levelOrder(firstNode); // Output: 2 3 4 5

        int[] nums = {1, 1, 1, 2, 2, 3};
        int k = 2;
        System.out.println("\nTop K frequent elements are:");
        List<Integer> topK = topKFrequentElement(nums, k);
        System.out.println(topK); // Output: [1, 2]

        int[] arr = {-10, 1, 5, 50, 10, 2, 30};
        int m = 3;
        System.out.println("\nMax sum of subarray of size k is:");
        int maxSum = maxSumOfSubArray(arr, m);
        System.out.println(maxSum); // Output: 92 (50 + 10 + 30)
    }

    public static void preOrder(Node node) {
        if (node == null) return;
        System.out.print(node.getData() + " ");
        preOrder(node.getLeft());
        preOrder(node.getRight());
    }

    public static void inOrder(Node node) {
        if (node == null) return;
        inOrder(node.getLeft());
        System.out.print(node.getData() + " ");
        inOrder(node.getRight());
    }

    public static void postOrder(Node node) {
        if (node == null) return;
        postOrder(node.getLeft());
        postOrder(node.getRight());
        System.out.print(node.getData() + " ");
    }

    // BFS (Level Order Traversal)
    public static void levelOrder(Node root) {
        if (root == null) return;
        java.util.Queue<Node> queue = new java.util.LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            System.out.print(current.getData() + " ");
            if (current.getLeft() != null) queue.add(current.getLeft());
            if (current.getRight() != null) queue.add(current.getRight());
        }
    }

    public static int height(Node node) {
        if (node == null) return 0;
        int leftHeight = height(node.getLeft());
        int rightHeight = height(node.getRight());
        return Math.max(leftHeight, rightHeight) + 1;
    }

    public static int size(Node node) {
        if (node == null) return 0;
        return size(node.getLeft()) + size(node.getRight()) + 1;
    }

    public static boolean search(Node node, int key) {
        if (node == null) return false;
        if (node.getData() == key) return true;
        return search(node.getLeft(), key) || search(node.getRight(), key);
    }

    public static int countLeaves(Node node) {
        if (node == null) return 0;
        if (node.getLeft() == null && node.getRight() == null) return 1;
        return countLeaves(node.getLeft()) + countLeaves(node.getRight());
    }

    public static int countFullNodes(Node node) {
        if (node == null) return 0;
        int count = 0;
        if (node.getLeft() != null && node.getRight() != null) count = 1;
        return count + countFullNodes(node.getLeft()) + countFullNodes(node.getRight());
    }

    public static int countHalfNodes(Node node) {
        if (node == null) return 0;
        int count = 0;
        if ((node.getLeft() == null) != (node.getRight() == null)) count = 1; // XOR condition
        return count + countHalfNodes(node.getLeft()) + countHalfNodes(node.getRight());
    }

    public static List<Integer> topKFrequentElement(int[] nums, int k) {
        Map<Integer, Integer> frequentElement = new HashMap<>();
        // Xây hashmap đếm tần suất xuất hiện của từng phần tử
        // => O(n)
        for (int num : nums) {
            frequentElement.put(num, frequentElement.getOrDefault(num, 0) + 1);
        }

        PriorityQueue<Integer> topK = new PriorityQueue<>(
                (a, b) -> frequentElement.get(a) - frequentElement.get(b)
        );

        // Duyệt qua các phần tử trong hashmap và duy trì một heap kích thước k
        // => O(n log k)
        // duyệt n phần tử, mỗi lần thêm vào heap mất log k
        // => O(n) + O(n log k) = O(n log k)
        // => O(n) nếu k << n
        for (int num : frequentElement.keySet()) {
            topK.add(num);
            if (topK.size() > k) {
                topK.poll();
            }
        }

        List<Integer> result = new ArrayList<>(topK);
        // Sắp xếp giảm dần theo tần suất
        result.sort((a, b) -> frequentElement.get(b) - frequentElement.get(a));
        return result;
    }

//    public static int maxSumOfSubArray(int[] arr, int k) {
//        int n = arr.length;
//        if (arr == null || arr.length == 0 || k <=0 || k > n) return 0;
//        int windowSum = 0;
//        for (int i = 0; i < n-k; i++) {
//            int maxSubArray = 0;
//            for (int j = i; j < n - k + 1; j++) {
//                maxSubArray += arr[j];
//            }
//            windowSum = Math.max(windowSum, maxSubArray);
//        }
//
//        return windowSum;
//    }

    public static int maxSumOfSubArray(int[] arr, int k) {
        int n = arr.length;
        if (arr == null || arr.length == 0 || k <=0 || k > n) return 0;

        // Tính tổng của cửa sổ đầu tiên
        int windowSum = 0;
        for (int i = 0; i < k; i++) {
            windowSum += arr[i];
        }

        for (int j = 1; j < n -k; j++) {
            windowSum = Math.max(windowSum, windowSum - arr[j - 1] + arr[j + k - 1]);
        }

        return windowSum;
    }

}
