package org.example.topsort.commons;

import org.jgrapht.Graph;
import org.jgrapht.generate.GnmRandomGraphGenerator;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.util.SupplierUtil;

import java.util.function.Supplier;

public class generateDAGs {
    public static Graph generate(int numVertices, int numEdges, boolean weighted) {
        Supplier<Integer> vSupplier = new Supplier<Integer>() {
            private int id;

            @Override
            public Integer get() {
                return id++;
            }
        };
        Graph<Integer, DefaultEdge> graph =
                new DirectedAcyclicGraph<>(vSupplier,
                        SupplierUtil.createDefaultEdgeSupplier(),
                        weighted);

        GnmRandomGraphGenerator<Integer, DefaultEdge> generator =
                new GnmRandomGraphGenerator<>(numVertices, numEdges);
        generator.generateGraph(graph);
        return graph;
    }
}
