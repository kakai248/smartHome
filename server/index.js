var dataset = require('./dataset.js');
var express = require('express');
var app = express();

// eg. : /livingroom
app.get('/:room', function (req, res) {
	var room = req.params.room;

	res.json(dataset['divisions'][room]);
});

// eg. : /livingroom/tv/volume/4
app.get('/:room/:device/:type/:status', function (req, res) {
	var room = req.params.room;
	var device = req.params.device;
	var type = req.params.type;
	var status = req.params.status;

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