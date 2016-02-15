package algorithms.data.graph;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.othelle.jtuples.Tuple2;
import com.othelle.jtuples.Tuples;

import java.util.*;

/**
 * User: Vasily Vlasov
 * Date: 03.02.13
 */
public class AdjGraph<NodeType extends AdjGraph.Node> {
    private Multimap<NodeType, NodeType> nodes;
    private Map<Tuple2<NodeType, NodeType>, Number> weights;

    public AdjGraph() {
        nodes = HashMultimap.create();
        weights = new HashMap<Tuple2<NodeType, NodeType>, Number>();
    }


    public AdjGraph(Multimap<NodeType, NodeType> nodes) {
        this.nodes = nodes;
    }

    /**
     * Only copy is supported for now
     *
     * @param inplace if the search must be done inplace
     */
    public AdjGraph<NodeType> transpose(boolean inplace) {
        HashMultimap<NodeType, NodeType> transpose = HashMultimap.create(nodes.size(), nodes.size() == 0 ? 0 : nodes.values().size() / nodes.size() + 1);

        Set<NodeType> keys = nodes.keySet();

        for (NodeType key : keys) {
            Collection<NodeType> adjNodeTypes = nodes.get(key);
            for (NodeType adjNodeType : adjNodeTypes) {
                transpose.put(adjNodeType, key);
            }
        }
        return new AdjGraph<NodeType>(transpose);
    }

    public List<NodeType> nodes() {
        return new ArrayList<NodeType>(nodes.keySet());
    }

    public List<NodeType> adjacentNodes(NodeType node) {
        Collection<NodeType> collection = nodes.get(node);
        return Lists.newArrayList(collection);
    }

    public int countEdges(NodeType node) {
        return nodes.get(node).size();
    }

    public static class Node {
        private String name;


        protected int flags = 0;

        public boolean hasFlag(int flag) {
            return (flags & flag) > 0;
        }

        public int setFlag(int flag) {
            return flags |= flag;
        }

        public int removeFlag(int flag) {
            return flags = setFlag(flag) ^ flag;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Node)) return false;

            Node node = (Node) o;

            if (name != null ? !name.equals(node.name) : node.name != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return name != null ? name.hashCode() : 0;
        }
    }

    public Number getWeight(NodeType a, NodeType b) {
        Number value = weights.get(Tuples.tuple(a, b));

        return value != null ? value : Integer.MAX_VALUE;
    }

    public void addEdge(NodeType a, NodeType b, Number weight) {
        nodes.put(getNode(a), getNode(b));
        weights.put(Tuples.tuple(getNode(a), getNode(b)), weight);
    }

    private Map<NodeType, NodeType> cachedNodes = new HashMap<NodeType, NodeType>();

    public NodeType getNode(NodeType node) {
        if (!cachedNodes.containsKey(node)) {
            cachedNodes.put(node, node);
        }
        return cachedNodes.get(node);
    }
}
