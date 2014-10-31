function Dataset() {
	
	this.divisions = {

		"bathRoom" : {

			"light" : {
				"status" : "off"
			}
		},

		"livingRoom" : {

			"light" : {
				"status" : "off"
			},		

			"tv" : {
				"status" : "off",
				"volume" : "0"
			}
		},

		"livingRoom" : {

			"airConditioner" : {
				"status" : "off"
			},

			"light" : {
				"status" : "off"
			},		

			"tv" : {
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

// Set bathroom status
Dataset.prototype.setBathRoomStatus = function(device, type, status, callback) {
	
	this.divisions['bathRoom'][device][type] = status;

	return callback({ success : true });
}

// Set livingroom status
Dataset.prototype.setLivingRoomStatus = function(device, type, status, callback) {
	
	this.divisions['livingRoom'][device][type] = status;

	return callback({ success : true });
}

module.exports = Dataset;