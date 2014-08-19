package org.sidiff.orderedsets.impl;

import java.util.Collection;
import java.util.LinkedList;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.sidiff.orderedsets.MMOrig2MMRefined;
import org.sidiff.orderedsets.util.OrderedSetUtil;

public class MMOrig2MMRefinedJava implements MMOrig2MMRefined {

	@Override
	public EPackage mmOrig2mmRefined(EPackage pOriginal) {
		
		Collection<EObject> orig = new LinkedList<EObject>();
		orig.add(pOriginal);
		
        Copier copier = new Copier();
        Collection<EObject> copy = copier.copyAll(orig);
        copier.copyReferences(); 
		
        EPackage pRefined = (EPackage) copy.iterator().next();
        pRefined.setNsURI(OrderedSetUtil.getRefinedUri(pOriginal.getNsURI()));
        
        TreeIterator<EObject> it  = pRefined.eAllContents();
		while (it.hasNext()) {
			EObject c = it.next();
			if (c instanceof EClass) {
				for (EReference r : ((EClass) c).getEReferences()) {
					if (r.isOrdered()) {
						EClass orderedItem = r.getEReferenceType();
						System.out.println("Found ordered item: " + orderedItem.getName());
						if (r.isContainment()) {
							System.out.println("Found containment reference to ordered item!");							
							
							EReference pre = EcoreFactory.eINSTANCE.createEReference();
							pre.setName("pre");
							pre.setContainment(false);
							pre.setLowerBound(0);
							pre.setUpperBound(1);
							pre.setOrdered(false);
							EClass src = orderedItem;
							EClass tgt = orderedItem;
							src.getEStructuralFeatures().add(pre);
							pre.setEType(tgt);
							
							EReference succ = EcoreFactory.eINSTANCE.createEReference();
							succ.setName("succ");
							succ.setContainment(false);
							succ.setLowerBound(0);
							succ.setUpperBound(1);
							succ.setOrdered(false);
							src.getEStructuralFeatures().add(succ);
							succ.setEType(tgt);
						
							pre.setEOpposite(succ);
							succ.setEOpposite(pre);
							
						} else {	
							EClass link = EcoreFactory.eINSTANCE.createEClass();
							link.setName(orderedItem.getName() + "_Link");
							EClass src = r.getEContainingClass();
							System.out.println("Found containing class: " + src.getName());
							EClass tgt = link;
							pRefined.getEClassifiers().add(link);
							
							EReference links = EcoreFactory.eINSTANCE.createEReference();
							links.setName(orderedItem.getName() + "_links");
							links.setContainment(true);
							links.setLowerBound(0);
							links.setUpperBound(-1);
							src.getEStructuralFeatures().add(links);
							links.setEType(tgt);
							
							EReference pre = EcoreFactory.eINSTANCE.createEReference();
							pre.setName("pre");
							pre.setContainment(false);
							pre.setLowerBound(1);
							pre.setUpperBound(1);
							pre.setOrdered(false);
							src = link;
							tgt = orderedItem;
							src.getEStructuralFeatures().add(pre);
							pre.setEType(tgt);
							
							EReference succ = EcoreFactory.eINSTANCE.createEReference();
							succ.setName("succ");
							succ.setContainment(false);
							succ.setLowerBound(1);
							succ.setUpperBound(1);
							succ.setOrdered(false);
							src.getEStructuralFeatures().add(succ);
							succ.setEType(tgt);							
						}
						r.setOrdered(false);						
					}
				}
			}				
		}
			
			
		
		//...
		
		return pRefined;
	}

}
