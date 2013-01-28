package algorithms.algo2;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import scala.Function2;

import javax.annotation.Nullable;
import java.util.*;

import static java.util.Collections.singletonList;

/**
 * User: Vasily Vlasov
 * Date: 28.01.13
 */
public class TSPProblemDynamic implements TSPProblem{

    private Collection<List<Integer>> addPointToList(Collection<List<Integer>> sets, final int point) {
        return Collections2.transform(sets, new Function<List<Integer>, List<Integer>>() {
            @Nullable
            @Override
            public List<Integer> apply(@Nullable List<Integer> input) {
                ArrayList<Integer> integers = new ArrayList<Integer>();
                integers.add(point);
                integers.addAll(input);
                return integers;
            }
        });
    }

    private Collection<List<Integer>> generatePermutations0(List<Integer> points, int size, int startingPoint) {
        if (size == 0) return singletonList(Collections.<Integer>emptyList());

        Collection<List<Integer>> result = new HashSet<List<Integer>>();

        for (int i = startingPoint; i <= points.size() - size; i++) {
            Integer point = points.get(i);
            result.addAll(addPointToList(generatePermutations0(points, size - 1, i + 1), point));
        }

        return result;
    }

    public Collection<List<Integer>> generatePermutations(List<Integer> points, int size) {
        return addPointToList(generatePermutations0(points, size - 1, 1), points.get(0));
    }




    public float calculateTSP(int numberOfPoints, Function2<Integer, Integer, Float> length) {
        final List<Integer> points = new ArrayList<Integer>(numberOfPoints);
        for (int i = 0; i < numberOfPoints; i++) points.add(i);


        Table<String, Integer, Float> previousStep = HashBasedTable.create((int) Math.pow(2, points.size()), points.size());

        for (Integer point : points) {
            previousStep.put("0", 0, 0f);
        }

        for (int i = 2; i <= points.size(); i++) {
            Table<String, Integer, Float> paths = HashBasedTable.create((int) Math.pow(2, points.size()), points.size());
            Collection<List<Integer>> lists = generatePermutations(points, i);

            for (List<Integer> split : lists) {
                for (final int j : split) {
                    if (j == 0) continue;
                    float minPath = Float.MAX_VALUE;
                    String splitJ = Joiner.on(',').join(Collections2.filter(split, new Predicate<Integer>() {
                        @Override
                        public boolean apply(@Nullable Integer input) {
                            return j != input;
                        }
                    }));

                    for (int k : split) {
                        if (k == j) continue;

                        Float pathFrom1ToK = !previousStep.contains(splitJ, k) ? null : previousStep.get(splitJ, k);

                        if (pathFrom1ToK != null && pathFrom1ToK != Float.MAX_VALUE && length.apply(j, k) != Float.MAX_VALUE) {
                            float newPath = pathFrom1ToK + length.apply(j, k);
                            minPath = Math.min(minPath, newPath);
                        }
                    }

                    if (minPath != Float.MAX_VALUE) {
                        paths.put(Joiner.on(',').join(split), j, minPath);
                    }
                }
            }
            previousStep = paths;
        }

        float minPath = Float.MAX_VALUE;

        String finalHop = Joiner.on(',').join(points);
        for (Integer point : points) {
            Float pathToPoint = previousStep.get(finalHop, point);
            Float cPointToStart = length.apply(0, point);
            if (cPointToStart != Float.MAX_VALUE && pathToPoint != Float.MAX_VALUE) {
                minPath = Math.min(pathToPoint + cPointToStart, minPath);
            }
        }

        return minPath;
    }
}
