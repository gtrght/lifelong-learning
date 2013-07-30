use city_data
db.zips.aggregate([
    {
        $match: {
            state: "NY",
            pop: {$gt: 50000}
        }

    }   ,
    {
        $group: {
            _id: {
                state: "$state",
                city: "$city"
            },
            zip_in_city: {$sum: 1}
        }
    },
    {
        $group: {
            _id: "$_id.state",
            cities: {$addToSet: "$_id.city"},
            cities_count: {$sum: 1},
            total_zips: {$sum: "$zip_in_city"}
        }
    }                                  ,
    {
        $project: {
            cities: "$cities_count",
            zips: "$total_zips"
        }
    }
])