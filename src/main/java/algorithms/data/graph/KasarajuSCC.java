package algorithms.data.graph;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static algorithms.data.graph.AdjGraph.Node;

/**
 * Search for Kasaraju's strongly connected components
 * User: Vasily Vlasov
 * Date: 03.02.13
 */
public class KasarajuSCC {
    private AdjGraph graphT;
    private Stack<Node> visitOrder;

    public List<List<Node>> findSCC(final AdjGraph graph) {
        List<Node> vertices = graph.nodes();

        Collections.sort(vertices, new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return graph.countEdges(o2) - graph.countEdges(o1);
            }
        });

        visitOrder = new Stack<Node>();
        AtomicInteger finishingTime = new AtomicInteger();

        for (Node vertex : vertices) {
            if (!vertex.isVisited())
                dfsIterative(graph, vertex, vertex, finishingTime);
        }

        graphT = graph.transpose(false);
        Collections.sort(vertices, new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return -o1.finishingTime + o2.finishingTime;
            }
        });


        List<List<Node>> result = new LinkedList<List<Node>>();

        for (Node vertex : vertices) {
            if (!vertex.isAdded())
                result.add(collect(graphT, vertex));
        }

        return result;
    }

    private List<Node> collect(AdjGraph graph, Node vertex) {

        Queue<Node> queue = new LinkedList<Node>();
        queue.offer(vertex);

        Node leader = vertex.leader;
        ArrayList<Node> cycle = new ArrayList<Node>();

        while (queue.size() > 0) {
            vertex = queue.poll();
            if (!vertex.isAdded()) {
                cycle.add(vertex);
            }

            vertex.markAdded();

            Collection<Node> edges = graph.edges(vertex);
            for (Node edge : edges) {
                if (!edge.isAdded() && edge.leader == leader) {
                    queue.offer(edge);
                }
            }

        }

        return cycle;
    }


    private void dfs(AdjGraph graph, Node vertex, Node leader, AtomicInteger finishingTime) {
        vertex.markVisited();
        vertex.leader = leader;

        Collection<Node> edges = graph.edges(vertex);

        for (Node edge : edges) {
            if (!edge.isVisited())
                dfs(graph, edge, leader, finishingTime);
        }

        vertex.finishingTime = finishingTime.incrementAndGet();

    }

    private void dfsIterative(AdjGraph graph, final Node vertex, final Node leader,  final AtomicInteger finishingTime) {
        new DFS().dfsPostOrderIterative(graph, vertex, new DFS.Callback() {
            @Override
            public void nodeVisited(AdjGraph graph, Node node) {
                node.leader = vertex;
                node.finishingTime = finishingTime.getAndIncrement();
            }
        });
    }
}
