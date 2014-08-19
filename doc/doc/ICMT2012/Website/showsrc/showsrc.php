<?php

/*
    showsrc
    Copyright (C) 2002 William Denniss

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program in the "COPYING.txt" file; if not, write to the 
    Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, 
    MA 02111-1307  USA or visit http://www.gnu.org/

*/

/** showsrc
 *
 * This PHP script makes it possible to dynamically generate nicly coloured source code for web pages.
 *
 * For example, if you have the file "eg/Delegate.java" (relitive to the script) you can use the script like so:
 *  showsrc.php?src=eg/Delegate.java
 *
 * The Headers, Footers, Title, and the CSS colour scheme can easily be changed by editing the
 * showsrc_header.php, showsrc_footer.php, showsrc_title.php and code.css respectivly
 *
 * The PHP variables that can be used in the header, footer and title are as follows:
 *  - $loc - number of Lines of Code
 *  - $src - the path to the unformatted source
 *
 * Take a look at the sourceindex.php and showfiles.php for an easy way to index your source files on the fly.
 */

require_once("function_showsrc.php");

$supported_files = "java|c|cc|cpp|h|cs";
$spaces_after_linenum = "";
$default_show_line_numbers = true;

$src = $_GET['src'];


// Check for illegal usage of script
if ($src == "") {
	echo "<b>Error:</b> Please specify a source file eg. ?src=MySourceFile.java";
	exit;
}

if (!preg_match("/.(".$supported_files.")\$/mi", $src)) {
	echo "<b>Error:</b> Unsupported File Type.";
	exit;
}
	
if (!file_exists($src)) {
	echo "<b>Error:</b> Non existant file.";
	exit;
}

// Reads source file off disk
$link = file($src);
$loc = sizeof($link);
$source = implode("",$link);

$loc++;

?>

<html>
  <head>
        <title><?php include("showsrc_title.php"); ?></title>
        <link rel=stylesheet href="code.css" type="text/css">  
  </head>
  <body>
  <?php include("showsrc_header.php"); ?>

<?php 

outputSource($source, $default_show_line_numbers);


?>

     <?php include("showsrc_footer.php"); ?>
   </body>
</html>
