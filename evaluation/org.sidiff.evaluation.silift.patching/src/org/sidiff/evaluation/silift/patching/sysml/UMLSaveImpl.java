package org.sidiff.evaluation.silift.patching.sysml;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.ecore.xmi.XMLHelper;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMISaveImpl;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Note: copied from UMLHandler (6.9.13)
 * 
 * @author kehrer
 *
 */
public class UMLSaveImpl extends XMISaveImpl {

	protected static class Lookup extends XMISaveImpl.Lookup {

		public Lookup(XMLResource.XMLMap map, ExtendedMetaData extendedMetaData) {
			super(map, extendedMetaData);
		}

		@Override
		protected int featureKind(EStructuralFeature f) {
			return f == UMLPackage.Literals.ACTIVITY__GROUP || f == UMLPackage.Literals.ACTIVITY__NODE ? OBJECT_HREF_MANY
					: super.featureKind(f);
		}

	}

	public UMLSaveImpl(XMLHelper helper) {
		super(helper);
	}

	@Override
	protected void init(XMLResource resource, Map<?, ?> options) {
		super.init(resource, options);

		featureTable = new Lookup(map, extendedMetaData);
	}

	@Override
	protected void saveIDRefMany(EObject o, EStructuralFeature f) {

		if (f == UMLPackage.Literals.INSTANCE_SPECIFICATION__CLASSIFIER && o instanceof EnumerationLiteral) {
			return;
		}

		super.saveIDRefMany(o, f);
	}

	@Override
	protected void saveHRefMany(EObject o, EStructuralFeature f) {

		if (f == UMLPackage.Literals.INSTANCE_SPECIFICATION__CLASSIFIER && o instanceof EnumerationLiteral) {
			return;
		}

		super.saveHRefMany(o, f);
	}

}
