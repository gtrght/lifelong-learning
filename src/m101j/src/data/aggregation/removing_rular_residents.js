use city_data
db.zips.aggregate([
    {
        $project: {
            pop: "$pop",
            first_char: {$substr: ["$city", 0, 1]},

        }
    },
    {
        $match: {
            "first_char": {$regex: "\\d"}
        }
    },
    {
        $group: {
            _id: null,
            count: {$sum: "$pop"}
        }
    }
])