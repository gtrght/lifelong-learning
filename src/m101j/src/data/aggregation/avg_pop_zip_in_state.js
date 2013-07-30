use city_data
db.zips.aggregate([
    {
        $group: {
            _id: {
                "state": "$state",
                "city": "$city"
            },
            pop: {$max: "$pop"}
        }
    },
    {
        $group: {
            _id: "$_id.state",
            pop: {$avg: "$pop"}
        }
    }
])