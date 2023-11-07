package org.example.trees.commons;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GenereateIntTreeGraph {
    public static TreeGraph generate(){
        int numVertices = 10;
        LinkedList<TreeNode>[] adjacencyList = new LinkedList[numVertices];

        for (int i = 0; i < numVertices; i++) {
            adjacencyList[i] = new LinkedList<>();
        }

        // Adding edges to create a tree
        adjacencyList[0].add(new TreeNode(1));
        adjacencyList[0].add(new TreeNode(2));
        adjacencyList[1].add(new TreeNode(0));
        adjacencyList[1].add(new TreeNode(3));
        adjacencyList[1].add(new TreeNode(4));
        adjacencyList[2].add(new TreeNode(0));
        adjacencyList[2].add(new TreeNode(5));
        adjacencyList[2].add(new TreeNode(6));
        adjacencyList[3].add(new TreeNode(1));
        adjacencyList[3].add(new TreeNode(7));
        adjacencyList[4].add(new TreeNode(1));
        adjacencyList[4].add(new TreeNode(8));
        adjacencyList[5].add(new TreeNode(2));
        adjacencyList[5].add(new TreeNode(9));
        adjacencyList[6].add(new TreeNode(2));
        adjacencyList[7].add(new TreeNode(3));
        adjacencyList[8].add(new TreeNode(4));
        adjacencyList[9].add(new TreeNode(5));

        return new TreeGraph(adjacencyList);
    }
}
