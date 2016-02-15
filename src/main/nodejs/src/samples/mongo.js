//Verifying the connection to mongo works fine

var MongoClient = require('mongodb').MongoClient;
var JsHamcrest = require('jshamcrest').JsHamcrest;

JsHamcrest.Integration.copyMembers(this);
JsHamcrest.assert = function () {
    console.log('Asserting');
};


JsHamcrest.assert();
equalTo('10').matches(12);       // Expected: true


MongoClient.connect("mongodb://localhost:27017/albums", function (err, db) {
    if (err) console.log(err);
    else {
        var collection = db.collection("albums");
        collection.find({}, {_id: 1}, {limit: 10, sort: {_id: -1}})
            .each(function (err, doc) {
                if (doc) console.log(doc);
            });
    }
});

