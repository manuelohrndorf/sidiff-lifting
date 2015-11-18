package org.sidiff.matcher.id.uuid;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.sidiff.common.emf.EMFUtil;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.common.emf.access.Scope;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.symmetric.Correspondence;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.matcher.BaseMatcher;

/**
 * XMI-IDs are used as matching criterion. Works only if the resources have XMI
 * IDs, otherwise we can't handle them.
 * 
 * @author kehrer
 */
public class UUIDMatcher extends BaseMatcher {

	public static final String KEY = "UUIDMatcher";

	@Override
	public String getName() {
		return "UUID Matcher";
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public SymmetricDifference createMatching(Resource modelA, Resource modelB, Scope scope,
			boolean calculateReliability) {

		checkHasUUIDs(modelA);
		checkHasUUIDs(modelB);

		super.createMatching(modelA, modelB, scope, calculateReliability);

		// UUID-based matching => reliability of matches = 1.0
		for (Correspondence c : getDifference().getCorrespondences()) {
			c.setReliability(1.0f);
		}

		return getDifference();
	}

	/**
	 * We override the default canHandle Behavior which only checks for the
	 * documentType. Here, we check if the resource really have XMI IDs.
	 */
	@Override
	public boolean canHandle(Resource modelA, Resource modelB) {
		// Check modelA
		if (!checkHasUUIDs(modelA)) {
			LogUtil.log(LogEvent.MESSAGE, KEY + " cannot handle resource " + modelA);
			return false;
		}

		// Check modelB
		if (!checkHasUUIDs(modelB)) {
			LogUtil.log(LogEvent.MESSAGE, KEY + " cannot handle resource " + modelB);
			return false;
		}

		// Everything fine
		return true;
	}

	@Override
	public String getDocumentType() {
		return EMFModelAccess.GENERIC_DOCUMENT_TYPE;
	}

	@Override
	protected boolean isCorresponding(EObject elementA, EObject elementB) {
		if (elementA != null && elementB != null) {
			String idA = EMFUtil.getXmiId(elementA);
			String idB = EMFUtil.getXmiId(elementB);
			if (idA != null && idB != null) {
				return idA.equals(idB);
			}
		}

		return false;
	}

	private boolean checkHasUUIDs(Resource r) {
		// Does resource provide IDs..?
		if (!(r instanceof XMIResource)) {
			LogUtil.log(LogEvent.MESSAGE, "Resource " + r + " doesn't provide XMI-IDs");
			return false;
		}

		// Are ids really unique..?
		Map<String, EObject> ids = new HashMap<String, EObject>();

		TreeIterator<EObject> iter = r.getAllContents();
		while (iter.hasNext()) {
			EObject element = iter.next();
			String id = EMFUtil.getXmiId(element);
			if (id == null) {
				// we should return false here...
				// LogUtil.log(LogEvent.MESSAGE, "EObject '" + element +
				// "' has no id at all!");
				// return false;

				// ..but this makes trouble with Ecore Generics, so we continue
				continue;
			}

			if (ids.containsKey(id)) {
				LogUtil.log(LogEvent.MESSAGE, "Id '" + id + "' of EObject '" + element
						+ "' is also used as id of object '" + ids.get(id) + "'");
				return false;
			}

			ids.put(id, element);
		}

		return true;
	}

	@Override
	public boolean canComputeReliability() {
		return false;
	}

	@Override
	public Map<String, Object> getConfigurationOptions() {
		// TODO Auto-generated method stub
		return Collections.emptyMap();
	}

}
