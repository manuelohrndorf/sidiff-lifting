package org.sidiff.patching.correspondence.sidiff.impl;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.osgi.framework.BundleContext;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.common.services.ServiceContext;
import org.sidiff.common.services.ServiceHelper;
import org.sidiff.common.util.StringUtil;
import org.sidiff.core.annotation.AnnotationService;
import org.sidiff.core.candidates.CandidatesTreeService;
import org.sidiff.core.correspondences.CorrespondencesService;
import org.sidiff.core.correspondences.ExternalElementException;
import org.sidiff.core.matching.HashMatchingService;
import org.sidiff.core.matching.IterativeMatchingService;
import org.sidiff.core.similarities.DefaultSimilaritiesService;
import org.sidiff.core.similaritiescalculator.DefaultSimilaritiesCalculationService;
import org.sidiff.patching.correspondence.sidiff.IReliabilityCalculator;


public class SidiffCorrespondence {
	private Logger logger = Logger.getLogger(Activator.class.getName());
	
	private Resource modelA;
	private Resource modelB;
	private CorrespondencesService correspondenceService;
	private IReliabilityCalculator reliabilityCalculator;
	
	public SidiffCorrespondence(Resource modelA, Resource modelB) {
		this.modelA = modelA;
		this.modelB = modelB;
	}
	
	public void initialize() {
		// Get document type of models
		String documentType = EMFModelAccess.getDocumentType(modelA);
		BundleContext bundleContext = Activator.getContext();

		// Annotate models
		ServiceHelper.getService(bundleContext, AnnotationService.class, documentType).annotate(modelA);
		ServiceHelper.getService(bundleContext, AnnotationService.class, documentType).annotate(modelB);

		// Create service context
		ServiceContext context = new ServiceContext();
		context.putService(ServiceHelper.getService(bundleContext, CorrespondencesService.class, documentType,
				ServiceHelper.DEFAULT));
		context.putService(ServiceHelper.getService(bundleContext, CandidatesTreeService.class, documentType,
				ServiceHelper.DEFAULT));
		context.putService(ServiceHelper.getService(bundleContext, DefaultSimilaritiesCalculationService.class,
				documentType, ServiceHelper.DEFAULT));
		context.putService(ServiceHelper.getService(bundleContext, DefaultSimilaritiesService.class, documentType,
				ServiceHelper.DEFAULT));
		context.putService(ServiceHelper.getService(bundleContext, IterativeMatchingService.class, documentType,
				ServiceHelper.DEFAULT));
		context.putService(ServiceHelper.getService(bundleContext, HashMatchingService.class, documentType,
				ServiceHelper.DEFAULT));
		// Used to compute reliability of matches
		IConfigurationElement[] configurationElements = Platform.getExtensionRegistry().getConfigurationElementsFor(IReliabilityCalculator.EXTENSION_POINT_ID);
		try {
			for (IConfigurationElement configurationElement : configurationElements) {
				String attribute = configurationElement.getAttribute(IReliabilityCalculator.DOCUMENT_TYPE);
				if (attribute.equals(documentType) || attribute.equals(IReliabilityCalculator.DEFAULT_DOCUMENT_TYPE)) {
					reliabilityCalculator = (IReliabilityCalculator) configurationElement.createExecutableExtension(IReliabilityCalculator.EXECUTEBALE);
					reliabilityCalculator.setContext(context);
				}
			}
		} catch (CoreException e) {
			logger.log(Level.SEVERE, e.getLocalizedMessage(), e.getCause());
		}
		
		if (reliabilityCalculator == null) {
			logger.log(Level.SEVERE, "ReliabilityCalculator not found!");
			return;
		}
		
		// Initialize Application		
		context.initialize(modelA, modelB);

		// Matching
		reliabilityCalculator.startingMatch();
		context.getService(HashMatchingService.class).match();
		context.getService(IterativeMatchingService.class).match();
		reliabilityCalculator.endingMatch();
		
		this.correspondenceService = context.getService(CorrespondencesService.class);
		
	}
	
	public EObject getCorrespondence(EObject elementA) {
		EObject eObject = null;
		try {
			Collection<EObject> correspondences = correspondenceService.getCorrespondences(elementA);
			// we assume only one correspondence
			if (!correspondences.isEmpty()) {
				assert correspondences.size() == 1 : "There are more than one correspondeces!";
				eObject = correspondences.iterator().next();
			}
		} catch (ExternalElementException e) {
			logger.log(Level.FINE, "Element is external: " + StringUtil.resolve(elementA));
			eObject = elementA;
		}
		return eObject;
	}
	
	public float getReliabilityOfMatch(EObject elementA, EObject elementB){
		float reliability = reliabilityCalculator.getReliability(elementA, elementB);
//		logger.log(Level.FINE, "Match reliability of " + StringUtil.resolve(elementA) + " and " + StringUtil.resolve(elementB) + ": "	+ reliability);
		return reliability;
	}

	public Collection<EObject> getUnmatchedObjects() {
		return correspondenceService.getElementsWithoutCorrespondences(modelB);
	}
	
}
