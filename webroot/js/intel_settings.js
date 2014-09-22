// --------------------------------------------------------

var settingsUrl = '/BraveIntelServer/user';

var settings = {};

// --------------------------------------------------------

$(document).ready(function() {
    settingsLoad();
    settingsBarRegister("s-alarm-audio-volume");
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
    if (key == "s-alarm-audio-volume") {
	return 75;
    }

    return '0';
}

function settingsSet(key, value) {
    settings[key] = value;
    settingsSave();
    if (key == 's-background-image') {
	settingsDoBackground();
    }
    if (key.lastIndexOf("s-alarm-audio", 0) === 0) {
	settingsDoPlay();
    }
}

// --------------------------------------------------------

function settingsUpdateUI() {
    settingsToggleButtons("s-background-image", "", "");
    settingsToggleButtons("s-map-action-select-filter");
    settingsToggleButtons("s-map-action-select-unknown");
    settingsToggleButtons("s-logs-action-select-filter");
    settingsToggleButtons("s-logs-action-select-unknown");

    settingsToggleButtons("s-alarm-audio");
    settingsToggleButtons("s-alarm-audio-file");
    settingsBarRender("s-alarm-audio-volume");
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

function settingsBarRegister(id) {
    div = $('#' + id + "-div");
    bar = $('#' + id + "-bar");

    div.click(function(e){
	w = div.width();
	x = e.pageX - bar.offset().left;
	p = Math.ceil(x / w * 100);

	settingsSet(id, p);
    });
}

function settingsBarRender(id) {
    value = settingsGet(id);

    div = $('#' + id + "-div");
    bar = $('#' + id + "-bar");
    w = div.width();
    bar.css("width",  w * (value / 100));
}

// --------------------------------------------------------

function settingsDoBackground() {
    var value = settingsGet('s-background-image');
    $('body').css("background", "#000000");
    if (value > 0) {
	$('body').css('background', '#000000 url("img/bg' + value + '.jpg") center center fixed no-repeat');
	$('body').css('-webkit-background-size', 'cover');
	$('body').css('-moz-background-size', 'cover');
	$('body').css('-o-background-size', 'cover');
	$('body').css('background-size', 'cover');
    }
}

// --------------------------------------------------------

function settingsDoPlay() {
    if (settingsGet('s-alarm-audio') == 0) {
	return;
    }

    file = settingsGet('s-alarm-audio-file');
    snd = "";
    if (file == 0) {
	snd = "woop.mp3";
    }
    if (file == 1) {
	snd = "school.mp3";
    }
    if (file == 2) {
	snd = "grocery.mp3";
    }
    if (file == 3) {
	snd = "blip.mp3";
    }

    var audio = new Audio("audio/" + snd );
    audio.volume = settingsGet('s-alarm-audio-volume') / 100;
    audio.play();
}
