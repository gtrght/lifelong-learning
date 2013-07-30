use city_data
db.zips.aggregate([
    {
        $group: {
            _id: {"state": "$state"},
            population: {$sum: "$pop"}
        }
    }
])