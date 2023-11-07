package org.example.trees.commons;

import java.util.Objects;

public class TreeNode {
    private int data;
    private TreeNode parent;
    private TreeNode[] children;

    public TreeNode(int data) {
        this.data = data;
    }
    public TreeNode(int data, TreeNode[] children) {
        this.data = data;
        this.children = children;
    }

    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public TreeNode(int data, TreeNode parent, TreeNode[] children) {
        this.data = data;
        this.parent = parent;
        this.children = children;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public TreeNode[] getChildren() {
        return children;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TreeNode node = (TreeNode) o;
        return data == node.data && Objects.equals(children, node.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, children);
    }

    public void setChildren(TreeNode[] children) {
        this.children = children;
    }

    public int sumChildren() {
        int sum = 0;
        if(hasChildren()){
            for(TreeNode node: children){
                sum += node.data;
            }
        }
        return sum;
    }

    public void addChildren(TreeNode node){
        if (!hasChildren()) {
            this.children = new TreeNode[1];
            this.children[0] = node;
        }
        int numChildren = getChildren().length;
        TreeNode[] newChildren = new TreeNode[numChildren+1];
        newChildren[numChildren+1] = node;
        this.children = newChildren;
    }

    public boolean hasChildren(){
        return children != null;
    }
}
