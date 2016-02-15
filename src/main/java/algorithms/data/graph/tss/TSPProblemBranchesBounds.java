package algorithms.data.graph.tss;

import algorithms.data.LazyUnion;
import org.apache.commons.lang.ArrayUtils;
import scala.Function2;
import scala.Tuple4;

import java.util.Arrays;
import java.util.Comparator;

/**
 * User: Vasily Vlasov
 * Date: 28.01.13
 */
public class TSPProblemBranchesBounds implements TSPProblem {


    public float estimate(float[][] edges, int[][] includeMatrix) {
        int count = edges.length;

        float approximation = 0;

        for (int i = 0; i < count; i++) {
            boolean min1IsFixed = false;
            float min1 = Float.MAX_VALUE;
            boolean min2IsFixed = false;
            float min2 = Float.MAX_VALUE;

            for (int j = 0; j < edges.length; j++) {
                float edge = edges[i][j];

                if (includeMatrix[i][j] == 1) {
                    if (min1IsFixed) {
                        min2IsFixed = true;
                        min2 = edge;
                    } else {
                        min1IsFixed = true;
                        min1 = edge;
                    }
                    continue;
                } else if (includeMatrix[i][j] == -1) {
                    continue;
                }


                if (edge < min2 && !min2IsFixed) {
                    min2 = edge;
                }

                if (edge < min1 && !min1IsFixed) {
                    min2 = min1;
                    min1 = edge;


                }
            }
            approximation += (min1 + min2);
        }
        return approximation / 2;
    }


    @SuppressWarnings("unchecked")
    private float branch(float[][] edges, int[][] includes, int[] degree, LazyUnion<Integer> union, float bestApproximation, int level) {
        int length = edges.length;

        boolean finalState = true;
        int root = union.findRoot(0);
        for (int i = 0; i < edges.length; i++) {
            if (degree[i] < 2) {
                finalState = false;
                break;
            }
        }

        if (finalState) {
            float sum = sum(edges, includes);
            if (sum < bestApproximation)
                System.out.println("Found a candidate: " + sum);
            return sum;
        }

        for (int i = 0; i < length; i++) {
            if (degree[i] == 2) continue;
            root = union.findRoot(i);

            for (int j = i + 1; j < length; j++) {
                if (edges[i][j] == Float.MAX_VALUE || includes[i][j] != 0 || degree[j] == 2 || (root == union.findRoot(j) && !isFinalHop(degree, i, j))) continue;
                includes[i][j] = 1;
                includes[j][i] = 1;
                degree = ArrayUtils.clone(degree);
                degree[j] += 1;
                degree[i] += 1;

                float estimate1 = estimate(edges, includes);

                includes[i][j] = -1;
                includes[j][i] = -1;
                degree[j] += -1;
                degree[i] += -1;
                float estimate2 = estimate(edges, includes);

                Tuple4<Float, Integer, Integer, Integer>[] estimates = new Tuple4[]{
                        new Tuple4(estimate1, 1, degree[j] + 1, degree[i] + 1),
                        new Tuple4(estimate2, -1, degree[j], degree[i])
                };


                Arrays.sort(estimates, new Comparator<Tuple4<Float, Integer, Integer, Integer>>() {
                    @Override
                    public int compare(Tuple4<Float, Integer, Integer, Integer> o1, Tuple4<Float, Integer, Integer, Integer> o2) {
                        return o1._1().compareTo(o2._1());
                    }
                });

                for (Tuple4<Float, Integer, Integer, Integer> estimate : estimates) {
                    if (estimate._1() < bestApproximation) {
                        includes[j][i] = includes[i][j] = estimate._2();



                        degree[j] = estimate._3();
                        degree[i] = estimate._4();

                        LazyUnion<Integer> clone = union.clone();

                        if (estimate._2() > 0) {
                            clone.merge(i, j);
                        }
//                        String determiner = (estimate._2() > 0 ? "+" : "-") + i + "," + j;
//                        System.out.println(determiner + " (" + estimate + ")");
                        float candidate = branch(edges, includes, degree, clone, bestApproximation, level + 1);

                        if (candidate < bestApproximation) {
                            bestApproximation = candidate;
                        }
                    }
                }

                includes[i][j] = includes[j][i] = 0;
                break;
            }
            break;
        }
        return bestApproximation;
    }

    private boolean isFinalHop(int[] degree, int i, int j) {

        if (degree[i] == 1 && degree[j] == 1) {
            for (int k = 0; k < degree.length; k++) {
                if (k == i || j == k) continue;
                if (degree[k] != 2) return false;
            }
            return true;
        } else
            return false;
    }

    private float sum(float[][] edges, int[][] includes) {
        float sum = 0;
        for (int i = 0; i < edges.length; i++)
            for (int j = i; j < edges.length; j++) {
                if (includes[i][j] > 0) {
                    sum += edges[i][j];
                }
            }

        return sum;
    }

    private int[][] copy(int[][] includes) {
        int length = includes.length;
        int[][] copy = new int[length][length];
        for (int i = 0; i < length; i++)
            copy[i] = ArrayUtils.clone(includes[i]);

        return copy;
    }

    @Override
    public float calculateTSP(int numberOfPoints, Function2<Integer, Integer, Float> length) {
        float[][] edges = new float[numberOfPoints][numberOfPoints];
        int[][] includes = new int[numberOfPoints][numberOfPoints];
        int[] degrees = new int[numberOfPoints];
        Integer[] nodes = new Integer[numberOfPoints];

        for (int i = 0; i < edges.length; i++) {
            degrees[i] = 0;
            nodes[i] = i;
            for (int j = i; j < numberOfPoints; j++) {
                edges[j][i] = edges[i][j] = i == j ? Float.MAX_VALUE : length.apply(i, j);
                includes[j][i] = includes[i][j] = 0;
            }
        }

        float kruscal = new TSPProblemKruscal().calculateTSP(numberOfPoints, length);
        return branch(edges, includes, degrees, new LazyUnion<Integer>(nodes), kruscal, 0);
    }
}
