// --------------------------------------------------------

var settingsUrl = '/BraveIntelServer/user';

var settings = {};

// --------------------------------------------------------

$(document).ready(function() {
    settingsLoad();
});

// --------------------------------------------------------

function settingsLoad() {
    $.ajax({
	async: false,
	type: "GET",
	url: settingsUrl,
	mimeType: "application/json",
	dataType: 'json',
	error: settingsError,
	success: settingsLoadSuccess,
    });
}

function settingsSave() {
    settingsUpdateUI();
    $.ajax({
	async: false,
	type: "PUT",
	url: settingsUrl,
	mimeType: "application/json",
	data: JSON.stringify(settings),
	dataType: 'json',
	error: settingsError,
	success: settingsSaveSuccess,
    });
}

function settingsLoadSuccess(response) {
    console.log("Settings loaded");
    settings = response;
    settingsUpdateUI();
    settingsDoBackground()
}

function settingsSaveSuccess(response) {
    console.log("Settings saved");
}

function settingsError(error) {
    console.log("Settings failed");
    $("#alert-settings").removeClass("hide");
}

// --------------------------------------------------------

function settingsGet(key) {
    var value = settings[key];
    if (value !== undefined) {
	return value;
    }
    if (key == "s-background-image") {
	return '7';
    }

    return '0';
}

function settingsSet(key, value) {
    settings[key] = value;
    settingsSave();
    if (key == 's-background-image') {
	settingsDoBackground();
    }
}

// --------------------------------------------------------

function settingsUpdateUI() {
    settingsToggleButtons("s-background-image", "", "");
    settingsToggleButtons("s-map-action-select-filter");
    settingsToggleButtons("s-map-action-select-unknown");
    settingsToggleButtons("s-logs-action-select-filter");
    settingsToggleButtons("s-logs-action-select-unknown");
}

function settingsToggleButtons(id, text, textSelected) {
    var value = settingsGet(id);
    $("#" + id + " button").each(function( index ) {
	$(this).removeClass('active');
	$(this).removeClass('btn-default btn-success');
	if ($(this).attr('value') == value) {
	    $(this).addClass('active');
	    $(this).addClass('btn-success');
	    if (textSelected != false) {
		$(this).text(textSelected);
	    }
	} else {
	    $(this).addClass('btn-default');
	    if (text != false) {
		$(this).text(text);
	    }
	}
    });
}

// --------------------------------------------------------

function settingsDoBackground() {
    var value = settingsGet('s-background-image');
    $('body').css("background", "#000000");
    if (value > 0) {
	$('body').css('background', 'url("img/bg' + value + '.jpg") no-repeat');
	$('body').css('background-attachment', 'fixed');
	$('body').css('-webkit-background-size', 'cover');
	$('body').css('-moz-background-size', 'cover');
	$('body').css('-o-background-size', 'cover');
	$('body').css('background-size', 'cover');
    }
}
