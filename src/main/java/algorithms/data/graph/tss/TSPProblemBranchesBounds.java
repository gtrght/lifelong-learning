package algorithms.data.graph.tss;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
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
    private float branch(float[][] edges, int[][] includes, int[] degree, float bestApproximation, int level) {
        int length = edges.length;

        boolean finalState = true;
        for (int aDegree : degree) {
            if (aDegree != 2) {
                finalState = false;
                break;
            }
        }

        if (finalState) {
            float sum = sum(edges, includes);
            if(sum < bestApproximation)
                System.out.println("Found a candidate: " + sum);
            return sum;
        }

        for (int i = 0; i < length; i++) {
            if (degree[i] == 2) continue;

            for (int j = i + 1; j < length; j++) {
                if (edges[i][j] == Float.MAX_VALUE || degree[i] >= 2 ||
                        includes[i][j] != 0 || (degree[i] == degree[j] &&
                        degree[i] == 1 && hasUnfinishedWork(degree, i, j))) continue;
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


                        String determiner = (estimate._2() > 0 ? "+" : "-") + i + "," + j;

                        degree[j] = estimate._3();
                        degree[i] = estimate._4();

                        if ("6,11".equals(determiner))
                            System.out.print(' ');
                        System.out.println(StringUtils.repeat(" ", level) + determiner + " (" + estimate + ")");
                        float candidate = branch(edges, includes, degree, bestApproximation, level + 1);

                        if (i == 0 && j == 1) {
                            System.out.println();
                        }

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

    private boolean hasUnfinishedWork(int[] degree, int i, int j) {
        for (int i1 = 0; i1 < degree.length; i1++) {
            if (i1 != i && i1 != j && degree[i1] < 2) return true;
        }
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

        for (int i = 0; i < edges.length; i++) {
            degrees[i] = 0;
            for (int j = i; j < numberOfPoints; j++) {
                edges[j][i] = edges[i][j] = i == j ? Float.MAX_VALUE : length.apply(i, j);
                includes[j][i] = includes[i][j] = 0;
            }
        }

        float kruscal = new TSPProblemKruscal().calculateTSP(numberOfPoints, length);
        return branch(edges, includes, degrees, kruscal, 0);
    }
}
