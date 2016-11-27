package org.sidiff.repair.casestudy.gmf;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Collection;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.impl.EValidatorRegistryImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.util.UMLValidator;
import org.sidiff.repair.casestudy.gmf.validation.EMFDiagnosticAdapter;
import org.sidiff.repair.casestudy.gmf.validation.EMFValidator;
import org.sidiff.repair.casestudy.gmf.validation.IValidationError;
import org.sidiff.repair.casestudy.gmf.validation.IValidator;

public class ReportGenerator2 implements IApplication {

	boolean overviewOnly = false;
	
	private static final String GIT_FOLDER = "/home/kehrer/git/sidiff-lifting";
	private static final String[] FILE_FILTERS = new String[] { "ecore", "uml"};
	private static final String[] MODELS = new String[] { "/testdata/org.sidiff.mixeddomains.testdata/gmf/gmfgen", "/testdata/org.sidiff.mixeddomains.testdata/gmf/gmfgraph", "/testdata/org.sidiff.mixeddomains.testdata/gmf/mappings" };
	//private static final String[] MODELS = new String[] { "/testdata/org.sidiff.mixeddomains.testdata/SysML" };
	
	
	private IValidator validator = new EMFValidator();
	
	@Override
	public Object start(IApplicationContext context) throws Exception {
		System.out.println(" ======================================== Start =================================");

		generate();
		
		return IApplication.EXIT_OK;
	}

	private void generate() {
		EValidator.Registry registry=new EValidatorRegistryImpl(EValidator.Registry.INSTANCE);
		registry.put(UMLPackage.eINSTANCE,new UMLValidator());
		
		for (String model : MODELS) {
			System.out.println("\n==" + model);

			String modelFolderPath = GIT_FOLDER + model;
			File modelFolder = new File(modelFolderPath);

			FilenameFilter filter = new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					boolean accept = false;
					for (String filter : FILE_FILTERS) {
						if(name.toLowerCase().endsWith(filter)){
							accept = true;
							break;
						}
					}
					return accept;
				}
			};
			File[] files = modelFolder.listFiles(filter);
			Arrays.sort(files);
			for (int i = 0; i < files.length - 1; i++) {
				File modelFile = files[i];
				generate(modelFile);
			}
		}
	}

	private void generate(File modelFile) {
		ResourceSet rs = new ResourceSetImpl();
		Resource model = rs.getResource(URI.createFileURI(modelFile.getAbsolutePath()), true);

		Collection<IValidationError> validationErrors = validator.validate(model);

		System.out.print(modelFile.getName());
		System.out.println(" (" + validationErrors.size() + ")");

		printValidationErrors(validationErrors);
	}

	private void printValidationErrors(Collection<IValidationError> validationErrors) {
		for (IValidationError validationError : validationErrors) {

			if (!overviewOnly){
				System.out.println("\tsource: " + validationError.getSource());
				System.out.println("\tseverity: " + validationError.getSeverity());
				System.out.println("\tmessage: " + validationError.getMessage());
				
				if (validationError instanceof EMFDiagnosticAdapter) {
					EMFDiagnosticAdapter adapter = (EMFDiagnosticAdapter) validationError;
					Diagnostic diagnostic = adapter.getAdaptee();
					System.out.println("\t\t" + diagnostic.getData());
				}
			}
			
		}
	}

	@Override
	public void stop() {
		System.out.println("Stop");

	}

}
