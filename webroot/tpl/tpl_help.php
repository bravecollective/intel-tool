<?php if (!defined('INTEL')) die('go away'); ?>

<!-- Help -->
<div class="container">

    <h3>Components</h3>
    <ul class="list-group">
	<li class="list-group-item">
	    The Brave Intel Map can be divided into three major components:
	    <ul>
		<li>Navigiation bar at the top</li>
		<li>Map view at the left</li>
		<li>Log view at the right</li>
	    </ul>
	</li>
    </ul>

    <h4>Navigation Bar</h4>
    <ul class="list-group">
	<li class="list-group-item">
	    The navigation bar at the top provides you important information and offers shortcts to, well, navigate around.
	    <h6>Polling Indicator</h6>
	    The polling indicator tells you that your map is successfully keeping its data up-to-date, it should almost always show <label class="label label-success">OK</label>. The appearance of <label class="label label-danger">Failed</label> indicates a problem, either wait some time (e.g. server maintenance going on) or try reloading the page.
	    <h6>Uploader Counter</h6>
	    The uploader counter tells you how many people are running the <a href="?nav=uploader">Brave Intel Reporter</a> right now. If this number drops down to <label class="label label-danger">0</label> no intel will get reported whatsoever.
	    <h6>Region</h6>
	    The region dropdown lets you select which region you would like to see drawn as a map. Changing the region has no other consequences except a different map is drawn.
	    <h6>Heatmap</h6>
	    The heatmap dropdown lets you select the data you want to draw as a background heatmap. Be aware that the data delivered by CCP can be delayed up to 30 minutes.
	    <h6>Legend</h6>
	    Well, its a legend for the map. Take a look and make yourself familiar with the color coding being used.
	    <h6>Map</h6>
	    The map brings you back to the <a href="/">main view</a>. So does the BRAVE icon.
	    <h6>Uploader</h6>
	    The uploader provides you a download link and authentication token required for running the <a href="?nav=uploader">Brave Intel Reporter</a>.
	    <h6>&lt;Your Name&gt;</h6>
	    The character name dropdown offers a link to change your application <a href="?nav=settings">settings</a> (background image, map behavior) and an option to logout.
	</li>
    </ul>

    <h4>Map View</h4>
    <ul class="list-group">
	<li class="list-group-item">
	    The map draws a chosen region in a <a href="https://evemaps.dotlan.net">dotlan</a> layout and gets enriched by intel reports matching solar systems in realtime. Check the legend in the navigation bar to learn about the color coding and use the region dropdown to select a region to be used.<br>
	</li>
	<li class="list-group-item">
	    The map always highlights all available intel in the selected region and is not influences by any filters selected in the log view. Clicking on a solar system will add/remove or replace (depending on your <a href="?nav=settings">settings</a>) the system in the log view filter.
	</li>
    </ul>

    <h4>Log View</h4>
    <ul class="list-group">
	<li class="list-group-item">
	    The log view shows the intel reports submitted by players in the <i>Intel - HERO</i> channel in realtime.<br>
	</li>
	<li class="list-group-item">
	    The log view can be influenced by filters. The currently active filter configuration is displayed above the list of reports.<br>
	    The filters are modified by interacting with the map and/or reports in the log view. Depending on your application <a href="?nav=settings">settings</a>, a click on a solar system will either add/remove or replace the filter list.

	    <h6>Unknown filter</h6>
	    The unknown filter is a static filter which can be toggled - by clicking on it - but never removed. Being <label class="label label-success">Unknown</label> indicates that the log view should show reports where it was unable to determine a matching solar system, while <label class="label label-danger">Unknown</label> tells the log view to hide any reports where the location is unknown.

	    <h6>System filters</h6>
	    The system filters limit the log view to show only reports matching the selected solar systems. Clicking on a system filter removes it from the list of filters.

	    <h6>Examples</h6>
	    <label class="label label-success">Unknown</label> Show everything you got.<br>
	    <br>
	    <label class="label label-danger">Unknown</label> Show only reports where a solar system was detected.<br>
	    <br>
	    <label class="label label-success">Unknown</label> <label class="label label-success">GE-8JV</label> Show reports detected as GE-8JV only, but also include reports where the location is unknown.<br>
	    <br>
	    <label class="label label-danger">Unknown</label> <label class="label label-success">GE-8JV</label> <label class="label label-success">V-3YG7</label> Show reports detected as GE-8JV and V-3YG7, but nothing else.<br>
	</li>
    </ul>

</div>
<!-- Help -->
