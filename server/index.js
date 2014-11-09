var dataset = require('./dataset.js');
var express = require('express');
var app = express();

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

var server = app.listen(3000, function () {
	var host = server.address().address;
	var port = server.address().port;

	console.log('Server running at http://%s:%s', host, port)

	// Create a Dataset instance
	dataset = new dataset();
});