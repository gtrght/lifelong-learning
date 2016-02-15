package algorithms.data.satisfy;

import algorithms.data.graph.AdjGraph;
import algorithms.data.graph.KasarajuNode;
import algorithms.data.graph.KasarajuSCC;
import com.google.common.collect.HashMultimap;
import scala.Tuple2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User: Vasily Vlasov
 * Date: 03.02.13
 */
public class TwoSatProblemKasaraju {
    public boolean checkSatisfiable(List<Tuple2<Integer, Integer>> disjunctions, int verticesCount) {
        if (verticesCount == 0) return true;

        AdjGraph<KasarajuNode> adjGraph = createGraph(disjunctions, verticesCount);


        for (List<KasarajuNode> scc : new KasarajuSCC().findSCC(adjGraph)) {
            if (scc != null) {
                Set<Integer> set = new HashSet<Integer>(scc.size());

                for (KasarajuNode node : scc) {
                    if (set.contains(-node.value)) return false;
                    set.add(node.value);
                }
            } else
                break;
        }

        return true;
    }

    private AdjGraph<KasarajuNode> createGraph(List<Tuple2<Integer, Integer>> disjunctions, int verticesCount) {
        HashMultimap<KasarajuNode, KasarajuNode> graphData = HashMultimap.create(verticesCount * 2, disjunctions.size() / verticesCount + 1);
        HashMap<Integer, KasarajuNode> map = new HashMap<Integer, KasarajuNode>(verticesCount * 2);

        for (Tuple2<Integer, Integer> disjunction : disjunctions) {
            Integer c1 = disjunction._1();
            Integer c2 = disjunction._2();

            graphData.put(getOrCreate(-c1, map), getOrCreate(c2, map));
            graphData.put(getOrCreate(-c2, map), getOrCreate(c1, map));
        }
        return new AdjGraph<KasarajuNode>(graphData);
    }

    private KasarajuNode getOrCreate(Integer code, HashMap<Integer, KasarajuNode> map) {
        KasarajuNode node = map.get(code);
        if (node == null) {
            node = new KasarajuNode(code);
            map.put(code, node);
        }
        return node;
    }
}
