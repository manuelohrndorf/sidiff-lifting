package org.sidiff.orderedsets.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.orderedsets.MRefined2MOrig;

public class MRefined2MOrigJava implements MRefined2MOrig {

	public static EPackage pOriginal; // FIXME

	// Abbildung objRefined -> objOriginal
	HashMap<EObject, EObject> objMap = new HashMap<EObject, EObject>();

	@Override
	public EObject mRefined2mOrig(Resource rRefined) {
		// 1. Objekte "kopieren"
		for (Iterator<EObject> iterator = rRefined.getAllContents(); iterator.hasNext();) {
			EObject objectRefined = iterator.next();
			EObject objectOriginal = createOriginal(objectRefined);

			// Mappings einfügen
			objMap.put(objectRefined, objectOriginal);
		}

		// 2. Schleife nochmal für Referenzen "kopieren"
		for (Iterator<EObject> iterator = rRefined.getAllContents(); iterator.hasNext();) {
			EObject objectRefined = iterator.next();
			createOriginalRefs(objectRefined);
		}

		// 3. Items sortieren

		TreeIterator<EObject> it = rRefined.getAllContents();

		while (it.hasNext()) {
			EObject objRefined = it.next();
			for (EReference refRefined : objRefined.eClass().getEAllReferences()) {
				EReference refOrig = refinedEReference2OriginalEReference(refRefined);
				if (refOrig != null && refOrig.isOrdered()) {
					EObject objOrig = objMap.get(objRefined);

					EList<EObject> listOrig = (EList) objOrig.eGet(refOrig);
					EList<EObject> listRefined = (EList) objRefined.eGet(refRefined);

					if (refOrig.isContainment()) {
						// Sort CList
						EObject currentRefined = getFirstInCList(listRefined);
						int idx = 0;
						while (currentRefined != null) {
							// set position
							EObject currentOrig = objMap.get(currentRefined);
							listOrig.move(idx, currentOrig);

							// go on
							currentRefined = getNextInCList(currentRefined);
							idx++;
						}
					} else {
						// TODO
						// NCList
					}
				}
			}
		}

		return objMap.get(rRefined.getContents().get(0));
	}

	private EObject createOriginal(EObject objectRefined) {
		EClass typeRefined = objectRefined.eClass();
		EClass typeOriginal = refinedType2OriginalType(typeRefined);

		EObject objectOriginal = pOriginal.getEFactoryInstance().create(typeOriginal);

		// Attributwerte "kopieren"
		for (EAttribute attrRefined : typeRefined.getEAllAttributes()) {
			for (EAttribute attrOriginal : typeOriginal.getEAllAttributes()) {
				if (attrOriginal.getName().equals(attrRefined.getName())) {
					// Wert kopieren
					objectOriginal.eSet(attrOriginal, objectRefined.eGet(attrRefined));
				}
			}
		}

		return objectOriginal;
	}

	private EClass refinedType2OriginalType(EClass typeRefined) {
		TreeIterator<EObject> it = pOriginal.eAllContents();
		while (it.hasNext()) {
			EObject c = it.next();
			if (c instanceof EClass) {
				if (((EClass) c).getName().equals(typeRefined.getName())) {
					return (EClass) c;
				}
			}
		}

		return null;
	}

	private EReference refinedEReference2OriginalEReference(EReference refRefined) {
		EClass srcRefined = (EClass) refRefined.eContainer();

		TreeIterator<EObject> it = pOriginal.eAllContents();
		while (it.hasNext()) {
			EObject c = it.next();
			if (c instanceof EClass) {
				EClass srcOrig = (EClass) c;
				for (EReference refOrig : srcOrig.getEAllReferences()) {
					if (srcOrig.getName().equals(srcRefined.getName())
							&& refOrig.getName().equals(refRefined.getName())) {
						return refOrig;
					}
				}
			}
		}

		return null;
	}

	private void createOriginalRefs(EObject objectRefined) {
		EClass typeRefined = objectRefined.eClass();
		EObject objectOriginal = objMap.get(objectRefined);
		EClass typeOriginal = objectOriginal.eClass();
		int i = 0;
		for (EReference refRefined : typeRefined.getEAllReferences()) {
			// EReference in MM_refined holen
			EReference refOriginal = null;
			for (EReference r : typeOriginal.getEAllReferences()) {
				if (r.getName().equals(refRefined.getName())) {
					refOriginal = r;
				}
			}

			if (refRefined.isMany()) {
				Collection<EObject> refinedTgts = (Collection<EObject>) objectRefined.eGet(refRefined);
				Collection<EObject> originalTgts = (Collection<EObject>) objectOriginal.eGet(refOriginal);
				for (EObject refinedTgt : refinedTgts) {
					originalTgts.add(objMap.get(refinedTgt));
				}
			} else {
				EObject refinedTgt = (EObject) objectRefined.eGet(refRefined);
				if (refinedTgt != null && refOriginal != null) {
					EObject originalTgt = objMap.get(refinedTgt);
					objectOriginal.eSet(refOriginal, originalTgt);
				}
			}
		}
	}

	private EObject getFirstInCList(Collection<EObject> cList) {
		for (EObject obj : cList) {
			EReference refPre = (EReference) obj.eClass().getEStructuralFeature("pre");
			EObject pre = (EObject) obj.eGet(refPre);
			if (pre == null) {
				return obj;
			}
		}

		return null;
	}

	private EObject getNextInCList(EObject obj) {
		EReference refSucc = (EReference) obj.eClass().getEStructuralFeature("succ");
		return (EObject) obj.eGet(refSucc);
	}
}
