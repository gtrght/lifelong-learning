package algorithms.data.graph;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * author: v.vlasov
 */
public class DFS {


    public void printDfsPostOrder(AdjGraph graph, AdjGraph.Node vertex, boolean recursive) {
        Callback printCallback = new Callback() {
            @Override
            public void nodeVisited(AdjGraph graph, AdjGraph.Node node) {
                System.out.println(node);
            }
        };

        if (!recursive)
            dfsPostOrderIterative(graph, vertex, printCallback);
        else
            dfsPostOrderRecursive(graph, vertex, printCallback);
    }

    public void dfsPostOrderRecursive(AdjGraph graph, AdjGraph.Node vertex, Callback callback) {
        if (vertex.isVisited())
            return;

        vertex.markVisited();

        List<AdjGraph.Node> edges = graph.edges(vertex);
        for (AdjGraph.Node node : edges) {
            if (!node.isVisited())
                dfsPostOrderRecursive(graph, node, callback);
        }

        callback.nodeVisited(graph, vertex);  //post order call to callback
    }

    public void dfsPostOrderIterative(AdjGraph graph, AdjGraph.Node vertex, Callback callback) {
        Stack<Level> toVisit = new Stack<Level>();
        toVisit.push(new Level(Collections.singletonList(vertex)));

        while (!toVisit.isEmpty()) {
            Level level = toVisit.peek();

            if (level.index >= level.nodes.size()) {
                toVisit.pop();
                continue;
            }

            AdjGraph.Node node = level.nodes.get(level.index);

            if (!node.isVisited()) {
                if (node.isChildrenExplored()) {
                    node.markVisited();
                    callback.nodeVisited(graph, node);
                    level.index++;
                } else {
                    List<AdjGraph.Node> edges = graph.edges(node);
                    List<AdjGraph.Node> outgoing = Lists.newArrayList(Collections2.filter(edges, new Predicate<AdjGraph.Node>() {
                        @Override
                        public boolean apply(AdjGraph.Node input) {
                            return !input.isChildrenExplored();
                        }
                    }));

                    if (outgoing.size() > 0)
                        toVisit.add(new Level(outgoing));
                    node.markChildrenExplored();
                }
            } else {
                level.index++;
            }
        }
    }


    public static class Level {
        int index = 0;
        List<AdjGraph.Node> nodes;

        Level(List<AdjGraph.Node> nodes) {
            this.nodes = nodes;
        }
    }

    public interface Callback {
        void nodeVisited(AdjGraph graph, AdjGraph.Node node);
    }
}
