package algorithms.data.graph;

import com.google.common.base.Function;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.apache.commons.io.IOUtils;
import scala.Tuple2;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.List;

import static java.lang.Math.min;
import static java.lang.Math.scalb;

/**
 * Question 1
 * In this assignment you will implement one or more algorithms for the all-pairs shortest-path problem. Here are data files describing three graphs: graph #1; graph #2; graph #3.
 * The first line indicates the number of vertices and edges, respectively. Each subsequent line describes an edge (the first two numbers are its tail and head, respectively) and its length (the third number). NOTE: some of the edge lengths are negative. NOTE: These graphs may or may not have negative-cost cycles.
 * <p/>
 * Your task is to compute the "shortest shortest path". Precisely, you must first identify which, if any, of the three graphs have no negative cycles. For each such graph, you should compute all-pairs shortest paths and remember the smallest one (i.e., compute minu,v∈Vd(u,v), where d(u,v) denotes the shortest-path distance from u to v).
 * <p/>
 * If each of the three graphs has a negative-cost cycle, then enter "NULL" in the box below. If exactly one graph has no negative-cost cycles, then enter the length of its shortest shortest path in the box below. If two or more of the graphs have no negative-cost cycles, then enter the smallest of the lengths of their shortest shortest paths in the box below.
 * <p/>
 * OPTIONAL: You can use whatever algorithm you like to solve this question. If you have extra time, try comparing the performance of different all-pairs shortest-path algorithms!
 * <p/>
 * OPTIONAL: If you want a bigger data set to play with, try computing the shortest shortest path for this graph.
 * User: Vasily Vlasov
 * Date: 21.01.13
 */
public class BellmanFordModifiedAlgo {
    private static int sum0(int[] array) {
        int sum = 0;
        for (int i : array) {
            sum += i;
        }
        return sum;
    }

    private static int min0(int[] array) {
        int min = Integer.MAX_VALUE;
        for (int i : array) {
            min = min(i, min);
        }
        return min;
    }


    private static int calculate0(Integer source, Multimap<Integer, Tuple2<Integer, Integer>> input) {
        int size = input.keySet().size();
        int[] lastStep = new int[size];
        int[] currentStep = new int[size];

        for (int i = 0; i < currentStep.length; i++)
            lastStep[i] = source == i ? 0 : Integer.MAX_VALUE;


        for (int j = 0; j < size; j++) {
            for (int i = 0; i < size; i++) {
                int minForIncomming = Integer.MAX_VALUE;
                Collection<Tuple2<Integer, Integer>> tuple2s = input.get(i);

                for (Tuple2<Integer, Integer> tuple2 : tuple2s) {
                    if (lastStep[tuple2._1()] != Integer.MAX_VALUE)
                        minForIncomming = min(minForIncomming, lastStep[tuple2._1()] + tuple2._2());
                }
                currentStep[i] = min(minForIncomming, lastStep[i]);
            }

            int[] tmp = lastStep;
            lastStep = currentStep;
            currentStep = tmp;
        }

        int sum1 = sum0(lastStep);
        int sum2 = sum0(currentStep);

        lastStep[source] = Integer.MAX_VALUE;

        return sum1 != sum2 ? Integer.MIN_VALUE : min0(lastStep);
    }

    /**
     * @param input the map with vertexes incoming and incoming edges for each vertex
     * @return
     */
    public static int calculate(Multimap<Integer, Tuple2<Integer, Integer>> input) {
        int answer = Integer.MAX_VALUE;
        for (Integer source : input.keySet()) {
            int candidate = calculate0(source, input);

            if(candidate == Integer.MIN_VALUE) return Integer.MIN_VALUE;

            answer = min(answer, candidate);
        }
        return answer;
    }


    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {

        String[] input = new String[]{"algo-q4-g1.txt", "algo-q4-g2.txt", "algo-q4-g3.txt"};

        for (String s : input) {
            URL resource = BellmanFordModifiedAlgo.class.getResource("/" + s);
            List<String> lines = IOUtils.readLines(new FileReader(resource.getFile()));

            //estimated number of vertexes and edges 1000 47978
            Multimap<Integer, Tuple2<Integer, Integer>> map = HashMultimap.<Integer, Tuple2<Integer, Integer>>create(1000, 48);


            List<Tuple2<Integer, Tuple2<Integer, Integer>>> transform = Lists.transform(lines, new Function<String, Tuple2<Integer, Tuple2<Integer, Integer>>>() {
                @Override
                public Tuple2<Integer, Tuple2<Integer, Integer>> apply(String s) {
                    String[] split = s.split("\\s");
                    Tuple2<Integer, Tuple2<Integer, Integer>> integerTuple2Tuple2 = new Tuple2<Integer, Tuple2<Integer, Integer>>(Integer.valueOf(split[1]) - 1, new Tuple2<Integer, Integer>(Integer.valueOf(split[0]) - 1, Integer.valueOf(split[2])));
                    return integerTuple2Tuple2;
                }
            });

            for (Tuple2<Integer, Tuple2<Integer, Integer>> integerTuple2Tuple2 : transform) {
                map.put(integerTuple2Tuple2._1(), integerTuple2Tuple2._2());
            }


            System.out.println(calculate(map));
        }
    }
}


