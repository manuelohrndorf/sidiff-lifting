package org.sidiff.patching.test;

import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sidiff.difference.lifting.facade.LiftingFacade;
import org.sidiff.difference.lifting.facade.util.PipelineUtils;
import org.sidiff.difference.matcher.IMatcher;
import org.sidiff.difference.matcher.util.MatcherUtil;
import org.sidiff.difference.symmetric.AddObject;
import org.sidiff.difference.symmetric.AddReference;
import org.sidiff.difference.symmetric.AttributeValueChange;
import org.sidiff.difference.symmetric.Change;
import org.sidiff.difference.symmetric.RemoveObject;
import org.sidiff.difference.symmetric.RemoveReference;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.technical.ITechnicalDifferenceBuilder;
import org.sidiff.difference.util.access.EMFModelAccessEx;

public class ModelCompare {

	private Resource modelA;
	private Resource modelB;
	private StringBuffer buffer;

	public ModelCompare(Resource modelA, Resource modelB) {
		this.modelA = modelA;
		this.modelB = modelB;
	}

	public boolean test() {
		buffer = new StringBuffer();
		buffer.append("A->B\n");
		boolean passedAB = test(modelA, modelB);
		buffer.append("B->A\n");
		boolean passedBA = test(modelB, modelA);
		EList<Change> changes = technicalEqual(modelA, modelB, true);
		if (changes.isEmpty()) {
			buffer.append("No Differences in technical Diff\n");
		} else {
			buffer.append("Changes: " + changes.toString());
		}
		return passedAB && passedBA && changes.isEmpty();
	}

	public static EList<Change> technicalEqual(Resource modelA, Resource modelB, Boolean normalize) {

		if (normalize) {
			// Convert to normal form
			INormalizer normalizer = NormalizerFactory.createNormalizer(modelA);
			normalizer.normalize(modelA);
			normalizer.normalize(modelB);
		}

		// Get suitable matcher
		String documentType = EMFModelAccessEx.getCharacteristicDocumentType(modelA);
		// IMatcher matcher = MatcherUtil.getMatcherByKey("UUIDMatcher", modelA,
		// modelB);
		IMatcher matcher = MatcherUtil.getMatcherByKey("URIFragmentMatcher", modelA, modelB);
		// IMatcher matcher = MatcherUtil.getMatcherByKey("SiDiff", modelA,
		// modelB);

		
		// do matching
		SymmetricDifference difference = matcher.createMatching(modelA, modelB);
		difference.setUriModelA(modelA.getURI().toString());
		difference.setUriModelB(modelB.getURI().toString());

		// derive techical difference
		ITechnicalDifferenceBuilder tdBuilder = PipelineUtils.getDefaultTechnicalDifferenceBuilder(documentType);
		LiftingFacade.deriveTechnicalDifferences(difference, tdBuilder);
		
		// now, return the set of obtained low-level changes (should be empty)
		return difference.getChanges();
	}

	private boolean test(Resource resourceA, Resource resourceB) {
		boolean passed = true;
		for (Iterator<EObject> iteratorA = resourceA.getAllContents(); iteratorA.hasNext();) {
			EObject eObjectA = (EObject) iteratorA.next();
			String fragment = EcoreUtil.getURI(eObjectA).fragment();
			// if (!fragment.contains("//"))
			// continue;
			EObject eObjectB = resourceB.getEObject(fragment);
			if (eObjectB == null) {
				buffer.append("Did not found: " + fragment + "\n");
				passed = false;
			}
		}
		if (passed) {
			buffer.append("Everything fine!\n");
		}
		return passed;
	}

	public String getReport() {
		return buffer.toString();
	}

	public static String getFormatedList(EList<Change> changes) {
		StringBuffer buffer = new StringBuffer();
		for (Change change : changes) {
			if (change instanceof AttributeValueChange) {
				AttributeValueChange valueChange = (AttributeValueChange) change;
				buffer.append("Missing ValueChange " + valueChange.getType().getName() + " in "
						+ EcoreUtil.getURI(valueChange.getObjA()).fragment());
				buffer.append("\n");
			} else if (change instanceof AddObject) {
				AddObject addObject = (AddObject) change;
				buffer.append("Missing AddObject " + EcoreUtil.getURI(addObject.getObj()).fragment());
				buffer.append("\n");
			} else if (change instanceof RemoveObject) {
				RemoveObject removeObject = (RemoveObject) change;
				buffer.append("Missing RemoveObject " + removeObject.getObj());
				buffer.append("\n");
			} else if (change instanceof AddReference) {
				AddReference addReference = (AddReference) change;
				buffer.append("Missing AddReference from " + EcoreUtil.getURI(addReference.getSrc()).fragment()
						+ " to " + EcoreUtil.getURI(addReference.getTgt()).fragment());
				buffer.append("\n");
			} else if (change instanceof RemoveReference) {
				RemoveReference removeReference = (RemoveReference) change;
				buffer.append("Missing RemoveReference from " + EcoreUtil.getURI(removeReference.getSrc()).fragment()
						+ " to " + EcoreUtil.getURI(removeReference.getTgt()).fragment());
				buffer.append("\n");
			}
		}
		buffer.append("\n");
		return buffer.toString();
	}

}
