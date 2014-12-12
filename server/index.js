var dataset = require('./dataset.js');
var express = require('express');
var app = express();
var gcm = require('node-gcm');

var NOTIFICATION_MORNING = "morning";
var NOTIFICATION_MORNING_MESSAGE = "Opening windows and turning off lights..";
var NOTIFICATION_AFTERNOON = "afternoon";
var NOTIFICATION_AFTERNOON_MESSAGE = "Turning on air conditioner..";
var NOTIFICATION_NIGHT = "night";
var NOTIFICATION_NIGHT_MESSAGE = "Closing windows, turning off air conditioner and turning on lights..";

// overview
app.get('/', function (req, res) {
	var overview = [];
	var divisions = dataset['divisions'];

	for (key in divisions) {

		var division = {};
		division["division"] = key;
		division["name"] = divisions[key]["name"];
		division["light"] = divisions[key]["light"];
		division["airconditioner"] = divisions[key]["airconditioner"];
		division["windows"] = divisions[key]["windows"];
		
		overview.push(division);
	}

	res.json(overview);
});

// eg. : /livingroom or /light
app.get('/:roomOrDevice', function (req, res) {
	var roomOrDevice = req.params.roomOrDevice;

	// it it's a division
	if(roomOrDevice === "livingroom" || roomOrDevice === "kitchen" ||
		roomOrDevice === "room1" || roomOrDevice === "room2" ||
		roomOrDevice === "bathroom" || roomOrDevice === "hall") {
	
		res.json(dataset['divisions'][roomOrDevice]);
	}

	// if it's a device
	else {
		var devices = [];
		var divisions = dataset['divisions'];

		for (key in divisions) {
			var name = divisions[key]["name"];
			var device = divisions[key][roomOrDevice];

			var division = {};
			division["division"] = key;
			division["name"] = name;
			division[roomOrDevice] = device;
			
			devices.push(division);
		}

		res.json(devices);
	}

});

// eg. : /livingroom/tv/volume/4
app.get('/:room/:device/:type/:status', function (req, res) {
	var room = req.params.room;
	var device = req.params.device;
	var type = req.params.type;
	var status = req.params.status;

	if(status === "true")
		status = true;
	else if(status === "false")
		status = false;
	else
		status = parseInt(status); // status is an integer

	dataset['divisions'][room][device][type] = status;

	res.json({ success : true });
});

// revery notification action
app.get('/revert/:action', function (req, res) {
	var action = req.params.action;

	if(action === "morning")
		handleMorningAction(true);
	else if(action === "afternoon")
		handleAfternoonAction(true);
	else
		handleNightAction(true);

	res.json({ success : true });
});


var server = app.listen(3389, function () {
	var host = server.address().address;
	var port = server.address().port;

	console.log('Server running at http://%s:%s', host, port)

	// Create a Dataset instance
	dataset = new dataset();

	// for demonstrate purposes,
	// a full day is 6 minutes
	var date = 0;

	setInterval(function() {
		// tick
		date = (date+2) % 6;

		if(date == 0)
			handleMorning();
		else if(date == 2)
			handleAfternoon();
		else if(date == 4)
			handleNight();

	}, 120000);

	function handleMorning() {
		handleMorningAction(false);

		// send notification
		sendNotification(NOTIFICATION_MORNING, NOTIFICATION_MORNING_MESSAGE);
	}

	function handleAfternoon() {
		handleAfternoonAction(false);

		// send notification
		sendNotification(NOTIFICATION_AFTERNOON, NOTIFICATION_AFTERNOON_MESSAGE);
	}

	function handleNight() {
		handleNightAction(false);

		// send notification
		sendNotification(NOTIFICATION_NIGHT, NOTIFICATION_NIGHT_MESSAGE);
	}

	function sendNotification(revertMessage, messageText) {
		var message = new gcm.Message({
		    data: {
		    	revertMessage : revertMessage,
		        message: messageText
		    }
		});

		var sender = new gcm.Sender('######');
		var registrationIds = ['??????'];

		sender.send(message, registrationIds, 3, function (err, result) {});
	}
});

// if revert is true, we want to revert the
// default morning action
function handleMorningAction(revert) {
	// handle windows
	dataset['divisions']['livingroom']['windows']['status'] = !revert;
	dataset['divisions']['kitchen']['windows']['status'] = !revert;
	dataset['divisions']['room2']['windows']['status'] = !revert;
	dataset['divisions']['bathroom']['windows']['status'] = !revert;
	dataset['divisions']['hall']['windows']['status'] = !revert;

	// handle lights
	dataset['divisions']['livingroom']['light']['status'] = revert;
	dataset['divisions']['hall']['light']['status'] = revert;
}

// if revert is true, we want to revert the
// default afternoon action
function handleAfternoonAction(revert) {
	// handle air conditioners
	dataset['divisions']['livingroom']['airconditioner']['status'] = !revert;
	dataset['divisions']['kitchen']['airconditioner']['status'] = !revert;
	dataset['divisions']['room1']['airconditioner']['status'] = !revert;
	dataset['divisions']['room2']['airconditioner']['status'] = !revert;
	dataset['divisions']['hall']['airconditioner']['status'] = !revert;
}

// if revert is true, we want to revert the
// default night action
function handleNightAction(revert) {
	// handle windows
	dataset['divisions']['livingroom']['windows']['status'] = revert;
	dataset['divisions']['kitchen']['windows']['status'] = revert;
	dataset['divisions']['room2']['windows']['status'] = revert;
	dataset['divisions']['bathroom']['windows']['status'] = revert;
	dataset['divisions']['hall']['windows']['status'] = revert;

	// handle lights
	dataset['divisions']['livingroom']['light']['status'] = !revert;
	dataset['divisions']['hall']['light']['status'] = !revert;

	// handle air conditioners
	dataset['divisions']['livingroom']['airconditioner']['status'] = revert;
	dataset['divisions']['kitchen']['airconditioner']['status'] = revert;
	dataset['divisions']['room1']['airconditioner']['status'] = revert;
	dataset['divisions']['room2']['airconditioner']['status'] = revert;
	dataset['divisions']['hall']['airconditioner']['status'] = revert;
}