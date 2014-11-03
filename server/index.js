var dataset = require('./dataset.js');
var express = require('express');
var app = express();

app.get('/divisions', function (req, res) {

	dataset.getDivisionsStatus(function(callback) {
		res.json(callback);
	});
});

/*app.get('/devices', function (req, res) {

	dataset.getDevicesStatus(function(callback) {
		res.json(callback);
	});
});*/

// eg. : /livingroom/tv/volume/4
app.get('/:room/:device/:type/:status', function (req, res) {
	var room = req.params.room;
	var device = req.params.device;
	var type = req.params.type;
	var status = req.params.status;

	switch(room) {
		case 'livingroom':
			dataset.setLivingRoomStatus(device, type, status, function(callback) {
				res.json(callback);
			});
			break;

		case 'kitchen':
			dataset.setKitchenStatus(device, type, status, function(callback) {
				res.json(callback);
			});
			break;

		case 'room1':
			dataset.setRoom1Status(device, type, status, function(callback) {
				res.json(callback);
			});
			break;

		case 'room2':
			dataset.setRoom2Status(device, type, status, function(callback) {
				res.json(callback);
			});
			break;

		case 'bathroom':
			dataset.setBathRoomStatus(device, type, status, function(callback) {
				res.json(callback);
			});
			break;

		case 'hall':
			dataset.setHallStatus(device, type, status, function(callback) {
				res.json(callback);
			});
			break;
	}
});

var server = app.listen(3000, function () {
	var host = server.address().address;
	var port = server.address().port;

	console.log('Server running at http://%s:%s', host, port)

	// Create a Dataset instance
	dataset = new dataset();
});