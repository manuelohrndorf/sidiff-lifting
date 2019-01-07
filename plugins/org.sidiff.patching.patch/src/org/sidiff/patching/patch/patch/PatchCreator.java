package org.sidiff.patching.patch.patch;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Unit;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.common.emf.modelstorage.SiDiffResourceSet;
import org.sidiff.common.file.FileOperations;
import org.sidiff.common.file.ZipUtil;
import org.sidiff.common.henshin.INamingConventions;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.asymmetric.api.AsymmetricDiffFacade;
import org.sidiff.difference.lifting.api.LiftingFacade;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.editrule.rulebase.EditRule;
import org.sidiff.integration.editor.access.IntegrationEditorAccess;
import org.sidiff.integration.editor.extension.IEditorIntegration;
import org.sidiff.matcher.IMatcher;
import org.silift.difference.symboliclink.SymbolicLinks;
import org.silift.difference.symboliclink.handler.ISymbolicLinkHandler;
import org.silift.difference.symboliclink.handler.util.SymbolicLinkHandlerUtil;

public class PatchCreator {

	public static final String FOLDER_EDIT_RULES = "EditRules";
	public static final String FOLDER_MODEL_A = "modelA";
	public static final String FOLDER_MODEL_B = "modelB";
	public static final String PATCH_MANIFEST = "Manifest.MF";

	private Patch patch;
	private Collection<SymbolicLinks> symbolicLinksSet;
	private Resource resourceA;
	private Resource resourceB;

	private AsymmetricDifference asymmetricDifference;
	private SymmetricDifference symmetricDifference;

	private boolean useSymbolicLinks;
	private IMatcher matcher;
	private ISymbolicLinkHandler symbolicLinkHandler;
	
	private Map<Unit,Unit> mainUnitCopies;
	
	private SiDiffResourceSet resourceSet;

	public PatchCreator(AsymmetricDifference asymmetricDifference, 
			IMatcher matcher, boolean useSymbolicLinks, ISymbolicLinkHandler symbolicLinkHandler) {
		
		this.patch = PatchFactory.eINSTANCE.createPatch();
		
		this.useSymbolicLinks = useSymbolicLinks;
		this.matcher = matcher;
		this.symbolicLinkHandler = symbolicLinkHandler;
		
		this.resourceA = asymmetricDifference.getOriginModel();
		this.resourceB = asymmetricDifference.getChangedModel();

		this.symmetricDifference = asymmetricDifference.getSymmetricDifference();
		this.asymmetricDifference = asymmetricDifference;
		this.asymmetricDifference.initializeRuleBase();
		
		this.resourceSet = SiDiffResourceSet.create();
		resourceSet.getResources().add(resourceA);
		resourceSet.getResources().add(resourceB);
		if(symmetricDifference.eResource() != null) {
			resourceSet.getResources().add(symmetricDifference.eResource());
		}
		if(asymmetricDifference.eResource() != null) {
			resourceSet.getResources().add(asymmetricDifference.eResource());
		}
	}

	public URI serializePatch(URI outputDir) throws IOException {
		return serializePatch(outputDir,
				"PATCH(origin_" + resourceA.getURI().lastSegment()
				+ "_to_" + "modified_" + resourceB.getURI().lastSegment() + ")");
	}

	public URI serializePatch(URI outputDir, String filename) throws IOException {
		URI patchFolderUri = outputDir.appendSegment(filename);

		if (!useSymbolicLinks) {
			URI modelA = copyModelResources(patchFolderUri.appendSegment(FOLDER_MODEL_A), resourceA);
			URI modelB = copyModelResources(patchFolderUri.appendSegment(FOLDER_MODEL_B), resourceB);

			URI base = patchFolderUri.appendSegment(""); // append empty segment to make this a prefix URI
			symmetricDifference.setUriModelA(modelA.deresolve(base).toString());
			symmetricDifference.setUriModelB(modelB.deresolve(base).toString());
			asymmetricDifference.setUriOriginModel(modelA.deresolve(base).toString());
			asymmetricDifference.setUriChangedModel(modelB.deresolve(base).toString());
			patch.getSettings().put("matcher", matcher.getName());
		} else {
			symbolicLinksSet = symbolicLinkHandler.generateSymbolicLinks(asymmetricDifference, false);
			SymbolicLinkHandlerUtil.serializeSymbolicLinks(resourceSet, symbolicLinksSet, asymmetricDifference, patchFolderUri);
			patch.getSettings().put("symbolicLinkHandler", symbolicLinkHandler.getName());
		}

		copyEditRules(patchFolderUri);
		copySymmetricDifference(patchFolderUri);
		copyAsymmetricDifference(patchFolderUri);

		patch.setAsymmetricDifference(asymmetricDifference);
		resourceSet.saveEObjectAs(patch, patchFolderUri.appendSegment(PATCH_MANIFEST));

		// zip all necessary files
		return zipPatch(patchFolderUri);
	}

	protected URI copyModelResources(URI outputDir, Resource resource) {
		URI origModelUri = resource.getURI();
		String filename = origModelUri.lastSegment();
		URI modelUri = outputDir.appendSegment(filename);
		LogUtil.log(LogEvent.NOTICE, "Serialize " + filename + " to "+ modelUri);
		resourceSet.saveResourceAs(resource, modelUri);

		IEditorIntegration domainEditor = IntegrationEditorAccess.getInstance().getIntegrationEditorForModel(resource);
		if (domainEditor.supportsDiagramming(resource)) {
			try {
				domainEditor.copyDiagram(origModelUri, outputDir);
			} catch (FileNotFoundException e) {
				LogUtil.log(LogEvent.MESSAGE, "Diagram was not copied: " + e.getMessage(), e);
			}
		}
		return modelUri;
	}
	
	protected void copyEditRules(URI patchFolderUri) {
		mainUnitCopies = new HashMap<>();
		for (OperationInvocation op : asymmetricDifference.getOperationInvocations()) {
			EditRule copyEditRule = EcoreUtil.copy(op.resolveEditRule());
			Unit oldMainUnit = copyEditRule.getExecuteModule().getUnit(INamingConventions.MAIN_UNIT);
			Module copyModule = EcoreUtil.copy(copyEditRule.getExecuteModule());
			Unit newMainUnit = copyModule.getUnit(INamingConventions.MAIN_UNIT);
			copyEditRule.setExecuteMainUnit(newMainUnit);
			mainUnitCopies.put(oldMainUnit, newMainUnit);
			patch.getEditRules().add(copyEditRule);

			URI erSavePath = patchFolderUri.appendSegment(FOLDER_EDIT_RULES)
					.appendSegment(copyModule.getName()).appendFileExtension("henshin");
			LogUtil.log(LogEvent.NOTICE, "Serialize " + copyModule.getName() + " to " + erSavePath);
			resourceSet.saveEObjectAs(copyModule, erSavePath);
		}
	}

	protected void copySymmetricDifference(URI patchFolderUri) {
		URI symmetricDiffUri = patchFolderUri
			.appendSegment(resourceA.getURI().lastSegment() + "_x_" + resourceB.getURI().lastSegment())
			.appendFileExtension(LiftingFacade.SYMMETRIC_DIFF_EXT);
		LogUtil.log(LogEvent.NOTICE, "Serialize symmetric difference to " + symmetricDiffUri);
		resourceSet.saveEObjectAs(symmetricDifference, symmetricDiffUri);
	}

	protected void copyAsymmetricDifference(URI patchFolderUri) {
		asymmetricDifference.getRulebase().getItems().forEach(item -> {
			Unit mainUnit = mainUnitCopies.get(item.getEditRule().getExecuteMainUnit());
			item.getEditRule().setExecuteMainUnit(mainUnit);
		});

		URI asymmetricDiffUri = patchFolderUri
			.appendSegment(resourceA.getURI().lastSegment() + "_x_" + resourceB.getURI().lastSegment())
			.appendFileExtension(AsymmetricDiffFacade.ASYMMETRIC_DIFF_EXT);
		LogUtil.log(LogEvent.NOTICE, "Serialize asymmetric difference to " + asymmetricDiffUri);
		resourceSet.saveEObjectAs(asymmetricDifference, asymmetricDiffUri);
	}

	protected URI zipPatch(URI patchFolderUri) throws IOException {
		URI patchFileUri = patchFolderUri.appendFileExtension(AsymmetricDiffFacade.PATCH_EXTENSION);
		Path patchFolderPath = EMFStorage.toFile(patchFolderUri).toPath();
		ZipUtil.zip(patchFolderPath, EMFStorage.toFile(patchFileUri).toPath());
		FileOperations.removeFolder(patchFolderPath);
		return patchFileUri;
	}
}
