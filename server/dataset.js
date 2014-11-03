function Dataset() {
	
	this.divisions = {

		"livingroom" : {

			"light" : {
				"status" : "off"
			},

			"airconditioner" : {
				"status" : "off"
			},	

			"tv" : {
				"status" : "off",
				"volume" : "0"
			},

			"windows" : {
				"status" : "off"
			},

			"soundsystem" : {
				"status" : "off",
				"volume" : "0"
			}
		},

		"kitchen" : {

			"light" : {
				"status" : "off"
			},

			"airconditioner" : {
				"status" : "off"
			},	

			"tv" : {
				"status" : "off",
				"volume" : "0"
			},

			"windows" : {
				"status" : "off"
			},

			"soundsystem" : {
				"status" : "off",
				"volume" : "0"
			}
		},

		"room1" : {

			"light" : {
				"status" : "off"
			},

			"airconditioner" : {
				"status" : "off"
			},	

			"tv" : {
				"status" : "off",
				"volume" : "0"
			},

			"windows" : {
				"status" : "off"
			},

			"soundsystem" : {
				"status" : "off",
				"volume" : "0"
			}
		},

		"room2" : {

			"light" : {
				"status" : "off"
			},

			"airconditioner" : {
				"status" : "off"
			},	

			"tv" : {
				"status" : "off",
				"volume" : "0"
			},

			"windows" : {
				"status" : "off"
			},

			"soundsystem" : {
				"status" : "off",
				"volume" : "0"
			}
		},

		"bathroom" : {

			"light" : {
				"status" : "off"
			},

			"airconditioner" : {
				"status" : "off"
			},

			"windows" : {
				"status" : "off"
			},

			"soundsystem" : {
				"status" : "off",
				"volume" : "0"
			}
		},

		"hall" : {

			"light" : {
				"status" : "off"
			},

			"airconditioner" : {
				"status" : "off"
			},

			"windows" : {
				"status" : "off"
			},

			"soundsystem" : {
				"status" : "off",
				"volume" : "0"
			}
		}
		
	};
}

// Get divisions statuses
Dataset.prototype.getDivisionsStatus = function(callback) {
	return callback( this.divisions );
}

// Get devices statuses
/*Dataset.prototype.getDevicesStatus = function(callback) {
	var devices;
	var divisions = this.divisions;

	Object.keys(divisions).forEach(function (div) {

		console.log('-> ' + div);

		Object.keys(divisions[div]).forEach(function (device) {
			console.log('-- ' + device);

			if(! devices[device]) {
				console.log('wat');

				devices[device] = divisions[div][device];
			}
				
				//devices[device] = divisions[div][device];

		});
	});

	return callback( this.divisions );
}*/

// Set livingroom status
Dataset.prototype.setLivingRoomStatus = function(device, type, status, callback) {
	
	this.divisions['livingroom'][device][type] = status;

	return callback({ success : true });
}

// Set kitchen status
Dataset.prototype.setKitchenStatus = function(device, type, status, callback) {
	
	this.divisions['kitchen'][device][type] = status;

	return callback({ success : true });
}

// Set room1 status
Dataset.prototype.setRoom1Status = function(device, type, status, callback) {
	
	this.divisions['room1'][device][type] = status;

	return callback({ success : true });
}

// Set room2 status
Dataset.prototype.setRoom2Status = function(device, type, status, callback) {
	
	this.divisions['room2'][device][type] = status;

	return callback({ success : true });
}

// Set bathroom status
Dataset.prototype.setBathRoomStatus = function(device, type, status, callback) {
	
	this.divisions['bathroom'][device][type] = status;

	return callback({ success : true });
}

// Set hall status
Dataset.prototype.setHallStatus = function(device, type, status, callback) {
	
	this.divisions['hall'][device][type] = status;

	return callback({ success : true });
}



module.exports = Dataset;