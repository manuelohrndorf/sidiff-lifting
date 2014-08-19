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

/** showListing
 *
 * This PHP script makes indexing a source file directory very easy.  It can be used in conjuntion with the
 * showsrc.php script.
 *
 * For example, if you have source files in a directory named "eg" (relitive to the script) you can use the the showListing method
 * in your own page like so (see sourceindex.php for a working example):
 * include_once ("sourcefiles.php");
 * showListing("eg/",".java");
 *
 */


// Show all files which satisfy the filter in the given path including subdirectories
function showListing ($path, $filter) {

GetDirArray($path,$filter);

}

/*

Thanks to:
ansinn@comcat.com
justin@fanetic.com

On the PHP.net web page for their recursive directory searching code on which this code is based.

*/
function GetDirArray($sPath, $filter) {
	GetDirArrayR($sPath, $sPath, $filter);
}

function GetDirArrayR($sPath, $sorigpath, $filter)
{
	//Load Directory Into Array
	$handle=opendir($sPath);
	while ($file = readdir($handle))
	{
        	$retVal[count($retVal)] = $file;
	}
	//Clean up and sort
	closedir($handle); 	
	sort($retVal);
	//return $retVal;
	
	while (list($key, $val) = each($retVal))
	{
		if ($val != "." && $val != "..")
		{ 
	        	$path = str_replace("//","/",$sPath.$val);
			$linktext = substr($path, strlen($sorigpath));
	        	if (substr_count($path, $filter) != 0) {
				echo "<a href=\"showsrc.php?src=$path\">$linktext</a>";
				echo " - <a href=\"$path\">(text)</a><br>";
				
			}
			
	        	if (is_dir($sPath.$val))
	        	{
				
	        		GetDirArrayR($sPath.$val."/", $sorigpath,$filter);
	        	}
	    	}
	}
}



?>