/**
 * Created by Vijaya Yeruva on 5/27/2017.
 */

var events = require('events');
var eventEmitter = new events.EventEmitter();

//Create an event handler:
var myEventHandler = function () {
    console.log('I hear an alert!');
}

//Assign the event handler to an event:
eventEmitter.on('alert', myEventHandler);

//Fire the 'alert' event:
eventEmitter.emit('alert');