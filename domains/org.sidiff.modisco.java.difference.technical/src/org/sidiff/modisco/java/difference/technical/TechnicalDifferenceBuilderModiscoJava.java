package org.sidiff.modisco.java.difference.technical;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.gmt.modisco.java.emf.JavaPackage;
import org.eclipse.gmt.modisco.java.emf.impl.AnnotationImpl;
import org.sidiff.difference.technical.TechnicalDifferenceBuilder;

/**
 * Filters technical stuff inclusive types related to Modisco Java.
 * 
 * @author mrindt
 */
public class TechnicalDifferenceBuilderModiscoJava extends TechnicalDifferenceBuilder{

	public TechnicalDifferenceBuilderModiscoJava() {
		super();
		this.checkEObjectLocation = false; //speed up
	}
	
	@Override
	public String getDocumentType() {
		return JavaPackage.eNS_URI;
	}

	@SuppressWarnings("restriction")
	@Override
	protected String getObjectName(EObject obj) {
		String name = null;
				
		try {
			
			// Workaround for Nullpointer regarding
			// Modisco's AnnotationImpl.getObjectName() restriction
			if(obj instanceof AnnotationImpl) {
				name = "annotation type = ";

				AnnotationImpl annoImpl = (AnnotationImpl) obj;
				if (annoImpl.getType() != null) {
					name += annoImpl.getType().toString();
				} else {
					name += "null";
				}

			}// normal string name
			else{
				name = obj.toString();	
			}
		}
		// catch NullPointer just in case there are
		// some more restrictions.
		catch(NullPointerException e) {
			name = "cant show object name";
		}
		
		return name;
	}

	@Override
	protected Set<EClass> getUnconsideredNodeTypes() {
		Set<EClass> unconsideredNodeTypes = new HashSet<EClass>();

		// technical
		unconsideredNodeTypes.add(JavaPackage.eINSTANCE.getUnresolvedAnnotationDeclaration());
		unconsideredNodeTypes.add(JavaPackage.eINSTANCE.getUnresolvedAnnotationTypeMemberDeclaration());
		unconsideredNodeTypes.add(JavaPackage.eINSTANCE.getUnresolvedClassDeclaration());
		unconsideredNodeTypes.add(JavaPackage.eINSTANCE.getUnresolvedEnumDeclaration());
		unconsideredNodeTypes.add(JavaPackage.eINSTANCE.getUnresolvedInterfaceDeclaration());
		unconsideredNodeTypes.add(JavaPackage.eINSTANCE.getUnresolvedItem());
		unconsideredNodeTypes.add(JavaPackage.eINSTANCE.getUnresolvedItemAccess());
		unconsideredNodeTypes.add(JavaPackage.eINSTANCE.getUnresolvedLabeledStatement());
		unconsideredNodeTypes.add(JavaPackage.eINSTANCE.getUnresolvedMethodDeclaration());
		unconsideredNodeTypes.add(JavaPackage.eINSTANCE.getUnresolvedSingleVariableDeclaration());
		unconsideredNodeTypes.add(JavaPackage.eINSTANCE.getUnresolvedType());
		unconsideredNodeTypes.add(JavaPackage.eINSTANCE.getUnresolvedTypeDeclaration());
		unconsideredNodeTypes.add(JavaPackage.eINSTANCE.getUnresolvedVariableDeclarationFragment());
		

		return unconsideredNodeTypes;
	}

	@Override
	protected Set<EReference> getUnconsideredEdgeTypes() {
		Set<EReference> unconsideredEdgeTypes = new HashSet<EReference>();

		// technical
		unconsideredEdgeTypes.add(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Element());
		unconsideredEdgeTypes.add(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier());

		return unconsideredEdgeTypes;
	}

	@Override
	protected Set<EAttribute> getUnconsideredAttributeTypes() {
		Set<EAttribute> unconsideredAttributeTypes = new HashSet<EAttribute>();

		// technical
		// none so far

		return unconsideredAttributeTypes;
	}
	
	@Override
	public String getName() {
		return "Modisco Java Technical Difference Builder (with Generics)";
	}

}
