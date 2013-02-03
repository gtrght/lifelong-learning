package algorithms.data.array.sort.impl;

import algorithms.data.array.sort.Sorter;

/**
 * User: Vasily Vlasov
 * Date: 13.12.12
 */
public class InsertingSort implements Sorter {
    @Override
    public int[] sort(int[] input) {

        //starting from index 1
        for (int i = 1; i < input.length; i++) {
            int j = i;
            while (j >= 1 && input[j - 1] > input[j]) {
                int tmp = input[j];
                input[j] = input[j - 1];
                input[j - 1] = tmp;
                j--;
            }
        }

        return input;
    }
}
