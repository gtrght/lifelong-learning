package algorithms.data.array.sort.impl;

/**
 * User: Vasily Vlasov
 * Date: 17.02.13
 */
public class QuickSortLast extends QuickSort {
    @Override
    protected PartitionFunction getPartitionFunction() {
        return new LastIndex();
    }
}
