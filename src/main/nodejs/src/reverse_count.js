/**
 * This file contains all of the 100,000 integers between 1 and 100,000 (inclusive) in some order, with no integer repeated.
 *
 * Your task is to compute the number of inversions in the file given, where the ith row of the file indicates the ith entry of an array.
 *
 * author: v.vlasov
 */


function copyArrayToPos(src, dest, srcStart, destStart, count) {
    for (var i = 0; i < count; i++)
        dest[destStart + i] = src[srcStart + i]
}

function countInverse0(input, temp, start, end) {
    if (start == end) return 0;

    var mid = Math.floor((end + start) / 2);

    var inverseLeft = countInverse0(input, temp, start, mid);
    var inverseRight = countInverse0(input, temp, mid + 1, end);

    //then a merge step
    var externalReverses = 0;

    var i = start;
    var j = mid + 1;
    var iteration = 0;


    while (iteration <= (end - start)) {
        var position = (start + iteration);
        if (i > mid)
            input[ position] = temp[j++];
        else if (j > end) {
            input[ position] = temp[i++];
        }
        else if (temp[i] > temp[j]) {
            externalReverses += (mid - i + 1);
            input[position] = temp[j++];
        }
        else {
            input[position] = temp[i++];
        }
        iteration++;
    }

    copyArrayToPos(input, temp, start, start, end - start + 1);

    return inverseLeft + inverseRight + externalReverses;
}

function countInverses(input) {
    var inverses = countInverse0(input, input.slice(0), 0, input.length - 1);

    console.log(input);

    return inverses;
}


if (require.main == module) {
    console.log('Started from a command line');
    var fs = require('fs');
    var content = fs.readFileSync('reverse_count.txt', {encoding: 'utf-8'}).trim();
    var inputArray = content.split('\n').map(function (x) {
        return parseInt(x);
    });
//    console.log(inputArray.length);
    console.log(countInverses(inputArray));
}

exports.countInverses = countInverses;