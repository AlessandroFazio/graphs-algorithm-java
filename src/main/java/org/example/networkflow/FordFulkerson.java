package org.example.networkflow;

import java.util.*;

import static java.lang.Math.*;

public class FordFulkerson {
    private static class Edge {
        public int from, to;
        public Edge residual;
        public long flow;
        public final long capacity;

        public Edge(int from, int to, long capacity) {
            this.from = from;
            this.to = to;
            this.capacity = capacity;
        }

        public boolean isResidual() {
            return capacity == 0;
        }

        public long remainingCapacity() {
            return capacity - flow;
        }

        public void augment(long bottleneck) {
            flow += bottleneck;
            residual.flow -= bottleneck;
        }

        public String toString(int s, int t) {
            String u = (from == s) ? "s" : (from == t) ? "t" : String.valueOf(from);
            String v = (to == s) ? "s" : (to == t) ? "t" : String.valueOf(to);
            return String.format("Edge %s -> %s | flow = %3d | capacity =%3d | isResidual: %s",
                    u, v, flow, capacity, isResidual());
        }
    }

    public static abstract class NetworkFlowSolverBase {
        static final long INF = Long.MAX_VALUE / 2;
        final int n, s, t;

        protected int visitedToken = 1;
        protected int[] visited;
        protected boolean solved;
        protected long maxFlow;
        protected List<Edge>[] graph;

        public NetworkFlowSolverBase(int n, int s, int t) {
            this.n = n;
            this.s = s;
            this.t = t;
            initializedEmptyGraph();
            visited = new int[n];
        }

        private void initializedEmptyGraph() {
            graph = new List[n];
            for(int i=0; i < n; i++)
                graph[i] = new ArrayList<Edge>();
        }

        public void addEdge(int from, int to, long capacity) {
            if(capacity <= 0)
                throw new IllegalArgumentException("Forward edge capacity <= 0");
            Edge e1 = new Edge(from, to, capacity);
            Edge e2 = new Edge(to, from, 0);
            e1.residual = e2;
            e2.residual = e1;
            graph[from].add(e1);
            graph[to].add(e2);
        }

        public List<Edge>[] getGraph() {
            execute();
            return graph;
        }

        public long getMaxFlow() {
            execute();
            return maxFlow;
        }

        public void visit(int i) {
            visited[i] = visitedToken;
        }

        public boolean visited(int i) {
            return visited[i] == visitedToken;
        }

        public void markAllNodesAsUnvisited() {
            visitedToken++;
        }

        private void execute() {
            if(solved) return;
            solved = true;
            solve();
        }

        public abstract void solve();
    }

    public static class FordFulkersonDfsSolver extends NetworkFlowSolverBase {
        public FordFulkersonDfsSolver(int n, int s, int t) {
            super(n, s, t);
        }

        @Override
        public void solve() {
            for(long f = dfs(s, INF); f != 0; dfs(s, INF)){
                visitedToken++;
                maxFlow += f;
            }
        }

        private long dfs(int node, long flow) {
            if(node == t) return flow;

            visited[node] = visitedToken;

            List<Edge> edges = graph[node];

            for(Edge edge: edges){
                if(edge.remainingCapacity() > 0 && visited[edge.to] != visitedToken) {
                    long bottleneck = dfs(edge.to, min(flow, edge.remainingCapacity()));

                    if(bottleneck > 0) {
                        edge.augment(bottleneck);
                        return bottleneck;
                    }
                }
            }
            return 0;
        }
    }

    public static class KarpEdmondsBfsSolver extends NetworkFlowSolverBase {
        public KarpEdmondsBfsSolver(int n, int s, int t) {
            super(n, s, t);
        }

        @Override
        public void solve() {
            long flow;
            do {
                markAllNodesAsUnvisited();
                flow = bfs();
                maxFlow += flow;
            } while (flow != 0);
        }

        private long bfs() {
            Queue<Integer> queue = new ArrayDeque<>(n);
            visit(s);
            queue.offer(s);

            Edge[] prev = new Edge[n];
            while(!queue.isEmpty()){
                int node = queue.poll();
                if(node == t) break;

                for(Edge edge: graph[node]) {
                    long cap = edge.remainingCapacity();
                    if(!visited(edge.to) && cap > 0) {
                        visit(edge.to);
                        prev[edge.to] = edge;
                        queue.offer(edge.to);
                    }
                }
            }

            if(prev[t] == null) return 0;

            long bottleneck = Long.MAX_VALUE;
            for(Edge edge=prev[t]; edge != null; edge=prev[edge.from]){
                bottleneck = min(bottleneck, edge.remainingCapacity());
            }

            for(Edge edge = prev[t]; edge != null; edge=prev[edge.from]) {
                edge.augment(bottleneck);
            }

            return bottleneck;
        }
    }

    public static class FordFulkersonDfsCapacityScaling extends NetworkFlowSolverBase {
        private long delta;
        public FordFulkersonDfsCapacityScaling(int n, int s, int t) {
            super(n, s, t);
        }

        @Override
        public void addEdge(int from, int to, long capacity) {
            super.addEdge(from, to, capacity);
            delta = max(delta, capacity);
        }

        @Override
        public void solve() {
            delta = Long.highestOneBit(delta);

            for(long f = 0; delta > 0; delta /=2) {
                do {
                    markAllNodesAsUnvisited();
                    f = dfs(s, INF);
                    maxFlow += f;
                } while (f != 0);
            }
        }

        private long dfs(int node, long flow) {
            if(node == t) return flow;
            visit(node);

            for(Edge edge: graph[node]){
                long cap = edge.remainingCapacity();
                if(!visited(edge.to) && cap >= delta) {
                    long bottleneck = dfs(edge.to, min(flow, cap));

                    if(bottleneck > 0) {
                        edge.augment(bottleneck);
                        return bottleneck;
                    }
                }
            }
            return 0;
        }
    }

    public static class DinicsSolver extends NetworkFlowSolverBase{
        private int[] level;
        public DinicsSolver(int n, int s, int t) {
            super(n, s, t);
            level = new int[n];
        }

        @Override
        public void solve() {
            int[] next = new int[n];

            while(bfs()) {
                Arrays.fill(next, 0);
                for(long f=dfs(s, next, INF); f != 0; f=dfs(s, next, INF)) {
                    maxFlow += f;
                }
            }
        }

        private boolean bfs() {
            Arrays.fill(level, -1);
            Deque<Integer> queue = new ArrayDeque<>(n);
            queue.offer(s);

            level[s] = 0;
            while(!queue.isEmpty()) {
                int node = queue.poll();
                for(Edge edge: graph[node]) {
                    long cap = edge.remainingCapacity();
                    if(cap > 0 && level[edge.to] == -1) {
                        queue.offer(edge.to);
                        level[edge.to] = level[node] + 1;
                    }

                }
            }
            return level[t] != -1;
        }

        private long dfs(int at, int[] next, long flow) {
            if(at == t) return flow;
            final int numEdges = graph[at].size();

            for(;next[at] < numEdges; next[at]++) {
                Edge edge = graph[at].get(next[at]);
                long cap = edge.remainingCapacity();
                if(level[edge.to] == level[at] +1 && cap > 0) {

                    long bottleneck = dfs(edge.to, next, min(flow, cap));
                    if(bottleneck > 0) {
                        edge.augment(bottleneck);
                        return bottleneck;
                    }
                }
            }
            return 0;
        }
    }

    public static void run(String augment_method) {
        int n = 12;

        int s = n-2;
        int t = n-1;

        NetworkFlowSolverBase solver = switch (augment_method) {
            case "dfs" -> new FordFulkersonDfsSolver(n, s, t);
            case "dfs-cap-scaling" -> new FordFulkersonDfsCapacityScaling(n, s, t);
            case "karp-edmonds" -> new KarpEdmondsBfsSolver(n, s, t);
            case "dinics" -> new DinicsSolver(n, s, t);
            default ->
                    throw new IllegalArgumentException("This method is not currently supported. Got: " + augment_method);
        };

        // Edges from source
        solver.addEdge(s, 0, 10);
        solver.addEdge(s, 1, 5);
        solver.addEdge(s, 2, 10);

        // Middle Edges
        solver.addEdge(0, 3, 10);
        solver.addEdge(1, 2, 10);
        solver.addEdge(2, 5, 15);
        solver.addEdge(3, 1, 2);
        solver.addEdge(3, 6, 15);
        solver.addEdge(4, 1, 15);
        solver.addEdge(4, 3, 3);
        solver.addEdge(5, 4, 4);
        solver.addEdge(5, 8, 10);
        solver.addEdge(6, 7, 10);
        solver.addEdge(7, 4, 10);
        solver.addEdge(7, 5, 7);

        // Edges to sink
        solver.addEdge(6, t, 15);
        solver.addEdge(8, t, 10);

        System.out.printf("Maximum Flow is:%d\n", solver.getMaxFlow());

        List<Edge>[] resultGraph = solver.getGraph();

        for(List<Edge> edges: resultGraph)
            for(Edge e: edges)
                System.out.println(e.toString(s, t));
    }
}
