db.messages.aggregate([
    {
        $unwind: "$headers.To"
    },
    {
        $group: {
            "_id": {
                "from": "$headers.From",
                "to": "$headers.To"
            },
            "messages": {$sum: 1}
        }
    },
    {
        $sort: {"messages": -1}
    },
    {
        $limit: 3
    }
]);

