use city_data
db.zips.aggregate([
    {
        $project: {
            city: {$toLower: "$city"},
            pop: "$pop",
            state: "$state",
            zip: "$_id"
        }
    }
])