var module = require('../reverse_count.js');

exports['Test Simple Cases'] = function (test) {
    test.equal(module.countInverses([4, 3]), 1);
    test.equal(module.countInverses([4, 3, 2]), 3);
    test.equal(module.countInverses([4, 3, 2 , 1]), 6);
    test.done();
};

exports['Complicated Cases'] = function (test) {
    test.equal(module.countInverses([2, 3, 1, 4]), 2);
    test.equal(module.countInverses([3, 7, 10, 14, 18, 19, 2, 11, 16, 17, 23, 25]), 13);
    test.equal(module.countInverses([1, 3, 2, 4, 5, 6]), 1);
    test.done();
};
