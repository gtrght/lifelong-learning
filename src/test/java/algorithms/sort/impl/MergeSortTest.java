package algorithms.sort.impl;

import algorithms.sort.BaseSortTester;
import algorithms.sort.Sorter;

/**
 * User: Vasily Vlasov
 * Date: 13.12.12
 */
public class MergeSortTest extends BaseSortTester {
    protected Sorter getSorter() {
        return new MergeSort();
    }
}
