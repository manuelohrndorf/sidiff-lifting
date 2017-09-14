/**
 */
package org.sidiff.editrule.rulebase;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Potential Conflict Kind</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.sidiff.editrule.rulebase.RulebasePackage#getPotentialConflictKind()
 * @model
 * @generated
 */
public enum PotentialConflictKind implements Enumerator {
	/**
	 * The '<em><b>Delete Use</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DELETE_USE_VALUE
	 * @generated
	 * @ordered
	 */
	DELETE_USE(0, "DeleteUse", "DeleteUse"),

	/**
	 * The '<em><b>Change Use</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CHANGE_USE_VALUE
	 * @generated
	 * @ordered
	 */
	CHANGE_USE(1, "ChangeUse", "ChangeUse"),

	/**
	 * The '<em><b>Create Forbid</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CREATE_FORBID_VALUE
	 * @generated
	 * @ordered
	 */
	CREATE_FORBID(2, "CreateForbid", "CreateForbid"),

	/**
	 * The '<em><b>Change Forbid</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CHANGE_FORBID_VALUE
	 * @generated
	 * @ordered
	 */
	CHANGE_FORBID(3, "ChangeForbid", "ChangeForbid");

	/**
	 * The '<em><b>Delete Use</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Delete Use</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DELETE_USE
	 * @model name="DeleteUse"
	 * @generated
	 * @ordered
	 */
	public static final int DELETE_USE_VALUE = 0;

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
	public static final int CHANGE_USE_VALUE = 1;

	/**
	 * The '<em><b>Create Forbid</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Create Forbid</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CREATE_FORBID
	 * @model name="CreateForbid"
	 * @generated
	 * @ordered
	 */
	public static final int CREATE_FORBID_VALUE = 2;

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
	public static final int CHANGE_FORBID_VALUE = 3;

	/**
	 * An array of all the '<em><b>Potential Conflict Kind</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final PotentialConflictKind[] VALUES_ARRAY =
		new PotentialConflictKind[] {
			DELETE_USE,
			CHANGE_USE,
			CREATE_FORBID,
			CHANGE_FORBID,
		};

	/**
	 * A public read-only list of all the '<em><b>Potential Conflict Kind</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<PotentialConflictKind> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Potential Conflict Kind</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static PotentialConflictKind get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			PotentialConflictKind result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Potential Conflict Kind</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static PotentialConflictKind getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			PotentialConflictKind result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Potential Conflict Kind</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static PotentialConflictKind get(int value) {
		switch (value) {
			case DELETE_USE_VALUE: return DELETE_USE;
			case CHANGE_USE_VALUE: return CHANGE_USE;
			case CREATE_FORBID_VALUE: return CREATE_FORBID;
			case CHANGE_FORBID_VALUE: return CHANGE_FORBID;
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
	private PotentialConflictKind(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getValue() {
	  return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
	  return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
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
	
} //PotentialConflictKind
