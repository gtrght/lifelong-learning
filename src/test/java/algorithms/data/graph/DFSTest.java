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
        AdjGraph<KasarajuNode> graph = GraphUtils.readAdjGraph("/dfs_post_order.txt");
        List<KasarajuNode> nodes = graph.nodes();

        KasarajuNode start = null;
        for (KasarajuNode node : nodes) {
            if (node.toString().equals("1"))
                start = node;

        }
        System.out.println("Iterative: ");
        new DFS().printDfsPostOrder(graph, start, false);
    }

    @Test
    public void testPrintDfsPostOrderRec() throws IOException {
        AdjGraph<KasarajuNode> graph = GraphUtils.readAdjGraph("/dfs_post_order.txt");
        List<KasarajuNode> nodes = graph.nodes();

        KasarajuNode start = null;
        for (KasarajuNode node : nodes) {
            if (node.toString().equals("1"))
                start = node;

        }

        System.out.println("Recursive: ");
        new DFS().printDfsPostOrder(graph, start, true);
    }
}
