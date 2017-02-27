Note that we generated the rules based on effective Henshin meta-model, which has been changed w.r.t. the original Henshin meta-model as follows:
- BinaryFormula.left: lowerBound changed from 1 to 0
- BinaryFormula.right: lowerBound changed from 1 to 0
- UnaryFormula.child: lowerBound changed from 1 to 0


Delete the following rules, they can never be applied without violating multiplicity constraints
(seems to be a bug in SERGe)
- ADD_Node_(incoming)_TGT_Edge_execute.henshin
- ADD_Node_(outgoing)_TGT_Edge_execute.henshin


There are definitely plenty of MOVE rules which do not make sense.
We keep them for the moment because they are not harmful, but this may be optimized in the future.  