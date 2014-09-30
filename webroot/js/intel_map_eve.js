// ---------------------------------------------------------------

var eveData = {};
var eveUrl = "/BraveIntelServer/evedata";
var eveInterval = 90000;
var eveHeat;
var eveMode = 0;
var eveId = 0;

// ---------------------------------------------------------------

$(document).ready(function() {
    eveHeat = simpleheat('canvas-heat');
});

// ---------------------------------------------------------------

function evePoll() {
    if (eveId != 0) {
	clearInterval(eveId);
	eveId = 0;
    }

    eveLoad();
    eveId = setInterval(function() {
	console.log("Timer: eve");
	eveLoad();
    }, eveInterval);
}

function eveLoad() {
    $.ajax({
	async: true,
	url: eveUrl + "?region=" + drawRegion,
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

function eveLoadError(error) {
    if (error.status == 401) {
	window.setTimeout(function() {
	    window.location.reload(true);
	}, 3000);
    }
    eveClear();
}

// ---------------------------------------------------------------

function eveClear() {
    if (eveHeat != null) {
	eveHeat.clear();
	eveHeat.resize();
	eveHeat.draw();
    }
}

function eveLegend(max) {

    var c = $("#map-heat-legend canvas")['0'];
    var ctx = c.getContext("2d");

    ctx.clearRect(0, 0, c.offsetWidth, c.offsetHeight);
    if (max == 0) {
	return;
    }

    var l = 5;
    var t = 15;
    var w = 100;
    var h = 6;

    var grd = ctx.createLinearGradient(l, 0, l + w, 0);
    grd.addColorStop(0.0, "black");
    grd.addColorStop(0.4, "blue");
    grd.addColorStop(0.6, "cyan");
    grd.addColorStop(0.7, "lime");
    grd.addColorStop(0.8, "yellow");
    grd.addColorStop(1.0, "red");

    ctx.fillStyle = grd;
    ctx.fillRect(l, t, w, h);

    ctx.strokeStyle = '#333333';
    ctx.strokeRect(l - 1, t - 1, w + 2,  h + 2);

    ctx.fillStyle = "#999999";
    ctx.textAlign="start"; 
    ctx.fillText("0", l - 2, t - 4);
    ctx.textAlign="end"; 
    ctx.fillText(max, l + w + 2, t - 4);
}

function eveDraw() {
    if (eveHeat === undefined) {
	return;
    }

    if (drawData['map'] === undefined) {
	return;
    }

    var max = 0;
    var dataraw = [];

    if (eveMode == 0) {
	$("#map-heat-legend").addClass('hide');
    } else {
	$("#map-heat-legend").removeClass('hide');
    }

    for (i in drawData['map']['systems']) {
	sid = drawData['map']['systems'][i]['id'];
	x = Math.floor(drawData['map']['systems'][i]['x']) + drawSystemOffsetX;
	y = Math.floor(drawData['map']['systems'][i]['y']) + drawSystemOffsetY;
	x = x * drawScale;
	y = y * drawScale;

	if (eveData['jumps'] !== undefined) {
	    if (eveMode == 1 && eveData['jumps'][sid] !== undefined) {
		    max = eveHeatDataAdd(dataraw, x, y, eveData['jumps'][sid], max);
	    }
	}
	if (eveData['kills'] !== undefined) {
	    if (eveData['kills'][sid] !== undefined) {
		if (eveMode == 2) {
		    max = eveHeatDataAdd(dataraw, x, y, eveData['kills'][sid]['ships'], max);
		}
		if (eveMode == 3) {
		    max = eveHeatDataAdd(dataraw, x, y, eveData['kills'][sid]['pods'], max);
		}
		if (eveMode == 4) {
		    max = eveHeatDataAdd(dataraw, x, y, eveData['kills'][sid]['rats'], max);
		}
		if (eveMode == 5) {
		    max = eveHeatDataAdd(dataraw, x, y, getValueOrZero(eveData['kills'][sid]['ships']) + getValueOrZero(eveData['kills'][sid]['pods']), max);
		}
	    }
	}
    }

    eveHeat.data(dataraw);
    eveHeat.max(max);
    if (drawScale !== undefined) {
	eveHeat.radius(60 * drawScale, 50 * drawScale);
    }
    eveHeat.resize();
    eveHeat.draw();

    eveLegend(max);
}

function getValueOrZero(value) {
    if (value === undefined) {
	return 0;
    }
    return value;
}

function eveHeatDataAdd(dataraw, x, y, value, max) {
    if (value !== undefined && value != 0) {
	max = Math.max(max, value);
	dataraw.push([x, y, value]);
    }
    return max;
}

// ---------------------------------------------------------------
