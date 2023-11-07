package org.example.topsort;

import org.example.topsort.commons.generateDAGs;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.Set;

public class ShortestPathDAG {
    public static void run(){
        int numVertices = 20;
        int numEdges = 80;
        boolean weighted = true;
        Graph<Integer, DefaultWeightedEdge> graph =
                generateDAGs.generate(numVertices, numEdges, weighted);
        int start = 0;
        for(Integer vertex: graph.vertexSet()) {
            start=vertex;
            break;
        }
        Double[] shortestPaths = shortestPath(graph, start);

        for(int i=0; i < shortestPaths.length; i++){
            System.out.print("Shortest path from " +  + shortestPaths[i] + ", ");
        }
    }

    public static Double[] shortestPath(
            Graph<Integer, DefaultWeightedEdge> graph, int start
    ){
        int[] topSort = DfsTopSort.topSort(graph);
        int numVertices = graph.vertexSet().size();
        Double[] distances = new Double[numVertices];
        distances[start] = 0.0;

        for(int i=0; i < numVertices; i++){
            int nodeIndex = topSort[i];

            Set<DefaultWeightedEdge> edges = graph.outgoingEdgesOf(nodeIndex);
            System.out.println(edges);
            if(edges == null){
                continue;
            }
            for(DefaultWeightedEdge edge: edges){
                double newDist =  distances[nodeIndex] + graph.getEdgeWeight(edge);
                int edgeTargetIndex = graph.getEdgeTarget(edge);
                if(distances[edgeTargetIndex] == null) {
                    distances[edgeTargetIndex] = newDist;
                }
                else {
                    distances[edgeTargetIndex] = Math.min(newDist, distances[edgeTargetIndex]);
                }
            }
        }
        return distances;
    }
}
