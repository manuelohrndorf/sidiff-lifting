package org.sidiff.difference.symmetric.execute.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobFunction;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.progress.UIJob;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.symmetric.execute.util.JavaDiscoverer;
import org.sidiff.difference.symmetric.execute.util.SiLiftUtil;
import org.sidiff.difference.symmetric.execute.util.UIUtil;
import org.sidiff.difference.symmetric.execute.util.SiLiftUtil.DifferenceCalculationException;
import org.sidiff.difference.technical.api.util.TechnicalDifferenceUtils;
import org.sidiff.matcher.adapter.emfcompare.EMFCompareMatcherAdapter;

public class DiscoverJavaProjectHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		Job compareJavaProjects = UIJob.create("Compare Java-Projects", new IJobFunction() {

			@Override
			public IStatus run(IProgressMonitor monitor) {
				ISelection currentSelection = HandlerUtil.getCurrentSelection(event);
				
				if ((currentSelection != null) && (currentSelection instanceof StructuredSelection) 
						&& (((StructuredSelection)currentSelection).toArray().length == 2)) {
					
					Object selectionA = ((StructuredSelection)currentSelection).toArray()[0];
					Object selectionB = ((StructuredSelection)currentSelection).toArray()[1];
					
					if ((selectionA instanceof IJavaProject) && (selectionB instanceof IJavaProject)) {
						
						// Discover MoDisco (EMF) Java-Model from Java-Project:
						Resource[] javaProjectModels = JavaDiscoverer.serializeJavaProjectModel(
								(IJavaProject) selectionA, (IJavaProject) selectionB, monitor);
						
						// Calculate the difference between the Java-Projects:
						try {
							SymmetricDifference difference = SiLiftUtil.calculateDifference(
									javaProjectModels[0], javaProjectModels[1], 
									TechnicalDifferenceUtils.getMatcherByKey(EMFCompareMatcherAdapter.class.getName()));
							
							SiLiftUtil.saveDifference(difference);
							
							UIUtil.showMessage("Difference Saved: \n\n" + difference.eResource().getURI());
						} catch (DifferenceCalculationException e) {
							UIUtil.showMessage(e.getMessage());
							e.printStackTrace();
						}
						
						return Status.OK_STATUS;
					}
				}
				
				UIUtil.showMessage("Please select 2 java projects!");
				return Status.CANCEL_STATUS;
			}
		});

		compareJavaProjects.schedule();

		return null;
	}
	
}
