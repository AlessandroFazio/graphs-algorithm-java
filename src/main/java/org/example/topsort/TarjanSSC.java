package org.example.topsort;

import java.net.UnknownServiceException;
import java.util.*;

import static java.lang.Math.min;

public class TarjanSSC {
    private int n;
    private List<List<Integer>> graph;

    private boolean solved;
    private int sscCount, id;
    private boolean[] onStack;
    private int[] ids, low;
    private Deque<Integer> stack;

    private static final int UNVISITED = -1;

    public TarjanSSC(List<List<Integer>> graph) {
        if(graph == null) throw new IllegalArgumentException("Graph cannot be null.");
        n = graph.size();
        this.graph = graph;
    }

    public int sccCount() {
        if(!solved) solve();
        return sscCount;
    }

    public int[] getSccs() {
        if(!solved) solve();
        return low;
    }

    public void solve() {
        if(solved) return;

        ids = new int [n];
        low = new int [n];
        onStack = new boolean[n];
        stack = new ArrayDeque<>();
        Arrays.fill(ids, UNVISITED);

        for(int i=0; i < n; i++)
            if(ids[i] == UNVISITED) dfs(i);

        solved = true;
    }

    private void dfs(int at){

        stack.push(at);
        onStack[at] = true;
        low[at] = ids[at] = id++;

        for(Integer to: graph.get(at)) {
            if(ids[to] != UNVISITED) dfs(to);
            if(onStack[to]) low[at] = min(low[at], low[to]);
        }

        if(ids[at] == low[at]) {
            for(int node = stack.pop();; node = stack.pop()) {
                onStack[node] = false;
                low[node] = ids[at];
                if(node == at) break;
            }
            sscCount++;
        }
    }

    public static void run(){
        int n=8;
        List<List<Integer>> graph = createGraph(n);

        addEdge(graph, 6, 0);
        addEdge(graph, 6, 2);
        addEdge(graph, 3, 4);
        addEdge(graph, 6, 4);
        addEdge(graph, 2, 0);
        addEdge(graph, 0, 1);
        addEdge(graph, 4, 5);
        addEdge(graph, 6, 6);
        addEdge(graph, 3, 7);
        addEdge(graph, 7, 5);
        addEdge(graph, 1, 2);
        addEdge(graph, 8, 3);
        addEdge(graph, 5, 0);

        TarjanSSC solver = new TarjanSSC(graph);

        int[] sccs = solver.getSccs();
        Map<Integer, List<Integer>> multimap = new HashMap<>();
        for(int i=0; i < n; i++){
            if(!multimap.containsKey(sccs[i])){
                multimap.put(sccs[i], new ArrayList<>());
            }
            multimap.get(sccs[i]).add(i);
        }

        System.out.printf("Number of Strongly Connected Components: %d\n", solver.sscCount);
        for(List<Integer> scc: multimap.values()){
            System.out.println("Nodes: " + scc + " form a Strongly Connected Components.");
        }
    }

    public static List<List<Integer>> createGraph(int n){
        List<List<Integer>> graph = new ArrayList<>();
        for(int i=0; i < n; i++) graph.add(new ArrayList<>());
        return graph;
    }

    public static void addEdge(List<List<Integer>> graph, int from, int to) {
        graph.get(from).add(to);
        graph.get(to).add(from);
    }
}
