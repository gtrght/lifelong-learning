use city_data
db.zips.aggregate([
    {
        $group: {
            _id: "$state",
            max_pop: {$max: "$pop"}
        }
    }
])