package org.sidiff.swml.matcher;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.refactor.examples.SimpleWebModelingLanguageStandaloneSetupGenerated;
import org.eclipse.emf.refactor.examples.simpleWebModelingLanguage.SimpleWebModelingLanguagePackage;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.ecl.EclModule;
import org.eclipse.epsilon.ecl.trace.Match;
import org.eclipse.epsilon.ecl.trace.MatchTrace;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.models.IModel;
import org.sidiff.candidates.ICandidates;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.common.emf.access.Scope;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.common.io.IOUtil;
import org.sidiff.common.io.ResourceUtil;
import org.sidiff.correspondences.CorrespondencesUtil;
import org.sidiff.correspondences.ICorrespondences;
import org.sidiff.matcher.IMatcher;
import org.sidiff.matcher.mode.MatcherMode;

/**
 * Basically a NamedElement Matcher, special handling of Links, DataLayer and
 * HypertextLayer (see Epsilon configuration file Comparison.ecl).
 * 
 * @author kehrer
 */
public class SWMLMatcher implements IMatcher {

	public static final String KEY = "Simplewebmodel";

	private File eclConfigFile = null;
	
	private Resource modelA;
	
	private Resource modelB;
	
	private ICorrespondences correspondences;

	/**
	 * Initialize matcher and start matching.
	 */
	public SWMLMatcher() {
		super();

		// Load the ECL config file
		ResourceUtil.registerClassLoader(this.getClass().getClassLoader());
		InputStream eclIs = IOUtil
				.getInputStream("platform:/plugin/org.sidiff.simplewebmodel.matcher/config/Comparison.ecl");
		Scanner sc = new Scanner(eclIs, "UTF-8");
		String eclString = sc.useDelimiter("\\A").next();
		try {
			sc.close();
			eclIs.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Store contents as tmp file
		String tmpFile = System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + "Comparison.ecl";
		PrintWriter out = null;
		try {
			out = new PrintWriter(tmpFile);
			out.println(eclString);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// Use the tmp file as Epsilon config file
		eclConfigFile = new File(tmpFile);
		
		correspondences = CorrespondencesUtil.getDefaultCorrespondencesService();
		
	}


	@Override
	public String getName() {
		return "SWML Matcher (configured using Epsilon ECL)";
	}

	@Override
	public String getKey() {
		return KEY;
	}


	@Override
	public String getDocumentType() {
		return SimpleWebModelingLanguagePackage.eNS_URI;
	}


	@Override
	public boolean isResourceSetCapable() {
		return false;
	}
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getServiceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void startMatching(Collection<Resource> models, Scope scope) {
		
		
		Iterator<Resource> modelsIt = models.iterator();
		modelA = modelsIt.next();
		modelB = modelsIt.next();
		
		// Init ECL module
		EclModule module = new EclModule();
		try {
			module.parse(eclConfigFile);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (module.getParseProblems().size() > 0) {
			System.err.println("Parse errors occured...");
			for (ParseProblem problem : module.getParseProblems()) {
				System.err.println(problem.toString());
			}
			return;
		}

		try {
			for (IModel model : getModels(modelA, modelB)) {
				module.getContext().getModelRepository().addModel(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Perform matching
		MatchTrace result = null;
		try {
			result = (MatchTrace) module.execute();
		} catch (EolRuntimeException e) {
			e.printStackTrace();
		}

		Resource resA = ((EObject)module.getContext().getModelRepository().getModels().get(0).allContents().iterator().next()).eResource();
		Resource resB = ((EObject)module.getContext().getModelRepository().getModels().get(1).allContents().iterator().next()).eResource();
		List<Resource> resources = new LinkedList<Resource>();
		resources.add(resA);
		resources.add(resB);
		correspondences.init(resources);
		for (Match match : result.getMatches()) {
			if (match.isMatching() && (match.getRule() != null)) {
				
				EObject elementA = (EObject) match.getLeft();
				EObject elementB = (EObject) match.getRight();
				
				correspondences.addCorrespondence(elementA, elementB);

				System.out.println("(" + match.getRule().getName() + ")" + elementA + " <-> " + elementB);
			}
		}

		// Dispose module
		// module.getContext().getModelRepository().dispose();
	}

	@Override
	public MatcherMode getMode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMode(MatcherMode mode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean canHandle(Collection<Resource> models) {
		Iterator<Resource> modelIt = models.iterator();
		
		String docTypeA = EMFModelAccess.getCharacteristicDocumentType(modelIt.next());
		String docTypeB = EMFModelAccess.getCharacteristicDocumentType(modelIt.next());

		return docTypeA.equals(SimpleWebModelingLanguagePackage.eNS_URI)
				&& docTypeB.equals(SimpleWebModelingLanguagePackage.eNS_URI);
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCorrespondencesService(ICorrespondences correspondencesService) {
		this.correspondences = correspondencesService;
		
	}

	@Override
	public ICorrespondences getCorrespondencesService() {
		return correspondences;
	}

	@Override
	public void setCandidatesService(ICandidates candidatesService) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ICandidates getCandidatesService() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Resource> getModels() {
		// TODO Auto-generated method stub
		return null;
	}
	

	// ========================================= Epsilon-specific helper methods

	private List<IModel> getModels(Resource modelA, Resource modelB) throws Exception {
		// Register Package and Factory (only needed in standalone mode)
		new SimpleWebModelingLanguageStandaloneSetupGenerated().createInjectorAndDoEMFRegistration();

		List<IModel> models = new ArrayList<IModel>();
		models.add(createEmfModelByURI("Left", modelA, SimpleWebModelingLanguagePackage.eNS_URI, true, true));
		models.add(createEmfModelByURI("Right", modelB, SimpleWebModelingLanguagePackage.eNS_URI, true, true));

		return models;
	}

	private EmfModel createEmfModelByURI(String name, Resource model, String metamodel, boolean readOnLoad,
			boolean storeOnDisposal) throws EolModelLoadingException, URISyntaxException {

		File fRelModel = EMFStorage.uriToFile(model.getURI());
		File fAbsModel = new File(EMFStorage.fileToPath(fRelModel));

		EmfModel emfModel = new EmfModel();
		StringProperties properties = new StringProperties();
		properties.put(EmfModel.PROPERTY_NAME, name);
		properties.put(EmfModel.PROPERTY_METAMODEL_URI, metamodel);
		properties.put(EmfModel.PROPERTY_MODEL_URI, fAbsModel.toURI().toString());
		properties.put(EmfModel.PROPERTY_READONLOAD, readOnLoad + "");
		properties.put(EmfModel.PROPERTY_STOREONDISPOSAL, storeOnDisposal + "");
		emfModel.load(properties);
		
		return emfModel;
	}

	

}
