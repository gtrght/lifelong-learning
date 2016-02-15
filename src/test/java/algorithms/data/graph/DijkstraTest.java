package algorithms.data.graph;

import com.othelle.jtuples.Tuple2;
import com.othelle.jtuples.Tuples;
import com.othelle.jtuples.ZipUtils;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * author: v.vlasov
 */
public class DijkstraTest {

    @Test
    @SuppressWarnings("unchecked")
    public void testDijkstra1() {
        String input = "dijkstra_test_1.txt";

        testAlgorithm(input, Arrays.asList(Tuples.tuple("2", 8), Tuples.tuple("3", 12)));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testDijkstraLarge() {
        String input = "dijkstra.txt";

        testAlgorithm(input, ZipUtils.zip(Arrays.asList("7", "37", "59", "82", "99", "115", "133", "165", "188", "197"),
                Arrays.asList(7, 37, 59, 82, 99, 115, 133, 165, 188, 197)));
    }

    private void testAlgorithm(String input, List<Tuple2<String, Integer>> expected) {
        AdjGraph<Dijkstra.DijkstraNode> graph = Dijkstra.loadGraph(input);
        new Dijkstra().findShortestPaths(graph, new Dijkstra.DijkstraNode("1"));


        for (Tuple2<String, Integer> tuple2 : expected)
            System.out.println(format("Path to %s is %d", tuple2._1(), graph.getNode(new Dijkstra.DijkstraNode(tuple2._1())).getMinimumPath()));

        for (Tuple2<String, Integer> tuple2 : expected)
            assertThat(graph.getNode(new Dijkstra.DijkstraNode(tuple2._1())).getMinimumPath(), Matchers.equalTo(tuple2._2()));
    }

    @Test
    public void testLoadGraph() {
        AdjGraph<Dijkstra.DijkstraNode> graph = Dijkstra.loadGraph("dijkstra.txt");

        System.out.println();

    }

}
