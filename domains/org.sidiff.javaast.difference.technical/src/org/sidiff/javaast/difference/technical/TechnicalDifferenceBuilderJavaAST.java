package org.sidiff.javaast.difference.technical;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.sidiff.difference.technical.AbstractTechnicalDifferenceBuilder;
import org.sidiff.javaast.model.JavaModelPackage;

/**
 * Filters all technical stuff 
 * 
 * @author dreuling
 */
public class TechnicalDifferenceBuilderJavaAST extends AbstractTechnicalDifferenceBuilder {

	@Override
	protected String getObjectName(EObject obj) {		
		return obj.toString();
	}

	@Override
	public String getDocumentType() {
		return JavaModelPackage.eNS_URI;
	}

	@Override
	public String getName() {
		return "Technical Difference Builder for Java AST";
	}

	@Override
	protected Set<EClass> getUnconsideredNodeTypes() {
		return Collections.emptySet();
	}

	@Override
	protected Set<EReference> getUnconsideredEdgeTypes() {
		Set<EReference> unconsideredEdges = new HashSet<EReference>();
		
		unconsideredEdges.add(JavaModelPackage.eINSTANCE.getJClass_SubClasses());
		unconsideredEdges.add(JavaModelPackage.eINSTANCE.getJField_AccessedBy());
		unconsideredEdges.add(JavaModelPackage.eINSTANCE.getJMethod_CalledBy());
		unconsideredEdges.add(JavaModelPackage.eINSTANCE.getJInterface_SubInterfaces());
		unconsideredEdges.add(JavaModelPackage.eINSTANCE.getJInterface_ImplementingClasses());
		
		return Collections.emptySet();
	}

	@Override
	protected Set<EAttribute> getUnconsideredAttributeTypes() {
		Set<EAttribute> unconsideredAttributeTypes = new HashSet<EAttribute>();

		unconsideredAttributeTypes.add(JavaModelPackage.eINSTANCE.getJIdentifiableElement_Id());
		unconsideredAttributeTypes.add(JavaModelPackage.eINSTANCE.getJAdressableFragment_StartByte());

		return unconsideredAttributeTypes;
	}

}