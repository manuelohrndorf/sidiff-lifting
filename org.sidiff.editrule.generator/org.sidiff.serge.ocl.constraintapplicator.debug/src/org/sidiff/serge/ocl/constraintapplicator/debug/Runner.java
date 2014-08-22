package org.sidiff.serge.ocl.constraintapplicator.debug;

import java.io.File;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.sidiff.serge.ocl.constraintapplicator.OCLConstraintApplicator;
import org.sidiff.serge.ocl.constraintapplicator.settings.OCLCASettings;

/**
 * This class should be used if OCLConstraintApplicator is run with OSGi for debug/testing purpose.
 * @author mrindt
 *
 */
public class Runner implements IApplication{


	@Override
	public Object start(IApplicationContext context) throws Exception {

		//temporarily hard coded testing values
		String inputPath ="/home/michaela/workspace/OCLConst/uml2.editrules.detailed/transformations";
		String outputPath ="/home/michaela/workspace/OCLConst/uml2.editrules.detailed/transformations/modified";
		String eAnnotationConstraintKey ="http://www.eclipse.org/emf/2002/GenModel";
		EPackage metaModelPackage = null; 
		
		
		
		//Load meta-model from file and not from EPackageRegistry!
		//Metamodels in the EPackageRegistry are stripped of Annotation whysoever....
		ResourceSet resourceSet = new ResourceSetImpl(); 
		File f = new File("/home/michaela/workspace/OCLConst/org.eclipse.uml2.uml/model/UML.ecore");
		URI uri = URI.createFileURI(f.getAbsolutePath()) ;
		Resource ecoreResource = resourceSet.getResource(uri, true); 
		metaModelPackage = (EPackage)ecoreResource.getContents().get(0);
		
		OCLCASettings settings = new OCLCASettings(
				inputPath,
				outputPath,
				metaModelPackage,
				eAnnotationConstraintKey);
		
		OCLConstraintApplicator oclConstraintApplicator = new OCLConstraintApplicator(settings);
		oclConstraintApplicator.applyConstraints();
		
		return null;
	}

	@Override
	public void stop() {
		// do nothing
	}
}