There was an error in the original plugin:

MySQL-5.typeslibrary was referencing an element in LogicalModel.typeslibrary
which had different xmi:id. Adjusted the xmi:id of the target element
in LogicalModel.typeslibrary to:

<nativeTypes xmi:id="_WWew-BEnEeGTa_JlZYBw1A" name="Texte" spec="Length"/>

Now models can be loaded again.