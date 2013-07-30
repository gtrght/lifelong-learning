use city_data
db.zips.aggregate([
    {
        $group: {
            _id: {
                "state": "$state",
                "city": "$city"
            },
            pop: {$sum: "$pop"}
        }
    },
    {
        $sort: {
            "_id.state": 1,
            "pop": -1
        }
    },
    {
        $group: {
            _id:"$_id.state",
            city: {$first: "$_id.city"},
            pop: {$first: "$pop"}
        }
    }
])