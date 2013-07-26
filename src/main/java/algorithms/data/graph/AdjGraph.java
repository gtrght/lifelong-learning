package algorithms.data.graph;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

import java.util.ArrayList;
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

    public List<Node> nodes() {
        return new ArrayList<Node>(nodes.keySet());
    }

    public List<Node> edges(Node node) {
        Collection<Node> collection = nodes.get(node);
        return Lists.newArrayList(collection);
    }

    public int countEdges(Node node) {
        return nodes.get(node).size();
    }


    public static class Node {
        public static final int EXPLORED = 1;
        public static final int ADDED = 1 << 1;
        public static final int CHILDREN_EXPLORED = 1 << 2;


        public Integer value;
        public Node leader = null;
        public int bitMask = 0;
        public int finishingTime = Integer.MAX_VALUE;

        public Node(Integer value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }

        public boolean isVisited() {
            return (bitMask & EXPLORED) > 0;
        }

        public void markVisited() {
            bitMask |= EXPLORED;
        }

        public boolean isAdded() {
            return (bitMask & ADDED) > 0;
        }

        public void markAdded() {
            bitMask |= ADDED;
        }

        public void markChildrenExplored() {
            bitMask |= CHILDREN_EXPLORED;
        }

        public boolean isChildrenExplored() {
            return (bitMask & CHILDREN_EXPLORED) > 0;
        }
    }
}
