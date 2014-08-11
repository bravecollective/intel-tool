<?php if (!defined('INTEL')) die('go away'); ?>

    <div style="font-size:70%; position:fixed; bottom:1px; right:1px; z-index:-23;" ><a href="http://evewho.com/pilot/kiu+Nakamura">kiu Nakamura</a> &bull; <a href="http://github.com/kiu/bravecollective-intel">github.com</a></div>

    <script src="js/jquery-1.10.2.min.js"></script>
    <script src="js/jquery.cookie.js"></script>
    <script src="js/bootstrap.min.js"></script>

<?php
    foreach ($pScripts as $file) {
	echo "  <script src='" . $file . "'></script>\n";
    }
?>

  </body>

</html>
