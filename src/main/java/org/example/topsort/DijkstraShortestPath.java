package org.example.topsort;

import org.example.topsort.commons.Edge;
import org.example.topsort.commons.MinIndexedDHeap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
// This implementation of the Dijkstra's algorithm uses the following optimizations:
// - indexed priority queue for storing key-value pairs (node index, distance)
// - D-ary Heap as the backing DataStructure for the Indexed PriorityQueue
public class DijkstraShortestPath {
    private final int n;

    private int edgeCount;
    private double[] dist;
    private Integer[] prev;
    private List<List<Edge>> graph;

    public DijkstraShortestPath(int n){
        this.n = n;
        createEmptyGraph();
    }

    private void createEmptyGraph(){
        graph = new ArrayList<>(n);
        for(int i=0; i<n; i++) graph.add(new ArrayList<>());
    }

    public void addEdge(int from, int to, int cost){
        edgeCount++;
        graph.get(from).add(new Edge(to, cost));
    }

    public List<List<Edge>> getGraph() {
        return graph;
    }

    public double dijkstra(int start, int end){
        int degree = edgeCount / n;
        MinIndexedDHeap<Double> indexedPQ = new MinIndexedDHeap<>(degree, n);
        indexedPQ.insert(start, 0.0);

        dist = new double[n];
        Arrays.fill(dist, Double.POSITIVE_INFINITY);
        dist[start] = 0.0;

        boolean[] visited = new boolean[n];
        prev = new Integer[n];

        while(!indexedPQ.isEmpty()){
            int nodeId = indexedPQ.peekMinKeyIndex();

            visited[nodeId] = true;
            double minValue = indexedPQ.pollMinValue();

            if(minValue > dist[nodeId]) continue;

            for(Edge edge: graph.get(nodeId)){
                if(visited[edge.to]) continue;

                double newDist = dist[nodeId] + edge.cost;
                if(newDist < dist[edge.to]){
                    prev[edge.to] = nodeId;
                    dist[edge.to] = newDist;
                   if (indexedPQ.contains(edge.to))
                       indexedPQ.decrease(edge.to, newDist);
                   else
                       indexedPQ.insert(edge.to, newDist);
                }
            }
            if(nodeId == end) return dist[end];
        }
        return Double.POSITIVE_INFINITY;
    }

    public List<Integer> reconstructPath(int start, int end){
        if(end < 0 || end > n)
            throw new IllegalArgumentException("Invalid node index");
        if(start < 0 || start > n)
            throw new IllegalArgumentException("Invalid node index");

        List<Integer> path = new ArrayList<>();
        double dist = dijkstra(start, end);
        if(dist == Double.POSITIVE_INFINITY) return path;
        for(Integer at = end; at != null; at=prev[at])
            path.add(at);
        Collections.reverse(path);
        return path;
    }
}

