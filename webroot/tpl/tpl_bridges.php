<?php if (!defined('INTEL')) die('go away'); ?>

<!-- Bridges -->
<div class="container">

<br>

    <ul class="nav nav-tabs">
	<li class="active"><a href="#overview" data-toggle="tab">Overview</a></li>
	<li><a href="#dotlan" data-toggle="tab">DOTLAN</a></li>
	<li><a href="#gts" data-toggle="tab">Garpa Topographical Survey</a></li>
    </ul>

    <div id="myTabContent" class="tab-content">

	<div class="tab-pane active in" id="overview">
	    <li class="list-group-item">
		The <i>BRAVE Intel Map</i> provides up-to-date information on <i>HERO</i> and known <i>hostile</i> jump bridges.
		<h6>How to use a bridge</h6>
		<ol>
		    <li>Go to a system which has a jumpbridge</li>
		    <li>Right click in space and locate the jumpbridge bookmark (usually labelled "--&gt;")</li>
		    <li>Warp to the jumpbridge (or a tactical first)</li>
		    <li>Be within 2500m, right click the jumpbridge and select <i>jump</i></li>
		</ol>
		Be aware that in contrast to a regular gate there is <b><span class="text-danger">no</span> invulnerability timer</b> on the other side - warp out immediately.
		<h6>Report wrong data</h6>
		If you find any errors or have intel on hostile bridge locations, get in touch with <a href="http://evewho.com/pilot/kiu+Nakamura">kiu Nakamura</a> or <a href="http://evewho.com/pilot/Serinus+Gareth">Serinus Gareth</a>.
		<h6>3rd party tools</h6>
		The list of jumpbridges can be imported into any tool supporting <i>DOTLAN</i> or <i>GTS</i> format. Select the desired format from the tabs above.
	    </li>
	</div>

	<div class="tab-pane" id="dotlan">
	    <li class="list-group-item">
		<h6>What is DOTLAN?</h6>
		<a href="http://evemaps.dotlan.net/">DOTLAN</a> offers a widely used web based map providing the option to include custom jumpbridges.
		<h6>How to Import</h6>
		<ol>
		    <li>Go to <a href="https://evemaps.dotlan.net/bridges">https://evemaps.dotlan.net/bridges</a></li>
		    <li>Click <i>Create network</i></li>
		    <li>Fill in <i>HERO</i> or <i>HOSTILE</i> as <i>Network name</i> and click <i>Create network</i></li>
		    <li>Paste the bridge list found below into <i>Network Import</i>, tick <i>Clear Bridges on Import</i> and click <i>Import</i></li>
		    <li>Go to <a href="https://evemaps.dotlan.net/bridges">https://evemaps.dotlan.net/bridges</a></li>
		    <li>Click <i>HERO</i> or <i>HOSTILE</i> network</li>
		    <li>Click on a region, e.g. <i>Catch</i></li>
		    <li>Save bookmark for future use</li>
		</ol>

		<h6>How to Update</h6>
		<ol>
		    <li>Go to <a href="https://evemaps.dotlan.net/bridges">https://evemaps.dotlan.net/bridges</a></li>
		    <li>Click <i>HERO</i> or <i>HOSTILE</i> network</li>
		    <li>Paste the bridge list found below into <i>Network Import</i>, tick <i>Clear Bridges on Import</i> and click <i>Import</i></li>
		</ol>
		<h6>Data Export (<span class="text-success">friendly</span>)</h6>
		<label for="bridges-dotlan-friendly" class="control-label small">CTRL+A, CTRL+C</label>
		<textarea class="form-control small" rows="4" id="bridges-dotlan-friendly" readonly>Loading data, please wait.</textarea>

		<h6>Data Export (<span class="text-danger">hostile</span>)</h6>
		<label for="bridges-dotlan-hostile" class="control-label small">CTRL+A, CTRL+C</label>
		<textarea class="form-control small" rows="4" id="bridges-dotlan-hostile" readonly>Loading data, please wait.</textarea>
	    </li>
	</div>

	<div class="tab-pane" id="gts">
	    <li class="list-group-item">
		<h6>What is Garpa Topographical Survey?</h6>
		<a href="https://forums.eveonline.com/default.aspx?g=posts&t=226437">Garpa Topographical Survey</a> (GTS) - featured in a <a href="http://community.eveonline.com/news/dev-blogs/community-spotlight-garpa/">community spotlight</a> - is a sophisticated standalone mapping tool developed by <a href="http://evewho.com/alli/Goonswarm+Federation">Goonswarm</a>.
		<h6>How to Import</h6>
		<ol>
		    <li>Click <i>Data</i> in the menu then select <i>Import Jump Bridges</i></li>
		    <li>Select <i>Wiki Import</i></li>
		    <li>Copy the bridge list found below</li>
		    <li>Click <i>Push Button</i></li>
		</ol>
		<h6>Data Export</h6>
		<label for="bridges-gts" class="control-label small">CTRL+A, CTRL+C</label>
		<textarea class="form-control small" rows="6" id="bridges-gts" readonly>Loading data, please wait.</textarea>
	    </li>
	</div>
    </div>

</div>
<!-- Bridges -->
