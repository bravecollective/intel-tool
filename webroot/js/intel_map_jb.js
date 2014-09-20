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
    drawMap();
}

function jbLoadError(error) {
    if (error.status == 401) {
	window.setTimeout(function() {
	    window.location.reload(true);
	}, 3000);
    }
}

// ---------------------------------------------------------------
