<!--

This is an example file showing you how to use showsrc, and the showfiles.php script.
You can edit it in a stndard html editor that supports PHP tags (such as dreamweaver).

-->

<?php

// Include the php script
include_once ("function_showlisting.php");




?>

Example Source Code
<hr>

<?php

// Show all .java files in the eg directory and it's sub directories.
showListing("src/",".");
?>

<hr>
Example code 2.2
