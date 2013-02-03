package algorithms.data.graph.tss;

import algorithms.data.LazyUnion;
import scala.Function2;
import scala.Tuple3;

import java.util.*;

/**
 * User: Vasily Vlasov
 * Date: 28.01.13
 */
public class TSPProblemKruscal implements TSPProblem {
    @Override
    public float calculateTSP(int numberOfPoints, Function2<Integer, Integer, Float> length) {
        int[] degrees = new int[numberOfPoints];
        Arrays.fill(degrees, 0);
        final List<Integer> points = new ArrayList<Integer>(numberOfPoints);
        for (int i = 0; i < numberOfPoints; i++) points.add(i);

        LazyUnion<Integer> union = new LazyUnion<Integer>(points);

        List<Tuple3<Integer, Integer, Float>> edges = new ArrayList<Tuple3<Integer, Integer, Float>>();

        for (int i = 0; i < numberOfPoints; i++) {
            for (int point = i + 1; point < numberOfPoints; point++) {
                if (i != point) {
                    int point1 = Math.min(i, point);
                    int point2 = Math.max(i, point);
                    edges.add(new Tuple3<Integer, Integer, Float>(point1, point2, length.apply(point1, point2)));
                }
            }
        }

        Collections.sort(edges, new Comparator<Tuple3<Integer, Integer, Float>>() {
            @Override
            public int compare(Tuple3<Integer, Integer, Float> o1, Tuple3<Integer, Integer, Float> o2) {
                return o1._3().compareTo(o2._3());
            }
        });


        List<Tuple3<Integer, Integer, Float>> included = new ArrayList<Tuple3<Integer, Integer, Float>>();

        for (Tuple3<Integer, Integer, Float> edge : edges) {
            Integer point1 = edge._1();
            Integer point2 = edge._2();

            if (union.findRoot(point1) != union.findRoot(point2) && degrees[point1] < 2 && degrees[point2] < 2) {
                included.add(edge);

                degrees[point1] += 1;
                degrees[point2] += 1;

                union.merge(point1, point2);
            }
        }

        int point1 = -1;
        int point2 = -1;


        for (int i = 0; i < degrees.length; i++) {
            int degree = degrees[i];
            if (degree == 1) {
                if (point1 == -1) point1 = i;
                else point2 = i;
            }
        }

        for (Tuple3<Integer, Integer, Float> edge : edges) {
            if (edge._1() == point1 && edge._2() == point2) {
                included.add(edge);
            }
        }

        float sum = 0;
        for (Tuple3<Integer, Integer, Float> integerIntegerFloatTuple3 : included) {
            sum += integerIntegerFloatTuple3._3();
        }

        return sum;
    }
}
