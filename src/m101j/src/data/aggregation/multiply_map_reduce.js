use matrix;

var row_num = 2;
var col_num = 3;

db.matrix.remove({});

for (i = 0; i < row_num; i++) {
    for (var j = 0; j < col_num; j++) {
        db.matrix.insert({"name": "M1", row: i, col: j, value: col_num * i + j});
        db.matrix.insert({"name": "M2", row: j, col: i, value: row_num * j + i});
    }
}

db.matrix.find({name:"M1"}, {}).sort({row: 1, col: 1});
db.matrix.find({name:"M2"}, {}).sort({row: 1, col: 1});

mapFunction = function () {
    if (this.name == "M1") {
        for (var i = 0; i < 2; i++)
            emit({row: this.row, col: i}, {name: 1, m_index: this.row * 10000 + i * 100 + this.col, value: this.value});
    }
    else if (this.name == "M2") {
        for (var i = 0; i < 2; i++)
            emit({row: i, col: this.col}, {name: 2, m_index: i * 10000 + this.col * 100 + this.row, value: this.value});
    }
};

reduceFunction = function (key, values) {
    var multipliers = {};
    var sum = 0;

    values.forEach(function (x) {
        if (multipliers[x.m_index] != undefined)
            sum += multipliers[x.m_index] * x.value;
        else
            multipliers[x.m_index] = x.value
    });

    return {value: sum};
};

db.matrix.mapReduce(mapFunction, reduceFunction, { out: "matrix_result" });
db.matrix_result.find();
