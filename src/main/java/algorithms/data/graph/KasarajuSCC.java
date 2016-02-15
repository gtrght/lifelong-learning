package algorithms.data.graph;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Search for Kasaraju's strongly connected components
 * User: Vasily Vlasov
 * Date: 03.02.13
 */
public class KasarajuSCC {
    private AdjGraph<KasarajuNode> graphT;
    private Stack<KasarajuNode> visitOrder;

    public List<List<KasarajuNode>> findSCC(final AdjGraph<KasarajuNode> graph) {
        List<KasarajuNode> vertices = graph.nodes();

        Collections.sort(vertices, new Comparator<KasarajuNode>() {
            @Override
            public int compare(KasarajuNode o1, KasarajuNode o2) {
                return graph.countEdges(o2) - graph.countEdges(o1);
            }
        });

        visitOrder = new Stack<KasarajuNode>();
        AtomicInteger finishingTime = new AtomicInteger();

        for (KasarajuNode vertex : vertices) {
            if (!vertex.isVisited())
                dfsIterative(graph, vertex, vertex, finishingTime);
        }

        graphT = graph.transpose(false);
        Collections.sort(vertices, new Comparator<KasarajuNode>() {
            @Override
            public int compare(KasarajuNode o1, KasarajuNode o2) {
                return -o1.finishingTime + o2.finishingTime;
            }
        });


        List<List<KasarajuNode>> result = new LinkedList<List<KasarajuNode>>();

        for (KasarajuNode vertex : vertices) {
            if (!vertex.isAdded())
                result.add(collect(graphT, vertex));
        }

        return result;
    }

    private List<KasarajuNode> collect(AdjGraph<KasarajuNode> graph, KasarajuNode vertex) {

        Queue<KasarajuNode> queue = new LinkedList<KasarajuNode>();
        queue.offer(vertex);

        KasarajuNode leader = vertex.leader;
        ArrayList<KasarajuNode> cycle = new ArrayList<KasarajuNode>();

        while (queue.size() > 0) {
            vertex = queue.poll();
            if (!vertex.isAdded()) {
                cycle.add(vertex);
            }

            vertex.markAdded();

            Collection<KasarajuNode> edges = graph.adjacentNodes(vertex);
            for (KasarajuNode edge : edges) {
                if (!edge.isAdded() && edge.leader == leader) {
                    queue.offer(edge);
                }
            }

        }

        return cycle;
    }


    private void dfs(AdjGraph graph, KasarajuNode vertex, KasarajuNode leader, AtomicInteger finishingTime) {
        vertex.markVisited();
        vertex.leader = leader;

        Collection<KasarajuNode> edges = graph.adjacentNodes(vertex);

        for (KasarajuNode edge : edges) {
            if (!edge.isVisited())
                dfs(graph, edge, leader, finishingTime);
        }

        vertex.finishingTime = finishingTime.incrementAndGet();

    }

    private void dfsIterative(AdjGraph graph, final KasarajuNode vertex, final KasarajuNode leader,  final AtomicInteger finishingTime) {
        new DFS().dfsPostOrderIterative(graph, vertex, new DFS.Callback() {
            @Override
            public void nodeVisited(AdjGraph graph, KasarajuNode node) {
                node.leader = vertex;
                node.finishingTime = finishingTime.getAndIncrement();
            }
        });
    }
}
