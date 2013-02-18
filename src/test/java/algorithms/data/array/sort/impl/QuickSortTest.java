package algorithms.data.array.sort.impl;

import org.apache.commons.io.IOUtils;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * User: Vasily Vlasov
 * Date: 17.02.13
 */
public class QuickSortTest {


    private boolean testArraySorted(int[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i - 1] > array[i]) return false;
        }
        return true;
    }


    @Test
    public void testSortSimple() {
        int[] ints = {5, 4, 3, 1, 2};
        QuickSort sorter = new QuickSortMedianThree();

        int[] sort = sorter.sort(ints);
        assertThat(testArraySorted(sort), Matchers.equalTo(true));
    }

    @Test
    public void testSortSimple2() {
        int[] ints = {1, 2, 3, 4, 5};
        QuickSort sorter = new QuickSortMedianThree();

        int[] sort = sorter.sort(ints);
        assertThat(testArraySorted(sort), Matchers.equalTo(true));
    }


    @Test
    public void testSortAndCount() throws Exception {
        int[] input = readArray();
        QuickSort sorter = new QuickSort();
        sorter.sort(input);

        for (int item : input) {
            System.out.print(item);
            System.out.print(", ");
        }
        System.out.println();
        assertThat(sorter.comparisons, Matchers.equalTo(162085));
        assertThat(testArraySorted(input), Matchers.equalTo(true));

        input = readArray();
        sorter = new QuickSortLast();
        sorter.sort(input);

        assertThat(sorter.comparisons, Matchers.equalTo(164123));
        assertThat(testArraySorted(input), Matchers.equalTo(true));

        input = readArray();
        sorter = new QuickSortMedianThree();
        sorter.sort(input);

        assertThat(sorter.comparisons, Matchers.equalTo(159894));
        assertThat(testArraySorted(input), Matchers.equalTo(true));
    }

    @Test
    public void testSortAndCountSimpleCases() throws Exception {
        int[] input = orderedArray(10, true);
        QuickSort sorter = new QuickSort();
        sorter.sort(input);

        assertThat(sorter.comparisons, Matchers.equalTo(45));
        assertThat(testArraySorted(input), Matchers.equalTo(true));

        input = orderedArray(10, true);
        sorter = new QuickSortLast();
        sorter.sort(input);

        assertThat(sorter.comparisons, Matchers.equalTo(45));
        assertThat(testArraySorted(input), Matchers.equalTo(true));

        input = orderedArray(10, true);
        sorter = new QuickSortMedianThree();
        sorter.sort(input);

        assertThat(sorter.comparisons, Matchers.equalTo(23));
        assertThat(testArraySorted(input), Matchers.equalTo(true));
    }

    private int[] orderedArray(int count, boolean b) {
        int[] ints = new int[count];
        for (int i = 0; i < ints.length; i++) {
            ints[i] = (b ? i + 1 : ints.length - i);

        }
        return ints;

    }

    @SuppressWarnings("unchecked")
    private int[] readArray() throws IOException {
        List<String> lines = IOUtils.readLines(QuickSortTest.class.getResourceAsStream("QuickSort.txt"));
        int[] input = new int[lines.size()];

        int index = 0;
        for (String line : lines) {
            input[index++] = Integer.parseInt(line);
        }
        return input;
    }
}
