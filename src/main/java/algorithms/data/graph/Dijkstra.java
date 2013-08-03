package algorithms.data.graph;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.PriorityQueue;

/**
 * author: v.vlasov
 */
public class Dijkstra {

    public void findShortestPaths(AdjGraph<DijkstraNode> graph, DijkstraNode start) {
        start.minimumPath = 0;
        PriorityQueue<DijkstraNode> queue = new PriorityQueue<DijkstraNode>(graph.nodes());

        updateAdjacent(queue, graph, start);

        while (!queue.isEmpty()) {
            DijkstraNode node = queue.poll();
            updateAdjacent(queue, graph, node);
        }
    }

    private void updateAdjacent(PriorityQueue<DijkstraNode> queue, AdjGraph<DijkstraNode> graph, DijkstraNode start) {
        queue.remove(start);

        List<DijkstraNode> nodes = graph.adjacentNodes(start);

        for (DijkstraNode node : nodes) {
            Number weight = graph.getWeight(start, node);

            if (node.getMinimumPath() > weight.intValue() + start.getMinimumPath()) {
                queue.remove(node);
                node.setMinimumPath(weight.intValue() + start.getMinimumPath());
                queue.offer(node);
            }
        }
    }


    public static class DijkstraNode extends AdjGraph.Node implements Comparable<DijkstraNode> {
        int minimumPath = Integer.MAX_VALUE;

        public DijkstraNode(String name) {
            setName(name);
        }

        public int getMinimumPath() {
            return minimumPath;
        }

        public void setMinimumPath(int minimumPath) {
            this.minimumPath = minimumPath;
        }

        @Override
        public int compareTo(DijkstraNode o) {
            return getMinimumPath() - o.getMinimumPath();
        }
    }

    public static AdjGraph<DijkstraNode> loadGraph(String resource) {
        InputStream inputStream = GraphUtils.class.getResourceAsStream(resource);

        try {
            AdjGraph<DijkstraNode> graph = new AdjGraph<DijkstraNode>();
            @SuppressWarnings("unchecked")
            List<String> lines = IOUtils.readLines(inputStream);

            for (String line : lines) {
                String[] split = line.split("\\s");

                DijkstraNode from = new DijkstraNode(split[0]);
                for (int i = 1; i < split.length; i++) {
                    String[] edge = split[i].split(",");
                    graph.addEdge(from, new DijkstraNode(edge[0]), Integer.parseInt(edge[1]));
                }
            }

            return graph;

        } catch (IOException e) {
            return null;
        }
    }
}
