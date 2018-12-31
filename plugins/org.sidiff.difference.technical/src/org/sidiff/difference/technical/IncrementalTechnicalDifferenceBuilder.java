package org.sidiff.difference.technical;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.common.emf.access.Scope;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.symmetric.SymmetricDifference;

/**
 * <p>Incremental technical difference builder which must be initialized with a
 * list of ITechnicalDifferenceBuilders. When the difference is derived, this
 * incremental difference builder invokes all sub-builders in the order which is
 * given by the list of technical difference builders.</p>
 * 
 * <p>Please note that each sub-builder must ensure that it derives only those
 * low-level changes that refer to types which are supported by this technical
 * difference builder.</p>
 * 
 * <p>Note also that this technical difference builder is not registered via an
 * extension point and will not be shown in the SiLift UI. So far, it is only
 * usable via API.</p>
 * 
 * <p>The incremental builder overrides <code>equals</code> and <code>hashCode</code>,
 * so that two incremental builders with the same list of sub-builders are considered equal.</p>
 * 
 * @author kehrer
 */
public class IncrementalTechnicalDifferenceBuilder implements ITechnicalDifferenceBuilder {

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
	 * @param tdBuilders
	 */
	public IncrementalTechnicalDifferenceBuilder(List<ITechnicalDifferenceBuilder> tdBuilders) {
		super();
		this.tdBuilders = tdBuilders;
	}

	@Override
	public String getName() {
		return "Incremental Technical Difference Builder";
	}

	@Override
	public SymmetricDifference deriveTechDiff(SymmetricDifference difference, Scope scope) {
		LogUtil.log(LogEvent.NOTICE, "Starting incremental derivation of low-level difference");

		Resource modelA = difference.getModelA();
		Resource modelB = difference.getModelB();
		
		for (int i = 0; i < tdBuilders.size(); i++) {
			ITechnicalDifferenceBuilder nextBuilder = tdBuilders.get(i);
			if (nextBuilder.canHandleModels(modelA, modelB)) {
				LogUtil.log(LogEvent.NOTICE, "Next tdBuilder (" + i + "): " + nextBuilder.getName());
				nextBuilder.deriveTechDiff(difference, scope);
			} else {
				LogUtil.log(LogEvent.NOTICE, "Next tdBuilder (" + i + "): " + nextBuilder.getName()
						+ ": Skip because cannot handle resources " + modelA + " and " + modelB);
			}
		}

		LogUtil.log(LogEvent.NOTICE, "Finished incremental derivation of low-level difference");
		
		return difference;
	}

	@Override
	public Set<String> getDocumentTypes() {
		Set<String> docTypes = new HashSet<String>();
		for(ITechnicalDifferenceBuilder builder : tdBuilders){
			docTypes.addAll(builder.getDocumentTypes());
		}
		return docTypes;
	}

	@Override
	public String getKey() {
		return getClass().getName();
	}

	@Override
	public boolean canHandleModels(Resource modelA, Resource modelB) {
		Set<String> docTypes = EMFModelAccess.getDocumentTypes(modelA, Scope.RESOURCE_SET);
		docTypes.addAll(EMFModelAccess.getDocumentTypes(modelB, Scope.RESOURCE_SET));
		return canHandle(docTypes);
	}

	public List<ITechnicalDifferenceBuilder> getTechnicalDifferenceBuilders() {
		return Collections.unmodifiableList(tdBuilders);
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == this) {
			return true;
		} else if(!(obj instanceof IncrementalTechnicalDifferenceBuilder)) {
			return false;
		}
		return tdBuilders.equals(((IncrementalTechnicalDifferenceBuilder)obj).tdBuilders);
	}

	@Override
	public int hashCode() {
		return tdBuilders.hashCode();
	}
}
