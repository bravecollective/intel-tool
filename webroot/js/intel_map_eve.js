// ---------------------------------------------------------------

var eveUrl = "/BraveIntelServer/evedata";
var eveData = {};
var eveInterval = 90000;
var eveHeat;
var eveMode = 0;
var eveId = 0;

// ---------------------------------------------------------------

$(document).ready(function() {
    var config = {
	container: document.getElementById('map-heat'),
	radius: 80,
	maxOpacity: .9,
	minOpacity: 0,
	blur: .75
    };

    eveResize();
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
	eveHeat.setData({ max: 0, min: 0, data: [] });
    }
}

function eveResize() {
    if (eveHeat != null) {
	$('#map-heat :last-child').remove();
	eveHeat.setData({ max: 0, min: 0, data: [] });
	eveHeat = null;
    }

    var config = {
	container: document.getElementById('map-heat'),
	radius: 80 * drawScale,
	maxOpacity: .9,
	minOpacity: 0,
	blur: .75,
	onExtremaChange: eveLegend
    };

    eveHeat = h337.create(config);
    $('#map-heat').css('position', 'absolute');

    eveDraw();
}
function eveLegend(extremes) {

    var c = $("#map-heat-legend canvas")['0'];
    var ctx = c.getContext("2d");

    ctx.clearRect(0, 0, c.offsetWidth, c.offsetHeight);
    if (extremes['max'] == 0) {
	return;
    }

    var l = 5;
    var t = 15;
    var w = 100;
    var h = 6;

    var grd = ctx.createLinearGradient(l, 0, l + w, 0);
    grd.addColorStop(0, "black");
    for (i in extremes['gradient']) {
	grd.addColorStop(i, extremes['gradient'][i]);
    }

    ctx.fillStyle = grd;
    ctx.fillRect(l, t, w, h);

    ctx.strokeStyle = '#333333';
    ctx.strokeRect(l - 1, t - 1, w + 2,  h + 2);

    ctx.fillStyle = "#999999";
    ctx.textAlign="start"; 
    ctx.fillText(extremes['min'], l - 2, t - 4);
    ctx.textAlign="end"; 
    ctx.fillText(extremes['max'], l + w + 2, t - 4);
}

function eveDraw() {
    if (eveHeat == null) {
	eveResize();
	return;
    }

    if (drawData['map'] === undefined) {
	return;
    }

    var dataheat = eveHeatData();
    eveHeat.setData( {
	min: 0,
	max: Math.max(dataheat[1], 20),
	data: dataheat[0]
    });
}

function eveHeatData() {
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
    return [dataraw, max];
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
	dataraw.push({x: x, y: y, value: value });
    }
    return max;
}

// ---------------------------------------------------------------
