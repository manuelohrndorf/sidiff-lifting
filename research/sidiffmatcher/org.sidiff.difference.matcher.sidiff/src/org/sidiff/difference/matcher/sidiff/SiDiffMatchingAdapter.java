package org.sidiff.difference.matcher.sidiff;

import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.common.services.ServiceContext;
import org.sidiff.common.services.ServiceHelper;
import org.sidiff.core.annotation.AnnotationService;
import org.sidiff.core.candidates.CandidatesTreeService;
import org.sidiff.core.correspondences.CorrespondencesService;
import org.sidiff.core.matching.IterativeMatchingService;
import org.sidiff.core.matching.ProfilesMatchingService;
import org.sidiff.core.matching.SignatureMatchingService;
import org.sidiff.core.matching.XMIIDMatchingService;
import org.sidiff.core.similarities.DefaultSimilaritiesService;
import org.sidiff.core.similaritiescalculator.DefaultSimilaritiesCalculationService;
import org.sidiff.difference.matcher.IMatcher;
import org.sidiff.difference.profiles.handler.DifferenceProfileHandlerUtil;
import org.sidiff.difference.profiles.handler.IDifferenceProfileHandler;
import org.sidiff.difference.symmetric.Correspondence;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.symmetric.SymmetricFactory;
import org.silift.common.util.access.EMFModelAccessEx;
import org.silift.common.util.emf.Scope;

public class SiDiffMatchingAdapter implements IMatcher {

	public static final String KEY = "SiDiff";
	
	private IDifferenceProfileHandler profileHandler;

	@Override
	public SymmetricDifference createMatching(Resource modelA, Resource modelB, Scope scope,
			boolean calculateReliability) {

		SymmetricDifference matching = SymmetricFactory.eINSTANCE.createSymmetricDifference();
		matching.setUriModelA(modelA.getURI().toString());
		matching.setUriModelB(modelB.getURI().toString());

		addMatches(modelA, modelB, matching, scope, calculateReliability);

		return matching;
	}

	@Override
	public void addMatches(Resource modelA, Resource modelB, SymmetricDifference matching, Scope scope,
			boolean calculateReliability) {
		// TODO: TK (6.11.2012): Expliziter Start aller SiDiff Matching Bundles
		// resultiert in
		// Nebenläufigkeitsproblem. Daher bislang doch alle SiDiff Matching
		// Bundles
		// über die SiDiffShotgun beim Startup von Eclipse geladen.
		// ===
		// Be sure all SiDiff Matching Engine Bundles are active
		// BundleHandler.getInstance().startBundles();

		// Get document type of models
		Set<String> docTypes = EMFModelAccessEx.getDocumentTypes(modelA, Scope.RESOURCE);
		String documentType = null;
		if (getProfileHandler(docTypes)!=null && profileHandler.isProfiled(modelA)) {
			documentType = profileHandler.getBaseType();
		} else {
			documentType = EMFModelAccessEx.getCharacteristicDocumentType(modelA);
		}

		// Create service context
		ServiceContext context = new ServiceContext();

		context.putService(ServiceHelper.getService(Activator.context, AnnotationService.class, documentType,
				ServiceHelper.DEFAULT));
		context.putService(ServiceHelper.getService(Activator.context, CorrespondencesService.class, documentType,
				ServiceHelper.DEFAULT));
		context.putService(ServiceHelper.getService(Activator.context, CandidatesTreeService.class, documentType,
				ServiceHelper.DEFAULT));
		context.putService(ServiceHelper.getService(Activator.context, DefaultSimilaritiesCalculationService.class,
				documentType, ServiceHelper.DEFAULT));
		context.putService(ServiceHelper.getService(Activator.context, DefaultSimilaritiesService.class, documentType,
				ServiceHelper.DEFAULT));
		context.putService(ServiceHelper.getService(Activator.context, IterativeMatchingService.class, documentType,
				ServiceHelper.DEFAULT));
		context.putService(ServiceHelper.getService(Activator.context, XMIIDMatchingService.class, documentType,
				ServiceHelper.DEFAULT));
		context.putService(ServiceHelper.getService(Activator.context, SignatureMatchingService.class, documentType,
				ServiceHelper.DEFAULT));

		// ad hoc stereotype matching?
		if (profileHandler!=null && profileHandler.isProfiled(modelA)) {
			context.putService(ServiceHelper.getService(Activator.context, ProfilesMatchingService.class, documentType,
					ServiceHelper.DEFAULT));
		}

		// Used to compute reliability of matches
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
						reliabilityCalculator.setContext(context);
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

		// Initialize Application
		context.initialize(modelA, modelB);

		// Annotate models
		context.getService(AnnotationService.class).annotate(modelA);
		context.getService(AnnotationService.class).annotate(modelB);

		// Do Matching
		if (calculateReliability) {
			reliabilityCalculator.startingMatch();
		}
		context.getService(XMIIDMatchingService.class).match();
		context.getService(SignatureMatchingService.class).match();
		context.getService(IterativeMatchingService.class).match();
		if (EMFModelAccessEx.isProfiled(modelA)) {
			context.getService(ProfilesMatchingService.class).match();
		}
		if (calculateReliability) {
			reliabilityCalculator.endingMatch();
		}

		// Convert correspondences to the SiLift representation of a matching
		CorrespondencesService cs = context.getService(CorrespondencesService.class);

		for (Iterator<EObject> it_a = modelA.getAllContents(); it_a.hasNext();) {
			EObject elementA = it_a.next();
			if (cs.hasCorrespondences(elementA)) {
				EObject elementB = cs.getCorrespondences(elementA).iterator().next();
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
		
		
		
		
		Set<String> docTypes = EMFModelAccessEx.getDocumentTypes(modelA, Scope.RESOURCE);
		if(getProfileHandler(docTypes)!=null && getProfileHandler(docTypes).isProfiled(modelA) && getProfileHandler(docTypes).isProfiled(modelB)){
			// Profile
			String baseDocType = getProfileHandler(docTypes).getBaseType();

			canHandle = ServiceHelper.getService(Activator.context,
					AnnotationService.class, baseDocType) != null
					&& ServiceHelper.getService(Activator.context,
							DefaultSimilaritiesCalculationService.class,
							baseDocType) != null
					&& ServiceHelper.getService(Activator.context,
							IterativeMatchingService.class, baseDocType) != null
					&& ServiceHelper.getService(Activator.context,  ProfilesMatchingService.class, baseDocType)!=null;
		} else {
			// No Profile or no handler found
			for(String docType : docTypes){
				if(canHandle){
					canHandle = ServiceHelper.getService(Activator.context, AnnotationService.class, docType) != null
					&& ServiceHelper.getService(Activator.context, DefaultSimilaritiesCalculationService.class,
							docType) != null
					&& ServiceHelper.getService(Activator.context, IterativeMatchingService.class,
							docType) != null;
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

}
