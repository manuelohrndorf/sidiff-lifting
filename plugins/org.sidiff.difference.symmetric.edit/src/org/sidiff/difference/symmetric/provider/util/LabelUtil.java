package org.sidiff.difference.symmetric.provider.util;

import org.eclipse.emf.ecore.EObject;
import org.sidiff.common.stringresolver.IStringResolver;
import org.sidiff.common.stringresolver.util.StringResolverUtil;
import org.silift.common.util.access.EMFModelAccessEx;

public class LabelUtil {

	public static String getLabel(Object object){
		EObject eObject = (EObject)object;
		String docType = EMFModelAccessEx.getCharacteristicDocumentType(eObject.eResource());
		IStringResolver resolver = StringResolverUtil.getAvailableStringResolver(docType);
		return resolver.resolve(eObject);
	}
	
	public static String getToolTipLabel(Object object){
		EObject eObject = (EObject)object;
		String docType = EMFModelAccessEx.getCharacteristicDocumentType(eObject.eResource());
		IStringResolver resolver = StringResolverUtil.getAvailableStringResolver(docType);
		return resolver.resolveQualified(eObject);
	}
}
