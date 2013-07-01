#!/usr/bin/env node

var primes = [];
var index = 2;
var str = "";

str.indexOf()

function testIfPrime(number, primes) {
    var sqrtOfN = Math.sqrt(number);

    for (var i in primes) {
        if (primes[i] > sqrtOfN) break;
        if (number % primes[i] == 0) return false;
    }
    return true;

}

while (primes.length < 100) {
    if (testIfPrime(index, primes)) {
        primes.push(index);
//        console.log(index);
    }
    index++;
}

var fs = require('fs');
var outfile = "primes.txt";
console.log(primes);
fs.writeFileSync(outfile, primes);
