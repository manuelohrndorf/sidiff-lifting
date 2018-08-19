As soon as we support NACs at <<delete>> nodes, move the following
two rules to complex.evolutionary:
- deleteBidirectionalReference
- deleteOppositeReference

The dependency analysis must pass the following tests
1. Testmodels A and B in which we delete a bidirectional reference
   -> unset must be done before deletion of single refs (dependency!)
2. Testmodels A and B in which we delete one of the opposite references
   -> unset must be done before deletion of ref (dependency!)
