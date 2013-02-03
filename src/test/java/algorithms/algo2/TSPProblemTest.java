package algorithms.algo2;

import algorithms.data.graph.tss.TSPProblemBranchesBounds;
import algorithms.data.graph.tss.TSPProblemDynamic;
import algorithms.data.graph.tss.TSPProblemKruscal;
import org.apache.commons.io.IOUtils;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import scala.Function2;
import scala.Tuple2;
import scala.runtime.AbstractFunction2;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * User: Vasily Vlasov
 * Date: 28.01.13
 */
public class TSPProblemTest {


    private TSPProblemDynamic problem;

    @Before
    public void setup() {
        problem = new TSPProblemDynamic();
    }

    @Test
    public void generatePermutationsTrivial() {
        Collection<List<Integer>> permutations = problem.generatePermutations(asList(1, 2, 3), 1);
        assertThat(permutations.size(), equalTo(1));
    }

    @Test
    public void generatePermutations() {
        Collection<List<Integer>> permutations = problem.generatePermutations(asList(1, 2, 3, 4), 2);
        assertThat(permutations.size(), equalTo(3));

        permutations = problem.generatePermutations(asList(1, 2, 3, 4), 3);
        assertThat(permutations.size(), equalTo(3));
    }

    @Test
    public void calculateTSPTrivial() throws IOException {
        float shortestPath = new TSPProblemDynamic().calculateTSP(4, createFixedPathFunction("tsp1.txt"));

        assertThat(shortestPath, Matchers.equalTo(9f));
    }

    @Test
    public void calculateTSPKruscalTrivial() throws IOException {
        float shortestPath = new TSPProblemKruscal().calculateTSP(4, createFixedPathFunction("tsp1.txt"));

        assertThat(shortestPath, Matchers.equalTo(12f));
    }

    @Test
    public void calculateTSPBoundsTrivial() throws IOException {
        float shortestPath = new TSPProblemBranchesBounds().calculateTSP(4, createFixedPathFunction("tsp1.txt"));

        assertThat(shortestPath, Matchers.equalTo(9f));
    }

    @Test
    public void calculateTSPBoundsMedium() throws IOException {
        float shortestPath = new TSPProblemBranchesBounds().calculateTSP(4, createFixedPathFunction("tsp2.txt"));

        assertThat(shortestPath, Matchers.equalTo(21f));
    }

    @Test
    public void calculateTSPDynamicMedium() throws IOException {
        float shortestPath = new TSPProblemDynamic().calculateTSP(4, createFixedPathFunction("tsp2.txt"));

        assertThat(shortestPath, Matchers.equalTo(21f));
    }

    @Test
    public void calculateTSPBranchesMedium1() throws IOException {
        String[] files = new String[]{"tsp-xy2.txt", "tsp-xy3.txt", "tsp-xy4.txt", "tsp-xy5.txt"};
        int[] expectations = new int[]{8387, 8607, 32, 15, 4356};

        for (int i = 0; i < files.length; i++) {
            String file = files[i];
            int lines = countLines(file);
            float shortestPath = new TSPProblemBranchesBounds().calculateTSP(lines, createXYFunction(file));

            assertThat(((int) shortestPath), Matchers.equalTo(expectations[i]));
        }
    }

    @Test
    public void calculateTSPBoundsBurma() throws IOException {
        float shortestPath = new TSPProblemBranchesBounds().calculateTSP(14, createXYFunction("tsp-xy6.txt"));

        assertThat(shortestPath, Matchers.equalTo(30f));
    }

    @Test
    @Ignore
    public void calculateTSPBoundsLarge() throws IOException {
        float shortestPath = new TSPProblemBranchesBounds().calculateTSP(25, createXYFunction("tsp-xy1.txt"));

        assertThat(shortestPath, Matchers.equalTo(9f));
    }

    @Test
    public void calculateTSPKruscalLarge() throws IOException {
        float shortestPath = new TSPProblemKruscal().calculateTSP(25, createXYFunction("tsp-xy1.txt"));
        assertThat(shortestPath, Matchers.equalTo(32605.363F));
    }

    @Test
    @Ignore
    public void calculateTSPNonTrivial() throws IOException {
        float shortestPath = problem.calculateTSP(25, createXYFunction("tsp-xy1.txt"));
    }


    @SuppressWarnings("unchecked")
    private static Function2<Integer, Integer, Float> createXYFunction(String fileName) throws IOException {
        List<String> lines = IOUtils.readLines(TSPProblemTest.class.getResourceAsStream(fileName));
        final Map<Integer, Tuple2<Float, Float>> map = new HashMap<Integer, Tuple2<Float, Float>>();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] split = line.split(" ");

            map.put(i, new Tuple2<Float, Float>(Float.parseFloat(split[0]), Float.parseFloat(split[1])));
        }

        return new AbstractFunction2<Integer, Integer, Float>() {
            @Override
            public Float apply(Integer v1, Integer v2) {
                Tuple2<Float, Float> t1 = map.get(v1);
                Tuple2<Float, Float> t2 = map.get(v2);

                return (float) Math.sqrt((t1._1() - t2._1()) * (t1._1() - t2._1()) + (t1._2() - t2._2()) * (t1._2() - t2._2()));
            }
        };
    }


    @SuppressWarnings("unchecked")
    static Function2<Integer, Integer, Float> createFixedPathFunction(String fileName) throws IOException {
        List<String> lines = IOUtils.readLines(TSPProblemTest.class.getResourceAsStream(fileName));

        final Map<String, Float> map = new HashMap<String, Float>();
        for (String line : lines) {
            String[] split = line.split(" ");
            map.put(split[0], Float.valueOf(split[1]));
        }

        return new AbstractFunction2<Integer, Integer, Float>() {
            @Override
            public Float apply(Integer v1, Integer v2) {
                String key;
                if (v1 < v2)
                    key = v1 + "," + v2;
                else
                    key = v2 + "," + v1;

                return map.containsKey(key) ? map.get(key) : Float.MAX_VALUE;
            }
        };
    }

    private int countLines(String s) throws IOException {
        return IOUtils.readLines(TSPProblemTest.class.getResourceAsStream(s)).size();
    }


}
