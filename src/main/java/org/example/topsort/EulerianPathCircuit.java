package org.example.topsort;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.abs;

public class EulerianPathCircuit{
    private final int n;
    private int edgeCount;
    private int[] in, out;
    private LinkedList<Integer> path;
    private List<List<Integer>> graph;

    public EulerianPathCircuit(List<List<Integer>> graph) {
        if(graph == null)
            throw new IllegalArgumentException("Graph cannot be null");
        n = graph.size();
        this.graph = graph;
        path = new LinkedList<>();
    }

    public int[] getEulerianPath() {
        setUp();
        if(edgeCount == 0) return null;

        if(!graphHasEulerianPath()) return null;
        dfs(findStartNode());

        if(path.size() != edgeCount+1) return null;

        int[] soln = new int[edgeCount+1];
        for(int i=0; !path.isEmpty(); i++)
            soln[i] = path.removeFirst();
        return soln;
    }

    private void setUp() {
        in = new int[n];
        out = new int[n];

        edgeCount = 0;

        for(int from=0; from < n; from++) {
            for(int to: graph.get(from)) {
                in[to]++;
                out[from]++;
                edgeCount++;
            }
        }
    }

    private boolean graphHasEulerianPath() {
        int startNodes = 0, endNodes = 0;
        for(int i=0; i < n; i++) {
            if(abs(in[i] - out[i]) > 1) return false;
            else if (out[i] - in[i] == 1) startNodes++;
            else if (in[i] - out[i] == 1) endNodes++;
        }
        return (startNodes == 0 && endNodes == 0) ||
                (startNodes == 1 && endNodes == 1);
    }

    private int findStartNode() {
        int start = 0;
        for(int i=0; i < n; i++) {
            if (out[i] - in[i] == 1) return i;

            if(out[i] > 0) start=i;
        }
        return start;
    }

    private void dfs(int at) {
        while (out[at] != 0) {
            int next = graph.get(at).get(--out[at]);
            dfs(next);
        }
        path.addFirst(at);
    }

    public static List<List<Integer>> initializeEmptyGraph(int n) {
        List<List<Integer>> graph = new ArrayList<>(n);
        for(int i=0; i <n; i++)
            graph.add(i, new ArrayList<>());
        return graph;
    }

    public static void addDirectedEdge(List<List<Integer>> graph, int from, int to) {
        graph.get(from).add(to);
    }

    public static void run() {
        int n = 7;
        List<List<Integer>> graph = initializeEmptyGraph(n);

        addDirectedEdge(graph, 1, 2);
        addDirectedEdge(graph, 1, 3);
        addDirectedEdge(graph, 2, 2);
        addDirectedEdge(graph, 2, 4);
        addDirectedEdge(graph, 3, 1);
        addDirectedEdge(graph, 1, 2);
        addDirectedEdge(graph, 1, 2);

    }
}