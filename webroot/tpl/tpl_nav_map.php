<?php if (!defined('INTEL')) die('go away'); ?>

<!-- Navigation MAP -->
    <span class="navbar-form navbar-left">
	<input type="text" class="form-control text-right typeahead" placeholder="Search System" id="system-search" onkeypress="if (event.keyCode == 13) systemLucky(value);">
    </span>

    <li class="navbar-text" style="border-right:1px solid;">&nbsp;</li>

    <li class="navbar-text">Polling <button id="poller" class="btn btn-xs btn-info" onclick="pollToggle()">?</span></li>
    <li class="navbar-text">Uploaders <span id="uploader" class="label label-info">?</span></li>

    <li class="dropdown">
	<a href="#" class="dropdown-toggle" data-toggle="dropdown">Region <b class="caret"></b></a>
	<ul class="dropdown-menu"  style="overflow: hidden; overflow-y: auto; max-height: 400px;">
<li><a href="#" onclick="drawLoad('Fountain');">Fountain</a></li>
<li><a href="#" onclick="drawLoad('Aridia');">Aridia</a></li>
<li><a href="#" onclick="drawLoad('Delve');">Delve</a></li>
<li><a href="#" onclick="drawLoad('Cloud_Ring');">Cloud Ring</a></li>
<li><a href="#" onclick="drawLoad('Outer_Ring');">Outer Ring</a></li>
<li class="divider"></li>
<li><a href="#" onclick="drawLoad('Catch');">Catch</a></li>
<li><a href="#" onclick="drawLoad('Providence');">Providence</a></li>
<li class="divider"></li>
<li><a href="#" onclick="drawLoad('Aridia');">Aridia</a></li>
<li><a href="#" onclick="drawLoad('Black_Rise');">Black Rise</a></li>
<li><a href="#" onclick="drawLoad('The_Bleak_Lands');">The Bleak Lands</a></li>
<li><a href="#" onclick="drawLoad('Branch');">Branch</a></li>
<li><a href="#" onclick="drawLoad('Cache');">Cache</a></li>
<li><a href="#" onclick="drawLoad('Catch');">Catch</a></li>
<li><a href="#" onclick="drawLoad('The_Citadel');">The Citadel</a></li>
<li><a href="#" onclick="drawLoad('Cloud_Ring');">Cloud Ring</a></li>
<li><a href="#" onclick="drawLoad('Cobalt_Edge');">Cobalt Edge</a></li>
<li><a href="#" onclick="drawLoad('Curse');">Curse</a></li>
<li><a href="#" onclick="drawLoad('Deklein');">Deklein</a></li>
<li><a href="#" onclick="drawLoad('Delve');">Delve</a></li>
<li><a href="#" onclick="drawLoad('Derelik');">Derelik</a></li>
<li><a href="#" onclick="drawLoad('Detorid');">Detorid</a></li>
<li><a href="#" onclick="drawLoad('Devoid');">Devoid</a></li>
<li><a href="#" onclick="drawLoad('Domain');">Domain</a></li>
<li><a href="#" onclick="drawLoad('Esoteria');">Esoteria</a></li>
<li><a href="#" onclick="drawLoad('Essence');">Essence</a></li>
<li><a href="#" onclick="drawLoad('Etherium_Reach');">Etherium Reach</a></li>
<li><a href="#" onclick="drawLoad('Everyshore');">Everyshore</a></li>
<li><a href="#" onclick="drawLoad('Fade');">Fade</a></li>
<li><a href="#" onclick="drawLoad('Feythabolis');">Feythabolis</a></li>
<li><a href="#" onclick="drawLoad('The_Forge');">The Forge</a></li>
<li><a href="#" onclick="drawLoad('Fountain');">Fountain</a></li>
<li><a href="#" onclick="drawLoad('Geminate');">Geminate</a></li>
<li><a href="#" onclick="drawLoad('Genesis');">Genesis</a></li>
<li><a href="#" onclick="drawLoad('Great_Wildlands');">Great Wildlands</a></li>
<li><a href="#" onclick="drawLoad('Heimatar');">Heimatar</a></li>
<li><a href="#" onclick="drawLoad('Immensea');">Immensea</a></li>
<li><a href="#" onclick="drawLoad('Impass');">Impass</a></li>
<li><a href="#" onclick="drawLoad('Insmother');">Insmother</a></li>
<li><a href="#" onclick="drawLoad('Kador');">Kador</a></li>
<li><a href="#" onclick="drawLoad('The_Kalevala_Expanse');">The Kalevala Expanse</a></li>
<li><a href="#" onclick="drawLoad('Khanid');">Khanid</a></li>
<li><a href="#" onclick="drawLoad('Kor-Azor');">Kor-Azor</a></li>
<li><a href="#" onclick="drawLoad('Lonetrek');">Lonetrek</a></li>
<li><a href="#" onclick="drawLoad('Malpais');">Malpais</a></li>
<li><a href="#" onclick="drawLoad('Metropolis');">Metropolis</a></li>
<li><a href="#" onclick="drawLoad('Molden_Heath');">Molden Heath</a></li>
<li><a href="#" onclick="drawLoad('Oasa');">Oasa</a></li>
<li><a href="#" onclick="drawLoad('Omist');">Omist</a></li>
<li><a href="#" onclick="drawLoad('Outer_Passage');">Outer Passage</a></li>
<li><a href="#" onclick="drawLoad('Outer_Ring');">Outer Ring</a></li>
<li><a href="#" onclick="drawLoad('Paragon_Soul');">Paragon Soul</a></li>
<li><a href="#" onclick="drawLoad('Period_Basis');">Period Basis</a></li>
<li><a href="#" onclick="drawLoad('Perrigen_Falls');">Perrigen Falls</a></li>
<li><a href="#" onclick="drawLoad('Placid');">Placid</a></li>
<li><a href="#" onclick="drawLoad('Providence');">Providence</a></li>
<li><a href="#" onclick="drawLoad('Pure_Blind');">Pure Blind</a></li>
<li><a href="#" onclick="drawLoad('Querious');">Querious</a></li>
<li><a href="#" onclick="drawLoad('Scalding_Pass');">Scalding Pass</a></li>
<li><a href="#" onclick="drawLoad('Sinq_Laison');">Sinq Laison</a></li>
<li><a href="#" onclick="drawLoad('Solitude');">Solitude</a></li>
<li><a href="#" onclick="drawLoad('The_Spire');">The Spire</a></li>
<li><a href="#" onclick="drawLoad('Stain');">Stain</a></li>
<li><a href="#" onclick="drawLoad('Syndicate');">Syndicate</a></li>
<li><a href="#" onclick="drawLoad('Tash-Murkon');">Tash-Murkon</a></li>
<li><a href="#" onclick="drawLoad('Tenal');">Tenal</a></li>
<li><a href="#" onclick="drawLoad('Tenerifis');">Tenerifis</a></li>
<li><a href="#" onclick="drawLoad('Tribute');">Tribute</a></li>
<li><a href="#" onclick="drawLoad('Vale_of_the_Silent');">Vale of the Silent</a></li>
<li><a href="#" onclick="drawLoad('Venal');">Venal</a></li>
<li><a href="#" onclick="drawLoad('Verge_Vendor');">Verge Vendor</a></li>
<li><a href="#" onclick="drawLoad('Wicked_Creek');">Wicked Creek</a></li>

	</ul>
    </li>


    <li class="dropdown">
	<a href="#" class="dropdown-toggle" data-toggle="dropdown">Heatmap <b class="caret"></b></a>
	<ul class="dropdown-menu">
	    <li class="dropdown-header">Off</li>
	    <li><a href="#" onclick="eveMode = 0; eveDraw();">None</a></li>
	    <li class="dropdown-header">Stats (1h)</li>
	    <li><a href="#" onclick="eveMode = 1; eveDraw();">Jumps</a></li>
	    <li class="dropdown-header">Kills (1h)</li>
	    <li><a href="#" onclick="eveMode = 2; eveDraw();">Ships</a></li>
	    <li><a href="#" onclick="eveMode = 3; eveDraw();">Pods</a></li>
	    <li><a href="#" onclick="eveMode = 5; eveDraw();">Ships &amp; pods</a></li>
	    <li><a href="#" onclick="eveMode = 4; eveDraw();">Rats</a></li>
	</ul>
    </li>


    <li class="dropdown">
	<a href="#" class="dropdown-toggle" data-toggle="dropdown">Legend <b class="caret"></b></a>
	<ul class="dropdown-menu">

	    <li class="dropdown-header">Systems</li>
	    <li><a href="#"><span id="legend-system-station">&#9632;</span> &nbsp;&nbsp;&nbsp; System with station</a></li>
	    <li><a href="#"><span id="legend-system">&#9679;</span> &nbsp;&nbsp;&nbsp; System without station</a></li>

	    <li class="dropdown-header">Connections</li>
	    <li><a href="#"><span id="legend-j">&#9644;</span> &nbsp;&nbsp;&nbsp; Gate within constellation</a></li>
	    <li><a href="#"><span id="legend-jc">&#9644;</span> &nbsp;&nbsp;&nbsp; Gate connecting constellations</a></li>
	    <li><a href="#"><span id="legend-jr">&#9644;</span> &nbsp;&nbsp;&nbsp; Gate connecting regions</a></li>
	    <li><a href="#"><span id="legend-jbf">&#9644;</span> &nbsp;&nbsp;&nbsp; Friendly jump bridge</a></li>
	    <li><a href="#"><span id="legend-jbh">&#9644;</span> &nbsp;&nbsp;&nbsp; Hostile jump bridge</a></li>

	    <li class="dropdown-header">Age</li>
	    <li><a href="#"><span id="legend-less2">&#9632; &#9679;</span> &nbsp;&nbsp; Less than 2m ago</a></li>
	    <li><a href="#"><span id="legend-less5">&#9632; &#9679;</span> &nbsp;&nbsp; Less than 5m ago</a></li>
	    <li><a href="#"><span id="legend-less10">&#9632; &#9679;</span> &nbsp;&nbsp; Less than 10m ago</a></li>
	    <li><a href="#"><span id="legend-less15">&#9632; &#9679;</span> &nbsp;&nbsp; Less than 15m ago</a></li>
	    <li><a href="#"><span id="legend-less20">&#9632; &#9679;</span> &nbsp;&nbsp; Less than 20m ago</a></li>

	</ul>
    </li>


    <li class="navbar-text" style="border-right:1px solid;">&nbsp;</li>
<!-- Navigation MAP -->
