package org.sidiff.difference.symmetric.execute.handler;

import java.io.IOException;
import java.util.Collections;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobFunction;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.progress.UIJob;
import org.sidiff.difference.symmetric.execute.util.JavaDiscoverer;

public class DiscoverJavaProjectHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		Job compareJavaProjects = UIJob.create("Compare Java-Projects", new IJobFunction() {

			@Override
			public IStatus run(IProgressMonitor monitor) {
				ISelection currentSelection = HandlerUtil.getCurrentSelection(event);
				
				if ((currentSelection != null) && (currentSelection instanceof StructuredSelection)) {
					Object selectionA = ((StructuredSelection)currentSelection).toArray()[0];
					Object selectionB = ((StructuredSelection)currentSelection).toArray()[1];
					
					if ((selectionA instanceof IJavaProject) && (selectionB instanceof IJavaProject)) {
						Resource javaModelA = JavaDiscoverer.getJavaProjectModel((IJavaProject) selectionA, monitor);
						Resource javaModelB = JavaDiscoverer.getJavaProjectModel((IJavaProject) selectionB, monitor);
						
						// Save models:
						IPath locationB = ((IJavaProject)selectionB).getProject().getLocation()
								.makeRelativeTo(ResourcesPlugin.getWorkspace().getRoot().getLocation());
						IPath locationA = ((IJavaProject)selectionA).getProject().getLocation()
								.makeRelativeTo(ResourcesPlugin.getWorkspace().getRoot().getLocation());
						
						javaModelA.setURI(URI.createPlatformResourceURI(locationB.toString(), true)
								.appendSegment(locationB.toString()).appendFileExtension("javaxmi"));
						javaModelB.setURI(URI.createPlatformResourceURI(locationB.toString(), true)
								.appendSegment(locationA.toString()).appendFileExtension("javaxmi"));
						
						try {
							javaModelA.save(Collections.EMPTY_MAP);
							javaModelB.save(Collections.EMPTY_MAP);
						} catch (IOException e) {
							e.printStackTrace();
						}
						
						return Status.OK_STATUS;
					}
				}
				
				return Status.CANCEL_STATUS;
			}
		});

		compareJavaProjects.schedule();

		return null;
	}
	
}
