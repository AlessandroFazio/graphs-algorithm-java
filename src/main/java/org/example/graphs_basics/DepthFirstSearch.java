package org.example.graphs_basics;

import org.example.graphs_basics.commons.GenerateUndirectedUnweightedIntGraph;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import java.util.List;

public class DepthFirstSearch {
    public static void run() {
        int numVertices = 100;
        int numEdges = 1000;
        Graph<Integer, DefaultEdge> graph =
                GenerateUndirectedUnweightedIntGraph.generate(numVertices, numEdges);

        int start = 0;
        boolean[] visitedList = new boolean[numVertices];

        dfs(start, visitedList, graph);

        int notVisitedVertices = 0;
        for(boolean visited: visitedList) {
            if(!visited){
                notVisitedVertices++;
            }
        }
        System.out.println("All vertices have been visited: " + (notVisitedVertices == 0));
    }

    public static void dfs(
            int vertexId,
            boolean[] visitedList,
            Graph<Integer, DefaultEdge> graph
    ) {
        if(visitedList[vertexId]) {
            return;
        }

        visitedList[vertexId] = true;
        List<Integer> neighbors = Graphs.neighborListOf(graph, vertexId);
        for(int next: neighbors) {
            dfs(next, visitedList, graph);
        }
    }
}
