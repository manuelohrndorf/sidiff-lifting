                                    _/    
                      _________   _/      
                     /          \/        
              ______/____________\______  
             /                         /  
            /  Tank Software          /   
           /     is William Denniss  /    
          /_________________________/     
                                          
              > TankSoftware.com          
                                          
					  
		     -------
                     showsrc
                     -------


Showsrc is a php script that "pretty prints" source code on the fly to the 
browser, marking up keywords and comments so as to make the code more readable 
than if it is mono-coloured. 

Included is also a simple script "showlisting" which can show all source files
in a tree structure.

To use this script, simply upload it to a PHP capable web server.  Upload all
of your source files into a directory named "src" within the showsrc one.
Point your browser to the sourceindex.php file and enjoy.

Alternativly you can simply link to showsrc.php from another HTML page.  Simply
pass the source code as the value of the "src" variable.  Eg:
showsrc.php?src=/path.to/Source.java

If you are really keen - then you can ignore the showsrc.php file and use the
functions from within function_showsrc.php to further customise it.  There
is also a sample implementation of a script which can be used to pass 
showsrc.php source of a remote origin (URL, file upload and pasted text - 
local files are disabled by default), it is named wshowsrc.php (web showsrc).
A working version of this can be found at http://showsrc.com/

See the showsrc.php and associated php files for more info.

Will.


Contact: showsrc@tanksoftware.com
Web Page: http://showsrc.sf.net/
				

    showsrc
    Copyright (C) 2002-2004 William Denniss

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
