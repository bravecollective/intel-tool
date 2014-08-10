<?php if (!defined('INTEL')) die('go away'); ?>

<!-- Settings -->
<div class="container">

    <h4>Map</h4>
    <ul class="list-group">
	<li class="list-group-item">
	    When I click on a system on the map, I want the selected system to
	    <div id="s-map-action-select-filter" class="btn-group">
		<button type="button" value="0" class="btn btn-default btn-xs" onclick="settingsSet('s-map-action-select-filter', value);">be added to</button>
		<button type="button" value="1" class="btn btn-default btn-xs" onclick="settingsSet('s-map-action-select-filter', value);">replace</button>
	    </div>
	    the list of filtered systems.
	</li>
	<li class="list-group-item">
	    When I click on a system on the map, I want the unknown filter to 
	    <div id="s-map-action-select-unknown" class="btn-group">
		<button type="button" value="2" class="btn btn-default btn-xs" onclick="settingsSet('s-map-action-select-unknown', value);">be enabled</button>
		<button type="button" value="0" class="btn btn-default btn-xs" onclick="settingsSet('s-map-action-select-unknown', value);">stay as is</button>
		<button type="button" value="1" class="btn btn-default btn-xs" onclick="settingsSet('s-map-action-select-unknown', value);">be disabled</button>
	    </div>
	</li>
    </ul>

    <h4>Logs</h4>
    <ul class="list-group">
	<li class="list-group-item">
	    When I click on a system in the logs, I want the selected system to
	    <div id="s-logs-action-select-filter" class="btn-group">
		<button type="button" value="0" class="btn btn-default btn-xs" onclick="settingsSet('s-logs-action-select-filter', value);">be added to</button>
		<button type="button" value="1" class="btn btn-default btn-xs" onclick="settingsSet('s-logs-action-select-filter', value);">replace</button>
	    </div>
	    the list of filtered systems.
	</li>
	<li class="list-group-item">
	    When I click on a system in the logs, I want the unknown filter to 
	    <div id="s-logs-action-select-unknown" class="btn-group">
		<button type="button" value="2" class="btn btn-default btn-xs" onclick="settingsSet('s-logs-action-select-unknown', value);">be enabled</button>
		<button type="button" value="0" class="btn btn-default btn-xs" onclick="settingsSet('s-logs-action-select-unknown', value);">stay as is</button>
		<button type="button" value="1" class="btn btn-default btn-xs" onclick="settingsSet('s-logs-action-select-unknown', value);">be disabled</button>
	    </div>
	</li>
    </ul>

    <h4>Background</h4>
    <ul class="list-group">
	<li id="s-background-image" class="list-group-item">
	    <button type="button" value="0" class="btn btn-default" style="width:100px; height:100px; background:black;" onclick="settingsSet('s-background-image', value);"></button>
	    <button type="button" value="1" class="btn btn-default" style="width:100px; height:100px; background:url('img/th_bg1.jpg');" onclick="settingsSet('s-background-image', value);"></button>
	    <button type="button" value="2" class="btn btn-default" style="width:100px; height:100px; background:url('img/th_bg2.jpg');" onclick="settingsSet('s-background-image', value);"></button>
	    <button type="button" value="3" class="btn btn-default" style="width:100px; height:100px; background:url('img/th_bg3.jpg');" onclick="settingsSet('s-background-image', value);"></button>
	    <button type="button" value="4" class="btn btn-default" style="width:100px; height:100px; background:url('img/th_bg4.jpg');" onclick="settingsSet('s-background-image', value);"></button>
	    <button type="button" value="5" class="btn btn-default" style="width:100px; height:100px; background:url('img/th_bg5.jpg');" onclick="settingsSet('s-background-image', value);"></button>
	    <button type="button" value="6" class="btn btn-default" style="width:100px; height:100px; background:url('img/th_bg6.jpg');" onclick="settingsSet('s-background-image', value);"></button>
	    <button type="button" value="7" class="btn btn-default" style="width:100px; height:100px; background:url('img/th_bg7.jpg');" onclick="settingsSet('s-background-image', value);"></button>
	    <button type="button" value="8" class="btn btn-default" style="width:100px; height:100px; background:url('img/th_bg8.jpg');" onclick="settingsSet('s-background-image', value);"></button>
	</li>
    </ul>

</div>
<!-- Settings -->
