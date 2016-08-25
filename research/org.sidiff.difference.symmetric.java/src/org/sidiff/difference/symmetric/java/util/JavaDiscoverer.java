package org.sidiff.difference.symmetric.java.util;

import java.io.IOException;
import java.util.Collections;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.modisco.infra.discovery.core.exception.DiscoveryException;
import org.eclipse.modisco.java.discoverer.DiscoverJavaModelFromJavaProject;

/*
 * Dependencies:
 * 
 * org.eclipse.modisco.infra.discovery.core
 * org.eclipse.modisco.java.discoverer
 * org.eclipse.gmt.modisco.java
 * org.eclipse.jdt.core
 */

/**
 * Creates an EMF-Model based on a Java-Project.
 * 
 * @author Manuel Ohrndorf
 */
public class JavaDiscoverer {

	public static Resource[] serializeJavaProjectModel(
			IJavaProject projectA, IJavaProject projectB, IProgressMonitor monitor) {
		
		Resource javaModelA = JavaDiscoverer.getJavaProjectModel(projectA, monitor);
		Resource javaModelB = JavaDiscoverer.getJavaProjectModel(projectB, monitor);
		
		// Save models:
		IPath locationB = (projectA).getProject().getLocation()
				.makeRelativeTo(ResourcesPlugin.getWorkspace().getRoot().getLocation());
		IPath locationA = (projectB).getProject().getLocation()
				.makeRelativeTo(ResourcesPlugin.getWorkspace().getRoot().getLocation());
		
		javaModelA.setURI(URI.createPlatformResourceURI(locationB.toString(), true)
				.appendSegment(locationB.toString()).appendFileExtension("javaxmi"));
		javaModelB.setURI(URI.createPlatformResourceURI(locationB.toString(), true)
				.appendSegment(locationA.toString()).appendFileExtension("javaxmi"));
		
		try {
			javaModelA.save(Collections.EMPTY_MAP);
			javaModelB.save(Collections.EMPTY_MAP);
			
			return new Resource[] {javaModelA, javaModelB};
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	public static Resource getJavaProjectModel(String projectName) {
		return getJavaProjectModel(projectName, new NullProgressMonitor());
	}
	
	public static Resource getJavaProjectModel(String projectName, IProgressMonitor monitor) {
		
		// Search the project by its name:
		IJavaProject javaProject = null;
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

		for (IProject project : root.getProjects()) {
			if (project.getName().equals(projectName)) {
				try {
					if (project.hasNature(JavaCore.NATURE_ID)) {
						javaProject = JavaCore.create(project);
					}
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
		}
		
		// Create a discoverer for a Java-Project
		return getJavaProjectModel(javaProject, monitor);
	}
	
	public static Resource getJavaProjectModel(IJavaProject javaProject, IProgressMonitor monitor) {
	
		// Create a discoverer for a Java-Project
		try {
			DiscoverJavaModelFromJavaProject javaDiscoverer = new DiscoverJavaModelFromJavaProject();
			javaDiscoverer.discoverElement(javaProject, monitor);
			return javaDiscoverer.getTargetModel();
		} catch (DiscoveryException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
