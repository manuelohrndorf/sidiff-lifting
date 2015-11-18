package sample.metamodel.refined.difference.technical;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.difference.technical.TechnicalDifferenceBuilder;

import sample.mm.refined.samplemm.CList;
import sample.mm.refined.samplemm.Item;
import sample.mm.refined.samplemm.Item_Link;
import sample.mm.refined.samplemm.NCList;
import sample.mm.refined.samplemm.SampleMetamodel;
import sample.mm.refined.samplemm.SamplemmPackage;

public class TechnicalDifferenceBuilderSampleMMRefined extends TechnicalDifferenceBuilder {
	
	@Override
	protected Set<EClass> getUnconsideredNodeTypes() {
		Set<EClass> unconsideredNodeTypes = new HashSet<EClass>();
		
		return unconsideredNodeTypes;
	}

	@Override
	protected Set<EReference> getUnconsideredEdgeTypes() {
		Set<EReference> unconsideredEdgeTypes = new HashSet<EReference>();
		
		return unconsideredEdgeTypes;
	}

	@Override
	protected Set<EAttribute> getUnconsideredAttributeTypes() {
		Set<EAttribute> unconsideredAttributeTypes = new HashSet<EAttribute>();
		
		return unconsideredAttributeTypes;
	}

	@Override
	protected void checkDocumentType(Resource model) {
		String docType = EMFModelAccess.getCharacteristicDocumentType(model);
		assert (docType == SamplemmPackage.eNS_URI) : "Wrong document type: Expected " + SamplemmPackage.eNS_URI + " but got " + docType;
	}
	
	@Override
	protected String getObjectName(EObject obj) {
		if (obj instanceof SampleMetamodel) {
			return "[Root]";
		}
		if (obj instanceof Item) {
			return ((Item) obj).getName();
		}
		if (obj instanceof NCList) {
			return ((NCList) obj).getName();
		}
		if (obj instanceof CList) {
			return ((CList) obj).getName();
		}
		if (obj instanceof Item_Link) {
			Item_Link link = (Item_Link) obj;
			String context = getObjectName(link.eContainer());
			String pre = getObjectName(link.getPre());
			String succ = getObjectName(link.getSucc());
			
			return "[" + context + "]: " + pre + " -> " + succ;			
		}
		
		return null;
	}

	@Override
	public String getDocumentType() {
		return SamplemmPackage.eNS_URI;
	}
	
}
