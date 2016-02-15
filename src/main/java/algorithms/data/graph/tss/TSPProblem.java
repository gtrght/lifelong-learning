package algorithms.data.graph.tss;

import scala.Function2;

/**
 * User: Vasily Vlasov
 * Date: 28.01.13
 */
public interface TSPProblem {
    float calculateTSP(int numberOfPoints, Function2<Integer, Integer, Float> length);
}
