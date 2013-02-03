package algorithms.algo2;

import algorithms.data.graph.AdjGraph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

/**
 * Search for Kasaraju's strongly connected components
 * User: Vasily Vlasov
 * Date: 03.02.13
 */
public class KasarajuSSC {
    public List<List<AdjGraph.Node>> findSCC(AdjGraph graph) {
        Collection<AdjGraph.Node> vertices = graph.vertices();
        Stack<AdjGraph.Node> visited = new Stack<AdjGraph.Node>();

        for (AdjGraph.Node vertex : vertices) {
            if (vertex.leader == null) {
                dfs(graph, vertex, visited, vertex.value, null);
            }
        }

        AdjGraph graphT = graph.transpose(false);


        List<List<AdjGraph.Node>> result = new ArrayList<List<AdjGraph.Node>>();

        while (visited.size() != 0) {
            AdjGraph.Node node = visited.pop();
            ArrayList<AdjGraph.Node> scc = new ArrayList<AdjGraph.Node>();
            dfs(graphT, node, visited, node.leader, scc);
            if (scc.size() > 1) {
                visited.removeAll(scc);
                result.add(scc);
            }
        }

        return result;
    }

    private void dfs(AdjGraph graph, AdjGraph.Node vertex, Stack<AdjGraph.Node> visited, Integer leader, Collection<AdjGraph.Node> collect) {
        Collection<AdjGraph.Node> edges = graph.edges(vertex);
        if (collect == null)
            vertex.leader = leader;
        else
            vertex.leader = null;

        for (AdjGraph.Node edge : edges) {
            if ((collect == null && !leader.equals(edge.leader)) || (collect != null && leader.equals(edge.leader))) {
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
