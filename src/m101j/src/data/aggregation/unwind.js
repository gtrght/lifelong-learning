use city_data
db.zips.aggregate([
    {
        $unwind:"$loc"
    },
    {
        $limit: 10
    }
])