package algorithms.algo2;

import algorithms.data.graph.AdjGraph;
import algorithms.data.graph.GraphUtils;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * User: Vasily Vlasov
 * Date: 03.02.13
 */
public class KasarajuSSCTest {
    @Test
    public void testFindSCC3() throws Exception {
        AdjGraph graph = GraphUtils.readAdjGraph("/kasaraju1.txt");

        List<List<AdjGraph.Node>> scc = new KasarajuSSC().findSCC(graph);

        assertThat(scc.size(), Matchers.equalTo(1));
        assertThat(scc.get(0).size(), Matchers.equalTo(3));
    }

    @Test
    public void testFindSCC4() throws Exception {
        AdjGraph graph = GraphUtils.readAdjGraph("/kasaraju2.txt");

        List<List<AdjGraph.Node>> scc = new KasarajuSSC().findSCC(graph);

        assertThat(scc.size(), Matchers.equalTo(1));
        assertThat(scc.get(0).size(), Matchers.equalTo(3));
    }

    @Test
    public void testFindSCC8() throws Exception {
        AdjGraph graph = GraphUtils.readAdjGraph("/kasaraju3.txt");

        List<List<AdjGraph.Node>> scc = new KasarajuSSC().findSCC(graph);

        assertThat(scc.size(), Matchers.equalTo(3));
        assertThat(scc.get(0).size(), Matchers.equalTo(3));
    }


}
