package algorithms.algo2;

import algorithms.data.graph.AdjGraph;
import algorithms.data.graph.GraphUtils;
import algorithms.data.graph.KasarajuSCC;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.Collections;
import java.util.Comparator;
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

        List<List<AdjGraph.Node>> scc = new KasarajuSCC().findSCC(graph);

        assertThat(scc.size(), Matchers.equalTo(1));
        assertThat(scc.get(0).size(), Matchers.equalTo(3));
    }

    @Test
    public void testFindSCC4() throws Exception {
        AdjGraph graph = GraphUtils.readAdjGraph("/kasaraju2.txt");

        List<List<AdjGraph.Node>> scc = new KasarajuSCC().findSCC(graph);

        assertThat(scc.size(), Matchers.equalTo(1));
        assertThat(scc.get(0).size(), Matchers.equalTo(3));
    }

    @Test
    public void testFindSCC8() throws Exception {
        AdjGraph graph = GraphUtils.readAdjGraph("/kasaraju3.txt");

        List<List<AdjGraph.Node>> scc = new KasarajuSCC().findSCC(graph);

        assertThat(scc.size(), Matchers.equalTo(4));
        assertThat(scc.get(0).size(), Matchers.equalTo(3));
    }

    @Test
    public void testFindSCC9() throws Exception {
        AdjGraph graph = GraphUtils.readAdjGraph("/kasaraju5.txt");

        List<List<AdjGraph.Node>> scc = new KasarajuSCC().findSCC(graph);

        Collections.sort(scc, new Comparator<List<AdjGraph.Node>>() {
            @Override
            public int compare(List<AdjGraph.Node> o1, List<AdjGraph.Node> o2) {
                return o2.size() - o1.size();
            }
        });

        assertThat(scc.get(0).size(), Matchers.equalTo(3));
        assertThat(scc.get(1).size(), Matchers.equalTo(3));
        assertThat(scc.get(2).size(), Matchers.equalTo(3));
    }

    @Test
    public void testFindSCC9_1() throws Exception {
        AdjGraph graph = GraphUtils.readAdjGraph("/kasaraju6.txt");

        List<List<AdjGraph.Node>> scc = new KasarajuSCC().findSCC(graph);

        Collections.sort(scc, new Comparator<List<AdjGraph.Node>>() {
            @Override
            public int compare(List<AdjGraph.Node> o1, List<AdjGraph.Node> o2) {
                return o2.size() - o1.size();
            }
        });

        assertThat(scc.get(0).size(), Matchers.equalTo(3));
        assertThat(scc.get(1).size(), Matchers.equalTo(3));
        assertThat(scc.get(2).size(), Matchers.equalTo(2));
        assertThat(scc.size(), Matchers.equalTo(3));
    }

    @Test
    public void testFindSCCHuge() throws Exception {
        AdjGraph graph = GraphUtils.readAdjGraph("/kasaraju4.txt");

        List<List<AdjGraph.Node>> scc = new KasarajuSCC().findSCC(graph);

        Collections.sort(scc, new Comparator<List<AdjGraph.Node>>() {
            @Override
            public int compare(List<AdjGraph.Node> o1, List<AdjGraph.Node> o2) {
                return o2.size() - o1.size();
            }
        });


        System.out.println(scc.get(0).size());
        System.out.println(scc.get(1).size());
        System.out.println(scc.get(2).size());
        System.out.println(scc.get(3).size());
        System.out.println(scc.get(4).size());
    }


}
