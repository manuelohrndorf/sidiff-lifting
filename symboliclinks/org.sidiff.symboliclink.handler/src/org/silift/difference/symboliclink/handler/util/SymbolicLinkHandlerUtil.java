package org.silift.difference.symboliclink.handler.util;

import java.util.Collection;

import org.eclipse.emf.common.util.URI;
import org.sidiff.common.emf.modelstorage.SiDiffResourceSet;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.silift.difference.symboliclink.SymbolicLinks;

/**
 * Util class for serializing {@link SymbolicLinks}.
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
	 * @param linkFilesFolder URI of folder containing the link files
	 * 			
	 */
	public static void serializeSymbolicLinks(SiDiffResourceSet resourceSet, Collection<SymbolicLinks> symbolicLinksSet,
			SymmetricDifference symmetricDifference, URI linkFilesFolder) {
		char c = 'A';
		for(SymbolicLinks symbolicLinks : symbolicLinksSet){
			String fileName = "LinksModel" + c + "." + SYMBOLIC_LINKS_EXT;
			resourceSet.saveEObjectAs(symbolicLinks, linkFilesFolder.appendSegment(fileName));
			if(c == 'A') {
				symmetricDifference.setUriModelA(fileName);
			} else if(c == 'B') {
				symmetricDifference.setUriModelB(fileName);
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
	 * @param linkFilesFolder URI of folder containing the link files
	 * 			
	 */
	public static void serializeSymbolicLinks(SiDiffResourceSet resourceSet, Collection<SymbolicLinks> symbolicLinksSet,
			AsymmetricDifference asymmetricDifference, URI linkFilesFolder) {
		char c = 'A';
		for(SymbolicLinks symbolicLinks : symbolicLinksSet) {
			String fileName = "LinksModel" + c + "." + SYMBOLIC_LINKS_EXT;
			resourceSet.saveEObjectAs(symbolicLinks, linkFilesFolder.appendSegment(fileName));
			if(c == 'A') {
				asymmetricDifference.getSymmetricDifference().setUriModelA(fileName);
				asymmetricDifference.setUriOriginModel(fileName);
			} else if(c == 'B') {
				asymmetricDifference.getSymmetricDifference().setUriModelB(fileName);
				asymmetricDifference.setUriChangedModel(fileName);
			}
			c++;
		}
	}
}
