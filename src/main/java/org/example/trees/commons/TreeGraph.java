package org.example.trees.commons;

import java.util.LinkedList;
import java.util.List;

public class TreeGraph {
    private LinkedList<TreeNode>[] adjacencyList;

    public TreeGraph(){
    }
    public TreeGraph(LinkedList<TreeNode>[] adjacencyList) {
        this.adjacencyList = adjacencyList;
    }

    public LinkedList<TreeNode>[] getAdjacencyList() {
        return adjacencyList;
    }

    public void setAdjacencyList(LinkedList<TreeNode>[] adjacencyList) {
        this.adjacencyList = adjacencyList;
    }

    public static void printTree(List<TreeNode>[] adjacencyList) {
        for (int i = 0; i < adjacencyList.length; i++) {
            System.out.print("Vertex " + i + " is connected to: ");
            for (TreeNode neighbor : adjacencyList[i]) {
                System.out.print(neighbor.getData() + " ");
            }
            System.out.println();
        }
    }
}
