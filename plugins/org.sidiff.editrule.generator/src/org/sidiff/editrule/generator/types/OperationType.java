package org.sidiff.editrule.generator.types;

public enum OperationType  { CREATE,DELETE,SET_ATTRIBUTE,
	SET_REFERENCE,UNSET_ATTRIBUTE,
	UNSET_REFERENCE,
	ADD,REMOVE,CHANGE_LITERAL, CHANGE_REFERENCE,
	MOVE,MOVE_REFERENCE_COMBINATION,MOVE_UP,MOVE_DOWN, ATTACH, DETACH,
	UNKNOWN; }
