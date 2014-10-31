var dataset = require('./dataset.js');
var express = require('express');
var app = express();

app.get('/', function (req, res) {

	dataset.getDivisionsStatus(function(callback) {
		res.json(callback);
	});
});

// exemplo : /bathroom/light/status/on
app.get('/bathroom/:device/:type/:status', function (req, res) {
	var device = req.params.device;
	var type = req.params.type;
	var status = req.params.status;

	dataset.setBathRoomStatus(device, type, status, function(callback) {
		res.json(callback);
	});
});

// exemplo : /livingroom/tv/volume/4
app.get('/livingroom/:device/:type/:status', function (req, res) {
	var device = req.params.device;
	var type = req.params.type;
	var status = req.params.status;

	dataset.setLivingRoomStatus(device, type, status, function(callback) {
		res.json(callback);
	});
});

var server = app.listen(3000, function () {
	var host = server.address().address;
	var port = server.address().port;

	console.log('Server running at http://%s:%s', host, port)

	// Create a Dataset instance
	dataset = new dataset();
});