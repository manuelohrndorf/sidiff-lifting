package org.sidiff.difference.matcher.uuid;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.sidiff.common.emf.EMFUtil;
import org.sidiff.difference.matcher.BaseMatcher;
import org.sidiff.difference.symmetric.SymmetricDifference;

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
	public SymmetricDifference createMatching(Resource modelA, Resource modelB) {
		checkHasUUIDs(modelA);
		checkHasUUIDs(modelB);
		
		return super.createMatching(modelA, modelB);
	}
	
	@Override
	public boolean canHandle(String documentType) {
		// TODO: canHandle braucht zum Prüfen hier eigentlich die Resourcen.
		// Müssen beides XMI-Resourcen sein!
		return true;
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

	private void checkHasUUIDs(Resource r) {
		// Does resource provide IDs..?
		assert (r instanceof XMIResource) : "Resource " + r + " doesn't provide XMI-IDs";

		// Are ids really unique..?
		Map<String, EObject> ids = new HashMap<String, EObject>();

		TreeIterator<EObject> iter = r.getAllContents();
		while (iter.hasNext()) {
			EObject element = iter.next();
			String id = EMFUtil.getXmiId(element);
			if (id == null){
				continue;
			}
			
			assert (!ids.containsKey(id)) : "Id '" + id + "' of EObject '" + element + "' is also used as id of object '" + ids.get(id) + "'";
			ids.put(id, element);
		}
	}

}
