package org.example.trees;

import org.example.trees.commons.GenerateIntTree;
import org.example.trees.commons.Tree;
import org.example.trees.commons.TreeNode;

import java.util.Arrays;

public class FindTreeHeight {
    public static void run(){
        Tree tree = GenerateIntTree.generate();
        int treeHeight = treeHeight(tree.getRoot());
        System.out.println("Tree height is: " + treeHeight);
    }

    public static int treeHeight(TreeNode node) {
        if(node == null) {
            return -1;
        }
        if(!node.hasChildren()){
            return 0;
        }
        int i = 0;
        int[] childrenHeight = new int[node.getChildren().length];
        for(TreeNode childNode: node.getChildren()){
            childrenHeight[i++] = treeHeight(childNode);
        }
        return Arrays.stream(childrenHeight).max().getAsInt() + 1;
    }
}
