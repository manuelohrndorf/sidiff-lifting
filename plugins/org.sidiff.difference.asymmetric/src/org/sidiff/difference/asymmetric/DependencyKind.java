/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.difference.asymmetric;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Dependency Kind</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.sidiff.difference.asymmetric.AsymmetricPackage#getDependencyKind()
 * @model
 * @generated
 */
public enum DependencyKind implements Enumerator {
	/**
	 * The '<em><b>Use Delete</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #USE_DELETE_VALUE
	 * @generated
	 * @ordered
	 */
	USE_DELETE(0, "UseDelete", "UseDelete"),

	/**
	 * The '<em><b>Create Use</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CREATE_USE_VALUE
	 * @generated
	 * @ordered
	 */
	CREATE_USE(1, "CreateUse", "CreateUse"),

	/**
	 * The '<em><b>Delete Forbid</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DELETE_FORBID_VALUE
	 * @generated
	 * @ordered
	 */
	DELETE_FORBID(2, "DeleteForbid", "DeleteForbid"),

	/**
	 * The '<em><b>Forbid Create</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #FORBID_CREATE_VALUE
	 * @generated
	 * @ordered
	 */
	FORBID_CREATE(3, "ForbidCreate", "ForbidCreate"),

	/**
	 * The '<em><b>Change Use</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CHANGE_USE_VALUE
	 * @generated
	 * @ordered
	 */
	CHANGE_USE(4, "ChangeUse", "ChangeUse"),

	/**
	 * The '<em><b>Use Change</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #USE_CHANGE_VALUE
	 * @generated
	 * @ordered
	 */
	USE_CHANGE(5, "UseChange", "UseChange"), /**
	 * The '<em><b>Change Forbid</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CHANGE_FORBID_VALUE
	 * @generated
	 * @ordered
	 */
	CHANGE_FORBID(6, "ChangeForbid", "ChangeForbid"), /**
	 * The '<em><b>Forbid Change</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #FORBID_CHANGE_VALUE
	 * @generated
	 * @ordered
	 */
	FORBID_CHANGE(7, "ForbidChange", "ForbidChange"), /**
	 * The '<em><b>Dangling Dependency</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DANGLING_DEPENDENCY_VALUE
	 * @generated
	 * @ordered
	 */
	DANGLING_DEPENDENCY(8, "DanglingDependency", "DanglingDependency");

	/**
	 * The '<em><b>Use Delete</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Use Delete</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #USE_DELETE
	 * @model name="UseDelete"
	 * @generated
	 * @ordered
	 */
	public static final int USE_DELETE_VALUE = 0;

	/**
	 * The '<em><b>Create Use</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Create Use</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CREATE_USE
	 * @model name="CreateUse"
	 * @generated
	 * @ordered
	 */
	public static final int CREATE_USE_VALUE = 1;

	/**
	 * The '<em><b>Delete Forbid</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Delete Forbid</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DELETE_FORBID
	 * @model name="DeleteForbid"
	 * @generated
	 * @ordered
	 */
	public static final int DELETE_FORBID_VALUE = 2;

	/**
	 * The '<em><b>Forbid Create</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Forbid Create</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #FORBID_CREATE
	 * @model name="ForbidCreate"
	 * @generated
	 * @ordered
	 */
	public static final int FORBID_CREATE_VALUE = 3;

	/**
	 * The '<em><b>Change Use</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Change Use</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CHANGE_USE
	 * @model name="ChangeUse"
	 * @generated
	 * @ordered
	 */
	public static final int CHANGE_USE_VALUE = 4;

	/**
	 * The '<em><b>Use Change</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Use Change</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #USE_CHANGE
	 * @model name="UseChange"
	 * @generated
	 * @ordered
	 */
	public static final int USE_CHANGE_VALUE = 5;

	/**
	 * The '<em><b>Change Forbid</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Change Forbid</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CHANGE_FORBID
	 * @model name="ChangeForbid"
	 * @generated
	 * @ordered
	 */
	public static final int CHANGE_FORBID_VALUE = 6;

	/**
	 * The '<em><b>Forbid Change</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Forbid Change</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #FORBID_CHANGE
	 * @model name="ForbidChange"
	 * @generated
	 * @ordered
	 */
	public static final int FORBID_CHANGE_VALUE = 7;

	/**
	 * The '<em><b>Dangling Dependency</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DANGLING_DEPENDENCY
	 * @model name="DanglingDependency"
	 * @generated
	 * @ordered
	 */
	public static final int DANGLING_DEPENDENCY_VALUE = 8;

	/**
	 * An array of all the '<em><b>Dependency Kind</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final DependencyKind[] VALUES_ARRAY =
		new DependencyKind[] {
			USE_DELETE,
			CREATE_USE,
			DELETE_FORBID,
			FORBID_CREATE,
			CHANGE_USE,
			USE_CHANGE,
			CHANGE_FORBID,
			FORBID_CHANGE,
			DANGLING_DEPENDENCY,
		};

	/**
	 * A public read-only list of all the '<em><b>Dependency Kind</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<DependencyKind> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Dependency Kind</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static DependencyKind get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			DependencyKind result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Dependency Kind</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static DependencyKind getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			DependencyKind result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Dependency Kind</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static DependencyKind get(int value) {
		switch (value) {
			case USE_DELETE_VALUE: return USE_DELETE;
			case CREATE_USE_VALUE: return CREATE_USE;
			case DELETE_FORBID_VALUE: return DELETE_FORBID;
			case FORBID_CREATE_VALUE: return FORBID_CREATE;
			case CHANGE_USE_VALUE: return CHANGE_USE;
			case USE_CHANGE_VALUE: return USE_CHANGE;
			case CHANGE_FORBID_VALUE: return CHANGE_FORBID;
			case FORBID_CHANGE_VALUE: return FORBID_CHANGE;
			case DANGLING_DEPENDENCY_VALUE: return DANGLING_DEPENDENCY;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private DependencyKind(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getValue() {
	  return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
	  return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getLiteral() {
	  return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}
	
} //DependencyKind
