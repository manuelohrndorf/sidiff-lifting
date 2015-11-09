package org.sidiff.difference.matcher.sidiff;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.annotation.IAnnotation;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.correspondences.CorrespondencesUtil;
import org.sidiff.correspondences.ICorrespondences;
import org.sidiff.difference.matcher.IMatcher;
import org.sidiff.difference.profiles.handler.DifferenceProfileHandlerUtil;
import org.sidiff.difference.profiles.handler.IDifferenceProfileHandler;
import org.sidiff.difference.symmetric.Correspondence;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.symmetric.SymmetricFactory;
import org.sidiff.domain.DomainServiceUtil;
import org.sidiff.domain.IDomain;
import org.sidiff.matching.BaseMatcher;
import org.sidiff.matching.IMatching;
import org.sidiff.matching.MatchingUtil;
import org.silift.common.util.access.EMFModelAccessEx;
import org.silift.common.util.emf.Scope;

public class SiDiffMatchingAdapter implements IMatcher {

	public static final String KEY = "SiDiff";
	
	private IDifferenceProfileHandler profileHandler;	

	@Override
	public SymmetricDifference createMatching(Resource modelA, Resource modelB, Scope scope,
			boolean calculateReliability) {

		
		// Create Matching
		SymmetricDifference matching = SymmetricFactory.eINSTANCE.createSymmetricDifference();
		matching.setUriModelA(modelA.getURI().toString());
		matching.setUriModelB(modelB.getURI().toString());

		addMatches(modelA, modelB, matching, scope, calculateReliability);

		return matching;
	}

	@Override
	public void addMatches(Resource modelA, Resource modelB, SymmetricDifference matching, Scope scope,
			boolean calculateReliability) {

		// First get the document type of models
		String documentType = "";
		Set<String> docTypes = EMFModelAccessEx.getDocumentTypes(modelA, Scope.RESOURCE);
		if (getProfileHandler(docTypes)!=null && profileHandler.isProfiled(modelA)) {
			documentType = profileHandler.getBaseType();
		} else {
			documentType = EMFModelAccessEx.getCharacteristicDocumentType(modelA);
		}		
		
		// Initialize the ReliabilityCalculator
		IReliabilityCalculator reliabilityCalculator = null;
		if (calculateReliability) {
			IConfigurationElement[] configurationElements = Platform.getExtensionRegistry()
					.getConfigurationElementsFor(IReliabilityCalculator.EXTENSION_POINT_ID);
			try {
				for (IConfigurationElement configurationElement : configurationElements) {
					String attribute = configurationElement.getAttribute(IReliabilityCalculator.DOCUMENT_TYPE);
					if (attribute.equals(documentType)
							|| attribute.equals(IReliabilityCalculator.DEFAULT_DOCUMENT_TYPE)) {
						reliabilityCalculator = (IReliabilityCalculator) configurationElement
								.createExecutableExtension(IReliabilityCalculator.EXECUTEBALE);
						// TODO is reliabilityCalculator.setContext(context); still needed? (MR 4.11.2015)
						// reliabilityCalculator.setContext(context);
					}
				}
			} catch (CoreException e) {
				e.printStackTrace();
				LogUtil.log(LogEvent.ERROR, e.getLocalizedMessage(), e.getCause());
			}

			if (reliabilityCalculator == null) {
				LogUtil.log(LogEvent.ERROR, "ReliabilityCalculator not found!");
				// return;
			}
		}

		// Initialize Domain Services
		Set<IDomain> domainServices = DomainServiceUtil.getCapableDomainServices(documentType);
		for(IDomain domainService: domainServices) {
			// do specific actions on specific services
			if(domainService instanceof IAnnotation) {
				IAnnotation annotationService = (IAnnotation) domainService;
				//TODO the following should better be done when instanciating the service (MR 4.11.2015)
				annotationService.init(domainService.getDefaultAnnotationConfiguration());
				annotationService.annotate(modelA);
				annotationService.annotate(modelB);
			}
		}
		
		
		// Notify ReliabilityCalculation of matching start
		if (calculateReliability) {
			reliabilityCalculator.startingMatch();
		}
		
		// Start Matching now! Sequence: xmiid, signature, similarity
		// TODO we should also use dynamic inremental matcher (MR 6.11.2015)
		// TODO we should use getMatchingService() by enum literal, not error prone strings (MR 6.11.2015)
		// TODO Can't init() be called by the domain service? getMatchingService() delivers an IMatcher, which
		// dosnt provide an init()-method (is only available in sub class BaseMatcher). The needed call of init()
		// is not obvious when using expected return type IMatcher (MR 6.11.2015)
		BaseMatcher xmiIdMatchingService = (BaseMatcher) MatchingUtil.getMatchingService("XMIIDMatchingService");
		xmiIdMatchingService.init(modelA, modelB);
		xmiIdMatchingService.match(modelA, modelB);
		
		BaseMatcher signatureBasedMatchingService = (BaseMatcher) MatchingUtil.getMatchingService("SignatureMatchingService");
		signatureBasedMatchingService.init(modelA, modelB);
		signatureBasedMatchingService.match(modelA, modelB);
		
		BaseMatcher similarityBasedMatchingService = (BaseMatcher) MatchingUtil.getMatchingService("SimilarityMatcher");
		similarityBasedMatchingService.init(modelA, modelB);
		similarityBasedMatchingService.match(modelA, modelB);
		
		// additional matcher for profiles
		if(profileHandler.isProfiled(modelA)) {
			BaseMatcher profileBasedMatchingService = (BaseMatcher) MatchingUtil.getMatchingService("ProfilesMatchingService");
			profileBasedMatchingService.init(modelA, modelB);
			profileBasedMatchingService.match(modelA, modelB);
		}
		
		// Notify ReliabilityCalculation of matching end
		if (calculateReliability) {
			reliabilityCalculator.endingMatch();
		}
		
		// Iterate through correspondences and create SiLift representation of a matching accordingly		
		ICorrespondences correspondenceService = CorrespondencesUtil.getAvailableCorrespondencesService("PairtableCorrespondenceService");
		//TODO the following should better be done when instanciating the service (MR 4.11.2015)
		correspondenceService.init(modelA, modelB);
		
		for (Iterator<EObject> it_a = modelA.getAllContents(); it_a.hasNext();) {
			EObject elementA = it_a.next();
			if (correspondenceService.hasCorrespondences(elementA)) {
				EObject elementB = correspondenceService.getCorrespondences(elementA).iterator().next();
				Correspondence c = SymmetricFactory.eINSTANCE.createCorrespondence(elementA, elementB);
				if (calculateReliability) {
					c.setReliability(reliabilityCalculator.getReliability(c.getObjA(), c.getObjB()));
				}
				matching.addCorrespondence(c);
			}
		}
	}

	@Override
	public String getName() {
		return "SiDiff Matching Engine";
	}

	@Override
	public String getKey() {
		return KEY;
	}

	/**
	 * considers no resource set
	 * @see {@link getDocumentType()}
	 */
	@Override
	public boolean canHandle(Resource modelA, Resource modelB) {

		boolean canHandle = true;
		
		IDomain domainService = DomainServiceUtil.getCapableDomainService(modelA);	
		
		Set<String> docTypes = EMFModelAccessEx.getDocumentTypes(modelA, Scope.RESOURCE);
		if(getProfileHandler(docTypes)!=null && getProfileHandler(docTypes).isProfiled(modelA) && getProfileHandler(docTypes).isProfiled(modelB)){
			// Profile			
			boolean hasAnnotationService = 
					(domainService.getDefaultAnnotationConfiguration()!=null);			
			boolean hasDefaultSimilarityCalculationService = 
					(domainService.getDefaultSimilarityConfiguration()!=null);
			boolean hasDefaultMatchingCalculationService = 
					(domainService.getDefaultMatchingConfiguration()!=null);			
			// Uncomment this, as soon as profile matching migration ready (MR 4.11.2015)
			//	boolean hasProfileMatchingService = 
			//	(domainService.getProfileMatchingConfiguration()!=null);
			
			canHandle =  hasAnnotationService
					  && hasDefaultSimilarityCalculationService
					  && hasDefaultMatchingCalculationService;
			//	  && hasProfileMatchingService; see above
			
		} else {
			// No Profile or no handler found
			for(String docType : docTypes){
				if(canHandle){
					
					boolean hasAnnotationService = 
							(domainService.getDefaultAnnotationConfiguration()!=null);			
					boolean hasDefaultMatchingCalculationService = 
							(domainService.getDefaultMatchingConfiguration()!=null);
					boolean hasDefaultSimilarityCalculationService = 
							(domainService.getDefaultSimilarityConfiguration()!=null);
					
					canHandle =  hasAnnotationService
							  && hasDefaultMatchingCalculationService
							  && hasDefaultSimilarityCalculationService;					
					
				}
			}
		}
		
		return canHandle;

	}

	@Override
	public String getDocumentType() {
		// generic (if configured properly)
		return EMFModelAccessEx.GENERIC_DOCUMENT_TYPE;
	}
	
	@Override
	public boolean isResourceSetCapable() {
		// yet, SiDiff is not capable of comparing complete resource
		// sets
		return false;
	}

	@Override
	public boolean canComputeReliability() {
		return true;
	}
	
	private IDifferenceProfileHandler getProfileHandler(Set<String> docTypes){
		if(profileHandler == null){
			for(String docType : docTypes){
				// at the moment there is only one profile handler available
				profileHandler = DifferenceProfileHandlerUtil.getDefaultDifferenceProfileHandler(docType);
				if(profileHandler != null){
					break;
				}
			}
		}
		return profileHandler;
	}

	@Override
	public Map<String, Object> getConfigurationOptions() {
		// TODO Auto-generated method stub
		return Collections.emptyMap();
	}

}
