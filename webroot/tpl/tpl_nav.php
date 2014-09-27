<?php if (!defined('INTEL')) die('go away'); ?>

<?php
function addMenu($act, $id, $link, $name, $c) {
    echo "<li";
    if ($act == $id) {
	echo " class='" . $c . "'";
  }
    echo "><a href='" . $link . "'>" . $name . "</a></li>";
}
?>

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

	<div class="navbar-collapse collapse navbar-inverse-collapse navbar-right">
	    <ul class="nav navbar-nav">
		<?php if ($active == 'map') require('tpl_nav_map.php'); ?>

<?php if ($active == 'map') {
		echo '<li class="dropdown">';
		    echo '<a href="#" class="dropdown-toggle" data-toggle="dropdown">More <b class="caret"></b></a>';
		    echo '<ul class="dropdown-menu">';
			addMenu($active, "map", "/", "Map", "disabled");
			addMenu($active, "bridges", "?nav=bridges", "Bridges", "disabled");
			addMenu($active, "uploader", "?nav=uploader", "Uploader", "disabled");
			addMenu($active, "help", "?nav=help", "Help", "disabled");
			echo '<li class="divider">';
			addMenu($active, "settings", "?nav=settings", "My Settings", "disabled");
			echo '<li><a href="#" onclick="$.cookie(\'' . $cfg_cookie_name . '\', null, { path: \'/\' }); location.reload(true);">Logout</a></li>';
		    echo '</ul>';
		echo '</li>';
} else {
		addMenu($active, "map", "/", "Map", "active");
		addMenu($active, "bridges", "?nav=bridges", "Bridges", "active");
		addMenu($active, "uploader", "?nav=uploader", "Uploader", "active");
		addMenu($active, "help", "?nav=help", "Help", "active");
		echo '<li class="dropdown">';
		    echo '<a href="#" class="dropdown-toggle" data-toggle="dropdown">' . $name . ' <b class="caret"></b></a>';
		    echo '<ul class="dropdown-menu">';
			addMenu($active, "settings", "?nav=settings", "My Settings", "active");
			echo '<li><a href="#" onclick="$.cookie(\'' . $cfg_cookie_name . '\', null, { path: \'/\' }); location.reload(true);">Logout</a></li>';
		    echo '</ul>';
		echo '</li>';
}
?>

	    </ul>
	</div>
    </div>
<!-- Navigation -->

<!-- Alert Settings -->
    <div id="alert-settings" class="alert alert-dismissable alert-warning hide" style="z-index:42;">
	<button type="button" class="close" data-dismiss="alert">&times;</button>
	<h4>Warning!</h4>
	<p>I had problems accessing your custom settings. You can ignore this and continue your business, but it is highly recommended to resolve this by <a href="">reloading the page</a>.</p>
    </div>
<!-- Alert Settings -->
