use city_data
db.zips.aggregate([
    {
        $group: {
            _id: "$state",
            avg_pop: {$avg: "$pop"}
        }
    }
])