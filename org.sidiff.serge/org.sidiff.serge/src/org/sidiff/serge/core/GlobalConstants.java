package org.sidiff.serge.core;

public interface GlobalConstants {
	
	public static final String CREATE_prefix 			= "CREATE_";
	public static final String DELETE_prefix			= "DELETE_";
	@Deprecated
	public static final String SET_prefix 				= "SET_";
	@Deprecated
	public static final String UNSET_prefix 			= "UNSET_";
	public static final String SET_ATTRIBUTE_prefix 	= "SET_ATTRIBUTE_";
	public static final String UNSET_ATTRIBUTE_prefix 	= "UNSET_ATTRIBUTE_";
	public static final String SET_REFERENCE_prefix 	= "SET_REFERENCE_";
	public static final String UNSET_REFERENCE_prefix 	= "UNSET_REFERENCE_";
	public static final String ADD_prefix 				= "ADD_";
	public static final String REMOVE_prefix 			= "REMOVE_";
	public static final String CHANGE_REFERENCE_prefix 	= "CHANGE_REFERENCE_";
	public static final String CHANGE_LITERAL_prefix 	= "CHANGE_LITERAL_";
	public static final String MOVE_prefix 				= "MOVE_";
	public static final String MOVE_MASKED_prefix 		= "MOVE_";
	public static final String MOVE_COMBI_prefix		= "MOVE_COMBI_";
	public static final String MOVE_UP_prefix			= "MOVE_UP_";
	public static final String MOVE_DOWN_prefix			= "MOVE_DOWN_";
	public static final String EXECUTE_suffix			= "_execute.henshin";
	public static final String INITIALCHECK_suffix		= "_initialcheck.henshin";
	public static final String TGT						= "_TGT_";
	public static final String FROM						= "_FROM_";
	public static final String TO						= "_TO_";
	public static final String IN						= "_IN_";
	
	public static final String SEL						= "Selected";
	public static final String SELEO					= "selectedEObject";
	public static final String CHILD					= "Child";
	public static final String EX						= "Existing";
	public static final String NEW						= "New";
	public static final String DEL						= "ToBeDeleted";
	public static final String NEWTGT					= "NewTarget";
	public static final String NEWSRC					= "NewSource";
	public static final String OLDTGT					= "OldTarget";
	public static final String OLDSRC					= "OldSource";
}
