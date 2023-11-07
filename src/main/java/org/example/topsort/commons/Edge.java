package org.example.topsort.commons;

public class Edge {
    public int to;
    public int from;
    public double cost;
    public Edge(int to, double cost){
        this.to = to;
        this.cost = cost;
    }

    public Edge( int from, int to, double cost){
        this.from = from;
        this.to = to;
        this.cost = cost;
    }
}
