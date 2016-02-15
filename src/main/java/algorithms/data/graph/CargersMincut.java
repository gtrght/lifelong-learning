package algorithms.data.graph;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.othelle.jtuples.Tuple2;
import com.othelle.jtuples.Tuples;
import com.othelle.jtuples.serialize.JacksonConverter;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author: v.vlasov
 */
public class CargersMincut {

    public static JacksonConverter converter = new JacksonConverter();

    @SuppressWarnings("unchecked")
    public static Graph loadGraph(String file) throws IOException {
        Graph graph = new Graph();

        List<String> lines = IOUtils.readLines(CargersMincut.class.getResourceAsStream(file));

        for (String line : lines) {
            String[] split = line.trim().split("\\s+");

            for (int i = 1; i < split.length; i++)
                graph.addEdge(split[0], split[i]);
        }
        return graph;
    }

    public static int carger(Graph graph) {
        while (graph.size() > 2) {
            Tuple2<String, String> pair = graph.randomPair();
            graph.merge(pair._1(), pair._2());
        }
        return graph.edgesCount();
    }


    public static void main(String[] args) throws IOException {
        Graph graph = loadGraph("karger_min_cut.txt");
        byte[] bytes = converter.writeValueAsBytes(graph);
        int minCut = Integer.MAX_VALUE;

        int size = graph.size();
        Graph minGraph = null;
        for (int i = 0; i < size * size * (400/size) * Math.log(size); i++) {
            Graph graph1 = converter.readValue(bytes, Graph.class);
            int candidate = carger(graph1);

            if (candidate < minCut) {
                minCut = candidate;
                minGraph = graph1;
                System.out.println(candidate / 2);
            }
        }

        System.out.println("Result: " + minCut / 2);
    }

    public static class Graph {
        private HashMap<String, HashMultiset<String>> edges = new HashMap<String, HashMultiset<String>>();

        public HashMap<String, HashMultiset<String>> getEdges() {
            return edges;
        }

        public void addEdge(String a, String b) {
            if (edges.get(a) == null) {
                edges.put(a, HashMultiset.<String>create());
            }
            edges.get(a).add(b);
        }

        public void renameNode(String a, String newName) {
            ArrayList<String> items = new ArrayList<String>(edges.get(a));
            items.add(newName);
            for (String item : items) {
                Multiset<String> multiset = edges.get(item);
                try {
                    int count = multiset.count(a);
                    if (count > 0) {
                        multiset.remove(a, count);
                        multiset.add(newName, count);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void removeCycles(String a) {
            edges.get(a).remove(a, edges.get(a).count(a));
        }

        public void removeNode(String a) {
            edges.remove(a);
        }

        public int size() {
            return edges.size();
        }

        public void merge(String a, String b) {
            String newName = a + "," + b;

            HashMultiset<String> edges1 = edges.get(a);
            HashMultiset<String> edges2 = edges.get(b);

            HashMultiset<String> resultSet = HashMultiset.create(edges1.size() + edges2.size());
            resultSet.addAll(edges1);
            resultSet.addAll(edges2);

            edges.put(newName, resultSet);

            renameNode(a, newName);
            renameNode(b, newName);
            removeNode(a);
            removeNode(b);

            removeCycles(newName);
        }

        @SuppressWarnings("unchecked")
        public Tuple2<String, String> randomPair() {
            Object[] objects = edges.keySet().toArray();

            if (objects.length < 2) throw new IllegalStateException("Less than 2");

            String a = (String) objects[((int) (Math.random() * objects.length))];
            String b = a;

            while (a.equals(b)) {
                b = (String) objects[((int) (Math.random() * objects.length))];
            }
            return Tuples.tuple(a, b);
        }

        public int edgesCount() {
            int sum = 0;
            for (Map.Entry<String, HashMultiset<String>> entry : edges.entrySet()) {
                sum += entry.getValue().size();
            }
            return sum;
        }
    }

}
