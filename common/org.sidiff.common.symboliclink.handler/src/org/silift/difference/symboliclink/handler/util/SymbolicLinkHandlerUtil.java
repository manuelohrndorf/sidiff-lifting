package org.silift.difference.symboliclink.handler.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.model.symboliclink.SymbolicLinks;
import org.silift.difference.symboliclink.handler.ISymbolicLinkHandler;

/**
 * Util class for serializing {@link SymbolicLinks} and gathering {@link ISymbolicLinkHandler}.
 * 
 * @author cpietsch
 *
 */
public class SymbolicLinkHandlerUtil {

	/**
	 * Symbolic links file extension.
	 */
	public static final String SYMBOLIC_LINKS_EXT = "symbl";
	
	/**
	 * Returns all available symbolic link handlers for the document type of the given models.
	 * @param models
	 * @return
	 */
	public static Set<ISymbolicLinkHandler> getAvailableSymbolicLinkHandlers(Collection<Resource> models) {
		Set<ISymbolicLinkHandler> handlers = new HashSet<ISymbolicLinkHandler>();
		
		for(ISymbolicLinkHandler handler : getAllAvailableSymbolicLinkHandlers()){
			if(handler.canHandle(models)){
				handlers.add(handler);
			}
		}
		
		return handlers;
	}
	
	/**
	 * Returns the default symbolic link handler for the document type of the given models.
	 * (not finished yet)
	 * 
	 * @param models
	 * @return
	 */
	public static ISymbolicLinkHandler getDefaultSymbolicLinkHandler(Collection<Resource> models){
		Set<ISymbolicLinkHandler> handlers = getAvailableSymbolicLinkHandlers(models);
		
		ISymbolicLinkHandler handler = null;
		for(ISymbolicLinkHandler iSymbolicLinkHandler : handlers){
			//TODO
		}
		
		if(handler == null){
			handler = getGenericSymbolicLinkHandler();
		}
		return handler;
	}
	
	/**
	 * Returns a generic symbolic link handler.
	 * (not finished yet)
	 * 
	 * @return
	 */
	public static ISymbolicLinkHandler getGenericSymbolicLinkHandler(){
		return null; //TODO new GenericSymbolicLinkHandler;
	}
	
	/**
	 * Gathers all available handlers which implements the {@link ISymbolicLinkHandler}
	 * interface from the registry.
	 * 
	 * @return a set of {@link ISymbolicLinkHandler}
	 */
	private static Set<ISymbolicLinkHandler> getAllAvailableSymbolicLinkHandlers(){
		Set<ISymbolicLinkHandler> handlers = new HashSet<ISymbolicLinkHandler>();

		for (IConfigurationElement configurationElement : Platform.getExtensionRegistry().getConfigurationElementsFor(
				ISymbolicLinkHandler.extensionPointID)) {
			try {
				ISymbolicLinkHandler linkHandlerExtension = (ISymbolicLinkHandler) configurationElement.createExecutableExtension("symbolic_link_handler");
				handlers.add(linkHandlerExtension);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return handlers;
	}
	
	/**
	 * Serializes the symbolic links of an {@link SymmetricDifference}
	 * and replaces the appropriate uris of the models from
	 * the {@link SymmetricDifference} with the uris of the link files.
	 * 
	 * @param symbolicLinksSet
	 * 			an collection of {@link SymbolicLinks} objects
	 * @param symmetricDifference
	 * 			a {@link SymmetricDifference}
	 * @param path
	 * 			the absolute path of the link files (will be parsed into workspace
	 * 			relative path)
	 * 			
	 */
	public static void serializeSymbolicLinks(Collection<SymbolicLinks> symbolicLinksSet, SymmetricDifference symmetricDifference, String path){
		if (!(path.endsWith("/") || path.endsWith("\\"))) {
			path = path + "/";
		}
		char c = 'A';
		for(SymbolicLinks symbolicLinks : symbolicLinksSet){
			
			String fileName = "LinksModel" + c + "." + SYMBOLIC_LINKS_EXT;
			URI savePath = EMFStorage.pathToUri(path + fileName);
			org.sidiff.common.emf.modelstorage.EMFStorage.eSaveAs(savePath, symbolicLinks);
			if(c == 'A'){
				symmetricDifference.setUriModelA(path + fileName);
			}else if(c == 'B'){
				symmetricDifference.setUriModelB(path + fileName);
			}
			c++;
		}
	}
	
	
	/**
	 * Serializes the symbolic links of an {@link AsymmetricDifference}
	 * and replaces the appropriate uris of the models from
	 * the {@link AsymmetricDifference} with the uris of the link files.
	 * 
	 * @param symbolicLinksSet
	 * 			an collection of {@link SymbolicLinks} objects
	 * @param asymmetricDifference
	 * 			an {@link AsymmetricDifference}
	 * @param path
	 * 			the absolute path of the link files (will be parsed into workspace
	 * 			relative path)
	 * 			
	 */
	public static void serializeSymbolicLinks(Collection<SymbolicLinks> symbolicLinksSet, AsymmetricDifference asymmetricDifference, String path){
		if (!(path.endsWith("/") || path.endsWith("\\"))) {
			path = path + "/";
		}
		char c = 'A';
		for(SymbolicLinks symbolicLinks : symbolicLinksSet){
			
			String fileName = "LinksModel" + c + "." + SYMBOLIC_LINKS_EXT;
			URI savePath = EMFStorage.pathToUri(path + fileName);
			EMFStorage.eSaveAs(savePath, symbolicLinks, true);
			String relativeSavePath = EMFStorage.pathToRelativeUri(path, path+fileName).toString();
			if(c == 'A'){
				asymmetricDifference.getSymmetricDifference().setUriModelA(relativeSavePath);
				asymmetricDifference.setUriOriginModel(relativeSavePath);
			}else if(c == 'B'){
				asymmetricDifference.getSymmetricDifference().setUriModelB(relativeSavePath);
				asymmetricDifference.setUriChangedModel(relativeSavePath);
			}
			c++;
		}
	}
}
