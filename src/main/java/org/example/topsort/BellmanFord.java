package org.example.topsort;

import org.example.topsort.commons.Edge;

import javax.sound.midi.Soundbank;
import java.util.Arrays;

public class BellmanFord {
    public static void run() {
        int E = 10, V = 9, start = 0;
        Edge[] edges = new Edge[E];
        edges[0] = new Edge(0,1,1);
        edges[1] = new Edge(1,2,1);
        edges[2] = new Edge(2,4,1);
        edges[3] = new Edge(4,3,-3);
        edges[4] = new Edge(3,2,1);
        edges[5] = new Edge(1,5,4);
        edges[6] = new Edge(1,6,4);
        edges[7] = new Edge(5,6,5);
        edges[8] = new Edge(6,7,4);
        edges[9] = new Edge(5,7,3);

        double[] d = bellmanFord(edges, V, start);

        for(int i=0; i < V; i++)
            System.out.printf("The cost of getting from node %d to %d is %.2f\n", start, i, d[i]);
    }

    public static double[] bellmanFord(Edge[] edges, int numVertices, int start){

        double[] dist = new double[numVertices];
        Arrays.fill(dist, Double.POSITIVE_INFINITY);
        dist[start] = 0.0;

        for(int i=0; i < numVertices-1; i++)
            for(Edge edge: edges)
                if(dist[edge.from] + edge.cost < dist[edge.to])
                    dist[edge.to] = dist[edge.from] + edge.cost;

        for(int i=0; i < numVertices; i++)
            for(Edge edge: edges)
                if(dist[edge.from] + edge.cost < dist[edge.to])
                    dist[edge.to] = Double.NEGATIVE_INFINITY;

        return dist;
    }
}
