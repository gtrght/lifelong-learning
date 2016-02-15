package algorithms.data.array.sort.impl;

import algorithms.data.array.sort.Sorter;

/**
 * User: Vasily Vlasov
 * Date: 13.12.12
 */
public class MergeSort implements Sorter {
    @Override
    public int[] sort(int[] input) {
        if (input.length <= 1) return input;
        int[] result = sort0(input, 0, (input.length - 1) / 2, input.length - 1);
        System.arraycopy(result, 0, input, 0, result.length);
        return result;
    }

    private int[] sort0(int[] input, int start, int med, int end) {
        if (end - start == 0) {
            return new int[]{input[start]};
        }
        int[] arr1 = sort0(input, start, start + (med - start) / 2, med);
        int[] arr2 = sort0(input, med + 1, med + 1 + (end - med) / 2, end);

        int in1 = 0;
        int in2 = 0;

        int[] result = new int[arr1.length + arr2.length];


        boolean left;
        while ((left = in1 < arr1.length) || (in2 < arr2.length)) {
            if ((in2 < arr2.length && left && (arr1[in1] > arr2[in2])) || !left) {
                result[in1 + in2] = arr2[in2];
                in2++;
            } else {
                result[in1 + in2] = arr1[in1];
                in1++;
            }
        }
        return result;
    }
}
