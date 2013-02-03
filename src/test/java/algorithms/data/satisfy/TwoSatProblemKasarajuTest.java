package algorithms.data.satisfy;

import algorithms.data.graph.AdjGraph;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;
import scala.Tuple2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * User: Vasily Vlasov
 * Date: 03.02.13
 */
public class TwoSatProblemKasarajuTest {

    @Test
    public void testSmallSamples2Neg() throws IOException {
        testSatisfied(false, 1);
    }

    @Test
    public void testSmallSamples2Pos() throws IOException {
        testSatisfied(true, 2);
    }

    @Test
    public void testSmallSamples3Pos() throws IOException {
        testSatisfied(true, 3);
    }

    @Test
    public void testSmallSamples4Pos() throws IOException {
        testSatisfied(true, 5);
    }

    @Test
    public void testSmallSamples5Neg() throws IOException {
        testSatisfied(false, 4);
    }


    @Test
    @Ignore
    public void testHugeSamples() throws IOException {
        for (int i = 1; i < 7; i++) {
            TwoSatProblemKasaraju solver = new TwoSatProblemKasaraju();
            String fileName = String.format("/twosat/2sat%s.txt", i );
            Tuple2<Integer, List<Tuple2<Integer, Integer>>> tuple2 = readDisjunctions(fileName);
            boolean b = solver.checkSatisfiable(tuple2._2(), tuple2._1());
            System.out.print(b ? 1 : 0);
        }
    }

    private void testSatisfied(boolean answer, int i) throws IOException {
        TwoSatProblemKasaraju solver = new TwoSatProblemKasaraju();
        String fileName = String.format("/twosat/test%s.txt", i );
        Tuple2<Integer, List<Tuple2<Integer, Integer>>> tuple2 = readDisjunctions(fileName);
        boolean b = solver.checkSatisfiable(tuple2._2(), tuple2._1());
        assertThat(fileName + " answer: " + b, Matchers.equalTo(fileName + " answer: " + answer));
    }

    public static Tuple2<Integer, List<Tuple2<Integer, Integer>>> readDisjunctions(String resource) throws IOException {
        InputStream inputStream = TwoSatProblemKasarajuTest.class.getResourceAsStream(resource);

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        Integer numberOfVertices = Integer.valueOf(reader.readLine());
        List<Tuple2<Integer, Integer>> result = new ArrayList<Tuple2<Integer, Integer>>(numberOfVertices * 5);
        while (reader.ready()) {
            String[] split = reader.readLine().split(" ");
            result.add(new Tuple2<Integer, Integer>(Integer.parseInt(split[0]), Integer.parseInt(split[1])));
        }

        return new Tuple2<Integer, List<Tuple2<Integer, Integer>>>(numberOfVertices, result);
    }
}
