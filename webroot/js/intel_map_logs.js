// ---------------------------------------------------------------

var logsFilterSystems = [];
var logsFilterUnknowns = true;
var logsDisplayedLatest = 0;

// ---------------------------------------------------------------

function logsToggle() {
    if ($('#map-log').hasClass('hide')) {
	$('#map').css('width', '69%');
	$('#map-heat').css('width', '69%');
	$('#map-log-btn-hidden').addClass('hide');
	$('#map-log').removeClass('hide');
    } else {
	$('#map-log').addClass('hide');
	$('#map-log-btn-hidden').removeClass('hide');
	$('#map').css('width', '98%');
	$('#map-heat').css('width', '98%');
    }

    drawResize();
}

// ---------------------------------------------------------------

function logsClear() {
    $("#logs > tr").each(function() {
	if (!$(this).hasClass("keep")) {
	$(this).remove();
	}
    });
    $('#logs .keep').removeClass("hide");
}

function logsRefresh(sound) {
    logsClear();

    var logsAddedLatest = 0;
    jQuery.each(reports, function(i, report) {
	if (!logsFilterUnknowns && report['systems'].length == 0) {
	    return;
	}

	if (logsFilterUnknowns && report['systems'].length == 0) {
	    logsAddedLatest = Math.max(logsAddedLatest, report['submittedAt']);
	    logsAdd(report);
	    return;
	}

	if (logsFilterSystems.length == 0) {
	    logsAddedLatest = Math.max(logsAddedLatest, report['submittedAt']);
	    logsAdd(report);
	    return;
	}

	for (i in report['systems']) {
	    if (jQuery.inArray(report['systems'][i], logsFilterSystems) != -1) {
		logsAddedLatest = Math.max(logsAddedLatest, report['submittedAt']);
		logsAdd(report);
		return;
	    }
	}
    });


    if (sound && logsDisplayedLatest != 0 && logsAddedLatest > logsDisplayedLatest) {
        settingsDoPlay();
    }

    logsDisplayedLatest = logsAddedLatest;
}

function logsAdd(report) {
    $('#logs .keep').addClass("hide");

    cnt = "";
    cnt += '<tr>';
    cnt += '<td class="small"><span class="ago pull-right" timestamp="' + report['submittedAt'] + '">...</span></td>';
    cnt += '<td class="small">' + report['textInterpreted'] + ' <span class="text-muted pull-right">' + report['reporter'] + '</span></td>'
    cnt += '</tr>';
    $('#logs tr:first').after(cnt);

}

// ---------------------------------------------------------------

function logsRefreshTimestamps() {
    $(".ago").each(function( index ) {
	var ts = $(this).attr('timestamp');
	$(this).text(timestampToAgo(ts));

	var color = timestampToColor(ts);
	if (color !== false) {
	    $(this).css('color', color);
	} else {
	    $(this).css('color', '');
	}
    });
}

// ---------------------------------------------------------------

function logsFilterSystemsClear() {
    logsFilterSystemsReplace([]);
    logsFilterUnknownsSet(true);
}

// ---------------------------------------------------------------

function logsFilterUnknownsToggle() {
    logsFilterUnknowns = !logsFilterUnknowns;
}

function logsFilterUnknownsSet(value) {
    logsFilterUnknowns = value;
}

// ---------------------------------------------------------------

function logsFilterSystemToggle(name) {
    logsFilterSystemsToggle([name]);
}
function logsFilterSystemsToggle(names) {
    for (i in names) {
	if (jQuery.inArray(names[i], logsFilterSystems) == -1) {
	    logsFilterSystemAdd(names[i]);
	} else {
	    logsFilterSystemRemove(names[i]);
	}
    }
}


function logsFilterSystemAdd(name) {
    logsFilterSystemsAdd([name]);
}
function logsFilterSystemsAdd(names) {
    for (i in names) {
	if (jQuery.inArray(names[i], logsFilterSystems) == -1) {
	    logsFilterSystems.push(names[i]);
	}
    }
}


function logsFilterSystemReplace(name) {
	logsFilterSystemsReplace([name]);
}
function logsFilterSystemsReplace(names) {
    logsFilterSystems = [];
    logsFilterSystemsAdd(names);
}


function logsFilterSystemRemove(name) {
    logsFilterSystemsRemove([name]);
}
function logsFilterSystemsRemove(names) {
    for (i in names) {
	logsFilterSystems.splice( $.inArray(names[i], logsFilterSystems), 1 );
    }
}

// ---------------------------------------------------------------

function logsFilterRefresh() {
    logsFilterSystems.sort();

    $('#filter-unknown').removeClass();
    if (logsFilterUnknowns) {
	$('#filter-unknown').addClass("btn btn-xs btn-success");
    } else {
	$('#filter-unknown').addClass("btn btn-xs btn-danger");
    }

    $("#sysfilter button").each(function( index ) {
	$(this).remove();
    });

    for (i in logsFilterSystems) {
	cnt = '<button type="button" class="btn btn-xs btn-success" onclick="logsFilterSystemRemove(\'' + logsFilterSystems[i] + '\'); applyFilter();">' + logsFilterSystems[i] + '</button> ';
	$('#sysfilter').append(cnt);
    }
}

// ---------------------------------------------------------------
