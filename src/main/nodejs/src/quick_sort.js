/**
 * The file contains all of the integers between 1 and 10,000 (inclusive, with no repeats) in unsorted order. The integer in the ith row of the file gives you the ith entry of an input array.
 *
 * Your task is to compute the total number of comparisons used to sort the given input file by QuickSort. As you know, the number of comparisons depends on which elements are chosen as pivots, so we'll ask you to explore three different pivoting rules.
 * You should not count comparisons one-by-one. Rather, when there is a recursive call on a subarray of length m, you should simply add m−1 to your running total of comparisons. (This is because the pivot element is compared to each of the other m−1 elements in the subarray in this recursive call.)
 * author: v.vlasov
 */


function firstItem(arr, start, end) {
    return [start, arr[start]];
}

function lastItem(arr, start, end) {
    return [end, arr[end]];
}

function medianOfThree(arr, start, end) {
    var medianIndex = start + Math.floor((end - start) / 2);
    var subArray = [
        [start, arr[start]],
        [medianIndex, arr[medianIndex]],
        [end, arr[end]]
    ];
    var sorted = subArray.sort(function (x, y) {
        return x[1] < y[1];
    });
    return sorted[1];
}

function swap(arr, index1, index2) {
    var tmp = arr[index1];
    arr[index1] = arr[index2];
    arr[index2] = tmp;
}

function partition(arr, start, end, pivotStrategy) {
    var pivot = pivotStrategy(arr, start, end)[0];
    swap(arr, start, pivot);
    var i = start + 1;
    for (var j = start + 1; j <= end; j++) {
        if (arr[j] < arr[start]) {
            swap(arr, i, j);
            i++;
        }
    }
    swap(arr, i - 1, start);
    return i - 1;
}


function quickSortWithCounter(arr, start, end, pivotStrategy) {
    if (start >= end) return 0;

    var pivot = partition(arr, start, end, pivotStrategy);
    var compLeft = quickSortWithCounter(arr, start, pivot - 1, pivotStrategy);
    var compRight = quickSortWithCounter(arr, pivot + 1, end, pivotStrategy);
    return compLeft + compRight + (end - start);
}

function countComparisonsFirstItem(arr) {
    return quickSortWithCounter(arr, 0, arr.length - 1, firstItem)
}

function countComparisonsLastItem(arr) {
    return quickSortWithCounter(arr, 0, arr.length - 1, lastItem)
}

function countComparisonsMedian(arr) {
    return quickSortWithCounter(arr, 0, arr.length - 1, medianOfThree)
}

if (require.main == module) {
    console.log('Starting application from a command line.');
    var arr = require('./io_utils.js').loadArrayOfInts('quick_sort.txt');
    console.log(countComparisonsFirstItem(arr.slice(0)));
    console.log(countComparisonsLastItem(arr.slice(0)));
    console.log(countComparisonsMedian(arr.slice(0)));
//    console.log(arr.slice(1, 20));
}


exports.medianOf3 = medianOfThree;
exports.quickSortWithCounter = quickSortWithCounter;
exports.countComparisonsFirstItem = countComparisonsFirstItem;
exports.countComparisonsLastItem = countComparisonsLastItem;
exports.countComparisonsMedian = countComparisonsMedian;



