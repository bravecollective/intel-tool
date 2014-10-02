// ---------------------------------------------------------------

var jbUrl = "/BraveIntelServer/map";
var jbData = {};

// ---------------------------------------------------------------

$(document).ready(function() {
    jbLoad();
});

// ---------------------------------------------------------------

function jbLoad() {
    $.ajax({
	async: true,
	url: jbUrl + "?region=jb",
	mimeType: "application/json",
	dataType: 'json',
	error: jbLoadError,
	success: jbLoadSuccess,
    });
}

function jbLoadSuccess(response) {
    jbData = response;

    if (typeof drawMap == 'function') {
	drawMapAll = true;
	drawMap();
    }
    if (typeof bridgesUpdate == 'function') bridgesUpdate();
}

function jbLoadError(error) {
    if (error.status == 401) {
	window.setTimeout(function() {
	    window.location.reload(true);
	}, 3000);
    }
}

// ---------------------------------------------------------------
