package com.springkafka.demokafka.entity;

/**
 * @Project: demokafka
 * @Package: com.springkafka.demokafka.entity  *
 * @Author: ChuVanNam
 * @Date: 8/31/2025
 * @Time: 3:33 PM
 */

public class Node {
    private Integer data;

    private Node left, right;

    public Node(Integer data) {
        this.data = data;
        left = right = null;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public Integer getData() {
        return data;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }
}
