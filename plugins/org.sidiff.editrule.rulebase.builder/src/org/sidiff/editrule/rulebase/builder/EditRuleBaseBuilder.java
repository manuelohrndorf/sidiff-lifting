package org.sidiff.editrule.rulebase.builder;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.henshin.model.Annotation;
import org.eclipse.emf.henshin.model.Module;
import org.sidiff.common.emf.EMFUtil;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.common.henshin.exceptions.NoMainUnitFoundException;
import org.sidiff.editrule.consistency.validation.EditRuleValidation;
import org.sidiff.editrule.consistency.validation.EditRuleValidator;
import org.sidiff.editrule.rulebase.EditRule;
import org.sidiff.editrule.rulebase.RuleBaseItem;
import org.sidiff.editrule.rulebase.builder.attachment.EditRuleAttachmentBuilderLibrary;
import org.sidiff.editrule.rulebase.builder.attachment.IEditRuleAttachmentBuilder;
import org.sidiff.editrule.rulebase.project.runtime.library.IRuleBaseProject;
import org.sidiff.editrule.rulebase.project.runtime.storage.RuleBaseStorage;

/**
 * Builds a rulebase of edit-rules.
 * 
 * @author dreuling, mohrndorf
 */
public class EditRuleBaseBuilder extends IncrementalProjectBuilder {

	public static final String RULE_ATTRIBUTE = "rule";
	
	/**
	 * The name of the java class of the rule base project.
	 */
	public static final String RULE_BASE_CLASS =  "RuleBaseProject";
	
	/**
	 * The name of the java class file of the rule base project.
	 */
	public static final String RULE_BASE_CLASS_FILE = RULE_BASE_CLASS + ".java";

	/**
	 * The global boolean to decide if in the last change there has been some EditRule involved.
	 */
	private boolean editRuleChanged = false;
	
	@Override
	protected IProject[] build(int kind, Map<String, String> args, IProgressMonitor monitor) {

		// The rulebase manager of the rulebase project for which this builder is defined:
		EditRuleBaseWrapper ruleBaseWrapper = createEditRuleBaseWrapper();
	
		// The edit-rule attachment builders of the rulebase project for which this builder is defined:
		Set<IEditRuleAttachmentBuilder> attachmentBuilders = createEditRuleAttachmentBuilders();
		
		if (kind == IncrementalProjectBuilder.FULL_BUILD) {
			fullBuild(monitor, ruleBaseWrapper, attachmentBuilders);
		} else {
			IResourceDelta delta = getDelta(getProject());
			
			if (delta == null) {
				fullBuild(monitor, ruleBaseWrapper, attachmentBuilders);
			} else {
				incrementalBuild(delta, monitor, ruleBaseWrapper, attachmentBuilders);
			}
		}
		
		// Run the garbage collector: 
		System.gc();
		
		return null;
	}
	
	private void buildRuleBaseProject(IProgressMonitor monitor, 
			EditRuleBaseWrapper ruleBaseWrapper, Set<IEditRuleAttachmentBuilder> attachmentBuilders) {
		
		if (editRuleChanged) {
			buildRuleBase(monitor, ruleBaseWrapper);
			addInverseEditRules(monitor, ruleBaseWrapper);
			buildRuleBaseClass(monitor, ruleBaseWrapper, attachmentBuilders);

			editRuleChanged = false;
		}
	}
	
	private void refreshProject() {
		// Refresh project:
		try {
			getProject().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
		} catch (CoreException e2) {
			// nothing
		}
	}

	private void incrementalBuild(IResourceDelta delta, final IProgressMonitor monitor, 
			final EditRuleBaseWrapper ruleBaseWrapper, final Set<IEditRuleAttachmentBuilder> attachmentBuilders) {

		// Initialize:
		refreshProject();
		editRuleChanged = false;
		
		// Iterate through resource and children of this resource
		try {
			delta.accept(new IResourceDeltaVisitor() {

				@Override
				public boolean visit(IResourceDelta delta) {

					// Continue only if Resource is an EditRule
					if (isEditRule(delta.getResource())) {

						editRuleChanged = true;

						// Remove Markers beforehand
						removeMarkers(delta.getResource(), IResource.DEPTH_ZERO);

						// If resource has been removed, try to delete old
						// Co-Rules (= Built EditRule).
						if (delta.getKind() == IResourceDelta.REMOVED) {
							deleteEditRule(delta.getResource(), monitor, ruleBaseWrapper, attachmentBuilders);
						}

						// If resource has been changed, try to delete old
						// Co-Rules (= Built EditRule) and build the "new" version.
						else if (delta.getKind() == IResourceDelta.CHANGED) {
							deleteEditRule(delta.getResource(), monitor, ruleBaseWrapper, attachmentBuilders);
							buildEditRule(delta.getResource(), monitor, ruleBaseWrapper, attachmentBuilders);
						}

						// If resource has been added, just build the EditRule.
						else if (delta.getKind() == IResourceDelta.ADDED) {
							buildEditRule(delta.getResource(), monitor, ruleBaseWrapper, attachmentBuilders);
						}

					}

					// Visit children
					return true;

				}
			});
		} catch (CoreException e) {
			e.printStackTrace();
		}
		
		// Update RuleBase accordingly
		buildRuleBaseProject(monitor, ruleBaseWrapper, attachmentBuilders);
		refreshProject();
	}

	private void fullBuild(final IProgressMonitor monitor, 
			final EditRuleBaseWrapper ruleBaseWrapper, final Set<IEditRuleAttachmentBuilder> attachmentBuilders) {
		
		// Initialize:
		refreshProject();
		
		// Clean up before, to be sure to "regenerate" rulebase
		try {
			internalClean(monitor, attachmentBuilders);
		} catch (CoreException e1) {
			e1.printStackTrace();
		}

		try {
			getProject().accept(new IResourceVisitor() {

				@Override
				public boolean visit(IResource resource) throws CoreException {

					// Continue only if Resource is an EditRule
					if (isEditRule(resource)) {
						editRuleChanged = true;
						buildEditRule(resource, monitor, ruleBaseWrapper, attachmentBuilders);
					}

					// Visit children
					return true;
				}

			});
		} catch (CoreException e) {
			e.printStackTrace();
		}

		// Update RuleBase accordingly
		buildRuleBaseProject(monitor, ruleBaseWrapper, attachmentBuilders);
		refreshProject();
	}
	
	private void buildRuleBaseClass(IProgressMonitor monitor, 
			EditRuleBaseWrapper ruleBaseWrapper, Set<IEditRuleAttachmentBuilder> attachmentBuilders) {
		
		if (monitor.isCanceled()) {
			throw new OperationCanceledException();
		}
		
		// The class builders of the rulebase project for which this builder is defined.
		EditRuleBaseClassBuilder classBuilder = new EditRuleBaseClassBuilder();
		
		// Get attachment type IDs:
		Set<String> attachmentTypes = new LinkedHashSet<String>();
		
		for (IEditRuleAttachmentBuilder attachmentBuilder: attachmentBuilders) {
			attachmentTypes.add(attachmentBuilder.getID());
		}
		
		// Write class file:
		String classFile = classBuilder.writeProjectClass(
				getRuleBaseClassFileJavaPackage(), 
				ruleBaseWrapper.getName(), 
				new LinkedHashSet<String>(ruleBaseWrapper.getRuleBase().getDocumentTypes()), 
				attachmentTypes);
		classBuilder.saveProjectClass(classFile, getRuleBaseClassFilePath());
	}
	
	private String getRuleBaseClassFileJavaPackage() {
		return getProject().getFullPath().toString().replaceAll("/", "");
	}
	
	private File getRuleBaseClassFilePath() {
		String packagePath = getProject().getLocation() + "/" + "src" + "/" + getRuleBaseClassFileJavaPackage().replaceAll("\\.", "/") + "/";
		return new File(packagePath + RULE_BASE_CLASS_FILE);
	}

	@Override
	protected void clean(IProgressMonitor monitor) throws CoreException {
		internalClean(monitor, createEditRuleAttachmentBuilders());
	}
	
	private void internalClean(IProgressMonitor monitor, 
			Set<IEditRuleAttachmentBuilder> attachmentBuilders) throws CoreException {

		// Remove Markers
		removeMarkers(getProject(), IResource.DEPTH_INFINITE);

		// Delete all co-rules
		for (IEditRuleAttachmentBuilder attachmentBuilder : attachmentBuilders) {
			attachmentBuilder.cleanAttachments(monitor, getProject());
		}

		// Delete Rule Base Files:
		IFile ruleBaseFile = getProject().getFile(IRuleBaseProject.RULEBASE_FILE);
		
		if (ruleBaseFile.exists()) {
			ruleBaseFile.delete(true, monitor);
		}
		
		// Remove the class file:
		File classFile = getRuleBaseClassFilePath();
		
		if (classFile.exists()) {
			classFile.delete();
		}
	}

	/**
	 * Cleans all markers from given Resource and its children according two depth parameter.
	 * 
	 * @param resource
	 *            Resource to clean of markers
	 * @param depth
	 *            Depth of recursion
	 */
	private void removeMarkers(IResource resource, int depth) {

		try {
			// Delete old markers
			resource.deleteMarkers(EValidator.MARKER, true, depth);
			
			// Also delete project marker
			getProject().deleteMarkers(IMarker.PROBLEM, false, IResource.DEPTH_ZERO);

		} catch (CoreException e) {
			// Something went wrong
		}
	}

	/**
	 * Deletes an already built EditRule and updates the rulebase accordingly.
	 * 
	 * @param editRule
	 *            The Edit-Rule which shall be deleted
	 * @param monitor
	 *            IProgressMonitor to use
	 */
	private void deleteEditRule(IResource editRule, IProgressMonitor monitor,
			EditRuleBaseWrapper ruleBaseWrapper, Set<IEditRuleAttachmentBuilder> attachmentBuilders) {

		try {
			// Remove Edit-Rule from rulebase:
			EditRule editRuleWrapper = ruleBaseWrapper.findEditRule(editRule.getLocation().toString());

			// Maybe invalid Edit-Rule that is not in the rulebase?
			if (editRuleWrapper != null) {
				RuleBaseItem item = editRuleWrapper.getRuleBaseItem();
				ruleBaseWrapper.removeItem(item);
				ruleBaseWrapper.saveRuleBase();
				
				// Remove Co-Rules:
				for (IEditRuleAttachmentBuilder attachmentBuilder : attachmentBuilders) {
					attachmentBuilder.deleteAttachment(monitor, getProject(), item);
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Builds an EditRule and its corresponding Co-Rules.
	 * 
	 * @param editRule
	 *            EditRule to build
	 * @param monitor
	 *            Used IProgressMonitor
	 */
	private void buildEditRule(IResource editRuleResource, IProgressMonitor monitor,
			EditRuleBaseWrapper ruleBaseWrapper, Set<IEditRuleAttachmentBuilder> attachmentBuilders) {

		// Abort if canceled
		if (monitor.isCanceled()) {
			throw new OperationCanceledException();
		}

		try {
			
			// Load the edit-rule:
			Module editRule = RuleBaseStorage.loadHenshinModule(editRuleResource);
			
			// Validate EditRule
			validateEditRule(editRuleResource, editRule, monitor);
			
			// Build EditRule if no error occurred during validation
			if (editRuleResource.findMaxProblemSeverity(EValidator.MARKER, false,
					IResource.DEPTH_ZERO) != IMarker.SEVERITY_ERROR) {

				monitor.subTask("Building Edit-Rule " + editRuleResource.getFullPath());

				// Build edit-rule:
				EditRule editRuleWrapper = ruleBaseWrapper.createEditRule(editRule);
				RuleBaseItem rulebaseItem = ruleBaseWrapper.createItem(editRuleWrapper);
				
				ruleBaseWrapper.addItem(rulebaseItem);
				
				// Build attachments:
				for (IEditRuleAttachmentBuilder attachmentBuilder : attachmentBuilders) {
					attachmentBuilder.buildAttachment(monitor, getProject(), rulebaseItem);
				}
				
				// Compress item:
				rulebaseItem.shrink();
			}
		} catch (CoreException e) {
			e.printStackTrace();
		} catch (NoMainUnitFoundException e) {
			// Checked by the edit-rule validation!
			e.printStackTrace();
		}

		// Abort if canceled
		if (monitor.isCanceled()) {
			throw new OperationCanceledException();
		}
	}

	/**
	 * Validates an EditRule according to the @link{EditRuleValidator}. If the
	 * given EditRule has some validation error, this will be reported as a IMarker.
	 * 
	 * @param editRule
	 *            EditRule to validate
	 * @param monitor
	 *            Used IProgressMonitor
	 * 
	 * @see{EditRuleValidation
	 */
	private void validateEditRule(IResource editRuleResource, Module editRule, IProgressMonitor monitor) {

		monitor.subTask("Validating Edit-Rule " + editRuleResource.getFullPath());

		// Validate Edit-Rule
		List<EditRuleValidation> validations = new ArrayList<EditRuleValidation>();
		validations = EditRuleValidator.calculateEditRuleValidations(editRule);

		try {
			// Create Markers according to validations
			for (EditRuleValidation validation : validations) {

				EObject locationObject = (EObject) validation.violatings.get(validation.violatings.size() - 1);

				String locationName = " ";

				EStructuralFeature nameFeature = locationObject.eClass().getEStructuralFeature("name");

				if (nameFeature != null) {
					if (locationObject.eGet(nameFeature) != null) {
						locationName += locationObject.eGet(nameFeature).toString();
					}
				}

				// Read all necessary attributes
				String elementURI = EMFUtil.getEObjectURI(locationObject);
				String elementID = elementURI.substring(elementURI.lastIndexOf("#"));
				URI platformResourceURI = EMFStorage.uriToPlatformUri(URI.createURI(elementURI));
				String attribute = platformResourceURI.toString() + elementID;

				IMarker marker = editRuleResource.createMarker(EValidator.MARKER);
				marker.setAttribute(IMarker.MESSAGE, validation.infoMessage);

				int severity = IMarker.SEVERITY_ERROR;
				int priority = IMarker.PRIORITY_HIGH;

				// Convert Validation Severity to Marker Severity and set priority accordingly
				switch (validation.severity) {
				case Diagnostic.ERROR:
					severity = IMarker.SEVERITY_ERROR;
					priority = IMarker.PRIORITY_HIGH;
					break;
				case Diagnostic.WARNING:
					severity = IMarker.SEVERITY_WARNING;
					priority = IMarker.PRIORITY_NORMAL;
					break;
				case Diagnostic.INFO:
					severity = IMarker.SEVERITY_INFO;
					priority = IMarker.PRIORITY_LOW;
					break;
				}

				marker.setAttribute(IMarker.SEVERITY, severity);
				marker.setAttribute(IMarker.PRIORITY, priority);
				marker.setAttribute(IMarker.LOCATION, "Henshin " + locationObject.eClass().getName() + locationName);
				marker.setAttribute(EValidator.URI_ATTRIBUTE, attribute);
				marker.setAttribute(RULE_ATTRIBUTE, validation.validationType.toString());

				// Search for context elements
				if (validation.violatings.size() > 1) {
					String relatedAttributes = "";
					for (int i = 0; i < validation.violatings.size(); i++) {
						locationObject = (EObject) validation.violatings.get(i);

						String relatedElementURI = EMFUtil.getEObjectURI(locationObject);
						String relatedElementID = relatedElementURI.substring(relatedElementURI.lastIndexOf("#"));
						URI relatedPlatformResourceURI = EMFStorage.uriToPlatformUri(URI.createURI(relatedElementURI));
						String relatedAttribute = relatedPlatformResourceURI.toString() + relatedElementID;
						relatedAttributes += relatedAttribute + " ";
					}
					marker.setAttribute(EValidator.RELATED_URIS_ATTRIBUTE, relatedAttributes);
				}
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Helper method to check whether the given method is an EditRule:
	 * <ul>
	 * <li>It has to have the right extension</li>
	 * <li>It shall not be marked as <em>derived</em></li>
	 * <li>It shall be in correct folder structure, defined through static
	 * variables (@see{EditRuleBuilder})</li>
	 * </ul>
	 * 
	 * @param resource
	 *            Resource to check
	 * @return whether given Resource is classified as @link{EditRule}
	 */
	private boolean isEditRule(IResource resource) {

		return resource.getFileExtension() != null && resource.getFileExtension().equals("henshin")
				&& !(resource.isDerived()) && resource.getFullPath().toString()
						.startsWith("/" + getProject().getName() + "/" + IRuleBaseProject.EDIT_RULE_FOLDER);
	}

	/**
	 * @return The rulebase manager of this project.
	 */
	private EditRuleBaseWrapper createEditRuleBaseWrapper() {

		IFile ruleBaseFile = getProject().getFile(IRuleBaseProject.RULEBASE_FILE);
		URI rulebase = EMFStorage.iFileToURI(ruleBaseFile);

		EditRuleBaseWrapper ruleBaseWrapper = new EditRuleBaseWrapper(rulebase, false);
		ruleBaseWrapper.setName(getRuleBasePluginBundleName());

		return ruleBaseWrapper;
	}
	
	/**
	 * @return The edit-rule attachment builder of this project.
	 */
	private Set<IEditRuleAttachmentBuilder> createEditRuleAttachmentBuilders() {
		return EditRuleAttachmentBuilderLibrary.getAttachmentBuilders();
	}

	/**
	 * @return The bundle name of the rulebase plug-in (inside MANIFEST.MF).
	 */
	public String getRuleBasePluginBundleName() {

		// Refresh first
		try {
			getProject().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
		} catch (CoreException e2) {
			// nothing
		}

		String editRulePluginId = "";
		IResource resource = getProject().findMember(new Path("META-INF").append("MANIFEST.MF"));
		if (resource != null) {

			StringBuffer contents = new StringBuffer();
			try {
				BufferedInputStream in = new BufferedInputStream(((IFile) resource).getContents());
				int c = 0;
				
				while ((c = in.read()) != -1) {
					contents.append((char) c);
				}
			} catch (CoreException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			String manifest = contents.toString();
			String pattern = "(?sm).*Bundle-Name:\\s*(\\p{Print}*)$.*";
			Matcher matcher = Pattern.compile(pattern, Pattern.DOTALL).matcher(manifest);
			
			if (matcher.matches()) {
				editRulePluginId = matcher.group(1);
			}
		}

		return editRulePluginId;
	}

	/**
	 * Method for building (=saving) the RuleBase file.
	 * 
	 * @param monitor
	 *            IProgressMonitor to use
	 */
	private void buildRuleBase(IProgressMonitor monitor, EditRuleBaseWrapper ruleBaseWrapper) {

		// Abort if canceled
		if (monitor.isCanceled()) {
			throw new OperationCanceledException();
		}

		IFile rulebaseFile = getProject().getFile(IRuleBaseProject.RULEBASE_FILE);

		monitor.subTask("Building RuleBase File " + rulebaseFile.getFullPath());
		try {
			ruleBaseWrapper.saveRuleBase();

			// Mark RuleBase as derived, if not already
			if (!rulebaseFile.isDerived()) {
				rulebaseFile.setDerived(true, monitor);
			}

			getProject().deleteMarkers(IMarker.PROBLEM, false, IResource.DEPTH_ZERO);

		} catch (CoreException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Abort if canceled
		if (monitor.isCanceled()) {
			throw new OperationCanceledException();
		}

	}

	private void addInverseEditRules(IProgressMonitor monitor, EditRuleBaseWrapper ruleBaseWrapper) {

		for (RuleBaseItem rule : ruleBaseWrapper.getRuleBase().getItems()) {
			if (rule.getEditRule().getInverse() == null) {
				for (Annotation a : rule.getEditRule().getExecuteModule().getAnnotations()) {
					if (a.getKey().equals("INVERSE")) {
						String ruleName = a.getValue();
						for (RuleBaseItem invRule : ruleBaseWrapper.getRuleBase().getItems()) {
							if (invRule.getEditRule().getExecuteModule().getName().equals(ruleName)) {
								rule.getEditRule().setInverse(invRule.getEditRule());
								// invRule.getEditRule().setInverse(rule.getEditRule());
								break;
							}
						}
					}
				}
			}
			
			if (monitor.isCanceled()) {
				throw new OperationCanceledException();
			}
		}
	}
}