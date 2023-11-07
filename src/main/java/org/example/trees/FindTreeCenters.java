package org.example.trees;

import org.example.trees.commons.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

// TODO: Fix bugs
public class FindTreeCenters {
    public static void run() {
        TreeGraph tree = GenereateIntTreeGraph.generate();
        List<Integer> treeCenters = treeCenter(tree);

        System.out.println("Tree centers are: " + treeCenters.toString());
    }

    public static List<Integer> treeCenter(TreeGraph tree) {
        LinkedList<TreeNode>[] adjacency = tree.getAdjacencyList();
        int[] nodeDegrees = new int[adjacency.length];
        int numVertices = adjacency.length;
        if(numVertices == 0) {
            return null;
        }
        List<Integer> leaves = new ArrayList<>();
        for(int i=0; i < numVertices; i++){
            nodeDegrees[i] = adjacency[i].size();
            if(nodeDegrees[i] == 1 || nodeDegrees[i] == 0){
                nodeDegrees[i] = 0;
                leaves.add(i);
            }
        }
        int count = leaves.size();
        while(count < numVertices){
            List<Integer> newLeaves = new ArrayList<>();
            for(int leafId: leaves) {
                for(TreeNode neighbor: adjacency[leafId]){
                    int neighborId = neighbor.getData();
                    nodeDegrees[neighborId] = nodeDegrees[neighborId] -1;
                    if(nodeDegrees[neighborId] == 1) {
                        newLeaves.add(neighborId);
                    }
                }
                nodeDegrees[leafId] = 0;
            }
            count += newLeaves.size();
            leaves = newLeaves;
        }
        return leaves;
    }
}
