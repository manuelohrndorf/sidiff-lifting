package org.sidiff.patching.correspondence.sidiff;

import org.eclipse.emf.ecore.EObject;
import org.sidiff.common.services.ServiceContext;

public interface IReliabilityCalculator {
	
	String EXTENSION_POINT_ID = "org.sidiff.patching.correspondence.sidiff.reliabilitycalculator";
	String DOCUMENT_TYPE = "documentType";
	String EXECUTEBALE = "class";
	String DEFAULT_DOCUMENT_TYPE = "*";

	public void setContext(ServiceContext context);

	public void startingMatch();

	public void endingMatch();

	public float getReliability(EObject elementA, EObject elementB) ;

}
