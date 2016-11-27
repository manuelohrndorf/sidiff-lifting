package org.sidiff.repair.casestudy.gmf;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.gmf.gmfgraph.GMFGraphPackage;
import org.eclipse.gmf.tooldef.GMFToolPackage;
import org.sidiff.repair.casestudy.gmf.validation.EMFDiagnosticAdapter;
import org.sidiff.repair.casestudy.gmf.validation.EMFValidator;
import org.sidiff.repair.casestudy.gmf.validation.IValidationError;
import org.sidiff.repair.casestudy.gmf.validation.IValidator;

public class ReportGenerator {

	private static final String GIT_FOLDER = "/home/kehrer/git/sidiff-lifting";
	private static final String[] MODELS = new String[] { "mappings" };

	private IValidator validator = new EMFValidator();

	static {
		// Initialize the meta-model
		EcorePackage.eINSTANCE.eClass();
		GMFGraphPackage.eINSTANCE.eClass();
		GMFToolPackage.eINSTANCE.eClass();
		
		// Register the XMIResourceFactory factory for the .ecore extension
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();
		if (!m.containsKey("ecore")) {
			m.put("ecore", new XMIResourceFactoryImpl());
		}
	}

	public void generate() {
		for (String model : MODELS) {
			System.out.println("\n==" + model);

			String modelFolderPath = GIT_FOLDER + "/testdata/org.sidiff.mixeddomains.testdata/gmf/" + model;
			File modelFolder = new File(modelFolderPath);

			FilenameFilter filter = new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.toLowerCase().endsWith("ecore");
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

			if (validationError.getException() != null) {
				System.out.println(" ====== exception != null =========");
			}
			if (!validationError.getSource().equals("org.eclipse.emf.ecore.model")) {
				System.out.println(" ====== source != org.eclipse.emf.ecore.model =========");
				System.out.println("\tsource: " + validationError.getSource());
			}

			if (!(validationError instanceof EMFDiagnosticAdapter)) {
				System.out.println("======= not a diagnostic adapter ========");
			}
			System.out.println("\tmessage: " + validationError.getMessage());

			if (validationError instanceof EMFDiagnosticAdapter) {
				EMFDiagnosticAdapter adapter = (EMFDiagnosticAdapter) validationError;
				Diagnostic diagnostic = adapter.getAdaptee();
				System.out.println("\t\t" + diagnostic.getData());
			}
		}
	}

	public static void main(String[] args) {
		new ReportGenerator().generate();
	}
}
