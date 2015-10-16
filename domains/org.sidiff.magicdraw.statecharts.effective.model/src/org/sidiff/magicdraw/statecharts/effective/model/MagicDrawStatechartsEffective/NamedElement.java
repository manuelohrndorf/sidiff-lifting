/**
 */
package org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Named Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A named element supports using a string expression to specify its name. This allows names of model elements to involve template parameters. The actual name is evaluated from the string expression only when it is sensible to do so (e.g., when a template is bound).
 * A named element is an element in a model that may have a name.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.NamedElement#getName <em>Name</em>}</li>
 *   <li>{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.NamedElement#getQualifiedName <em>Qualified Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.MagicDrawStatechartsEffectivePackage#getNamedElement()
 * @model abstract="true"
 * @generated
 */
public interface NamedElement extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The name of the NamedElement.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #isSetName()
	 * @see #unsetName()
	 * @see #setName(String)
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.MagicDrawStatechartsEffectivePackage#getNamedElement_Name()
	 * @model unsettable="true" ordered="false"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.NamedElement#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #isSetName()
	 * @see #unsetName()
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Unsets the value of the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.NamedElement#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetName()
	 * @see #getName()
	 * @see #setName(String)
	 * @generated
	 */
	void unsetName();

	/**
	 * Returns whether the value of the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.NamedElement#getName <em>Name</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Name</em>' attribute is set.
	 * @see #unsetName()
	 * @see #getName()
	 * @see #setName(String)
	 * @generated
	 */
	boolean isSetName();

	/**
	 * Returns the value of the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A name which allows the NamedElement to be identified within a hierarchy of nested Namespaces. It is constructed from the names of the containing namespaces starting at the root of the hierarchy and ending with the name of the NamedElement itself.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Qualified Name</em>' attribute.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.MagicDrawStatechartsEffectivePackage#getNamedElement_QualifiedName()
	 * @model dataType="org.eclipse.uml2.types.String" transient="true" changeable="false" volatile="true" derived="true" ordered="false"
	 * @generated
	 */
	String getQualifiedName();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * When there is a name, and all of the containing namespaces have a name, the qualified name is constructed from the names of the containing namespaces.
	 * (self.name->notEmpty() and self.allNamespaces()->select(ns | ns.name->isEmpty())->isEmpty()) implies
	 *   self.qualifiedName = self.allNamespaces()->iterate( ns : Namespace; result: String = self.name | ns.name->union(self.separator())->union(result))
	 * @param diagnostics The chain of diagnostics to which problems are to be appended.
	 * @param context The cache of context-specific information.
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	boolean has_qualified_name(DiagnosticChain diagnostics, Map<Object, Object> context);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * If there is no name, or one of the containing namespaces has no name, there is no qualified name.
	 * (self.name->isEmpty() or self.allNamespaces()->select(ns | ns.name->isEmpty())->notEmpty())
	 *   implies self.qualifiedName->isEmpty()
	 * @param diagnostics The chain of diagnostics to which problems are to be appended.
	 * @param context The cache of context-specific information.
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	boolean has_no_qualified_name(DiagnosticChain diagnostics, Map<Object, Object> context);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * If a NamedElement is not owned by a Namespace, it does not have a visibility.
	 * namespace->isEmpty() implies visibility->isEmpty()
	 * @param diagnostics The chain of diagnostics to which problems are to be appended.
	 * @param context The cache of context-specific information.
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	boolean visibility_needs_ownership(DiagnosticChain diagnostics, Map<Object, Object> context);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Creates a dependency between this named element and the specified supplier, owned by this named element's nearest package.
	 * @param supplier The supplier for the new dependency.
	 * <!-- end-model-doc -->
	 * @model supplierRequired="true" supplierOrdered="false"
	 * @generated
	 */
	void createDependency(NamedElement supplier);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Creates a usage between this named element and the specified supplier, owned by this named element's nearest package.
	 * @param supplier The supplier for the new usage.
	 * <!-- end-model-doc -->
	 * @model supplierRequired="true" supplierOrdered="false"
	 * @generated
	 */
	void createUsage(NamedElement supplier);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Retrieves a localized label for this named element.
	 * <!-- end-model-doc -->
	 * @model kind="operation" dataType="org.eclipse.uml2.types.String" ordered="false"
	 * @generated
	 */
	String getLabel();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Retrieves a label for this named element, localized if indicated.
	 * @param localize Whether to localize the label.
	 * <!-- end-model-doc -->
	 * @model dataType="org.eclipse.uml2.types.String" ordered="false" localizeDataType="org.eclipse.uml2.types.Boolean" localizeRequired="true" localizeOrdered="false"
	 * @generated
	 */
	String getLabel(boolean localize);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The query allNamespaces() gives the sequence of namespaces in which the NamedElement is nested, working outwards.
	 * result = if self.namespace->isEmpty()
	 * then Sequence{}
	 * else self.namespace.allNamespaces()->prepend(self.namespace)
	 * endif
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	EList<Namespace> allNamespaces();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The query isDistinguishableFrom() determines whether two NamedElements may logically co-exist within a Namespace. By default, two named elements are distinguishable if (a) they have unrelated types or (b) they have related types but different names.
	 * result = if self.oclIsKindOf(n.oclType) or n.oclIsKindOf(self.oclType)
	 * then ns.getNamesOfMember(self)->intersection(ns.getNamesOfMember(n))->isEmpty()
	 * else true
	 * endif
	 * <!-- end-model-doc -->
	 * @model dataType="org.eclipse.uml2.types.Boolean" required="true" ordered="false" nRequired="true" nOrdered="false" nsRequired="true" nsOrdered="false"
	 * @generated
	 */
	boolean isDistinguishableFrom(NamedElement n, Namespace ns);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The query separator() gives the string that is used to separate names when constructing a qualified name.
	 * result = '::'
	 * <!-- end-model-doc -->
	 * @model dataType="org.eclipse.uml2.types.String" required="true" ordered="false"
	 * @generated
	 */
	String separator();

} // NamedElement
