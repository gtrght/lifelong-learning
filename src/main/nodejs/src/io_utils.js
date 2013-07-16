/**
 * author: v.vlasov
 */


var fs = require('fs');

function loadArrayOfInts(filename) {
    var content = fs.readFileSync(filename, {encoding: 'utf-8'}).trim();
    var inputArray = content.split('\n').map(function (x) {
        return parseInt(x);
    });
    return inputArray;
}

exports.loadArrayOfInts = loadArrayOfInts;
