
package org.sidiff.difference.rulebase.builder;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.sidiff.common.emf.EMFUtil;
import org.sidiff.difference.rulebase.EditRule;
import org.sidiff.difference.rulebase.RuleBaseItem;
import org.sidiff.difference.rulebase.wrapper.RuleBaseWrapper;
import org.sidiff.difference.rulebase.wrapper.util.Edit2RecognitionException;
import org.sidiff.editrule.validation.EditRuleValidation;
import org.sidiff.editrule.validation.EditRuleValidator;
import org.silift.common.util.emf.EMFStorage;

/**
 * This class represents a builder for an @link{EditRule} and outputs a corresponding
 * @link{RecognitionRule} by invoking the correct methods of @link{EditModule2RecognitionModule}.
 * 
 * The default folder structure is statically defined in here.
 * 
 * @author dreuling
 * 
 */
public class RuleBaseBuilder extends IncrementalProjectBuilder {
	
	public static final String BUILDER_ID = "org.sidiff.difference.rulebase.builder.rulebasebuilder";
	
	public static final String RULE_ATTRIBUTE = "rule";
	
	// Used folder structure for projects with nature @link{RuleBaseProjectNature}
	public static final String BUILD_FOLDER = "recognitionrules";
	
	public static final String SOURCE_FOLDER = "transformation";
	
	public static final String RULEBASE_FILE = "sidiff.rulebase";
	
	/**
	 * The rulebase manager of the rulebase project for which this builder is defined.
	 */
	private RuleBaseWrapper ruleBaseWrapper;
	
	protected IProject[] build(int kind, Map<String, String> args, IProgressMonitor monitor) {
		
		if (kind == IncrementalProjectBuilder.FULL_BUILD) {
			fullBuild(monitor);
		} else {
			IResourceDelta delta = getDelta(getProject());
			if (delta == null) {
				fullBuild(monitor);
			} else {
				incrementalBuild(delta, monitor);
			}
		}
		
		return null;
	}
	
	private void incrementalBuild(IResourceDelta delta, final IProgressMonitor monitor) {
		
		// Iterate through resource and children of this resource
		try {
			delta.accept(new IResourceDeltaVisitor() {
				
				public boolean visit(IResourceDelta delta) {
					
					// Continue only if Resource is an EditRule
					if (isEditRule(delta.getResource())) {
						
						// Remove Markers beforehand
						removeMarkers(delta.getResource(), IResource.DEPTH_ZERO);
						
						// If resource has been removed, try to delete old
						// generated RecognitionRule (= Built EditRule).
						if (delta.getKind() == IResourceDelta.REMOVED) {
							deleteCorrespondingRR(delta.getResource(), monitor);
						}
						
						// If resource has been changed, try to delete old
						// generated RecognitionRule (= Built EditRule) and build
						// the "new" version.
						else if (delta.getKind() == IResourceDelta.CHANGED) {
							deleteCorrespondingRR(delta.getResource(), monitor);
							buildEditRule(delta.getResource(), monitor);
						}
						
						// If resource has been added, just build
						// the EditRule.
						else if (delta.getKind() == IResourceDelta.ADDED) {
							buildEditRule(delta.getResource(), monitor);
						}
						
					}
					
					// Visit children
					return true;
					
				}
			});
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
	
	private void fullBuild(final IProgressMonitor monitor) {
		
		try {
			getProject().accept(new IResourceVisitor() {
				
				@Override
				public boolean visit(IResource resource) throws CoreException {
					
					// Continue only if Resource is an EditRule
					if (isEditRule(resource)) {
						
						buildEditRule(resource, monitor);
					}
					
					// Visit children
					return true;
				}
				
			});
		} catch (CoreException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	protected void clean(IProgressMonitor monitor) throws CoreException {
		
		removeMarkers(getProject(), IResource.DEPTH_INFINITE);

		// Clean the RuleBase completely:
		getProject().getFile(RULEBASE_FILE).delete(true, monitor);
		ruleBaseWrapper = null;
		
		// Delete all elements in Build Folder if already existent
		IFolder buildFolder = getProject().getFolder(BUILD_FOLDER);
		
		if (buildFolder.exists()) {
			for (IResource builtRR : buildFolder.members()) {
				builtRR.delete(true, monitor);
			}
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
		
		// Delete old markers
		try {
			resource.deleteMarkers(EValidator.MARKER, true, depth);
		} catch (CoreException e) {
			// Something went wrong
		}
		
	}
	
	/**
	 * Deletes an already built EditRule (=RecognitionRule) and updates the RuleBase accordingly.
	 * 
	 * This method is executed if the old generated RecognitionRule is obsolete somehow, e.g. the
	 * corresponding EditRule has been deleted.
	 * 
	 * @param editRule
	 *            Source Edit-Rule of which the RecognitionRule shall be deleted
	 * @param monitor
	 *            IProgressMonitor to use
	 */
	private void deleteCorrespondingRR(IResource editRule, IProgressMonitor monitor) {
		
		try {
			// Remove Recognition-Rule from rulebase:
			RuleBaseWrapper rbWrapper = getRuleBaseWrapper();
			EditRule editRuleWrapper = rbWrapper.findEditRule(editRule.getLocation().toString());
			
			// Maybe invalid Edit-Rule that is not in the rulebase?
			if (editRuleWrapper != null) {
				RuleBaseItem item = editRuleWrapper.getRuleBaseItem();
				rbWrapper.removeItem(item, true);
				rbWrapper.saveRuleBase();		
			}
			
			// Refresh project:
			getProject().getFolder(BUILD_FOLDER).refreshLocal(IResource.DEPTH_ONE, monitor);
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Builds an EditRule, which results in a generated
	 * 
	 * @link{RecognitionRule as well as an entry in the corresponding
	 * @link{RuleBase . This method also invokes an Validation beforehand, to check whether the
	 *                EditRule can be built at all.
	 * 
	 * @param editRule
	 *            EditRule to build
	 * @param monitor
	 *            Used IProgressMonitor
	 */
	private void buildEditRule(IResource editRule, IProgressMonitor monitor) {
		
		// Abort if canceled
		if (monitor.isCanceled()) {
			throw new OperationCanceledException();
		}
		
		// Check if build folder exists, create it otherwise
		IFolder buildFolder = getProject().getFolder(BUILD_FOLDER);
		
		if (!buildFolder.exists()) {
			try {
				buildFolder.create(true, true, monitor);
				buildFolder.setDerived(true, monitor);
			} catch (CoreException e) {
				// Something went wrong
			}
		}
		
		// Refresh EditRule
		try {
			editRule.refreshLocal(IResource.DEPTH_ZERO, null);
		} catch (CoreException e1) {
			e1.printStackTrace();
		}
		
		// Validate EditRule
		validateEditRule(editRule, monitor);
		
		try {
			// Build EditRule if no error occurred during validation
			if (editRule.findMaxProblemSeverity(EValidator.MARKER, false, IResource.DEPTH_ZERO) != IMarker.SEVERITY_ERROR) {
				
				monitor.subTask("Building Edit-Rule " + editRule.getName());
				
				// Generate Recognition-Rule:
				RuleBaseWrapper rbWrapper = getRuleBaseWrapper();
				rbWrapper.generateItemFromFile(EMFStorage.pathToUri(editRule.getLocation().toString()));
				rbWrapper.saveRuleBase();
			}
		} catch (CoreException e) {
			e.printStackTrace();
		} catch (Edit2RecognitionException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Abort if canceled
		if (monitor.isCanceled()) {
			throw new OperationCanceledException();
		}
	}
	
	/**
	 * Validates an EditRule according to the @link{EditRuleValidator}. If the given EditRule has
	 * some validation error, this will be reported as a IMarker.
	 * 
	 * @param editRule
	 *            EditRule to validate
	 * @param monitor
	 *            Used IProgressMonitor
	 * 
	 * @see{EditRuleValidation
	 */
	private void validateEditRule(IResource editRule, IProgressMonitor monitor) {
		
		monitor.subTask("Validating Edit-Rule " + editRule.getName());
		
		// Validate Edit-Rule
		List<EditRuleValidation> validations = new ArrayList<EditRuleValidation>();
		validations = EditRuleValidator.calculateEditRuleValidations(loadModule(editRule));
		
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
				
				IMarker marker = editRule.createMarker(EValidator.MARKER);
				marker.setAttribute(IMarker.MESSAGE, validation.infoMessage);
				
				int severity = IMarker.SEVERITY_ERROR;
				int priority = IMarker.PRIORITY_HIGH;
				
				// Convert Validation Severity to Marker Severity
				// and set priority accordingly
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
				
				// search for context elements
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
	 * <li>It shall be in correct folder structure, defined through static variables
	 * (@see{EditRuleBuilder})</li>
	 * </ul>
	 * 
	 * @param resource
	 *            Resource to check
	 * @return whether given Resource is classified as @link{EditRule}
	 */
	private boolean isEditRule(IResource resource) {
		
		return resource.getFileExtension() != null && resource.getFileExtension().equals("henshin")
				&& !(resource.isDerived()) && resource.getFullPath().toString().startsWith(
						"/" + getProject().getName() + "/" + SOURCE_FOLDER);
	}
	
	/**
	 * Helper method to load an module from given EditRule.
	 * 
	 * @param editRule
	 *            EditRule to load the module from
	 * @return The Henshin Module of the given EditRule
	 */
	private Module loadModule(IResource editRule) {
		IPath editRulePath = editRule.getRawLocation();
		HenshinResourceSet resourceSet = new HenshinResourceSet();
		Module editModule = resourceSet.getModule(editRulePath.toOSString());
		return editModule;
		
	}
	
	/**
	 * Returns the rulebase manager of the rulebase project for which this builder is defined.
	 * 
	 * @return The rulebase manager of this project.
	 */
	private RuleBaseWrapper getRuleBaseWrapper() {
		URI rulebase = EMFStorage.iFileToURI(getProject().getFile(RULEBASE_FILE));
		
		if ((ruleBaseWrapper == null) || !RuleBaseWrapper.exists(rulebase)) {
			URI recognitionRuleFolder = EMFStorage.iFolderToURI(getProject().getFolder(BUILD_FOLDER));
			ruleBaseWrapper =  new RuleBaseWrapper(rulebase, recognitionRuleFolder, false);
			ruleBaseWrapper.setName(getRuleBasePluginBundleName());
		} 
		
		return ruleBaseWrapper;
	}
	
    /**
     * @return The bundle name of the rulebase plug-in (inside MANIFEST.MF).
     */
    public String getRuleBasePluginBundleName() {

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
    		String pattern = ".*Bundle-Name:\\s*([\\.\\w]*);?.*"; // FIXME: Name bis zum Zeilenende...
    		Matcher matcher = Pattern.compile(pattern, Pattern.DOTALL).matcher(manifest);
    		if (matcher.matches()) {
    			editRulePluginId = matcher.group(1);
    		}
    	}
            
        return editRulePluginId;
    } 
}