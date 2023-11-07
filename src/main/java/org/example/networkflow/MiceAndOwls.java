package org.example.networkflow;

import org.example.networkflow.FordFulkerson.FordFulkersonDfsSolver;
import org.example.networkflow.FordFulkerson.NetworkFlowSolverBase;
import java.awt.geom.Point2D;

public class MiceAndOwls {

    static class Mouse {
        Point2D point;
        public Mouse(int x, int y) {
            this.point = new Point2D.Double(x, y);
        }
    }

    static class Hole {
        int capacity;
        Point2D point;
        public Hole(int x, int y, int capacity) {
            this.point = new Point2D.Double(x, y);
            this.capacity = capacity;
        }
    }

    public static void run() {
        Mouse[] mice = {
                new Mouse(1, 0),
                new Mouse(0, 1),
                new Mouse(8, 1),
                new Mouse(12, 0),
                new Mouse(12, 4),
                new Mouse(15, 5),
        };
        Hole[] holes = {
                new Hole(1, 1, 1),
                new Hole(10, 2, 2),
                new Hole(14, 5, 1),
        };
        solve(mice, holes, /* radius = */ 3);
    }

    static void solve(Mouse[] mice, Hole[] holes, int radius) {
        final int M = mice.length;
        final int H = holes.length;

        final int N = M + H + 2;
        final int S = N - 1;
        final int T = N - 2;

        NetworkFlowSolverBase solver;
        solver = new FordFulkersonDfsSolver(N, S, T);

        for(int i=0; i < M; i++) {
            solver.addEdge(S, i, 1);
        }

        for(int i=0; i < M; i++) {
            Point2D mouse = mice[i].point;
            for(int j=0; j < H; j++) {
                Point2D hole = holes[j].point;
                if(mouse.distance(hole) <= radius) {
                    solver.addEdge(i, M+j, 1);
                }
            }
        }

        for(int i=0; i < M; i++) {
            solver.addEdge(S, i, 1);
        }

        for(int j=0; j < H; j++) {
            solver.addEdge(M+j, T, holes[j].capacity);
        }

        System.out.println("Number of safe mice: " + solver.getMaxFlow());
    }
}
