<?php
/*
    showsrc
    Copyright (C) 2002-4 William Denniss

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


// Stores certain elements such as comments for inclusion at the end of the processing

$strings = array();
$strings_counter = 0;

function showsrc($source) {
	global $strings;
	global $strings_counter;
	
	// Replace less than character
	$source = str_replace("<","&lt;",$source);
	
	//Pattern for breaking char
	$breakingchar = " |	|\\\\r|\\\\n|\\(|\\)|\\[|\\]|{|}";
	//Space variations
	$space = " |	|\\\\r|\\\\n";
	
	/*
	 * Comments
	 *
	 */
	$methods = "/(\\/\\*[\\w\\W]*\\*\\/)|". // Block and Javadoc comments (/*, anything, */) (note: ungready modifyer set)
		   "(\"(\\\\\"|[^\"])*\")|". // String literals (", anything including \" but not " , ")
		   "('[\\w\\W]*')|".  // Char literal (', anything, ')
		   "(\\/\\/[\\w\\W]*\$)/mU"; // End of line comment (//, anything, end of line)
		 
	// All such items get processed and stored away (so that none of the keyword etc replacements effect them
	$source = preg_replace_callback ($methods, "processComments", $source);

	//>>>>
	$source = preg_replace ('/class/', "<span class=\"keyword\">class</span>", $source);
	$source = preg_replace ('/static/', "<span class=\"keyword\">static</span>", $source);
	//>>>>

	// Keywords
	$keywords = "/(^|$breakingchar)(abstract|break|byvalue|case|cast|catch|const|continue|default|".
		"do|else|extends|false|final|finally|future|generic|goto|if|implements|import|".
		"inner|instanceof|interface|native|new|null|operator|outer|".
		"package|private|protected|public|rest|return|super|switch|synchronised|".
		"this|throw|throws|transient|true|try|var|volatile|while|for)($breakingchar)/m";
	$replace_keyword = "\\1<span class=\"keyword\">\\2</span>\\3";
	$source = preg_replace ($keywords, $replace_keyword, $source);
	
	// Primitives
	$primitives = "/(^|$breakingchar)(boolean|byte|char|double|float|int|long|short|void)($breakingchar)/m";
	$replace_keyword = "\\1<span class=\"primitive\">\\2</span>\\3";
	$source = preg_replace ($primitives, $replace_keyword, $source);
	
	// Methods/Classes
	$methods = "/(|$space)([\\w^\\(]*)($space)*(\\()/m";
	$replace_keyword = "<span class=\"method\">\\1\\2\\3</span>(";
	$source = preg_replace ($methods, $replace_keyword, $source);
	
	// Put back in javadoc, block, literals and inline comments
	$source = restoreStrings($source);
	
	return $source;
}


function processComments ($input) {
	global $strings;
	global $strings_counter;	
	
	$output = trim($input[0]);
	//>>>>>$output = $input[0];
	
	$match = false;
	
	// Javadoc Comment
	if (!$match) {
		$pattern = "/(\\/\\*\\*)([\\W\\w]*)(\\*\\/)/m";
		$replace_keyword = "<span class=\"javadoc\">\\1\\2\\3\\4</span>";
		$match = preg_match($pattern, $output);
		$output = preg_replace ($pattern, $replace_keyword, $output);

		//>>>>
		$output = preg_replace ('/@param/', "<span class=\"jdockey\">@param</span>", $output);
		$output = preg_replace ('/@return/', "<span class=\"jdockey\">@return</span>", $output);
		$output = preg_replace ('/@author/', "<span class=\"jdockey\">@author</span>", $output);
		//>>>>
	}
	
	//Normal Comment
	if (!$match) {
		$pattern = "/(\\/\\*)([\\W\\w]*)(\\*\\/)/m";
		$replace_keyword = "<span class=\"blockcomment\">\\1\\2\\3\\4</span>";
		$match = preg_match($pattern, $output);
		$output = preg_replace ($pattern, $replace_keyword, $output);
	}
	
	// String Literals
	if (!$match) {
		$pattern = "/(\")((\\\\\"|[^\"])*)(\")/m";
		$replace_keyword = "\\1<span class=\"literal\">\\2</span>\\4";
		$match = preg_match($pattern, $output);
		$output = preg_replace ($pattern, $replace_keyword, $output);
	}	
	
	// Char Literals
	if (!$match) {
		$pattern = "/(')(\\\\\'|[^\'])*(')/m";
		$replace_keyword = "\\1<span class=\"literal\">\\2</span>\\3";
		$match = preg_match($pattern, $output);
		$output = preg_replace ($pattern, $replace_keyword, $output);
	}	

	// In Line comment
	if (!$match) {	
		$pattern =  "/(\\/\\/)([\\W\\w]*)/m";
		$replace_keyword = "<span class=\"comment\">\\1\\2</span>";
		$match = preg_match($pattern, $output);
		$output = preg_replace ($pattern, $replace_keyword, $output);
	}	
	
	$strings[$strings_counter] = $output;
	$strings_counter++;
	
	return "!".($strings_counter-1)."#";
}


function getLines ($src) {

	$formatted = $src;
	
	$lines = explode("\n", $formatted);
	
	$digits = strlen(sizeof($lines)."");
	
	for ($i = 0; $i < sizeof($lines); $i++) {
		
		$number = ($i+1)."";
		$space = "";

		while ((strlen($space) + strlen($number)) < $digits) {
			$space = " ".$space;
		}
		
		$lines[$i] = $space."<a class=\"linelink\" name=\"".$number."\" href=\"#".$number."\">".$number."</a>";
	}
	
	$formatted = implode("\n", $lines);	
	
	return $formatted;
	
}

function outputSource ($source, $default_show_line_numbers) {
	
	$lines = ($_GET['showlines'] == "true" || ($default_show_line_numbers && $_GET['showlines'] != "false"));
	
	$formatted = showsrc($source);
	
	if ($lines) {
		?>
		<table cellpadding="0" cellspacing="0" border="0"><tr><td valign="top"><pre><div class="lineruler"><span class="linenum"><?php
		echo getLines($formatted);
		?></span></div></pre></td>
		<td valign="top">
		
		<?php
	}
	?><pre><?php
	echo $formatted;
	?></pre><?php
	
	if ($lines) {
		?>
		</td></tr></table>
		<?php
	}
	
		
}


function restoreStrings($input) {
	global $strings;
	global $strings_counter;
	
	$methods = "/!([\d]+)#/e";

	return preg_replace ($methods, "\$strings['\\1']", $input);
	
}




