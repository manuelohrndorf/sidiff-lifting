package org.sidiff.patching.patch.patch;

import java.io.FileNotFoundException;
import java.util.Collection;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Module;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.common.file.FileOperations;
import org.sidiff.common.file.ZipUtil;
import org.sidiff.common.henshin.INamingConventions;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.asymmetric.facade.AsymmetricDiffFacade;
import org.sidiff.difference.lifting.facade.LiftingFacade;
import org.sidiff.difference.lifting.settings.LiftingSettings;
import org.sidiff.difference.rulebase.EditRule;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.integration.editor.access.IntegrationEditorAccess;
import org.sidiff.integration.editor.extension.IEditorIntegration;
import org.silift.difference.symboliclink.SymbolicLinks;
import org.silift.difference.symboliclink.handler.ISymbolicLinkHandler;
import org.silift.difference.symboliclink.handler.util.SymbolicLinkHandlerUtil;

public class PatchCreator {

	public static final String FOLDER_EDIT_RULES = "EditRules";
	public static final String FOLDER_MODEL_A = "modelA";
	public static final String FOLDER_MODEL_B = "modelB";

	private Patch patch;
	private LiftingSettings settings;
	private Collection<SymbolicLinks> symbolicLinksSet;
	private Resource resourceA;
	private Resource resourceB;
	private final URI modelAOriginalUri;
	private final URI modelBOriginalUri;
	
	private final IEditorIntegration domainEditorA, domainEditorB;
	private final boolean domainEditorAsupportsDiagram,
			domainEditorBsupportsDiagram;

	private AsymmetricDifference asymmetricDifference;
	private SymmetricDifference symmetricDifference;

	private String separator;
	private String savePath;

	private String filename;

	private String resourceA_name;
	private String resourceB_name;

	private String relativeResASavePath;
	private String relativeResBSavePath;

	private String symmetricDiff_name;
	private String asymmetricDiff_name;

	public PatchCreator(AsymmetricDifference asymmetricDifference,
			LiftingSettings settings) {
		this.patch = PatchFactory.eINSTANCE.createPatch();
		this.settings = settings;
		this.resourceA = asymmetricDifference.getOriginModel();
		modelAOriginalUri = resourceA.getURI();
		this.resourceB = asymmetricDifference.getChangedModel();
		modelBOriginalUri = resourceB.getURI();
		domainEditorA = IntegrationEditorAccess.getInstance()
				.getIntegrationEditorForModel(resourceA);
		domainEditorAsupportsDiagram = domainEditorA
				.supportsDiagramming(resourceA);
		domainEditorB = IntegrationEditorAccess.getInstance()
				.getIntegrationEditorForModel(resourceB);
		domainEditorBsupportsDiagram = domainEditorB
				.supportsDiagramming(resourceB);

		separator = System.getProperty("file.separator");

		this.symmetricDifference = asymmetricDifference
				.getSymmetricDifference();

		this.asymmetricDifference = asymmetricDifference;
		this.asymmetricDifference.initTransientRulebase();
	}

	public String serializePatch(String path, String filename)
			throws FileNotFoundException {

		this.filename = filename;
		return serializePatch(path);
	}

	public String serializePatch(String path) throws FileNotFoundException {

		resourceA_name = resourceA.getURI().lastSegment();
		resourceB_name = resourceB.getURI().lastSegment();

		String s_path = path;
		if (!s_path.endsWith(separator)) {
			s_path += separator;
		}
		if (filename == null) {
			savePath = s_path + "PATCH(origin_" + resourceA_name + "_to_"
					+ "modified_" + resourceB_name + ")";
		} else {
			savePath = s_path + filename;
		}

		if (!settings.useSymbolicLinks()) {
			String modelADir = savePath + separator + FOLDER_MODEL_A;
			String modelBDir = savePath + separator + FOLDER_MODEL_B;

			String resASavePath = modelADir + separator + resourceA_name;
			String resBSavePath = modelBDir + separator + resourceB_name;

			LogUtil.log(LogEvent.NOTICE, "serialize " + resourceA_name + " to "
					+ resASavePath);
			EMFStorage.eSaveAs(EMFStorage.pathToUri(resASavePath), resourceA
					.getContents().get(0), true);

			if (domainEditorAsupportsDiagram) {
				try {
					domainEditorA.copyDiagram(modelAOriginalUri, modelADir);
				} catch (FileNotFoundException e) {
					LogUtil.log(LogEvent.MESSAGE, "Diagram was not copied: "
							+ e.getMessage());
				}
			}

			LogUtil.log(LogEvent.NOTICE, "serialize " + resourceB_name + " to "
					+ resBSavePath);
			EMFStorage.eSaveAs(EMFStorage.pathToUri(resBSavePath), resourceB
					.getContents().get(0), true);

			if (domainEditorBsupportsDiagram) {
				try {
					domainEditorB.copyDiagram(modelBOriginalUri, modelBDir);
				} catch (FileNotFoundException e) {
					LogUtil.log(LogEvent.MESSAGE, "Diagram was not copied: "
							+ e.getMessage());
				}
			}

			relativeResASavePath = EMFStorage.pathToRelativeUri(savePath,
					resASavePath).toString();
			relativeResBSavePath = EMFStorage.pathToRelativeUri(savePath,
					resBSavePath).toString();

			symmetricDifference.setUriModelA(relativeResASavePath);
			symmetricDifference.setUriModelB(relativeResBSavePath);
			asymmetricDifference.setUriOriginModel(relativeResASavePath);
			asymmetricDifference.setUriChangedModel(relativeResBSavePath);
			patch.getSettings().put("matcher", settings.getMatcher().getName());
		} else {
			ISymbolicLinkHandler handler = settings.getSymbolicLinkHandler();
			symbolicLinksSet = handler.generateSymbolicLinks(
					asymmetricDifference, false);
			SymbolicLinkHandlerUtil.serializeSymbolicLinks(symbolicLinksSet,
					asymmetricDifference, savePath);
			patch.getSettings().put("symbolicLinkHandler",
					settings.getSymbolicLinkHandler().getName());
		}

		for (OperationInvocation op : asymmetricDifference
				.getOperationInvocations()) {
			EditRule editRule = EcoreUtil.copy(op.resolveEditRule());
			Module module = EcoreUtil.copy(editRule.getExecuteModule());
			String erSavePath = savePath + separator + FOLDER_EDIT_RULES
					+ separator + module.getName() + ".henshin";
			LogUtil.log(LogEvent.NOTICE, "serialize "
					+ editRule.getExecuteModule().getName() + " to "
					+ erSavePath);
			EMFStorage.eSaveAs(EMFStorage.pathToUri(erSavePath), module, true);
			editRule.setExecuteMainUnit(module
					.getUnit(INamingConventions.MAIN_UNIT));
			patch.getEditRules().add(editRule);
		}

		// copy symmetric difference
		symmetricDiff_name = resourceA_name + "_x_" + resourceB_name + "."
				+ LiftingFacade.SYMMETRIC_DIFF_EXT;
		String symmetricDiffSavePath = savePath + separator
				+ symmetricDiff_name;
		LogUtil.log(LogEvent.NOTICE, "serialize symmetric difference " + " to "
				+ symmetricDiffSavePath);
		EMFStorage.eSaveAs(EMFStorage.pathToUri(symmetricDiffSavePath),
				symmetricDifference, true);

		// copy asymmetric difference
		asymmetricDiff_name = resourceA_name + "_x_" + resourceB_name + "."
				+ AsymmetricDiffFacade.ASYMMETRIC_DIFF_EXT;
		String asymmetricDiffSavePath = savePath + separator
				+ asymmetricDiff_name;
		LogUtil.log(LogEvent.NOTICE, "serialize asymmetric difference "
				+ " to " + asymmetricDiffSavePath);
		EMFStorage.eSaveAs(EMFStorage.pathToUri(asymmetricDiffSavePath),
				asymmetricDifference, true);

		patch.setAsymmetricDifference(asymmetricDifference);
		String manifest = savePath + separator + "Manifest.MF";
		EMFStorage.eSaveAs(EMFStorage.pathToUri(manifest), patch, true);
		// zip all necessary files
		ZipUtil.zip(savePath, savePath, AsymmetricDiffFacade.PATCH_EXTENSION);
		FileOperations.removeFolder(savePath);

		// Return path of saved zip:
		return savePath + "." + AsymmetricDiffFacade.PATCH_EXTENSION;
	}

}
