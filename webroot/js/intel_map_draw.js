// ---------------------------------------------------------------

var drawUrl = "/BraveIntelServer/map";
var drawData = {};
var drawMapAll = true;
var drawCacheData;

var drawReady = false;

var drawSystemSize = 12;
var drawSystemOffsetX = 28;
var drawSystemOffsetY = 14;
var drawXMax = 0;
var drawYMax = 0;
var drawScale = 1.0;

var drawRegion = "";

var drawMarkerSystem = "";

// ---------------------------------------------------------------

$(document).ready(function() {
    var resizeTimer = 0;
    $(window).on('resize', function(){
	clearTimeout(resizeTimer);
	resizeTimer = setTimeout(drawResize, 400);
    });

    var map = $.cookie('brave-intel-region');
    if (!map) {
	map = "Fountain";
    }
    drawLoad(map);
});

// ---------------------------------------------------------------

function drawLoad(map, system) {
    drawMarkerSystem = system;

    if (drawRegion == map) {
	drawMarker();
	return;
    }

    drawReady = false;
    drawRegion = map;

    $.cookie('brave-intel-region', map, { path: '/' });

    eveClear();
    drawClear();
    $.ajax({
	async: true,
	url: drawUrl + '?region=' + map,
	mimeType: "application/json",
	dataType: 'json',
	error: drawLoadError,
	success: drawLoadSuccess,
    });
    evePoll();
}

function drawLoadSuccess(response) {
    drawData = response;
    drawReady = true;
    drawResize();
    drawMarker();
}

function drawLoadError(error) {
    if (error.status == 401) {
	window.setTimeout(function() {
	    window.location.reload(true);
	}, 3000);
    }
}

// ---------------------------------------------------------------

function drawResize() {

    // -------------------

    var div = document.getElementById("map");
    dw = div.offsetWidth;
    dh = div.offsetHeight;

    var canvas = document.getElementById('canvas');
    canvas.width = dw;
    canvas.height = dh;

    drawScale = Math.min(dw / 1054, dh / 770);

    var ctx = canvas.getContext('2d');
    ctx.scale(drawScale, drawScale);

    // -------------------

    var divheat = document.getElementById("map-heat");
    dwh = divheat.offsetWidth;
    dhh = divheat.offsetHeight;

    var canvasheat = document.getElementById('canvas-heat');
    canvasheat.width = dw;
    canvasheat.height = dh;

    // -------------------

    drawXMax = 0;
    drawYMax = 0;

    drawMapAll = true;

    drawMap();
    drawDivs();

    $("#map-heat-legend").css('left', drawXMax - 100);
    $("#map-heat-legend").css('top', drawYMax - 20 + div.offsetTop);
}

// ---------------------------------------------------------------

function drawClear() {
    var canvas = document.getElementById('canvas');
    var ctx = canvas.getContext('2d');
    ctx.clearRect(0, 0, 4096, 4096);
}


function drawMap() {
    if (!drawReady) {
	return;
    }

    var ctx = document.getElementById('canvas').getContext('2d');

    ctx.shadowOffsetX = 2;
    ctx.shadowOffsetY = 2;
    ctx.shadowColor = '#000000';

    if (drawMapAll == true) {
	drawClear();
	drawMapAll = false;
	drawConnections(ctx, true);
	drawConnections(ctx, false);

	drawBridges(ctx, true);
	drawBridges(ctx, false);

	drawSystemNames(ctx);
	drawSystems(ctx, true);

	drawCacheData = ctx.getImageData(0,0,canvas.width, canvas.height);
    } else {
	ctx.putImageData(drawCacheData,0,0);
    }

    drawSystems(ctx, false);
    drawSystemSelects(ctx);

    eveDraw();

}

function drawConnections(ctx, shadow) {
    ctx.shadowBlur = 8;
    ctx.lineWidth = 3;

    for (i in drawData['map']['connections']) {
	x1 = drawData['map']['connections'][i]['x1'];
	y1 = drawData['map']['connections'][i]['y1'];
	x2 = drawData['map']['connections'][i]['x2'];
	y2 = drawData['map']['connections'][i]['y2'];

	if (shadow === true) {
	    ctx.lineWidth = 5;
	    ctx.strokeStyle = '#000000';
	} else {
	    ctx.lineWidth = 3;
	    ctx.strokeStyle = connectionToColor(drawData['map']['connections'][i]['type']);
	}

	ctx.beginPath();
	ctx.moveTo(x1,y1);
	ctx.lineTo(x2,y2);
	ctx.stroke();
    }
}

function drawBridges(ctx, shadow) {
    ctx.shadowBlur = 6;

    for (i in jbData['bridges']) {

	sidA = jbData['bridges'][i]['idA'];
	sidB = jbData['bridges'][i]['idB'];
	friendly = jbData['bridges'][i]['friendly'];

	sdataA = findSystem(sidA);
	sdataB = findSystem(sidB);
	if (sdataA === false && sdataB === false) {
	    continue;
	}

	x1 = Math.floor(sdataA['x']) + drawSystemOffsetX;
	y1 = Math.floor(sdataA['y']) + drawSystemOffsetY;
	x3 = Math.floor(sdataB['x']) + drawSystemOffsetX;
	y3 = Math.floor(sdataB['y']) + drawSystemOffsetY;

	if (sdataA === false || sdataB === false) {
	    if (sdataA === false) {
		x1 = x3 - 40;
		y1 = y3 - 20;
	    } else {
		x3 = x1 - 40;
		y3 = y1 - 20;
	    }
	}

	xmin = Math.min(x1, x3);
	ymin = Math.min(y1, y3);
	xmax = Math.max(x1, x3);
	ymax = Math.max(y1, y3);
	
	ctx.beginPath();
	if (shadow === true) {
	    ctx.lineWidth = 5;
	    ctx.strokeStyle = '#000000';
	} else {
	    ctx.lineWidth = 3;
	    if (friendly) {
		ctx.strokeStyle = connectionToColor('jbf');
	    } else {
		ctx.strokeStyle = connectionToColor('jbh');
	    }
	}
	ctx.moveTo(x1,y1);
	if ( (xmax-xmin) > (ymax-ymin)) {
	    ctx.bezierCurveTo(x1, y3 + (y1 - y3) / 2, x3, y1 + (y3 - y1) / 2, x3, y3);
	} else {
	    ctx.bezierCurveTo(x3 + (x1 - x3) / 2, y1, x1 + (x3 - x1) / 2, y3, x3, y3);
	}
	ctx.stroke();
    }
}


function drawSystems(ctx, shadow) {
    ctx.shadowBlur = 30;

    for (i in drawData['map']['systems']) {
	x = Math.floor(drawData['map']['systems'][i]['x']);
	y = Math.floor(drawData['map']['systems'][i]['y']);
	name = drawData['map']['systems'][i]['name'];
	stn = drawData['map']['systems'][i]['hasStation'];

	drawXMax = Math.max(drawXMax, Math.floor(x * drawScale) + drawSystemOffsetX);
	drawYMax = Math.max(drawYMax, Math.floor(y * drawScale) + drawSystemOffsetY);

	if (shadow === true) {
	    ctx.fillStyle = '#000000';
	    if (stn) {
		ctx.fillRect (x + drawSystemOffsetX - drawSystemSize/2 - 2, y + drawSystemOffsetY - drawSystemSize/2 - 2, drawSystemSize + 4, drawSystemSize + 4);
	    } else {
		ctx.beginPath();
		ctx.arc(x + drawSystemOffsetX, y + drawSystemOffsetY, drawSystemSize/2 + 2, 0, 2 * Math.PI);
		ctx.fill();
	    }

	} else {

	    color = timestampToColor(reportsLatestGet(name));
	    if (color === false) {
		ctx.fillStyle = systemToColor();
	    } else {
		ctx.fillStyle = color;
	    }

	    if (stn) {
		ctx.fillRect (x + drawSystemOffsetX - drawSystemSize/2, y + drawSystemOffsetY  - drawSystemSize/2, drawSystemSize, drawSystemSize);
	    } else {
		ctx.beginPath();
		ctx.arc(x + drawSystemOffsetX, y + drawSystemOffsetY, drawSystemSize/2, 0, 2 * Math.PI);
		ctx.fill();
	    }

	}
    }
}

function drawSystemNames(ctx) {
    ctx.shadowBlur = 2;
    ctx.font = "9pt Helvetica";

    for (i in drawData['map']['systems']) {
	x = Math.floor(drawData['map']['systems'][i]['x']);
	y = Math.floor(drawData['map']['systems'][i]['y']);
	name = drawData['map']['systems'][i]['name'];
	region = drawData['map']['systems'][i]['region'];

	ctx.fillStyle = nameToColor();
	ctx.fillText(name, x + drawSystemOffsetX + drawSystemSize/2 + 8, y + drawSystemOffsetY - 6);

	if (region != undefined) {
	    ctx.fillStyle = connectionToColor('jr');
	    ctx.fillText(region, x + drawSystemOffsetX + drawSystemSize/2 + 8, y + drawSystemOffsetY + 16);
	}

    }
}

function drawSystemSelects(ctx) {
    ctx.shadowBlur = 6;
    ctx.strokeStyle = '#508000';

    ctx.lineWidth = 2;
    border = 12;

    for (i in drawData['map']['systems']) {
	x = Math.floor(drawData['map']['systems'][i]['x']);
	y = Math.floor(drawData['map']['systems'][i]['y']);
	name = drawData['map']['systems'][i]['name'];
	stn = drawData['map']['systems'][i]['hasStation'];

	if ($.inArray(name, logsFilterSystems) != -1) {
	    if (stn) {
		ctx.beginPath();
		ctx.strokeRect (x + drawSystemOffsetX - drawSystemSize/2 - border/2, y + drawSystemOffsetY - drawSystemSize/2 - border/2, drawSystemSize + border, drawSystemSize + border);
		ctx.stroke();
	    } else {
		ctx.beginPath();
		ctx.arc(x + drawSystemOffsetX, y + drawSystemOffsetY, drawSystemSize/2 + border / 2, 0, 2 * Math.PI);
		ctx.stroke();
	    }
	}
    }
}

function drawMarker() {
    border = 60;
    target = drawMarkerSystem;
    drawMarkerSystem = "";

    for (i in drawData['map']['systems']) {
	name = drawData['map']['systems'][i]['name'];
	if (name != target) {
	    continue;
	}

	x = Math.floor(drawData['map']['systems'][i]['x']);
	y = Math.floor(drawData['map']['systems'][i]['y']);

	dx = (x + drawSystemOffsetX) * drawScale - border/2;
	dy = (y + drawSystemOffsetY) * drawScale - border/2;

	$("#marker").css("left", dx).css("top", dy);
	$("#marker").animate({opacity: 1}, 200).animate({opacity: 0}, 7000);
    }
}

function drawDivs() {
    border = 8;
    dw = (drawSystemSize + border) * drawScale;
    dh = (drawSystemSize + border) * drawScale;

    $("#map > div").each(function() {
	$(this).remove();
    });

    for (i in drawData['map']['systems']) {
	id = drawData['map']['systems'][i]['id'];
	x = Math.floor(drawData['map']['systems'][i]['x']);
	y = Math.floor(drawData['map']['systems'][i]['y']);
	name = drawData['map']['systems'][i]['name'];
	stn = drawData['map']['systems'][i]['hasStation'];

	dx = (x + drawSystemOffsetX - drawSystemSize/2 - border/2) * drawScale;
	dy = (y + drawSystemOffsetY - drawSystemSize/2 - border/2) * drawScale;

	style = "";
	if (!stn) {
	    style = "border-radius: 50%;";
	}

	cnt = "";
	cnt += "<div id='blink-" + name + "'";
	cnt += " style='position: absolute; left: " +  dx + "px; top: " +  dy + "px; width: " + dw + "px; height: " + dh + "px; cursor: pointer; z-index:23; background-color: #FF0000; opacity: 0; " + style + "'";
	cnt += " onclick='mapSystemClicked(\"" + name + "\");' onmouseover='showSystemDetails($(this), \"" + id + "\");' onmouseleave='hideSystemDetails($(this), \"" + id + "\");'></div>";
	$("#map").append(cnt);
    }

}

// ---------------------------------------------------------------

function drawDivBlink(id) {
    $('#' + id)
	.animate({opacity: 1}, 400)
	.animate({opacity: 0}, 400)
	.animate({opacity: 1}, 400)
	.animate({opacity: 0}, 400)
	.animate({opacity: 1}, 400)
	.animate({opacity: 0}, 400)
	.animate({opacity: 1}, 400)
	.animate({opacity: 0}, 400)
	.animate({opacity: 1}, 400)
	.animate({opacity: 0}, 400)
	.animate({opacity: 1}, 400)
	.animate({opacity: 0}, 400)
	.animate({opacity: 1}, 400)
	.animate({opacity: 0}, 400)
	.animate({opacity: 1}, 400)
	.animate({opacity: 0}, 400)
	.animate({opacity: 1}, 400)
	.animate({opacity: 0}, 400)
	.animate({opacity: 1}, 400)
	.animate({opacity: 0}, 400)
	.animate({opacity: 1}, 400)
	.animate({opacity: 0}, 400);
}

// ---------------------------------------------------------------

