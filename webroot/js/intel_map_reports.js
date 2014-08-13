// ---------------------------------------------------------------

var reports = [];
var reportsLatest = {};

// ---------------------------------------------------------------

$(document).ready(function() {
    setInterval(function() {
	console.log("Timer: clean reports");
	reportsClean();
	//logsRefresh();
	applyData();
    }, 60000);
});

// ---------------------------------------------------------------

function reportsAdd(incoming) {
    if (incoming.length == 0) {
	return;
    }

    for (i in incoming) {
	reportsLatestUpdate(incoming[i]); 
    }

    reports.push.apply(reports, incoming);
    applyData();
}

// ---------------------------------------------------------------

function reportsLatestUpdate(report) {
    submitted = report['submittedAt'];
    for (i in report['systems']) {
	system = report['systems'][i];
	reportsLatest[system] = submitted;
	if (timestampToAgo(submitted) == "new") {
	    drawDivBlink("blink-" + system);
	}
    }
}

function reportsLatestGet(system) {
    var last = reportsLatest[system];
    if (last == undefined) {
	return 0;
    }
    return last;
}

// ---------------------------------------------------------------

function reportsClean() {
    var now = new Date().getTime();
    i = reports.length;
    while (i--) {
	if (now - reports[i]['submittedAt'] < 1000 * 60 * 90) {
	    continue;
	}
	reports.splice(i, 1);
    }
}

// ---------------------------------------------------------------
