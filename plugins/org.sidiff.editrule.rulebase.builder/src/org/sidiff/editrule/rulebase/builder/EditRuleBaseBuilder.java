package org.sidiff.editrule.rulebase.builder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Annotation;
import org.eclipse.emf.henshin.model.Module;
import org.sidiff.common.emf.EMFUtil;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.common.exceptions.ExceptionUtil;
import org.sidiff.common.henshin.exceptions.NoMainUnitFoundException;
import org.sidiff.common.io.IOUtil;
import org.sidiff.editrule.consistency.validation.EditRuleValidation;
import org.sidiff.editrule.consistency.validation.EditRuleValidator;
import org.sidiff.editrule.rulebase.EditRule;
import org.sidiff.editrule.rulebase.RuleBaseItem;
import org.sidiff.editrule.rulebase.builder.attachment.IEditRuleAttachmentBuilder;
import org.sidiff.editrule.rulebase.builder.internal.Activator;
import org.sidiff.editrule.rulebase.project.runtime.library.IRuleBaseProject;
import org.sidiff.editrule.rulebase.project.runtime.storage.RuleBaseStorage;

/**
 * Builds a rulebase of edit-rules.
 * 
 * @author dreuling
 * @author mohrndorf
 * @author rmueller
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
	
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static final Pattern MANIFEST_BUNDLE_NAME_PATTERN = Pattern.compile("(\\A|\\r|\\n)+Bundle-Name: (.+)(\\Z|\\r|\\n)+");
	
	/**
	 * The global boolean to decide if in the last change there has been some EditRule involved.
	 */
	private boolean editRuleChanged = false;
	
	/**
	 * The class builders of the rulebase project for which this builder is defined.
	 */
	private EditRuleBaseClassBuilder classBuilder;

	@Override
	protected void startupOnInitialize() {
		super.startupOnInitialize();
		classBuilder = new EditRuleBaseClassBuilder(getProject());
	}

	@Override
	protected IProject[] build(int kind, Map<String, String> args, IProgressMonitor monitor) throws CoreException {

		// Unload runtime rulebases:
		IRuleBaseProject.MANAGER.clearRuleBaseCache();
		
		// The rulebase manager of the rulebase project for which this builder is defined:
		EditRuleBaseWrapper ruleBaseWrapper = createEditRuleBaseWrapper();
	
		// The edit-rule attachment builders of the rulebase project for which this builder is defined:
		Set<IEditRuleAttachmentBuilder> attachmentBuilders = new HashSet<>(createEditRuleAttachmentBuilders());
		
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
			EditRuleBaseWrapper ruleBaseWrapper, Set<IEditRuleAttachmentBuilder> attachmentBuilders) throws CoreException {
		SubMonitor progress = SubMonitor.convert(monitor, 10);
		if (editRuleChanged) {
			addInverseEditRules(progress.split(2), ruleBaseWrapper);
			buildRuleBase(progress.split(7), ruleBaseWrapper);
			buildRuleBaseClass(progress.split(1), ruleBaseWrapper, attachmentBuilders);

			editRuleChanged = false;
		}
	}
	
	private void refreshProject(IProgressMonitor monitor) {
		// Refresh project:
		try {
			getProject().refreshLocal(IResource.DEPTH_INFINITE, monitor);
		} catch (CoreException e2) {
			// nothing
		}
	}

	private void incrementalBuild(IResourceDelta delta, final IProgressMonitor monitor, 
			final EditRuleBaseWrapper ruleBaseWrapper, final Set<IEditRuleAttachmentBuilder> attachmentBuilders) throws CoreException {

		SubMonitor progress = SubMonitor.convert(monitor, 100);
		
		// Initialize:
		refreshProject(progress.split(10));
		editRuleChanged = false;
		
		// Iterate through resource and children of this resource
		delta.accept(d -> {
			handleResourceDelta(d, progress.split(1), ruleBaseWrapper, attachmentBuilders);
			// Visit children
			return true;
		});
		
		// Update RuleBase accordingly
		buildRuleBaseProject(progress.split(50), ruleBaseWrapper, attachmentBuilders);
		refreshProject(progress.split(10));
	}
	
	private void handleResourceDelta(final IResourceDelta delta, final IProgressMonitor monitor, 
			final EditRuleBaseWrapper ruleBaseWrapper, final Set<IEditRuleAttachmentBuilder> attachmentBuilders) throws CoreException {
		
		// Continue only if Resource is an EditRule
		if (isEditRule(delta.getResource())) {
			editRuleChanged = true;

			// Remove Markers beforehand
			removeMarkers(delta.getResource(), IResource.DEPTH_ZERO);
			
			switch(delta.getKind()) {
				case IResourceDelta.REMOVED:
					// If resource has been removed, try to delete old
					// Co-Rules (= Built EditRule).
					deleteEditRule(delta.getResource(), monitor, ruleBaseWrapper, attachmentBuilders);
					break;
				
				case IResourceDelta.CHANGED:
					// If resource has been changed, try to delete old
					// Co-Rules (= Built EditRule) and build the "new" version.
					SubMonitor progress = SubMonitor.convert(monitor, 2);
					deleteEditRule(delta.getResource(), progress.split(1), ruleBaseWrapper, attachmentBuilders);
					buildEditRule(delta.getResource(), progress.split(1), ruleBaseWrapper, attachmentBuilders);
					break;
				
				case IResourceDelta.ADDED:
					// If resource has been added, just build the EditRule.
					buildEditRule(delta.getResource(), monitor, ruleBaseWrapper, attachmentBuilders);
					break;
			};
		}
	}

	private void fullBuild(final IProgressMonitor monitor, 
			final EditRuleBaseWrapper ruleBaseWrapper, final Set<IEditRuleAttachmentBuilder> attachmentBuilders) throws CoreException {
		
		SubMonitor progress = SubMonitor.convert(monitor, 100);
		
		// Initialize:
		refreshProject(progress.split(10));
		
		// Clean up before, to be sure to "regenerate" rulebase
		internalClean(progress.split(1), attachmentBuilders);

		getProject().accept(resource -> {
			// Continue only if Resource is an EditRule
			if (isEditRule(resource)) {
				editRuleChanged = true;
				buildEditRule(resource, progress.split(1), ruleBaseWrapper, attachmentBuilders);
			}

			// Visit children
			return true;
		});

		// Update RuleBase accordingly
		buildRuleBaseProject(progress.split(50), ruleBaseWrapper, attachmentBuilders);
		refreshProject(progress.split(10));
	}
	
	private void buildRuleBaseClass(IProgressMonitor monitor, 
			EditRuleBaseWrapper ruleBaseWrapper, Set<IEditRuleAttachmentBuilder> attachmentBuilders) throws CoreException {

		try {
			// Write class file:
			classBuilder.generateClassFile(
				ruleBaseWrapper.getKey(),
				ruleBaseWrapper.getName(),
				new Date(), // build date is now
				new LinkedHashSet<String>(ruleBaseWrapper.getRuleBase().getDocumentTypes()), 
				attachmentBuilders.stream().map(builder -> builder.getKey()).collect(Collectors.toSet()),
				monitor);
		} catch (IOException e) {
			throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Failed to generate project class file", e));
		}
	}

	@Override
	protected void clean(IProgressMonitor monitor) throws CoreException {
		internalClean(monitor, createEditRuleAttachmentBuilders());
	}
	
	private void internalClean(IProgressMonitor monitor, 
			Collection<IEditRuleAttachmentBuilder> attachmentBuilders) throws CoreException {
		SubMonitor progress = SubMonitor.convert(monitor, attachmentBuilders.size()+4);

		// Unload runtime rulebases:
		IRuleBaseProject.MANAGER.clearRuleBaseCache();
		progress.worked(1);
		
		// Remove Markers
		removeMarkers(getProject(), IResource.DEPTH_INFINITE);
		progress.worked(1);

		// Delete all co-rules
		for (IEditRuleAttachmentBuilder attachmentBuilder : attachmentBuilders) {
			attachmentBuilder.cleanAttachments(progress.split(1), getProject());
		}

		// Delete Rule Base Files:
		IFile ruleBaseFile = getProject().getFile(IRuleBaseProject.RULEBASE_FILE);
		if (ruleBaseFile.exists()) {
			ruleBaseFile.delete(true, progress.split(1));
		}
		
		// Remove the class file:
		classBuilder.deleteClassFile(progress.split(1));
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
			EditRuleBaseWrapper ruleBaseWrapper, Set<IEditRuleAttachmentBuilder> attachmentBuilders) throws CoreException {

		// Remove Edit-Rule from rulebase:
		EditRule editRuleWrapper = ruleBaseWrapper.findEditRule(editRule.getLocation().toString());

		// Maybe invalid Edit-Rule that is not in the rulebase?
		if (editRuleWrapper != null) {
			SubMonitor progress = SubMonitor.convert(monitor, attachmentBuilders.size()+1);
			RuleBaseItem item = editRuleWrapper.getRuleBaseItem();
			ruleBaseWrapper.removeItem(item);
			try {
				ruleBaseWrapper.saveRuleBase();
			} catch (IOException e) {
				throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Failed to save rulebase", e));
			}
			progress.worked(1);
			
			// Remove Co-Rules:
			for (IEditRuleAttachmentBuilder attachmentBuilder : attachmentBuilders) {
				attachmentBuilder.deleteAttachment(progress.split(1), getProject(), item);
			}
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
			EditRuleBaseWrapper ruleBaseWrapper, Set<IEditRuleAttachmentBuilder> attachmentBuilders) throws CoreException {

		SubMonitor progress = SubMonitor.convert(monitor, 2);
		
		// Load the edit-rule:
		Module editRule = RuleBaseStorage.loadHenshinModule(editRuleResource);
		
		// Validate EditRule
		validateEditRule(editRuleResource, editRule, progress.split(1));
		
		// Build EditRule if no error occurred during validation
		if (editRuleResource.findMaxProblemSeverity(EValidator.MARKER, false,
				IResource.DEPTH_ZERO) != IMarker.SEVERITY_ERROR) {

			SubMonitor subProgress = SubMonitor.convert(progress,
					"Building Edit-Rule " + editRuleResource.getFullPath(),
					attachmentBuilders.size()+2);

			// Build edit-rule:
			EditRule editRuleWrapper = null;
			try {
				editRuleWrapper = ruleBaseWrapper.createEditRule(editRule);
			} catch (NoMainUnitFoundException e) {
				// Checked by the edit-rule validation!
				throw new AssertionError(e);
			}
			RuleBaseItem rulebaseItem = ruleBaseWrapper.createItem(editRuleWrapper);
			ruleBaseWrapper.addItem(rulebaseItem);
			subProgress.worked(1);
			
			// Build attachments:
			for (IEditRuleAttachmentBuilder attachmentBuilder : attachmentBuilders) {
				attachmentBuilder.buildAttachment(subProgress.split(1), getProject(), rulebaseItem);
			}
			
			// Compress item:
			rulebaseItem.shrink();
			subProgress.worked(1);
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
	private void validateEditRule(IResource editRuleResource, Module editRule, IProgressMonitor monitor) throws CoreException {

		// Validate Edit-Rule
		List<EditRuleValidation> validations = EditRuleValidator.calculateEditRuleValidations(editRule);
		SubMonitor progress = SubMonitor.convert(monitor, "Validating Edit-Rule " + editRuleResource.getFullPath(), validations.size());

		// Create Markers according to validations
		for (EditRuleValidation validation : validations) {

			EObject locationObject = (EObject) validation.violatings.get(validation.violatings.size() - 1);

			// Read all necessary attributes
			URI elementURI = EcoreUtil.getURI(locationObject);
			URI platformResourceURI = EMFStorage.uriToPlatformUri(elementURI).appendFragment(elementURI.fragment());

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
			String locationObjectName = EMFUtil.getEObjectName(locationObject);
			marker.setAttribute(IMarker.LOCATION,
					"Henshin " + locationObject.eClass().getName()
					+ (locationObjectName == null ? "" : locationObjectName));
			marker.setAttribute(EValidator.URI_ATTRIBUTE, platformResourceURI.toString());
			marker.setAttribute(RULE_ATTRIBUTE, validation.validationType.toString());

			// Search for context elements
			if (validation.violatings.size() > 1) {
				String relatedAttributes = validation.violatings.stream()
					.map(EObject.class::cast)
					.map(EcoreUtil::getURI)
					.map(uri -> EMFStorage.uriToPlatformUri(uri).appendFragment(uri.fragment()))
					.map(Object::toString)
					.collect(Collectors.joining(" "));
				marker.setAttribute(EValidator.RELATED_URIS_ATTRIBUTE, relatedAttributes);
			}
			progress.worked(1);
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
	private EditRuleBaseWrapper createEditRuleBaseWrapper() throws CoreException {
		IFile ruleBaseFile = getProject().getFile(IRuleBaseProject.RULEBASE_FILE);
		URI rulebase = EMFStorage.iResourceToURI(ruleBaseFile);
		EditRuleBaseWrapper ruleBaseWrapper = new EditRuleBaseWrapper(rulebase, false);
		ruleBaseWrapper.setKey(getProject().getName());
		ruleBaseWrapper.setName(readManifestBundleName() + " (" + DATE_FORMAT.format(new Date()) + ")");
		return ruleBaseWrapper;
	}

	private String readManifestBundleName() throws CoreException {
		try(InputStream manifest = getProject().getFolder("META-INF").getFile("MANIFEST.MF").getContents()) {
			Matcher matcher = MANIFEST_BUNDLE_NAME_PATTERN.matcher(IOUtil.toString(manifest, StandardCharsets.UTF_8));
			if(matcher.find()) {
				return matcher.group(2);
			}
			throw new IllegalStateException("The manifest of the project does not specify a Bundle-Name");
		} catch (IOException e) {
			throw ExceptionUtil.asCoreException(e);
		}
	}

	/**
	 * @return The edit-rule attachment builder of this project.
	 */
	private Collection<IEditRuleAttachmentBuilder> createEditRuleAttachmentBuilders() {
		return IEditRuleAttachmentBuilder.MANAGER.getExtensions();
	}

	/**
	 * Method for building (=saving) the RuleBase file.
	 * 
	 * @param monitor
	 *            IProgressMonitor to use
	 */
	private void buildRuleBase(IProgressMonitor monitor, EditRuleBaseWrapper ruleBaseWrapper) throws CoreException {
		IFile rulebaseFile = getProject().getFile(IRuleBaseProject.RULEBASE_FILE);
		SubMonitor progress = SubMonitor.convert(monitor, "Building RuleBase File " + rulebaseFile.getFullPath(), 10);

		try {
			ruleBaseWrapper.saveRuleBase();
			progress.worked(8);

			// Mark RuleBase as derived, if not already
			if (!rulebaseFile.isDerived()) {
				rulebaseFile.setDerived(true, progress.split(1));
			}

			getProject().deleteMarkers(IMarker.PROBLEM, false, IResource.DEPTH_ZERO);
			progress.worked(1);

		} catch (IOException e) {
			throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Failed to save rulebase", e));
		}
	}

	private void addInverseEditRules(IProgressMonitor monitor, EditRuleBaseWrapper ruleBaseWrapper) {
		SubMonitor progress = SubMonitor.convert(monitor, ruleBaseWrapper.getRuleBase().getItems().size());
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
			progress.worked(1);
		}
	}
}