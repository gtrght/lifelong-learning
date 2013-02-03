package algorithms.data.satisfy;

import algorithms.data.graph.AdjGraph;
import algorithms.data.graph.KasarajuSSC;
import com.google.common.collect.HashMultimap;
import scala.Tuple2;

import java.util.*;

/**
 * User: Vasily Vlasov
 * Date: 03.02.13
 */
public class TwoSatProblemKasaraju {
    public boolean checkSatisfiable(List<Tuple2<Integer, Integer>> disjunctions, int verticesCount) {
        if (verticesCount == 0) return true;

        AdjGraph adjGraph = createGraph(disjunctions, verticesCount);


        for (List<AdjGraph.Node> scc : new KasarajuSSC().findSCC(adjGraph)) {
            if (scc != null) {
                Set<Integer> set = new HashSet<Integer>(scc.size());

                for (AdjGraph.Node node : scc) {
                    if (set.contains(-node.value)) return false;
                    set.add(node.value);
                }
            } else
                break;
        }

        return true;
    }

    private AdjGraph createGraph(List<Tuple2<Integer, Integer>> disjunctions, int verticesCount) {
        HashMultimap<AdjGraph.Node, AdjGraph.Node> graphData = HashMultimap.create(verticesCount * 2, disjunctions.size() / verticesCount + 1);
        HashMap<Integer, AdjGraph.Node> map = new HashMap<Integer, AdjGraph.Node>(verticesCount * 2);

        for (Tuple2<Integer, Integer> disjunction : disjunctions) {
            Integer c1 = disjunction._1();
            Integer c2 = disjunction._2();

            graphData.put(getOrCreate(-c1, map), getOrCreate(c2, map));
            graphData.put(getOrCreate(-c2, map), getOrCreate(c1, map));
        }
        return new AdjGraph(graphData);
    }

    private AdjGraph.Node getOrCreate(Integer code, HashMap<Integer, AdjGraph.Node> map) {
        AdjGraph.Node node = map.get(code);
        if (node == null) {
            node = new AdjGraph.Node(code);
            map.put(code, node);
        }
        return node;
    }
}
