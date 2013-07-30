var cursor = db.images.find();


cursor.forEach(function (x) {
    if (db.albums.count({"images": x._id}) == 0) {
        db.images.remove({_id: x._id});
    }
});


