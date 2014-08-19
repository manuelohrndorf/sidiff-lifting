<?php
	require_once("showsrc/function_showsrc.php");

	$src = $_GET['src'];

	$spaces_after_linenum = "";
	$default_show_line_numbers = true;

	// Get Source Path
	$path = "";


	if ($src == "EditRule2RecognitionRule") {
		$path = "src/EditRule2RecognitionRule.java";	
	}
	else if ($src == "HenshinRuleAnalysisUtilEx") {
		$path = "src/HenshinRuleAnalysisUtilEx.java";
	}
	else if ($src == "NodePair") {
		$path = "src/NodePair.java";
	}

	if ($path != "") {
		// Check Source
		if (!file_exists($path)) {
			echo "<b>Error:</b> Non existant file.";
			exit;
		}

		$link = file($path);
		$loc = sizeof($link);
		$source = implode("",$link);
		$loc++;
	}

?>

<html>
  <head>
        <title><?php include("showsrc/showsrc_title.php"); ?></title>
        <link rel=stylesheet href="showsrc/code.css" type="text/css">  
  </head>
  <body>
    <?php include("showsrc/showsrc_header.php"); ?>

    <?php 

    outputSource($source, $default_show_line_numbers);

    ?>

    <?php include("showsrc/showsrc_footer.php"); ?>
  </body>
</html>