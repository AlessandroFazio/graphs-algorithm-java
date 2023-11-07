package org.example.graphs_basics.commons;

import org.jgrapht.Graph;
import org.jgrapht.generate.GnmRandomGraphGenerator;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.util.SupplierUtil;

import java.util.function.Supplier;

public class GenerateUndirectedUnweightedIntGraph {
    public static Graph<Integer, DefaultEdge> generate(int numVertices, int numEdges) {
        Supplier<Integer> vSupplier = new Supplier<Integer>() {
            private int id;
            @Override
            public Integer get() {
                return id++;
            }
        };

        Graph<Integer, DefaultEdge> graph =
                new SimpleGraph<>(vSupplier,
                        SupplierUtil.createDefaultEdgeSupplier(),
                        false);

        GnmRandomGraphGenerator<Integer, DefaultEdge> generator =
                new GnmRandomGraphGenerator<>(numVertices, numEdges);
        generator.generateGraph(graph);

        System.out.println("Number of vertices: " + graph.vertexSet().size());
        System.out.println("Number of edges: " + graph.edgeSet().size());
        return graph;
    }
}
