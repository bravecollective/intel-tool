<?php if (!defined('INTEL')) die('go away'); ?>

<!-- Map -->
    <div id="map-heat" style="position:absolute; top:60px; bottom:5px; left:10px; width:69%; z-index:-3;">
    </div>
    <div id="map-heat-legend" style="position:absolute; bottom:5px; left:10px; width: 114px; height: 29px; z-index:-1;">
	<canvas id="canvas-heat-legend" width="110" height="25"></canvas>
    </div>
    <div id="map" style="position:absolute; top:60px; bottom:5px; left:10px; width:69%; z-index:-1;">
	<canvas id="canvas"></canvas>
    </div>
<!-- Map -->

<!-- Logs -->
    <div id="map-log-btn-hidden" class="btn btn-primary btn-xs hide" onclick="logsToggle();" style="position:absolute; right: 2px; top: 55px; opacity: 0.6; z-index: 42;">show</div>
    <div id="map-log" style="position:absolute; top:60px; bottom:10px; left:71%; right:0px; padding-right:5px; margin-bottom: 5px; overflow-y: scroll; overflow-x: auto; z-index:-1;">
	<p style="line-height:200%;">
	    <label>Filters</label> <span class="text-muted">click to remove or toggle</span>
	    <span class="pull-right"><button type="button" class="btn btn-link btn-xs" onclick="logsToggle();">hide</button></span>
	    <span class="pull-right"><button type="button" class="btn btn-link btn-xs" onclick="logsFilterSystemsClear(); applyFilter();">clear filters</button></span>
	    <br>
	    <button type="button" id="filter-unknown" class="btn btn-default btn-xs" onclick="logsFilterUnknownsToggle(); applyFilter();">Unknown</button>
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

<!-- Popups -->
      <div class="hide" id="popsys" style="min-width:120px"  onclick="$('#popsys').addClass('hide')">
        <table class="table table-condensed" style='border: 2px solid black;'>
          <thead>
            <tr>
              <th style='border: 2px solid black; text-align:center;'>
		    <span id="popsys-name" style="font-size: 120%;">&nbsp;</span>
              </th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td style='border: 2px solid black;' id="popsys-content">&nbsp;</td>
            </tr>
          </tbody>
        </table>
      </div>
<!-- Popups -->
