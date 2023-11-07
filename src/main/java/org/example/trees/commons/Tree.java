package org.example.trees.commons;

public class Tree {
    private TreeNode root;

    public Tree(){}

    public Tree(TreeNode root) {
        this.root = root;
    }

    public TreeNode getRoot() {
        return root;
    }
}
