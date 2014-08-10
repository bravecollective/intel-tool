<?php if (!defined('INTEL')) die('go away'); ?>

<!-- Audio -->
<!--
    <audio id="audio_new" preload="auto">
	<source src="snd/new.wav" type="audio/wave">
    </audio>
-->
<!-- Audio -->

<!-- Map -->
    <div id="map" style="position:absolute; top:60px; bottom:5px; left:10px; width:69%; z-index:-1;">
	<canvas id="canvas" width="100px" height="100px"></canvas>
    </div>
<!-- Map -->

<!-- Logs -->
    <div style="position:absolute; top:60px; bottom:10px; left:71%; right:0px; padding-right:5px; margin-bottom:5px; overflow-y:scroll; z-index:-1;">
	<p style="line-height:200%;">
	    <label>Systems</label> <span class="text-muted">click to remove or toggle</span>
	    <span class="pull-right"><button type="button" class="btn btn-link btn-xs" onclick="logsFilterSystemsClear();">clear filters</button></span>
	    <br>
	    <button type="button" id="filter-unknown" class="btn btn-default btn-xs" onclick="logsFilterUnknownsToggle();">Unknown</button>
	    <span id="sysfilter"></span>
	</p>

	<table class="table table-condensed table-striped">
	    <thead>
		<tr>
		    <th>Time</th>
		    <th>Intel <span class="text-muted pull-right">Reporter</span></th>
		</tr>
	    </thead>
	    <tbody id="logs">
		<tr class="keep"><td colspan="2" class="small">No intel matching your criteria ...</td></tr>
	    </tbody>
	</table>
    </div>
<!-- Logs -->
