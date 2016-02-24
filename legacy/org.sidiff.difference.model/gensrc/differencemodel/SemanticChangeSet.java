/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package differencemodel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Semantic Change Set</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link differencemodel.SemanticChangeSet#getChanges <em>Changes</em>}</li>
 *   <li>{@link differencemodel.SemanticChangeSet#getName <em>Name</em>}</li>
 *   <li>{@link differencemodel.SemanticChangeSet#getPriority <em>Priority</em>}</li>
 *   <li>{@link differencemodel.SemanticChangeSet#getRefinementLevel <em>Refinement Level</em>}</li>
 *   <li>{@link differencemodel.SemanticChangeSet#getEditRName <em>Edit RName</em>}</li>
 *   <li>{@link differencemodel.SemanticChangeSet#getParameterSubstitutions <em>Parameter Substitutions</em>}</li>
 *   <li>{@link differencemodel.SemanticChangeSet#getRecognitionRName <em>Recognition RName</em>}</li>
 *   <li>{@link differencemodel.SemanticChangeSet#getOutgoing <em>Outgoing</em>}</li>
 *   <li>{@link differencemodel.SemanticChangeSet#getIncoming <em>Incoming</em>}</li>
 * </ul>
 * </p>
 *
 * @see differencemodel.DifferencemodelPackage#getSemanticChangeSet()
 * @model
 * @generated
 */
public interface SemanticChangeSet extends EObject {
	/**
	 * Returns the value of the '<em><b>Changes</b></em>' reference list.
	 * The list contents are of type {@link differencemodel.Change}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Changes</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Changes</em>' reference list.
	 * @see differencemodel.DifferencemodelPackage#getSemanticChangeSet_Changes()
	 * @model
	 * @generated
	 */
	EList<Change> getChanges();

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see differencemodel.DifferencemodelPackage#getSemanticChangeSet_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link differencemodel.SemanticChangeSet#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Priority</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Priority</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Priority</em>' attribute.
	 * @see #setPriority(int)
	 * @see differencemodel.DifferencemodelPackage#getSemanticChangeSet_Priority()
	 * @model default="0"
	 * @generated
	 */
	int getPriority();

	/**
	 * Sets the value of the '{@link differencemodel.SemanticChangeSet#getPriority <em>Priority</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Priority</em>' attribute.
	 * @see #getPriority()
	 * @generated
	 */
	void setPriority(int value);

	/**
	 * Returns the value of the '<em><b>Refinement Level</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Refinement Level</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Refinement Level</em>' attribute.
	 * @see #setRefinementLevel(int)
	 * @see differencemodel.DifferencemodelPackage#getSemanticChangeSet_RefinementLevel()
	 * @model default="0"
	 * @generated
	 */
	int getRefinementLevel();

	/**
	 * Sets the value of the '{@link differencemodel.SemanticChangeSet#getRefinementLevel <em>Refinement Level</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Refinement Level</em>' attribute.
	 * @see #getRefinementLevel()
	 * @generated
	 */
	void setRefinementLevel(int value);

	/**
	 * Returns the value of the '<em><b>Edit RName</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Edit RName</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Edit RName</em>' attribute.
	 * @see #setEditRName(String)
	 * @see differencemodel.DifferencemodelPackage#getSemanticChangeSet_EditRName()
	 * @model
	 * @generated
	 */
	String getEditRName();

	/**
	 * Sets the value of the '{@link differencemodel.SemanticChangeSet#getEditRName <em>Edit RName</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Edit RName</em>' attribute.
	 * @see #getEditRName()
	 * @generated
	 */
	void setEditRName(String value);

	/**
	 * Returns the value of the '<em><b>Parameter Substitutions</b></em>' containment reference list.
	 * The list contents are of type {@link differencemodel.ParameterSubstitution}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameter Substitutions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameter Substitutions</em>' containment reference list.
	 * @see differencemodel.DifferencemodelPackage#getSemanticChangeSet_ParameterSubstitutions()
	 * @model containment="true"
	 * @generated
	 */
	EList<ParameterSubstitution> getParameterSubstitutions();

	/**
	 * Returns the value of the '<em><b>Recognition RName</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Recognition RName</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Recognition RName</em>' attribute.
	 * @see #setRecognitionRName(String)
	 * @see differencemodel.DifferencemodelPackage#getSemanticChangeSet_RecognitionRName()
	 * @model
	 * @generated
	 */
	String getRecognitionRName();

	/**
	 * Sets the value of the '{@link differencemodel.SemanticChangeSet#getRecognitionRName <em>Recognition RName</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Recognition RName</em>' attribute.
	 * @see #getRecognitionRName()
	 * @generated
	 */
	void setRecognitionRName(String value);

	/**
	 * Returns the value of the '<em><b>Outgoing</b></em>' reference list.
	 * The list contents are of type {@link differencemodel.Dependency}.
	 * It is bidirectional and its opposite is '{@link differencemodel.Dependency#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Outgoing</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Outgoing</em>' reference list.
	 * @see differencemodel.DifferencemodelPackage#getSemanticChangeSet_Outgoing()
	 * @see differencemodel.Dependency#getSource
	 * @model opposite="source"
	 * @generated
	 */
	EList<Dependency> getOutgoing();

	/**
	 * Returns the value of the '<em><b>Incoming</b></em>' reference list.
	 * The list contents are of type {@link differencemodel.Dependency}.
	 * It is bidirectional and its opposite is '{@link differencemodel.Dependency#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Incoming</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Incoming</em>' reference list.
	 * @see differencemodel.DifferencemodelPackage#getSemanticChangeSet_Incoming()
	 * @see differencemodel.Dependency#getTarget
	 * @model opposite="target"
	 * @generated
	 */
	EList<Dependency> getIncoming();

} // SemanticChangeSet
