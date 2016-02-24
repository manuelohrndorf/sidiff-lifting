/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.javaast.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Structural Fragment Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.sidiff.javaast.model.JavaModelPackage#getStructuralFragmentType()
 * @model
 * @generated
 */
public enum StructuralFragmentType implements Enumerator
{
  /**
	 * The '<em><b>If</b></em>' literal object.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #IF_VALUE
	 * @generated
	 * @ordered
	 */
  IF(0, "if", "if"),

  /**
	 * The '<em><b>For</b></em>' literal object.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #FOR_VALUE
	 * @generated
	 * @ordered
	 */
  FOR(1, "for", "for"),

  /**
	 * The '<em><b>Foreach</b></em>' literal object.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #FOREACH_VALUE
	 * @generated
	 * @ordered
	 */
  FOREACH(2, "foreach", "foreach"),

  /**
	 * The '<em><b>While</b></em>' literal object.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #WHILE_VALUE
	 * @generated
	 * @ordered
	 */
  WHILE(3, "while", "while"),

  /**
	 * The '<em><b>Do</b></em>' literal object.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #DO_VALUE
	 * @generated
	 * @ordered
	 */
  DO(4, "do", "do"),

  /**
	 * The '<em><b>Switch</b></em>' literal object.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #SWITCH_VALUE
	 * @generated
	 * @ordered
	 */
  SWITCH(5, "switch", "switch"),

  /**
	 * The '<em><b>Case</b></em>' literal object.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #CASE_VALUE
	 * @generated
	 * @ordered
	 */
  CASE(6, "case", "case"),

  /**
	 * The '<em><b>Finally</b></em>' literal object.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #FINALLY_VALUE
	 * @generated
	 * @ordered
	 */
  FINALLY(7, "finally", "finally"),

  /**
	 * The '<em><b>Try</b></em>' literal object.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #TRY_VALUE
	 * @generated
	 * @ordered
	 */
  TRY(8, "try", "try"),

  /**
	 * The '<em><b>Catch</b></em>' literal object.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #CATCH_VALUE
	 * @generated
	 * @ordered
	 */
  CATCH(9, "catch", "catch"),

  /**
	 * The '<em><b>Synchronized</b></em>' literal object.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #SYNCHRONIZED_VALUE
	 * @generated
	 * @ordered
	 */
  SYNCHRONIZED(10, "synchronized", "synchronized");

  /**
	 * The '<em><b>If</b></em>' literal value.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>If</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @see #IF
	 * @model name="if"
	 * @generated
	 * @ordered
	 */
  public static final int IF_VALUE = 0;

  /**
	 * The '<em><b>For</b></em>' literal value.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>For</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @see #FOR
	 * @model name="for"
	 * @generated
	 * @ordered
	 */
  public static final int FOR_VALUE = 1;

  /**
	 * The '<em><b>Foreach</b></em>' literal value.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Foreach</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @see #FOREACH
	 * @model name="foreach"
	 * @generated
	 * @ordered
	 */
  public static final int FOREACH_VALUE = 2;

  /**
	 * The '<em><b>While</b></em>' literal value.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>While</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @see #WHILE
	 * @model name="while"
	 * @generated
	 * @ordered
	 */
  public static final int WHILE_VALUE = 3;

  /**
	 * The '<em><b>Do</b></em>' literal value.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Do</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @see #DO
	 * @model name="do"
	 * @generated
	 * @ordered
	 */
  public static final int DO_VALUE = 4;

  /**
	 * The '<em><b>Switch</b></em>' literal value.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Switch</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @see #SWITCH
	 * @model name="switch"
	 * @generated
	 * @ordered
	 */
  public static final int SWITCH_VALUE = 5;

  /**
	 * The '<em><b>Case</b></em>' literal value.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Case</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @see #CASE
	 * @model name="case"
	 * @generated
	 * @ordered
	 */
  public static final int CASE_VALUE = 6;

  /**
	 * The '<em><b>Finally</b></em>' literal value.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Finally</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @see #FINALLY
	 * @model name="finally"
	 * @generated
	 * @ordered
	 */
  public static final int FINALLY_VALUE = 7;

  /**
	 * The '<em><b>Try</b></em>' literal value.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Try</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @see #TRY
	 * @model name="try"
	 * @generated
	 * @ordered
	 */
  public static final int TRY_VALUE = 8;

  /**
	 * The '<em><b>Catch</b></em>' literal value.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Catch</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @see #CATCH
	 * @model name="catch"
	 * @generated
	 * @ordered
	 */
  public static final int CATCH_VALUE = 9;

  /**
	 * The '<em><b>Synchronized</b></em>' literal value.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Synchronized</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @see #SYNCHRONIZED
	 * @model name="synchronized"
	 * @generated
	 * @ordered
	 */
  public static final int SYNCHRONIZED_VALUE = 10;

  /**
	 * An array of all the '<em><b>Structural Fragment Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private static final StructuralFragmentType[] VALUES_ARRAY =
    new StructuralFragmentType[] {
			IF,
			FOR,
			FOREACH,
			WHILE,
			DO,
			SWITCH,
			CASE,
			FINALLY,
			TRY,
			CATCH,
			SYNCHRONIZED,
		};

  /**
	 * A public read-only list of all the '<em><b>Structural Fragment Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public static final List<StructuralFragmentType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

  /**
	 * Returns the '<em><b>Structural Fragment Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public static StructuralFragmentType get(String literal)
  {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			StructuralFragmentType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

  /**
	 * Returns the '<em><b>Structural Fragment Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public static StructuralFragmentType getByName(String name)
  {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			StructuralFragmentType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

  /**
	 * Returns the '<em><b>Structural Fragment Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public static StructuralFragmentType get(int value)
  {
		switch (value) {
			case IF_VALUE: return IF;
			case FOR_VALUE: return FOR;
			case FOREACH_VALUE: return FOREACH;
			case WHILE_VALUE: return WHILE;
			case DO_VALUE: return DO;
			case SWITCH_VALUE: return SWITCH;
			case CASE_VALUE: return CASE;
			case FINALLY_VALUE: return FINALLY;
			case TRY_VALUE: return TRY;
			case CATCH_VALUE: return CATCH;
			case SYNCHRONIZED_VALUE: return SYNCHRONIZED;
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
  private StructuralFragmentType(int value, String name, String literal)
  {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public int getValue()
  {
	  return value;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public String getName()
  {
	  return name;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public String getLiteral()
  {
	  return literal;
	}

  /**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  @Override
  public String toString()
  {
		return literal;
	}
  
} //StructuralFragmentType
