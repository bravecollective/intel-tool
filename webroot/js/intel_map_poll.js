// ---------------------------------------------------------------

var pollUrl = '/BraveIntelServer/report';

var pollLast = 0;
var pollId = 0;
var pollInterval = 3000;

// ---------------------------------------------------------------

$(document).ready(function() {
	pollStart();
});

// ---------------------------------------------------------------

function pollStart(adjust) {
    if (adjust === true) {
	pollSetStatus("btn-primary", "Adjusting");
    } else {
	pollSetStatus("btn-warning", "Started");
	pollSetSubmitter('?');
    }

    pollId = setInterval(function() {
	console.log("Timer: polling");
	$.ajax({
	    async: false,
	    url: pollUrl + "?since=" + pollLast,
	    mimeType: "application/json",
	    dataType: 'json',
	    error: pollError,
	    success: pollSuccess,
	});
    }, pollInterval);
}

function pollStop(adjust) {
    if (pollId == 0) {
	return;
    }

    clearInterval(pollId);
    pollId = 0;

    pollSetStatus("btn-danger", "Stopped");
    if (adjust !== true) {
	pollSetSubmitter('?');
    }
}

function pollToggle() {
    if (pollId == 0) {
	pollStart();
    } else {
	pollStop();
    }
}

function pollSuccess(response) {
	pollSetStatus("btn-success", "OK");

	pollLast = response['timestamp'];

	pollSetSubmitter(response['submitterCount']);

	pollIntervalNew = response['pollInterval'];
	pollIntervalNew = 6000;
        if (pollInterval != pollIntervalNew) {
	    pollInterval = pollIntervalNew;
	    pollStop(true);
	    pollStart(true);
        }

	reportsAdd(response['reports']);
}

function pollError(error) {
    pollSetStatus("btn-danger", "Failed");
    pollSetSubmitter('?');
    if (error.status == 401) {
	window.setTimeout(function() {
	    window.location.reload(true);
	    }, 3000);
    }
}

function pollSetStatus(classes, text) {
	$('#poller').removeClass();
	$('#poller').addClass("btn btn-xs " + classes);
	$('#poller').text(text);
}


function pollSetSubmitter(count) {
    $('#uploader').removeClass();
    if (count == 0) {
	$('#uploader').addClass("label label-danger");
    }
    if (count == 1) {
	$('#uploader').addClass("label label-warning");
    }
    if (count > 1) {
	$('#uploader').addClass("label label-success");
    }
    if (count == '?') {
	$('#uploader').addClass("label label-default");
    }
    $('#uploader').text(count);
}

// ---------------------------------------------------------------
