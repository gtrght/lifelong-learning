package algorithms.data.graph;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Search for Kasaraju's strongly connected components
 * User: Vasily Vlasov
 * Date: 03.02.13
 */
public class KasarajuSCC {
    private AdjGraph graphT;
    private Stack<AdjGraph.Node> visitOrder;

    public List<List<AdjGraph.Node>> findSCC(final AdjGraph graph) {
        List<AdjGraph.Node> vertices = graph.vertices();

        Collections.sort(vertices, new Comparator<AdjGraph.Node>() {
            @Override
            public int compare(AdjGraph.Node o1, AdjGraph.Node o2) {
                return graph.countEdges(o2) - graph.countEdges(o1);
            }
        });

        visitOrder = new Stack<AdjGraph.Node>();
        AtomicInteger finishingTime = new AtomicInteger();

        for (AdjGraph.Node vertex : vertices) {
            if (!vertex.isExplored())
                dfs(graph, vertex, vertex, finishingTime);
        }

        graphT = graph.transpose(false);
        Collections.sort(vertices, new Comparator<AdjGraph.Node>() {
            @Override
            public int compare(AdjGraph.Node o1, AdjGraph.Node o2) {
                return -o1.finishingTime + o2.finishingTime;
            }
        });


        List<List<AdjGraph.Node>> result = new LinkedList<List<AdjGraph.Node>>();

        for (AdjGraph.Node vertex : vertices) {
            if (!vertex.isAdded())
                result.add(collect(graphT, vertex));
        }

        return result;
    }

    private List<AdjGraph.Node> collect(AdjGraph graph, AdjGraph.Node vertex) {

        Queue<AdjGraph.Node> queue = new LinkedList<AdjGraph.Node>();
        queue.offer(vertex);

        AdjGraph.Node leader = vertex.leader;
        ArrayList<AdjGraph.Node> cycle = new ArrayList<AdjGraph.Node>();

        while (queue.size() > 0) {
            vertex = queue.poll();
            if (!vertex.isAdded()) {
                cycle.add(vertex);
            }

            vertex.markAdded();

            Collection<AdjGraph.Node> edges = graph.edges(vertex);
            for (AdjGraph.Node edge : edges) {
                if (!edge.isAdded() && edge.leader == leader) {
                    queue.offer(edge);
                }
            }

        }

        return cycle;
    }


    private void dfs(AdjGraph graph, AdjGraph.Node vertex, AdjGraph.Node leader, AtomicInteger finishingTime) {
        vertex.markExplored();
        vertex.leader = leader;

        Collection<AdjGraph.Node> edges = graph.edges(vertex);

        for (AdjGraph.Node edge : edges) {
            if (!edge.isExplored())
                dfs(graph, edge, leader, finishingTime);
        }

        vertex.finishingTime = finishingTime.incrementAndGet();

    }


    private void dfs1(AdjGraph graph, AdjGraph.Node vertex, AtomicInteger finishingTime) {
        Stack<AdjGraph.Node> toVisit = new Stack<AdjGraph.Node>();
        Stack<AdjGraph.Node> visitedAncestors = new Stack<AdjGraph.Node>();
        toVisit.push(vertex);
        AdjGraph.Node leader = vertex;

        while (!toVisit.empty()) {
            vertex = toVisit.peek();
            if (vertex.isExplored()) {
                System.out.println("already explored: " + vertex);
            }

            vertex.leader = leader;
            vertex.markExplored();


            Collection<AdjGraph.Node> edges = graph.edges(vertex);
            if (edges.size() > 0) {
                if (visitedAncestors.size() == 0 || visitedAncestors.peek() != vertex) {
                    visitedAncestors.push(vertex);

                    Iterator<AdjGraph.Node> descIterator = new LinkedList<AdjGraph.Node>(edges).descendingIterator();

                    while (descIterator.hasNext()) {
                        AdjGraph.Node edge = descIterator.next();
                        if (!edge.isExplored()) {
                            toVisit.push(edge);
                        }
                    }
                    continue;
                }
                System.out.println(visitedAncestors.pop());
            }
            vertex = toVisit.pop();
            vertex.finishingTime = finishingTime.incrementAndGet();
        }
    }


//    private void dfs(AdjGraph graph, AdjGraph.Node vertex, Stack<AdjGraph.Node> visitOrder, Integer leader, Collection<AdjGraph.Node> collect) {
//        System.out.println("Traversing with leader: " + vertex);
//        Stack<AdjGraph.Node> toVisit = new Stack<AdjGraph.Node>();
//        Stack<AdjGraph.Node> visitedAncestors = new Stack<AdjGraph.Node>();
//        toVisit.push(vertex);
//
//        while (!toVisit.empty()) {
//            vertex = toVisit.peek();
//            if (collect == null) {
//                vertex.leader = leader;
//            } else {
//                vertex.leader = null;
//            }
//
//            Collection<AdjGraph.Node> edges = graph.edges(vertex);
//            if (edges.size() > 0) {
//                if (visitedAncestors.size() == 0 || visitedAncestors.peek() != vertex) {
//                    visitedAncestors.push(vertex);
//
//                    for (AdjGraph.Node edge : edges) {
//                        if (!toVisit.contains(edge) && (collect == null && edge.leader == null) || (collect != null && leader.equals(edge.leader))) {
//                            toVisit.push(edge);
//                        }
//                    }
//                    continue;
//                }
//                visitedAncestors.pop();
//            }
//            vertex = toVisit.pop();
//            if (collect == null) {
//                visitOrder.push(vertex);
//            } else {
//                collect.add(vertex);
//            }
//        }
//    }
}
