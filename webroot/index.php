<?php

define('INTEL', 23);

require("config.php");

try {
    $dbr = new PDO($cfg_sql_url, $cfg_sql_user, $cfg_sql_pass);
} catch (PDOException $e) {
    die('database init failed');
}

require("tpl/tpl.php");
require("auth/auth.php");

$authResult = authCheck();

    $pNav = preg_replace("/[^a-z]/", '', $_GET['nav']);

if ($authResult === false) {

    if ($pNav == "") {
	tpl_header();
	tpl_nav_empty();
	tpl_auth_needed();
	tpl_footer();
	return;
    }

    if ($pNav == "init") {
	if (authInit() === NULL) {
	    tpl_header();
	    tpl_nav_empty();
	    tpl_auth_error();
	    tpl_footer();
	}
	return;
    }

    if ($pNav == "verify") {
	$verify = authVerify();

	if ($verify === NULL) {
	    tpl_header();
	    tpl_nav_empty();
	    tpl_auth_error();
	    tpl_footer();
	}

	if ($verify === false) {
	    tpl_header();
	    tpl_nav_empty();
	    tpl_auth_negative();
	    tpl_footer();
	}

    if ($pNav == "error") {
	tpl_header();
	tpl_nav_empty();
	tpl_auth_error();
	tpl_footer();
	return;
    }

	return;
    }

} else {
    $authCharId = $authResult[0];
    $authCharName = $authResult[1];

    $pNav = preg_replace("/[^a-z]/", '', $_GET['nav']);
    if (empty($pNav)) {
	$pNav = "map";
    }

    if ($pNav == "map") {
	tpl_header();
	tpl_nav($authCharName, $pNav);
	tpl_map();
	tpl_footer(array('js/intel_settings.js', 'js/intel_map.js', 'js/intel_map_poll.js', 'js/intel_map_draw.js', 'js/intel_map_reports.js', 'js/intel_map_logs.js'));
	return;
    }

    if ($pNav == "settings") {
	tpl_header();
	tpl_nav($authCharName, $pNav);
	tpl_settings();
	tpl_footer(array('js/intel_settings.js'));
	return;
    }

    if ($pNav == "help") {
	tpl_header();
	tpl_nav($authCharName, $pNav);
	tpl_help();
	tpl_footer(array('js/intel_settings.js'));
	return;
    }

    if ($pNav == "uploader") {
	tpl_header();
	tpl_nav($authCharName, $pNav);
	tpl_uploader();
	tpl_footer(array('js/intel_settings.js'));
	return;
    }

    tpl_header();
    tpl_nav_empty();
    tpl_error();
    tpl_footer();
}

?>
