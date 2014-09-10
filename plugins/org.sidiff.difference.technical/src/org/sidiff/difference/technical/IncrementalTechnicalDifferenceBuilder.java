package org.sidiff.difference.technical;

import java.util.List;

import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.silift.common.util.access.EMFModelAccessEx;
import org.silift.common.util.emf.Scope;

/**
 * Incremental technical difference builder which must be initialized with a
 * list of ITechnicalDifferenceBuilders. When the difference is derived, this
 * incremental difference builder invokes all sub-builders in the order which is
 * given by the list of technical difference builders.
 * 
 * Please note that each sub-builder must ensure that it derives only those
 * low-level changes that refer to types which are supported by this technical
 * difference builder.
 * 
 * Note also that this technical difference builder is not registered via an
 * extension point and will not be shown in the SiLift UI. So far, it is only
 * usable via API.
 * 
 * 
 * @author kehrer
 */
public class IncrementalTechnicalDifferenceBuilder implements ITechnicalDifferenceBuilder {

	public static final String NAME = "Incremental Technical Difference Builder";

	/**
	 * The list of technical difference builders which will be executed in the
	 * order given by the List.
	 */
	private List<ITechnicalDifferenceBuilder> tdBuilders;

	/**
	 * Constructs a new incremental technical difference builder based on a list
	 * of sub-builders. These sub-builders will be executed in the order given
	 * by the list.
	 * 
	 * @param matchers
	 */
	public IncrementalTechnicalDifferenceBuilder(List<ITechnicalDifferenceBuilder> tdBuilders) {
		super();
		this.tdBuilders = tdBuilders;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public SymmetricDifference deriveTechDiff(SymmetricDifference difference, Scope scope) {
		LogUtil.log(LogEvent.NOTICE, "Starting incremental derivation of low-level difference");

		Resource modelA = difference.getModelA();
		Resource modelB = difference.getModelB();
		
		for (int i = 0; i < tdBuilders.size(); i++) {
			ITechnicalDifferenceBuilder nextBuilder = tdBuilders.get(i);

			if (nextBuilder.canHandle(modelA, modelB)) {
				LogUtil.log(LogEvent.NOTICE, "Next tdBuilder (" + i + "): " + nextBuilder.getName());
				nextBuilder.deriveTechDiff(difference, scope);
			} else {
				LogUtil.log(LogEvent.NOTICE, "Next matcher (" + i + "): " + nextBuilder.getName()
						+ ": Skip because cannot handle resources " + modelA + " and " + modelB);
			}
		}

		LogUtil.log(LogEvent.NOTICE, "Finished incremental derivation of low-level difference");
		
		return difference;
	}

	@Override
	public String getDocumentType() {
		return EMFModelAccessEx.GENERIC_DOCUMENT_TYPE;
	}

	@Override
	public boolean canHandle(Resource modelA, Resource modelB) {
		// true if at least one of the tdBuilders can handle modelA/modelB
		for (ITechnicalDifferenceBuilder builder : tdBuilders) {
			if (builder.canHandle(modelA, modelB)) {
				return true;
			}
		}

		return false;
	}

}
