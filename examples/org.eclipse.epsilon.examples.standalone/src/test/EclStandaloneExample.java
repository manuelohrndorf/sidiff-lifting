package test;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.refactor.examples.SimpleWebModelingLanguageStandaloneSetupGenerated;
import org.eclipse.emf.refactor.examples.simpleWebModelingLanguage.SimpleWebModelingLanguagePackage;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.ecl.EclModule;
import org.eclipse.epsilon.ecl.trace.Match;
import org.eclipse.epsilon.ecl.trace.MatchTrace;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.models.IModel;

/**
 * This example demonstrates using the Epsilon Comparison Language (ECL) in a
 * stand-alone manner.
 * 
 * @author kehrer
 */
public class EclStandaloneExample {

	public static void main(String[] args) throws Exception {
		new EclStandaloneExample().execute();
	}

	public void execute() throws Exception {

		// Initialize ECL module and module context
		EclModule module = new EclModule();
		module.parse(getFile(getSource()));

		if (module.getParseProblems().size() > 0) {
			System.err.println("Parse errors occured...");
			for (ParseProblem problem : module.getParseProblems()) {
				System.err.println(problem.toString());
			}
			return;
		}

		for (IModel model : getModels()) {
			module.getContext().getModelRepository().addModel(model);
		}

		// Perform matching
		MatchTrace result = (MatchTrace) module.execute();
		int matchCounter = 0;
		for (Match match : result.getMatches()) {
			if (match.isMatching() && (match.getRule() != null)) {
				matchCounter = matchCounter + 1;
				System.out
						.println("(" + match.getRule().getName() + ")" + match.getLeft() + " <-> " + match.getRight());
			}
		}
		System.out.println("Total No. of Matches: " + matchCounter);

		// Dispose module
		module.getContext().getModelRepository().dispose();
	}

	private List<IModel> getModels() throws Exception {
		// Register Package and Factory (only needed in standalone mode)
		new SimpleWebModelingLanguageStandaloneSetupGenerated().createInjectorAndDoEMFRegistration();

		List<IModel> models = new ArrayList<IModel>();
		models.add(createEmfModelByURI("Left", "VehicleRentalCompany_v1.swml",
				SimpleWebModelingLanguagePackage.eNS_URI, true, true));
		models.add(createEmfModelByURI("Right", "VehicleRentalCompany_v2.swml",
				SimpleWebModelingLanguagePackage.eNS_URI, true, true));

		return models;
	}

	private String getSource() throws Exception {
		return "Comparison2.ecl";
	}

	private EmfModel createEmfModelByURI(String name, String model, String metamodel, boolean readOnLoad,
			boolean storeOnDisposal) throws EolModelLoadingException, URISyntaxException {
		EmfModel emfModel = new EmfModel();
		StringProperties properties = new StringProperties();
		properties.put(EmfModel.PROPERTY_NAME, name);
		properties.put(EmfModel.PROPERTY_METAMODEL_URI, metamodel);
		properties.put(EmfModel.PROPERTY_MODEL_URI, getFile(model).toURI().toString());
		properties.put(EmfModel.PROPERTY_READONLOAD, readOnLoad + "");
		properties.put(EmfModel.PROPERTY_STOREONDISPOSAL, storeOnDisposal + "");
		emfModel.load(properties, null);
		return emfModel;
	}

	private File getFile(String fileName) throws URISyntaxException {

		URI binUri = EclStandaloneExample.class.getResource(fileName).toURI();
		URI uri = null;

		if (binUri.toString().indexOf("bin") > -1) {
			uri = new URI(binUri.toString().replaceAll("bin", "src"));
		} else {
			uri = binUri;
		}

		return new File(uri);
	}

}
