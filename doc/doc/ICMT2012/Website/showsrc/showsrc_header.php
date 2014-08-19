<table width="100%" border="0" cellspacing="1" cellpadding="0"><tr><td>

<a target="_self" href="index.htm">Back</a> | Currently showing <a href="<?php echo($path); ?>"><?php echo($path); ?></a> 

</td><td style="text-align: right">

<?php
if ($_GET['showlines'] == "true" || ($default_show_line_numbers && $_GET['showlines'] != "false")) {
	echo "Line Numbers: [show|<a href=\"".$_SERVER['REQUEST_URI']."&showlines=false\">hide</a>]";
} else {
	echo "Line Numbers: [<a href=\"".$_SERVER['REQUEST_URI']."&showlines=true\">show</a>|hide]";
}
?>

</td></tr></table>
<hr>

