package org.sidiff.patching.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Factory;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.asymmetric.DependencyContainer;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.silift.common.util.exceptions.FileAlreadyExistsException;
import org.silift.common.util.file.ZipUtil;

public class PatchUtil {	
	
	/**
	 * The patch file extension.
	 */
	// TODO[MO@26.10.13]: Move to patching facade.
	public static final String PATCH_EXTENSION = "slp";
	
	private static int stage = 0;
	
	/**
	 * Sorts OperationInvocations in a processable order and returns a flat list.
	 * 
	 * @return sorted list of OperationInvocation
	 */
	public static List<OperationInvocation> getOrderdOperationInvocations(EList<OperationInvocation> unorderdOperationInvocations) {
		List<OperationInvocation> operationInvocations = new ArrayList<OperationInvocation>();
		operationInvocations = sortDFS(unorderdOperationInvocations);
		return Collections.unmodifiableList(operationInvocations);
	}

	/**
	 * Sorts the OperationInvocations in dependency order using the depth-first search algorithm
	 * 
	 * @param unorderdOperationInvocations
	 */
	private static List<OperationInvocation> sortDFS(List<OperationInvocation> unorderdOperationInvocations) {
		List<OperationInvocation> operationInvocations = new ArrayList<OperationInvocation>();
		for (OperationInvocation operationInvocation : unorderdOperationInvocations) {
			if (operationInvocation.getOutgoing().isEmpty()) {
				addIncomingOperations(operationInvocations, operationInvocation);
			}
		}
		return operationInvocations;
	}

	private static void addIncomingOperations(List<OperationInvocation> operationInvocations, OperationInvocation invocation) {
		if (!operationInvocations.contains(invocation)) {
			stage++;
			for (OperationInvocation operationInvocation : getAllIncoming(invocation.getIncoming())) {
				addIncomingOperations(operationInvocations, operationInvocation);
			}
			operationInvocations.add(0, invocation);
			LogUtil.log(LogEvent.DEBUG, "Stage: " + stage + " " + invocation.getChangeSet().getName());
			stage--;
		}
	}


	/**
	 * Checks all following OperationInvocations and applies the same execution
	 * state
	 * 
	 * @param invocation
	 * @param apply
	 */
	public static void ensureDependency(OperationInvocation invocation, boolean apply) {
//		setAllPreciding(invocation, apply);
		invocation.setApply(apply);
		setAllFollowing(invocation, apply);
	}

//	private static void setAllPreciding (OperationInvocation invocation, boolean apply) {
//		for (OperationInvocation outgoingInvocation: getAllOutgoing(invocation.getOutgoing())) {
//			outgoingInvocation.setApply(apply);
//			setAllPreciding(outgoingInvocation, apply);
//		}
//	}

//	private static List<OperationInvocation> getAllOutgoing (EList<Dependency> incoming) {
//		List<OperationInvocation> result = new ArrayList<OperationInvocation>();
//		for (Dependency dependency : incoming) {
//			result.add(dependency.getTarget());
//		}
//		return result;
//	}

	private static void setAllFollowing(OperationInvocation invocation, boolean apply) {
		for (OperationInvocation incomingInvocation: getAllIncoming(invocation.getIncoming())) {
			incomingInvocation.setApply(apply);
			setAllFollowing(incomingInvocation, apply);
		}
	}
	
	private static List<OperationInvocation> getAllIncoming(
			EList<DependencyContainer> incoming) {
		List<OperationInvocation> result = new ArrayList<OperationInvocation>();
		
		for (DependencyContainer dependency : incoming) {			
			result.add(dependency.getSource());
			
		}
		return result;
	}

	public static Resource copyWithId(Resource from, URI uri, boolean withId, Copier copier) {
		copier.copyAll(from.getContents());
		copier.copyReferences();

		Factory factory = from.getResourceSet().getResourceFactoryRegistry().getFactory(from.getURI());

		ResourceSet rs = new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put(uri.fileExtension(), factory);
		Resource to = rs.createResource(uri);
		for (int i = 0; i < from.getContents().size(); i++) {
			to.getContents().add(copier.get(from.getContents().get(i)));
		}

		if (from instanceof XMIResource) {
			XMIResource xmlFrom = (XMIResource) from;
			for (EObject orignal : copier.keySet()) {
				EObject copy = copier.get(orignal);
				String id = withId ? xmlFrom.getID(orignal) : null;
				((XMIResource) to).setID(copy, id);
			}
		}
		return to;
	}
	
	public static URI createURI(URI uri, String suffix) {
		URI base = uri.trimSegments(1);
		String name = uri.trimFileExtension().lastSegment();
		String newFile = name + "_" + suffix + "." + uri.fileExtension();
		return base.appendSegment(newFile);
	}
	
	/**
	 * Uncompress the patch (if necessary).
	 * 
	 * @param path
	 *            The system path of the compressed patch.
	 * @return The folder of the uncompressed patch.
	 */
	public static File extractPatch(IPath path) {
		String compressedPath = path.toOSString();
		File uncompressedPath = new File(compressedPath
				.substring(0, compressedPath.length() - (PATCH_EXTENSION.length() + 1)));

		if (!uncompressedPath.exists()) {

			// TODO[MO@26.10.13]: Calculate MD5 hash of the patch and store it
			// in the extracted patch to check if the data is up-to-date.

			try {
				ZipUtil.extractFiles(
						compressedPath,
						uncompressedPath.getAbsolutePath(), "",
						true);
			} catch (FileAlreadyExistsException e) {
				e.printStackTrace();
			}
		}
		return uncompressedPath;
	}

	/**
	 * Checks the file extension.
	 * 
	 * @param path
	 *            The path of the patch file.
	 * @return <code>true</code> if is a patch file; <code>false</code> otherwise.
	 */
	public static boolean isPatchFile(IPath path) {
		return (path.getFileExtension().equalsIgnoreCase(PATCH_EXTENSION));
	}
}
