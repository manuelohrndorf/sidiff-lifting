package org.silift.common.util.emf;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.silift.common.util.access.EMFMetaAccessEx;

/**
 * Calculates all external references from the viewpoint of the given model and
 * with respect to the selected comparisonMode.
 * 
 * Note that we apply two filters when iterating over all EReferences of the
 * meta-model:
 * <ul>
 * <li>EReferences that are unchangeable or derived (see
 * {@link EMFMetaAccessEx#isUnconsideredStructualFeature(EStructuralFeature)})</li>
 * <li>Containment EReferences (because we assume that containments can only
 * point to a target object within the same resource)</li>
 * </ul>
 * 
 * @author kehrer
 */
public class ExternalReferenceCalculator {

	private List<ExternalReference> registryReferences;
	private List<ExternalReference> resourceSetReferences;
	private int comparisonMode;

	/**
	 * Calculates all external references from the viewpoint of the given model
	 * and with respect to the selected comparisonMode.
	 * 
	 * @param model
	 * @param comparisonMode
	 *            {@link EMFResourceUtil#COMPARE_RESOURCE} or
	 *            {@link EMFResourceUtil#COMPARE_RESOURCE_SET}
	 * @return
	 */
	public ExternalReferenceContainer calculate(Resource model, int comparisonMode) {
		this.registryReferences = new LinkedList<ExternalReference>();
		this.resourceSetReferences = new LinkedList<ExternalReference>();
		this.comparisonMode = comparisonMode;

		// Find external references, i.e.
		// RESOURCE -> PACKAGE_REGISTRY, and
		// RESOURCE -> RESOURCE_SET
		intl_calculate(model);

		// Also add external References from RESOURCE_SET to
		// PACKAGE_REGISTRY (when modus = COMPLETE RESOURCE SET)
		if (comparisonMode == EMFResourceUtil.COMPARE_RESOURCE_SET) {
			for (Resource r : model.getResourceSet().getResources()) {
				if (r == model) {
					continue;
				}
				intl_calculate(r);
			}
		}

		return new ExternalReferenceContainer(registryReferences, resourceSetReferences);
	}

	private void intl_calculate(Resource model) {
		// Iterate over all model objects
		TreeIterator<EObject> iterator = model.getAllContents();

		while (iterator.hasNext()) {
			EObject eObject = iterator.next();
			EClass eClass = eObject.eClass();

			// Check all class features (also inherited)
			for (EStructuralFeature eStructuralFeature : eClass.getEAllStructuralFeatures()) {
				// Check only changeable and not derived features
				if (!EMFMetaAccessEx.isUnconsideredStructualFeature(eStructuralFeature)) {

					// Check references but do not check the containments
					if ((eStructuralFeature instanceof EReference)
							&& (!((EReference) eStructuralFeature).isContainment())) {

						// Check the objects reference targets for imports
						if (eStructuralFeature.isMany()) {
							@SuppressWarnings("unchecked")
							List<EObject> eStructuralFeatureValues = (List<EObject>) eObject.eGet(eStructuralFeature);

							for (int i = 0; i < eStructuralFeatureValues.size(); i++) {
								EObject eStructuralFeatureValue = eStructuralFeatureValues.get(i);
								int location = EMFResourceUtil.locate(model, eStructuralFeatureValue);

								if (location == EMFResourceUtil.PACKAGE_REGISTRY) {
									registryReferences.add(new ExternalManyReference(eObject, (EReference) eStructuralFeature,
											eStructuralFeatureValue, i));
								}
								if (location == EMFResourceUtil.RESOURCE_SET_INTERNAL
										&& comparisonMode == EMFResourceUtil.COMPARE_RESOURCE) {
									resourceSetReferences.add(new ExternalManyReference(eObject, (EReference) eStructuralFeature,
											eStructuralFeatureValue, i));
								}
							}
						} else {
							EObject eStructuralFeatureValue = (EObject) eObject.eGet(eStructuralFeature);

							if (eStructuralFeatureValue != null) {
								int location = EMFResourceUtil.locate(model, eStructuralFeatureValue);

								if (location == EMFResourceUtil.PACKAGE_REGISTRY) {
									registryReferences.add(new ExternalReference(eObject, (EReference) eStructuralFeature,
											eStructuralFeatureValue));
								}
								if (location == EMFResourceUtil.RESOURCE_SET_INTERNAL
										&& comparisonMode == EMFResourceUtil.COMPARE_RESOURCE) {
									resourceSetReferences.add(new ExternalReference(eObject, (EReference) eStructuralFeature,
											eStructuralFeatureValue));
								}
							}
						}
					}
				}
			}
		} // end iterate
	}
}
