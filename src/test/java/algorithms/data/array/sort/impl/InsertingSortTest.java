package algorithms.data.array.sort.impl;

import algorithms.data.array.sort.BaseSortTester;
import algorithms.data.array.sort.Sorter;

/**
 * User: Vasily Vlasov
 * Date: 13.12.12
 */
public class InsertingSortTest extends BaseSortTester {
    protected Sorter getSorter() {
        return new InsertingSort();
    }
}
