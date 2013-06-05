package org.sidiff.difference.matcher.emfcompare;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.compare.match.MatchOptions;
import org.eclipse.emf.compare.match.metamodel.Match2Elements;
import org.eclipse.emf.compare.match.metamodel.MatchElement;
import org.eclipse.emf.compare.match.metamodel.MatchModel;
import org.eclipse.emf.compare.match.service.MatchService;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.difference.matcher.IMatcher;
import org.sidiff.difference.symmetric.Correspondence;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.symmetric.SymmetricFactory;

/**
 * Concrete matcher that delegates to EMFCompare matching engine.
 * 
 * @author kehrer
 */
public class EMFCompareMatchingAdapter implements IMatcher {

	/**
	 * Initialize
	 */
	public EMFCompareMatchingAdapter() {
		super();
	}

	@Override
	public SymmetricDifference createMatching(Resource modelA, Resource modelB) {
		// Specify options
		 Map<String, Object> matchOptions = new HashMap<String, Object>();
		 matchOptions.put(MatchOptions.OPTION_IGNORE_ID, true);
		 matchOptions.put(MatchOptions.OPTION_IGNORE_XMI_ID, true);
		 matchOptions.put(MatchOptions.OPTION_SEARCH_WINDOW, 100);

		assert (!modelA.getContents().isEmpty()) : "modelA is empty!";
		assert (!modelB.getContents().isEmpty()) : "modelB is empty!";

		MatchModel matching = null;
		try {
			matching = MatchService.doResourceMatch(modelA, modelB, matchOptions);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SymmetricDifference difference = SymmetricFactory.eINSTANCE.createSymmetricDifference();
		difference.setUriModelA(modelA.getURI().toString());
		difference.setUriModelB(modelB.getURI().toString());
		populateDifference(difference, matching);
		
		return difference;

	}

	private void populateDifference(SymmetricDifference difference, MatchModel matchModel) {
		for (MatchElement matchElement : matchModel.getMatchedElements()) {
			populateDifference(difference, matchElement);	
		}
	}

	private void populateDifference(SymmetricDifference difference, MatchElement matchElement) {
		if (matchElement instanceof Match2Elements){
			Match2Elements match = (Match2Elements) matchElement;
			Correspondence c = SymmetricFactory.eINSTANCE.createCorrespondence();
			c.setObjA(match.getLeftElement());
			c.setObjB(match.getRightElement());
			difference.getCorrespondences().add(c);
			
			System.out.println(">>> Correspondence " + match.getLeftElement() + " <-> " + match.getRightElement());
		}
		
		for (MatchElement subMatchElement : matchElement.getSubMatchElements()) {
			populateDifference(difference, subMatchElement);
		}
	}
	
	@Override
	public String getName() {
		return "EMFCompare Generic Matcher";
	}

	@Override
	public String getKey() {
		return "EMFCompare";
	}

	@Override
	public boolean canHandle(String documentType) {
		// EMFCompare can handle every model type, at least with the generic matcher.
		return true;
	}
}
