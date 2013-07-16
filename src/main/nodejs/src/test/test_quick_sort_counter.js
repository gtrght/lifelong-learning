var module = require('../quick_sort.js');


exports['Test Median of Three Picker'] = function (test) {
    var arr = [8, 2, 4, 5, 7, 1];
    test.deepEqual(module.medianOf3(arr, 0, arr.length - 1), [2, 4]);
    test.deepEqual(module.medianOf3(arr, 3, arr.length - 1), [3, 5]);
    test.deepEqual(module.medianOf3(arr, 1, 4), [2, 4]);
    test.deepEqual(module.medianOf3(arr, 4, 5), [4, 7]);

    arr = [4, 5, 6, 7];
    test.deepEqual(module.medianOf3(arr, 0, arr.length - 1), [1, 5]);

    test.done();
};

exports['Test Simple Cases'] = function (test) {
    var input = [3, 9, 8 , 4  , 6   , 10   , 2     , 5      , 7       , 1];
    test.equal(module.countComparisonsFirstItem(input.slice(0, input.length)), 25);
    test.equal(module.countComparisonsLastItem(input.slice(0, input.length)), 29);
    test.equal(module.countComparisonsMedian(input.slice(0, input.length)), 21);
    test.done();
};


exports['Test 100 numbers'] = function (test) {
    var input = require('../io_utils.js').loadArrayOfInts('test/100.txt');
    test.equal(module.countComparisonsFirstItem(input.slice(0)), 615);
    test.equal(module.countComparisonsLastItem(input.slice(0)), 587);
    test.equal(module.countComparisonsMedian(input.slice(0)), 518);
    test.done();
};

exports['Test 1000 numbers'] = function (test) {
    var input = require('../io_utils.js').loadArrayOfInts('test/1000.txt');
    test.equal(module.countComparisonsFirstItem(input.slice(0)), 10297);
    test.equal(module.countComparisonsLastItem(input.slice(0)), 10184);
    test.equal(module.countComparisonsMedian(input.slice(0)), 8921);
    test.done();
};
