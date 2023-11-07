package org.example.topsort;

import org.example.topsort.commons.generateDAGs;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;

public class DfsTopSort {
    public static void run(){
        int numVertices = 20;
        int numEdges = 80;
        boolean weigthed = false;
        Graph<Integer, DefaultEdge> graph = generateDAGs.generate(numVertices, numEdges, weigthed);

        int[] ordering = topSort(graph);

        System.out.println("Topological sorted arrray: ");
        for(int i=0; i < ordering.length; i++){
            System.out.print(ordering[i] + ", ");
        }
    }
    public static int[] topSort(Graph graph){
        int numNodes = graph.vertexSet().size();
        int[] ordering = new int[numNodes];
        boolean[] visited = new boolean[numNodes];
        int i = numNodes -1;

        for(int at=0; at < numNodes; at++){
            if(!visited[at]){
               i = dfs(at, visited, i, graph, ordering);
            }
        }
        return ordering;
    }
    private static int dfs(
            int start, boolean[] visited, int index,
            Graph<Integer, DefaultEdge> graph, int[] ordering
    ){
        visited[start] = true;
        for(int node: Graphs.neighborListOf(graph, start)){
            if(!visited[node]){
                index = dfs(node, visited, index, graph, ordering);
            }
        }
        ordering[index] = start;
        return index -1;
    }
}
