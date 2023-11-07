package org.example.graphs_basics;

import org.example.graphs_basics.commons.GenerateUndirectedUnweightedIntGraph;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// TODO: Fix because it's broken
public class BreathFirstSearchShortestPath {
    public static void run() {
        int numVertices = 10;
        int numEdges = 25;
        Graph<Integer, DefaultEdge> graph =
                GenerateUndirectedUnweightedIntGraph.generate(numVertices, numEdges);

        int start = 0;
        int end = 8;
        boolean[] visitedList = new boolean[numVertices];
        Integer[] prevList = new Integer[numVertices];
        bfs(start, end, visitedList, prevList, graph);
    }

    public static List<Integer> bfs(
        int startVertex,
        int endVertex,
        boolean[] visitedList,
        Integer[] prevList,
        Graph<Integer, DefaultEdge> graph
    ) {
        prevList = solve(startVertex, visitedList, prevList, graph);
        return reconstructPath(startVertex, endVertex, prevList);
    }

    public static Integer[] solve(
            int startVertex,
            boolean[] visitedList,
            Integer[] prevList,
            Graph<Integer, DefaultEdge> graph
    ) {
        Queue<Integer> queue = new LinkedList<Integer>();
        queue.offer(startVertex);

        visitedList[startVertex] = true;

        while(!queue.isEmpty()){
            int node = queue.poll();

            List<Integer> neighbors = Graphs.neighborListOf(graph, node);
            for(Integer next: neighbors) {
                if(!visitedList[next]) {
                    queue.offer(next);
                }
                visitedList[next] = true;
                prevList[next] = node;
            }
        }
        return prevList;
    }

    public static List<Integer> reconstructPath(
            int startVertex,
            int endVertex,
            Integer[] prevList
    ) {

        LinkedList<Integer> path = new LinkedList<Integer>();
        for(Integer at = endVertex; at != null; at=prevList[at]) {
            path.addFirst(at);
        }
        return path;
    }
}
