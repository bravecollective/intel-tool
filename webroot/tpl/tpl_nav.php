<?php if (!defined('INTEL')) die('go away'); ?>

<!-- Navigation -->
    <div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	<div class="navbar-header">
	    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-inverse-collapse">
		<span class="icon-bar"></span>
		<span class="icon-bar"></span>
		<span class="icon-bar"></span>
	    </button>
	    <a class="navbar-brand" href="/"><img style="margin-top: -7px;" src='img/brave.png'></a>
	</div>
	<div class="navbar-collapse collapse navbar-inverse-collapse pull-right">
	    <ul class="nav navbar-nav">
		<?php if ($active == 'map') require('tpl_nav_map.php'); ?>
		<li <?php if ($active == "help") echo "class='active'"; ?>><a href="/">Help</a></li>
		<li <?php if ($active == "map") echo "class='active'"; ?>><a href="/">Map</a></li>
		<li <?php if ($active == "uploader") echo "class='active'"; ?>><a href="?nav=uploader">Uploader</a></li>
		<li class="dropdown">
		    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><?php echo $name ?> <b class="caret"></b></a>
		    <ul class="dropdown-menu pull-right">
			<li><a href="?nav=settings">My Settings</a></li>
			<li><a href="#" onclick="$.cookie(<?php echo "'" . $cfg_cookie_name . "'"; ?>, null, { path: '/' }); location.reload(true);">Logout</a></li>
		    </ul>
		</li>
	    </ul>
	</div>
    </div>
<!-- Navigation -->

<!-- Alert Settings -->
    <div id="alert-settings" class="alert alert-dismissable alert-warning hide" style="z-index:0;">
	<button type="button" class="close" data-dismiss="alert">×</button>
	<h4>Warning!</h4>
	<p>I had problems accessing your custom settings. You can ignore this and continue your business, but it is highly recommended to resolve this by <a href="">reloading the page</a>.</p>
    </div>
<!-- Alert Settings -->
