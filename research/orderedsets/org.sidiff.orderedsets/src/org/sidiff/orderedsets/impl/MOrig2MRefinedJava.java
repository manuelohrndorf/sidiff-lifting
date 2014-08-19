package org.sidiff.orderedsets.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.orderedsets.MOrig2MRefined;
import org.sidiff.orderedsets.util.ConstantValues;

public class MOrig2MRefinedJava implements MOrig2MRefined {

	public static EPackage pRefined; // FIXME

	// Abbildung objOriginal -> objRefined
	HashMap<EObject, EObject> objMap = new HashMap<EObject, EObject>();

	@Override
	public EObject mOrig2mRefined(Resource rOriginal) {
		// 1. Objekte "kopieren"
		for (Iterator<EObject> iterator = rOriginal.getAllContents(); iterator.hasNext();) {
			EObject objectOriginal = iterator.next();
			EObject objectRefined = createRefined(objectOriginal);

			// Mapping einfügen objectOriginal -> objectRefined
			objMap.put(objectOriginal, objectRefined);
		}

		// 2. Schleife nochmal für Referenzen "kopieren"
		for (Iterator<EObject> iterator = rOriginal.getAllContents(); iterator.hasNext();) {
			EObject objectOriginal = iterator.next();
			createRefinedRefs(objectOriginal);
		}

		// 3. "Pre" und "Succ" setzen
		// (wobei wir nach Containment-Listen (a) und Non-Containment-Listen (b)
		// unterscheiden muessen)

		TreeIterator<EObject> it = rOriginal.getAllContents();

		while (it.hasNext()) {
			EObject o = it.next();
			for (EReference refOrig : o.eClass().getEAllReferences()) {
				if (refOrig.isOrdered()) {
					EObject objekt = objMap.get(o);
					List<EObject> items = (List) objekt.eGet(origType2RefinedType(refOrig));
					if (items.size() <= 1) {
						continue;
					}

					if (refOrig.isContainment()) {
						// (a) Containment-Liste => nur pre und succ
						EObject akt_item = items.get(0);
						EObject succ_item = items.get(1);
						setItemSucc(akt_item, succ_item);						
						if (items.size() > 2) {
							for (int i = 1; i < items.size() - 1; i++) {
								EObject pre_item = akt_item;
								akt_item = succ_item;
								setItemPre(akt_item, pre_item);								
								if (i < items.size() - 1) {
									succ_item = items.get(i + 1);
									setItemSucc(akt_item, succ_item);
								}
							}
						}
					} else {
						// (b) Non-Containment-Liste => Link-Objekt mit pre und succ
						
						// ...
						// TODO
						// ...
					}
				}
			}
		}

		return objMap.get(rOriginal.getContents().get(0));
	}

	private EObject createRefined(EObject objectOriginal) {
		EClass typeOrig = objectOriginal.eClass();
		EClass typeRefined = origType2RefinedType(typeOrig);

		EObject objectRefined = pRefined.getEFactoryInstance().create(typeRefined);

		// Attributwerte "kopieren"
		for (EAttribute attrOrig : typeOrig.getEAllAttributes()) {
			for (EAttribute attrRefined : typeRefined.getEAllAttributes()) {
				if (attrRefined.getName().equals(attrOrig.getName())) {
					// Wert kopieren
					objectRefined.eSet(attrRefined, objectOriginal.eGet(attrOrig));
				}
			}
		}

		return objectRefined;
	}

	private void setItemSucc(EObject item, EObject succ){
		for (EReference r : item.eClass().getEReferences()) {
			if (r.getName().equals(ConstantValues.ITEM_SUCC)){
				item.eSet(r, succ);
			}
		}
	}
	
	private void setItemPre(EObject item, EObject pre){
		for (EReference r : item.eClass().getEReferences()) {
			if (r.getName().equals(ConstantValues.ITEM_PRE)){
				item.eSet(r, pre);
			}
		}
	}
	
	private EClass origType2RefinedType(EClass typeOrig) {
		TreeIterator<EObject> it = pRefined.eAllContents();
		while (it.hasNext()) {
			EObject c = it.next();
			if (c instanceof EClass) {
				if (((EClass) c).getName().equals(typeOrig.getName())) {
					return (EClass) c;
				}
			}
		}

		return null;
	}

	private EReference origType2RefinedType(EReference typeOrig) {
		TreeIterator<EObject> it = pRefined.eAllContents();
		while (it.hasNext()) {
			EObject c = it.next();
			if (c instanceof EClass) {
				for (EReference r : ((EClass) c).getEReferences()) {
					if (r.getName().equals(typeOrig.getName())) {
						return r;
					}
				}
			}
		}

		return null;
	}

	private void createRefinedRefs(EObject objectOriginal) {
		EClass typeOrig = objectOriginal.eClass();
		EObject objectRefined = objMap.get(objectOriginal);
		EClass typeRefined = objectRefined.eClass();

		for (EReference refOrig : typeOrig.getEAllReferences()) {
			// EReference in MM_refined holen
			EReference refRefined = null;
			for (EReference r : typeRefined.getEAllReferences()) {
				if (r.getName().equals(refOrig.getName())) {
					refRefined = r;
				}
			}

			if (refOrig.isMany()) {
				Collection<EObject> origTgts = (Collection<EObject>) objectOriginal.eGet(refOrig);
				Collection<EObject> refinedTgts = (Collection<EObject>) objectRefined.eGet(refRefined);
				for (EObject origTgt : origTgts) {
					refinedTgts.add(objMap.get(origTgt));
				}
			} else {
				EObject origTgt = (EObject) objectOriginal.eGet(refOrig);
				if (origTgt != null) {
					EObject refinedTgt = objMap.get(origTgt);
					objectRefined.eSet(refRefined, refinedTgt);
				}
			}
		}
	}

}
