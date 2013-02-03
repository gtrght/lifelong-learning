package algorithms.data.graph;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * User: Vasily Vlasov
 * Date: 03.02.13
 */
public class AdjGraph {
    private Multimap<Node, Node> nodes;

    public AdjGraph(Multimap<Node, Node> nodes) {
        this.nodes = nodes;
    }

    /**
     * Only copy is supported for now
     *
     * @param inplace if the search must be done inplace
     */
    public AdjGraph transpose(boolean inplace) {
        HashMultimap<Node, Node> transpose = HashMultimap.create(nodes.size(), nodes.size() == 0 ? 0 : nodes.values().size() / nodes.size() + 1);

        Set<Node> keys = nodes.keySet();

        for (Node key : keys) {
            Collection<Node> adjNodes = nodes.get(key);
            for (Node adjNode : adjNodes) {
                transpose.put(adjNode, key);
            }
        }
        return new AdjGraph(transpose);
    }

    public Collection<Node> vertices() {
        return nodes.keySet();
    }

    public Collection<Node> edges(Node node) {
        return nodes.get(node);
    }


    public static class Node {
        public Integer value;
        public Integer leader = null;

        Node(Integer value) {
            this.value = value;
        }
    }
}
