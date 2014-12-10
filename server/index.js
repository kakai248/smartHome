var dataset = require('./dataset.js');
var express = require('express');
var app = express();

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

		if(date == 0) {
			handleMorning();
			console.log("morning");
		}
		else if(date == 2) {
			handleAfternoon();
			console.log("afternoon");
		}
		else if(date == 4) {
			handleNight();
			console.log("night");
		}

	}, 120000);

	function handleMorning() {
		// open all windows
		dataset['divisions']['livingroom']['windows']['status'] = true;
		dataset['divisions']['kitchen']['windows']['status'] = true;
		dataset['divisions']['room2']['windows']['status'] = true;
		dataset['divisions']['bathroom']['windows']['status'] = true;
		dataset['divisions']['hall']['windows']['status'] = true;

		// turn off living room lights
		dataset['divisions']['livingroom']['light']['status'] = false;
		dataset['divisions']['hall']['light']['status'] = false;
	}

	function handleAfternoon() {
		// turn on air conditioner
		dataset['divisions']['livingroom']['airconditioner']['status'] = true;
		dataset['divisions']['kitchen']['airconditioner']['status'] = true;
		dataset['divisions']['room1']['airconditioner']['status'] = true;
		dataset['divisions']['room2']['airconditioner']['status'] = true;
		dataset['divisions']['hall']['airconditioner']['status'] = true;
	}

	function handleNight() {
		// close all windows
		dataset['divisions']['livingroom']['windows']['status'] = false;
		dataset['divisions']['kitchen']['windows']['status'] = false;
		dataset['divisions']['room2']['windows']['status'] = false;
		dataset['divisions']['bathroom']['windows']['status'] = false;
		dataset['divisions']['hall']['windows']['status'] = false;

		// turn on lights
		dataset['divisions']['livingroom']['light']['status'] = true;
		dataset['divisions']['hall']['light']['status'] = true;

		// turn off air conditioner
		dataset['divisions']['livingroom']['airconditioner']['status'] = false;
		dataset['divisions']['kitchen']['airconditioner']['status'] = false;
		dataset['divisions']['room1']['airconditioner']['status'] = false;
		dataset['divisions']['room2']['airconditioner']['status'] = false;
		dataset['divisions']['hall']['airconditioner']['status'] = false;
	}
});