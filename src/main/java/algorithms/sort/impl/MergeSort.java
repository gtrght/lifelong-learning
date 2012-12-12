package algorithms.sort.impl;

import algorithms.sort.Sorter;

/**
 * User: Vasily Vlasov
 * Date: 13.12.12
 */
public class MergeSort implements Sorter {
    @Override
    public int[] sort(int[] input) {
        if (input.length <= 1) return input;
        return sort0(input, new int[input.length], 0, (input.length - 1) / 2, input.length - 1);
    }

    private int[] sort0(int[] input, int[] out, int start, int med, int end) {
        if (end - start == 0) {
            out[start] = input[start];
            return out;
        }
        sort0(input, out, start, start + (med - start) / 2, med);
        sort0(input, out, med + 1, med + 1 + (end - med) / 2, end);

        //merge the sorted arrays
        int index = start;
        int in1 = start;
        int in2 = med + 1;

        boolean leftBound;

        while ((leftBound = in1 <= med) || (in2 <= end)) {
            if (!leftBound || (in2 <= end && input[in1] > input[in2])) {
                out[index] = input[in2];
                in2++;
            } else {
                out[index] = input[in1];
                in1++;
            }
            index++;
        }
        System.arraycopy(out, start, input, start, end - start);

        return out;
    }
}
