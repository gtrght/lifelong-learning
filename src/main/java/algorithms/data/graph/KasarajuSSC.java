package algorithms.data.graph;

import algorithms.data.graph.AdjGraph;

import java.util.*;

/**
 * Search for Kasaraju's strongly connected components
 * User: Vasily Vlasov
 * Date: 03.02.13
 */
public class KasarajuSSC {
    private AdjGraph graphT;
    private Stack<AdjGraph.Node> visited;

    public List<List<AdjGraph.Node>> findSCC(AdjGraph graph) {
        Collection<AdjGraph.Node> vertices = graph.vertices();
        visited = new Stack<AdjGraph.Node>();

        for (AdjGraph.Node vertex : vertices) {
            if (vertex.leader == null) {
                dfs(graph, vertex, visited, vertex.value, null);
            }
        }

        graphT = graph.transpose(false);


        List<List<AdjGraph.Node>> result = new ArrayList<List<AdjGraph.Node>>();

        Iterator<List<AdjGraph.Node>> iterator = getIterator();
        while (iterator.hasNext()) {
            List<AdjGraph.Node> next = iterator.next();
            if (next != null)
                result.add(next);
        }

        return result;
    }

    public Iterator<List<AdjGraph.Node>> getIterator() {
        return new Iterator<List<AdjGraph.Node>>() {
            @Override
            public boolean hasNext() {
                return visited.size() != 0;
            }

            @Override
            public List<AdjGraph.Node> next() {
                while (visited.size() != 0) {
                    AdjGraph.Node node = visited.pop();
                    if (node.leader == null) continue;
                    ArrayList<AdjGraph.Node> scc = new ArrayList<AdjGraph.Node>();
                    dfs(graphT, node, visited, node.leader, scc);
                    if (scc.size() > 1 || graphT.edges(scc.get(0)).contains(scc.get(0))) {
                        visited.removeAll(scc);
                        return scc;
                    }
                }
                return null;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }

    private void dfs(AdjGraph graph, AdjGraph.Node vertex, Stack<AdjGraph.Node> visited, Integer leader, Collection<AdjGraph.Node> collect) {
        Collection<AdjGraph.Node> edges = graph.edges(vertex);
        if (collect == null)
            vertex.leader = leader;
        else
            vertex.leader = null;

        for (AdjGraph.Node edge : edges) {
            if ((collect == null && edge.leader == null) || (collect != null && leader.equals(edge.leader))) {
                dfs(graph, edge, visited, leader, collect);
            }
        }
        if (collect == null) {
            visited.add(vertex);
        } else {
            collect.add(vertex);

        }
    }
}
