use matrix;

use matrix

var row_num = 2;
var col_num = 3;

db.matrix1.remove({});
db.matrix2.remove({});

for (i = 0; i < row_num; i++) {
    for (var j = 0; j < col_num; j++) {
        db.matrix1.insert({row: i, col: j, value: col_num * i + j});
        db.matrix2.insert({row: j, col: i, value: row_num * j + i});
    }
}

db.matrix1.find({}, {}).sort({row: 1, col: 1});
db.matrix2.find({}, {}).sort({row: 1, col: 1});


map1 = function () {
    for (var i = 0; i < 2; i++)
        emit({matrix: 1, row: this.row, col: i}, {m_index: this.row * 10000 + i * 100 + this.col, value: this.value});
};

map2 = function () {
    for (var i = 0; i < 2; i++)
        emit({matrix: 2, row: i, col: this.col}, {m_index: i * 10000 + this.col * 100 + this.row, value: this.value});
};

reduceFunction = function (key, values) {
    return {value: values};
};

db.matrix1.mapReduce(map1, reduceFunction, { out: "matrix_intermediate" });
db.matrix2.mapReduce(map2, reduceFunction, { out: {merge: "matrix_intermediate"} });

db.matrix_intermediate.aggregate([
    {
        $unwind: "$value.value"
    },
    {
        $group: {
            _id: {
                m_index: "$value.value.m_index",
                row: "$_id.row",
                col: "$_id.col"
            },
            value1: {$min: "$value.value.value"},
            value2: {$max: "$value.value.value"}
        }
    },
    {
        $project: {
            _id: {
                row: "$_id.row",
                col: "$_id.col"
            },
            value: {$multiply: ["$value1", "$value2"]}
        }
    },
    {
        $group: {
            _id: {
                row: "$_id.row",
                col: "$_id.col"
            },
            value: {$sum: "$value"}
        }
    },
    {
        $project: {
            _id: 0,
            row: "$_id.row",
            col: "$_id.col",
            value: "$value"
        }
    }
]);

