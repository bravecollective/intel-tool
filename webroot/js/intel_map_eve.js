// ---------------------------------------------------------------

var eveUrl = "/BraveIntelServer/eve";
var eveData = {};
var eveInterval = 60000;

// ---------------------------------------------------------------

$(document).ready(function() {
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
}

function eveLoadError(error) {
    if (error.status == 401) {
	window.setTimeout(function() {
	    window.location.reload(true);
	}, 3000);
    }
}

// ---------------------------------------------------------------
