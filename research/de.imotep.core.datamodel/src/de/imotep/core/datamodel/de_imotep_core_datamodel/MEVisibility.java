/**
 */
package de.imotep.core.datamodel.de_imotep_core_datamodel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>ME Visibility</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.De_imotep_core_datamodelPackage#getMEVisibility()
 * @model
 * @generated
 */
public enum MEVisibility implements Enumerator {
	/**
	 * The '<em><b>PUBLIC</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PUBLIC_VALUE
	 * @generated
	 * @ordered
	 */
	PUBLIC(0, "PUBLIC", "PUBLIC"),

	/**
	 * The '<em><b>PRIVATE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PRIVATE_VALUE
	 * @generated
	 * @ordered
	 */
	PRIVATE(1, "PRIVATE", "PRIVATE");

	/**
	 * The '<em><b>PUBLIC</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>PUBLIC</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PUBLIC
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int PUBLIC_VALUE = 0;

	/**
	 * The '<em><b>PRIVATE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>PRIVATE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PRIVATE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int PRIVATE_VALUE = 1;

	/**
	 * An array of all the '<em><b>ME Visibility</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final MEVisibility[] VALUES_ARRAY =
		new MEVisibility[] {
			PUBLIC,
			PRIVATE,
		};

	/**
	 * A public read-only list of all the '<em><b>ME Visibility</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<MEVisibility> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>ME Visibility</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static MEVisibility get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			MEVisibility result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>ME Visibility</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static MEVisibility getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			MEVisibility result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>ME Visibility</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static MEVisibility get(int value) {
		switch (value) {
			case PUBLIC_VALUE: return PUBLIC;
			case PRIVATE_VALUE: return PRIVATE;
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
	private MEVisibility(int value, String name, String literal) {
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
	
} //MEVisibility
