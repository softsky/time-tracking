function(head, req) {
    // !code vendor/couchapp/lib/underscore.js
    _ = this._;

    var CAPTURE_INTERVAL = 300000 + (10) /* some gap */

    start({
        "headers": { "Content-Type": "application/json" }
    })

    var row;
    var event_map = {}

    while(row = getRow()){
        var wasInsert = false;
        if(event_map[row.value.username] === undefined)
            event_map[row.value.username] = [];
        var eventTime = parseInt(row.id);
        _.each(event_map[row.value.username], function(event){
            if(wasInsert == false)
                if(Math.abs(event.start - eventTime) < (CAPTURE_INTERVAL + 1000)){
                    event.start = Math.min(event.start, eventTime)                
                    wasInsert = true;
                } else if(Math.abs(event.end - eventTime) < (CAPTURE_INTERVAL + 1000)){
                    event.end = Math.max(event.end, eventTime)
                    wasInsert = true;
                };
        })
        if(!wasInsert)
            event_map[row.value.username].push({start: eventTime, end: eventTime + CAPTURE_INTERVAL})

    }
    var events = {};
    for(var user in event_map){
        _.each(event_map[user], function(evt){
            events[user] = events[user] || [];
            events[user].push({start: new Date(evt.start), end: new Date(evt.end)})
        })
    }

    send(JSON.stringify(events, null, 4))
}
