function(head, req) {
    // !code vendor/couchapp/lib/underscore.js
    _ = this._;

    var CAPTURE_INTERVAL = 300000 + (10) /* some gap */

    start({
        "headers": { "Content-Type": "application/json" }
    })

    var row;
    var rows = {}

    while(row = getRow()){
        if(rows[row.key[1]] === undefined)
            rows[row.key[1]] = []
        
        rows[row.key[1]].push(row.value)
    }

    var times = {};
    _.each(rows, function(id, user){
        var time = 0;
        var prevTime = 0;
        _.each(rows[user], function(row){
            if(row._id - prevTime < CAPTURE_INTERVAL )
                time += (row._id - prevTime) // capture interval has been met
            else
                time += 0;// capture interval has been unmet, some idle time in between
            prevTime = row._id;
        });
        times[user] =  Math.ceil(time / 1000);
    })
    
    // // processing chunks
    // var resultRows = []
    // while(rows.length){
    //     row = rows.pop()
    //     if(row.id.indexOf("chunk:") == 0){
    //         // chunk detected
    //         var chunkId = row.id.split(":")
    //         var docId = chunkId.slice(1, chunkId.length - 1).join(":")

    //         // usually chunk should come *after* its document, so we're looking in resultRows only
    //         // CHANGEME this may not work if order is descending
    //         var doc = _.find(resultRows, function(it) { return it.id == docId }) 
    //         if(doc){
    //             delete row.value._id
    //             delete row.value._rev
    //             delete row.value.security
    //             _.extend(doc.value, row.value)
    //         }

    //     } else {
    //         resultRows.push(row)
    //     }
    // }
    send(JSON.stringify(times, null, 4))
}
