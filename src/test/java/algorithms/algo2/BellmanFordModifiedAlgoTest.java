package algorithms.algo2;

import algorithms.data.graph.BellmanFordModifiedAlgo;
import com.google.common.collect.HashMultimap;
import org.hamcrest.Matchers;
import org.junit.Test;
import scala.Tuple2;

import static org.junit.Assert.assertThat;

/**
 * User: Vasily Vlasov
 * Date: 21.01.13
 */
public class BellmanFordModifiedAlgoTest {
    @Test
    public void testCalculate() throws Exception {
        HashMultimap<Integer, Tuple2<Integer, Integer>> map = HashMultimap.create(3, 2);

        map.put(0, new Tuple2<Integer, Integer>(2, 1));
        map.put(1, new Tuple2<Integer, Integer>(0, 2));
        map.put(2, new Tuple2<Integer, Integer>(1, 4));
        map.put(2, new Tuple2<Integer, Integer>(3, 1));
        map.put(3, new Tuple2<Integer, Integer>(0, 5));

        assertThat(BellmanFordModifiedAlgo.calculate(map), Matchers.equalTo(1));
    }

    @Test
    public void testNegativeCost() {
        HashMultimap<Integer, Tuple2<Integer, Integer>> map = HashMultimap.create(3, 2);

        map.put(0, new Tuple2<Integer, Integer>(2, 1));
        map.put(1, new Tuple2<Integer, Integer>(0, 2));
        map.put(2, new Tuple2<Integer, Integer>(1, 4));
        map.put(2, new Tuple2<Integer, Integer>(3, 1));
        map.put(3, new Tuple2<Integer, Integer>(0, -2));

        assertThat(BellmanFordModifiedAlgo.calculate(map), Matchers.equalTo(-2));
    }

    @Test
    public void testNegativeCostCycle() {
        HashMultimap<Integer, Tuple2<Integer, Integer>> map = HashMultimap.create(3, 2);

        map.put(0, new Tuple2<Integer, Integer>(2, 1));
        map.put(1, new Tuple2<Integer, Integer>(0, 2));
        map.put(2, new Tuple2<Integer, Integer>(1, 4));
        map.put(2, new Tuple2<Integer, Integer>(3, 1));
        map.put(3, new Tuple2<Integer, Integer>(0, -3));

        assertThat(BellmanFordModifiedAlgo.calculate(map), Matchers.equalTo(Integer.MIN_VALUE));
    }
}
