<?php if (!defined('INTEL')) die('go away'); ?>
<?php

// -------------------------------------------------

function tpl_header() {
    require("tpl/tpl_header.php");
}

function tpl_nav($name, $active) {
    global $cfg_cookie_name;
    require("tpl/tpl_nav.php");
}

function tpl_nav_empty() {
    require("tpl/tpl_nav_empty.php");
}

function tpl_footer($pScripts) {
    require("tpl/tpl_footer.php");
}

// -------------------------------------------------

function tpl_map() {
    require("tpl/tpl_map.php");
}

function tpl_settings() {
    require("tpl/tpl_settings.php");
}

function tpl_uploader() {
    require("tpl/tpl_uploader.php");
}

function tpl_help() {
    require("tpl/tpl_help.php");
}

function tpl_bridges() {
    require("tpl/tpl_bridges.php");
}

// -------------------------------------------------

function tpl_error() {
    require("tpl/tpl_error.php");
}

// -------------------------------------------------

function tpl_auth_needed() {
    global $cfg_url_auth_init;
    require("tpl/tpl_auth_needed.php");
}

function tpl_auth_error() {
    global $cfg_url_auth_init;
    require("tpl/tpl_auth_error.php");
}

function tpl_auth_negative() {
    global $cfg_url_auth_init;
    require("tpl/tpl_auth_negative.php");
}

// -------------------------------------------------

?>
