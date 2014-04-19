function(doc) {
    emit([parseInt(doc._id), doc.username], doc);
};
