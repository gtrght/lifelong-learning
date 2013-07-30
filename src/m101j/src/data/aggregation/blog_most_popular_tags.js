use blog
db.posts.aggregate([
    {
        $project: {
            "tags": "$tags"
        }
    }                     ,
    {
        $unwind: "$tags"
    },
    {
        $group: {
            _id: "$tags",
            tags: {$sum:1}
        }
    },
    {
        $sort:{
            "tags":-1
        }
    },
    {
        $limit: 10
    }
])