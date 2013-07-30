use city_data
db.zips.aggregate([
    {
        $match: {
            "state": "NY"
        }
    }                    ,
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
        $project: {
            _id: 0,
            city: "$_id.city",
            pop: "$pop"
        }
    },
    {
        $sort: {
            pop:-1
        }
    }

])