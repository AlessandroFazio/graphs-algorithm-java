package org.example.trees;

import org.example.trees.commons.GenerateIntTree;
import org.example.trees.commons.Tree;
import org.example.trees.commons.TreeNode;

import java.util.List;

public class SumLeaves {
    public static void run() {
        Tree tree = GenerateIntTree.generate();
        int sumLeaves = sumLeaves(tree.getRoot());
        System.out.println("Leaves sum is: " + sumLeaves);
    }

    public static int sumLeaves(TreeNode node){
        if(node == null) {
            return 0;
        }
        if(!node.hasChildren()){
            return node.getData();
        }
        int sum = 0;

        for(TreeNode childNode: node.getChildren()){
            sum += sumLeaves(childNode);
        }
        return sum;
    }
}
