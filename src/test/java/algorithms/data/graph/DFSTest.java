package algorithms.data.graph;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * author: v.vlasov
 */
public class DFSTest {
    @Test
    public void testPrintDfsPostOrder() throws Exception {
        AdjGraph graph = GraphUtils.readAdjGraph("/dfs_post_order.txt");
        List<AdjGraph.Node> nodes = graph.nodes();

        AdjGraph.Node start = null;
        for (AdjGraph.Node node : nodes) {
            if (node.toString().equals("1"))
                start = node;

        }
        System.out.println("Iterative: ");
        new DFS().printDfsPostOrder(graph, start, false);
    }

    @Test
    public void testPrintDfsPostOrderRec() throws IOException {
        AdjGraph graph = GraphUtils.readAdjGraph("/dfs_post_order.txt");
        List<AdjGraph.Node> nodes = graph.nodes();

        AdjGraph.Node start = null;
        for (AdjGraph.Node node : nodes) {
            if (node.toString().equals("1"))
                start = node;

        }

        System.out.println("Recursive: ");
        new DFS().printDfsPostOrder(graph, start, true);
    }
}
