var dataset = require('./dataset.js');
var express = require('express');
var app = express();

app.get('/divisions', function (req, res) {
	res.json(dataset['divisions']);
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