// ---------------------------------------------------------------

var eveUrl = "/BraveIntelServer/eve";
var eveData = {};
var eveInterval = 90000;
var eveHeat;
var eveMode = 0;

// ---------------------------------------------------------------

$(document).ready(function() {
    var config = {
	container: document.getElementById('map-heat'),
	radius: 80,
	maxOpacity: .9,
	minOpacity: 0,
	blur: .75
    };

    eveHeat = h337.create(config)
    $('#map-heat').css('position', 'absolute');

    eveLoad();

    eveId = setInterval(function() {
	console.log("Timer: eve");
	eveLoad();
    }, eveInterval);

});

// ---------------------------------------------------------------

function eveLoad() {
    $.ajax({
	async: true,
	url: eveUrl,
	mimeType: "application/json",
	dataType: 'json',
	error: eveLoadError,
	success: eveLoadSuccess,
    });
}

function eveLoadSuccess(response) {
    eveData = response;
    eveDraw();
}

function eveDraw() {
    max = 0;
    dataraw = [];

    for (i in drawData['map']['systems']) {
	sid = drawData['map']['systems'][i]['id'];
	x = Math.floor(drawData['map']['systems'][i]['x']) + drawSystemOffsetX;
	y = Math.floor(drawData['map']['systems'][i]['y']) + drawSystemOffsetY;
	x = x * drawScale;
	y = y * drawScale;

	if (eveMode == 1 && eveData['jumps'][sid] !== undefined) {
	    value = eveData['jumps'][sid];
	    max = Math.max(max, value);
	    dataraw.push({x: x, y: y, value: value });
	}

	if (eveData['kills'][sid] !== undefined) {
	    if (eveMode == 2) {
		value = eveData['kills'][sid]['ships'];
		max = Math.max(max, value);
		dataraw.push({x: x, y: y, value: value });
	    }
	    if (eveMode == 3) {
		value = eveData['kills'][sid]['pods'];
		max = Math.max(max, value);
		dataraw.push({x: x, y: y, value: value });
	    }
	    if (eveMode == 4) {
		value = eveData['kills'][sid]['rats'];
		max = Math.max(max, value);
		dataraw.push({x: x, y: y, value: value });
	    }
	}
    }

    max = Math.max(max, 10);
    eveHeat.setData({ max: max, min: 0, data: dataraw });
}

function eveClear() {
    eveHeat.setData({ max: 0, min: 0, data: [] });
}

function eveLoadError(error) {
    if (error.status == 401) {
	window.setTimeout(function() {
	    window.location.reload(true);
	}, 3000);
    }
}

// ---------------------------------------------------------------
