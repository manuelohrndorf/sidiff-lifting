package org.silift.difference.symboliclink.handler.util;

import java.util.Collection;

import org.eclipse.emf.common.util.URI;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.silift.difference.symboliclink.SymbolicLinks;

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
