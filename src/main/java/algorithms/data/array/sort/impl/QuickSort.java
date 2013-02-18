package algorithms.data.array.sort.impl;

import algorithms.data.array.sort.Sorter;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Comparator;

/**
 * User: Vasily Vlasov
 * Date: 17.02.13
 */
public class QuickSort implements Sorter {
    protected int comparisons = 0;

    @Override
    public int[] sort(int[] input) {
        return sort0(input, 0, input.length - 1, getPartitionFunction());
    }

    protected PartitionFunction getPartitionFunction() {
        return new FirstIndex();
    }

    public int[] sort0(int[] input, int lo, int hi, PartitionFunction partition) {
        if (lo >= hi) return input;
        comparisons += (hi - lo);

        int j = partition(input, lo, hi, partition);
        sort0(input, lo, j - 1, partition);
        sort0(input, j + 1, hi, partition);
        return input;
    }

    public int partition(int[] array, int lo, int hi, PartitionFunction function) {
        int pivot = function.apply(array, lo, hi);
        exchange(array, lo, pivot);
        pivot = lo;
        int i = lo + 1;
        int j = i;

        while (j <= hi) {
            while (j <= hi && array[j] > array[pivot]) j++;
            while (i <= hi && i < j && array[i] <= array[pivot]) i++;

            if (j <= hi) {
                if (i != j) {
                    exchange(array, i, j);
                }
                i++;
                j++;
            } else
                break;
        }
        if (array[pivot] > array[i - 1]) {
            exchange(array, i - 1, pivot);
        }
        return i - 1;
    }

    private void exchange(int[] array, int i, int j) {
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }


    static interface PartitionFunction {
        int apply(int[] array, int lo, int hi);
    }


    static class FirstIndex implements PartitionFunction {
        @Override
        public int apply(int[] array, int lo, int hi) {
            return lo;
        }
    }

    static class LastIndex implements PartitionFunction {
        @Override
        public int apply(int[] array, int lo, int hi) {
            return hi;
        }
    }

    @SuppressWarnings("unchecked")
    static class MedianOfThree implements PartitionFunction {
        @Override
        public int apply(int[] array, int lo, int hi) {
            Tuple2[] sub = {new Tuple2(array[lo], lo), new Tuple2(array[(hi - lo) / 2], (hi - lo) / 2), new Tuple2(array[hi], hi)};
            Arrays.sort(sub, new Comparator<Tuple2<Integer, Integer>>() {
                @Override
                public int compare(Tuple2<Integer, Integer> o1, Tuple2<Integer, Integer> o2) {
                    return o1._1().compareTo(o2._1());
                }
            });
            return (Integer) sub[1]._2();
        }
    }


}
