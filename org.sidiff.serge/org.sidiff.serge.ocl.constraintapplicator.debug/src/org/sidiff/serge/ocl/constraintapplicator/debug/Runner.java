package org.sidiff.serge.ocl.constraintapplicator.debug;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
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
		EPackage metaModelPackage =EPackageRegistryImpl.INSTANCE.getEPackage("http://www.eclipse.org/uml2/5.0.0/UML");
		String eAnnotationConstraintKey ="http://www.eclipse.org/emf/2002/GenModel";
		
		
		OCLCASettings settings = new OCLCASettings(
				inputPath,
				outputPath,
				metaModelPackage,
				eAnnotationConstraintKey);
		
		OCLConstraintApplicator oclConstraintApplicator = new OCLConstraintApplicator(settings);
		
		return null;
	}

	@Override
	public void stop() {
		// do nothing
	}
}