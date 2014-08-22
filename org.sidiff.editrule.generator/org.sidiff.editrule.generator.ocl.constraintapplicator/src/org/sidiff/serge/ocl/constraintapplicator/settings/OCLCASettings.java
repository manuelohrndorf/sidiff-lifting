package org.sidiff.serge.ocl.constraintapplicator.settings;

import org.eclipse.emf.ecore.EPackage;

/**
 * The OCLConstraintApplicator settings class.
 * It will contain input and output paths etc.
 * 
 * @author mrindt
 *
 */
public class OCLCASettings {

	private String inputPath;
	private String outputPath;
	private EPackage metaModelEPackage;
	private String eAnnotationConstraintKey;
	
	
	public OCLCASettings(String inputPath, String outputPath, EPackage metaModelEPackage, String eAnnotationConstraintKey) {
		
		this.inputPath = inputPath;
		this.outputPath = outputPath;
		this.metaModelEPackage = metaModelEPackage;
		this.eAnnotationConstraintKey = eAnnotationConstraintKey;
		
	}


	public String getInputPath() {
		return inputPath;
	}


	public String getOutputPath() {
		return outputPath;
	}
	
	public EPackage getMetaModelEPackage() {
		return metaModelEPackage;
	}


	public String geteAnnotationConstraintKey() {
		return eAnnotationConstraintKey;
	}
}
