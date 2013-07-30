use city_data
db.zips.aggregate([
    {
        $group: {
            _id: "$city",
            zips: {$addToSet: "$_id"}
        }
    }
])