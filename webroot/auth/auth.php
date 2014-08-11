<?php if (!defined('INTEL')) die('go away'); ?>
<?php

function authCheck() {
    global $dbr, $cfg_cookie_name, $cfg_expire_session, $cfg_expire_uploader;

    $cookie = preg_replace("/[^A-Za-z0-9]/", '', $_COOKIE[$cfg_cookie_name]);
    if (empty($cookie)) {
	return false;
    }

    $stm = $dbr->prepare('DELETE FROM session WHERE createdAt < :time;');
    $stm->bindValue(':time', time() - $cfg_expire_session);
    if (!$stm->execute()) { die('sql error'); };

    $stm = $dbr->prepare('DELETE FROM uploader WHERE createdAt < :time;');
    $stm->bindValue(':time', time() - $cfg_expire_uploader);
    if (!$stm->execute()) { die('sql error'); };

    $stm = $dbr->prepare('SELECT * FROM session WHERE sessionId = :sessionId;');
    $stm->bindValue(':sessionId', $cookie);
    if (!$stm->execute()) { die('sql error'); };
    $row = $stm->fetch();
    if (!$row) {
	return false;
    }
    return array($row['charId'], $row['charName']);
}

function authSession($charId, $charName) {
    global $dbr, $cfg_cookie_name, $cfg_expire_session, $cfg_cookie_https_only;

    require('PassHash.class.php');
    $ph = new PassHash();
    $cookie = $ph->gen_salt(32);
    setcookie($cfg_cookie_name, $cookie, time() + $cfg_expire_session, '/', null, $cfg_cookie_https_only, false);

    $stm = $dbr->prepare('INSERT INTO session (charId, charName, sessionId, createdAt) VALUES (:charId, :charName, :sessionId, :createdAt);');
    $stm->bindValue(':charId', $charId);
    $stm->bindValue(':charName', $charName);
    $stm->bindValue(':sessionId', $cookie);
    $stm->bindValue(':createdAt', time());
    if (!$stm->execute()) { die('sql error'); };

    $stm = $dbr->prepare('UPDATE uploader SET createdAt = :createdAt WHERE charId = :charId;');
    $stm->bindValue(':charId', $charId);
    $stm->bindValue(':createdAt', time());
    if (!$stm->execute()) { die('sql error'); };
}

function authUploaderToken() {
    global $dbr, $cfg_expire_uploader;

    $user = authCheck();
    if ($user === false) {
	return false;
    }

    $stm = $dbr->prepare('SELECT * FROM uploader WHERE charId = :charId;');
    $stm->bindValue(':charId', $user[0]);
    if (!$stm->execute()) { die('sql error'); };
    $row = $stm->fetch();
    if ($row) {
	return $row['sessionId'];
    }

    require('PassHash.class.php');
    $ph = new PassHash();
    $token = $ph->gen_salt(32);

    $stm = $dbr->prepare('INSERT INTO uploader (charId, charName, sessionId, createdAt) VALUES (:charId, :charName, :sessionId, :createdAt);');
    $stm->bindValue(':charId', $user[0]);
    $stm->bindValue(':charName', $user[1]);
    $stm->bindValue(':sessionId', $token);
    $stm->bindValue(':createdAt', time());
    if (!$stm->execute()) { die('sql error'); };

    return $token;
}

function authInit() {
    global $cfg_core_endpoint, $cfg_core_application_id, $cfg_core_private_key, $cfg_core_public_key, $cfg_url_auth_success, $cfg_url_auth_fail;
    define('USE_EXT', 'GMP');
    require 'vendor/autoload.php';
    try {
	$api = new Brave\API($cfg_core_endpoint, $cfg_core_application_id, $cfg_core_private_key, $cfg_core_public_key);
	$info_data = array( 'success' => $cfg_url_auth_success, 'failure' => $cfg_url_auth_fail);
	$result = $api->core->authorize($info_data);
	header("Location: " . $result->location);
	return true;
    } catch(\Exception $e) {
	return NULL;
    }
}

function authVerify() {
    global $cfg_core_endpoint, $cfg_core_application_id, $cfg_core_private_key, $cfg_core_public_key, $cfg_url_base;
    $token = preg_replace("/[^A-Za-z0-9]/", '', $_GET['token']);
    if (empty($token)) {
	return NULL;
    }
    define('USE_EXT', 'GMP');
    require 'vendor/autoload.php';
    try {
	$api = new Brave\API($cfg_core_endpoint, $cfg_core_application_id, $cfg_core_private_key, $cfg_core_public_key);
	$result = $api->core->info(array('token' => $token));
    } catch(\Exception $e) {
	return NULL;
    }

    $charId = $result->character->id;
    $charName = $result->character->name;
    $tags = $result->tags;

    $valid = false;
    if (in_array("member", $tags)) {
	$valid = true;
    }
    if (in_array("blue", $tags)) {
	$valid = true;
    }

    if ($valid) {
	authSession($charId, $charName);
	header("Location: " . $cfg_url_base);
	return true;
    } else {
	return false;
    }

}

?>
