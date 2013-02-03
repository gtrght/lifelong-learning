package algorithms.data.array.sort;

/**
 * User: Vasily Vlasov
 * Date: 03.02.13
 */
public class InversionsCounter {
    /**
     * The number of internal inversions
     *
     * @return
     */
    public long calculateInversionsAndSort(int[] array) {
        return calculateInversionsAndSort(array, 0, array.length);
    }


    public long calculateInversionsAndSort(int[] array, int startIndex, int length) {
        if (length <= 1) {
            return 0;
        } else {
            long leftInternal = calculateInversionsAndSort(array, startIndex, (length) / 2);
            long rightInternal = calculateInversionsAndSort(array, startIndex + (length) / 2, length / 2 + (length % 2 == 0 ? 0 : 1));

            int[] tmpArray = new int[length];

            int tmpIndex = 0;
            int k = startIndex;

            int rightArrayBound = startIndex + (length) / 2;
            int j = rightArrayBound;
            int internalInversions = 0;
            while (tmpIndex < length) {
                boolean leftBound = k < rightArrayBound;
                boolean rightBound = j < startIndex + length;

                if (leftBound && rightBound) {
                    if (array[k] < array[j]) {
                        tmpArray[tmpIndex] = array[k];
                        k++;
                        internalInversions += (j - rightArrayBound);
                    } else {
                        tmpArray[tmpIndex] = array[j];
                        j++;
                    }
                } else if (leftBound) {
                    tmpArray[tmpIndex] = array[k];
                    internalInversions += length / 2 + (length % 2 == 0 ? 0 : 1);
                    k++;
                } else {
                    tmpArray[tmpIndex] = array[j];
                    j++;
                }
                tmpIndex++;
            }
            System.arraycopy(tmpArray, 0, array, startIndex, length);

            return leftInternal + rightInternal + internalInversions;
        }
    }
}
