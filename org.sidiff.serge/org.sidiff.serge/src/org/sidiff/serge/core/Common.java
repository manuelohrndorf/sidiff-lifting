package org.sidiff.serge.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx;
import org.sidiff.serge.exceptions.EClassUnresolvableException;
import org.sidiff.serge.exceptions.EPackageNotFoundException;
import org.w3c.dom.NamedNodeMap;

public class Common {

	/**
	 * Converts the first letter of a string to upper case
	 * @param s
	 * @return
	 */
	public static String toCamelCase(String s) {
		if(Character.isLetter(s.charAt(0))) {
			char c = s.charAt(0);			
			return s.replaceFirst(String.valueOf(c), String.valueOf(c).toUpperCase());
		}else{
			return s;
		}
	}
	
	public static void replaceNewsWithToBeDeleted(Module module) {
		
		for(Rule r: HenshinRuleAnalysisUtilEx.getRulesUnderModule(module)) {
			for(Node n: r.getLhs().getNodes()) {
				String nN = n.getName();
				if(nN!=null && nN.equals("New")) {
					n.setName("toBeDeleted");
				}
			}
			for(Node n: r.getRhs().getNodes()) {
				if(n.getName().equals("New")) {
					n.setName("toBeDeleted");
				}
			}
		}
		
	}

	public static String getAttributeValue(String attribName, org.w3c.dom.Node node) {
			
		NamedNodeMap attribs = node.getAttributes(); 
		for(int i=0; i<attribs.getLength(); i++) {
			if(attribs.item(i).getNodeName().equals(attribName)) {
				return attribs.item(i).getNodeValue();
			}
		}

		return null;
	}
	
	/**
	 * This method converts an unresolved EObject / EProxyURI into a correct file path.
	 * e.g. "org.something.de@1234 (eProxyURI: platform:/plugin/org.something.de/model/my.ecore#//someclassifier"
	 * into "C:\myworkspace\org.something.de\model\my.ecore".
	 * The workspace location needs to be given in as an argument (retrievable via run configuration variable).
	 * @param eProxyURIString
	 * @param workspace_loc
	 * @return
	 */
	public static String convertEProxyURIToFilePath(String eProxyURIString, String workspace_loc) {
		
		//convert eProxyURI into correct plugin-path inside workspace
		String proxyUri = eProxyURIString;
		proxyUri = proxyUri.replaceFirst("[\\w\\.@\\s\\(:]*[plugin/]+/", ""); //org...(eProxyURI: platform:/plugin/)
		proxyUri = proxyUri.replaceFirst("#[\\/]{2}[\\w\\d]*[\\)]$", ""); 	  // #//classifier)						
		String FILE_SEPERATOR = System.getProperty("file.separator");
		if(!FILE_SEPERATOR.equals("/")) {
			proxyUri = proxyUri.replace("/", "\\");
		}
		
		return workspace_loc + FILE_SEPERATOR + proxyUri;
	}
	
	/**
	 * This method recursively finds all sub EPackages of an EPackage.
	 * @param ePackage
	 * @return
	 * @throws EPackageNotFoundException 
	 */
	public static List<EPackage> getAllSubEPackages(EPackage ePackage) throws EPackageNotFoundException {
		
		if(ePackage == null) {
			throw new EPackageNotFoundException();
		}
		
		ArrayList<EPackage> list = new ArrayList<EPackage>();
		
		for(EPackage sub: ePackage.getESubpackages()) {
			if(!list.contains(sub)) {
				// add current sub package
				list.add(sub);
				// recursively add sub of sub packages...
				List<EPackage> subsOfSub = getAllSubEPackages(sub);
				subsOfSub.removeAll(list);
				list.addAll(subsOfSub);
			}
		}		
		
		return list;
	}
	
	public static EClass resolveStringAsEClass(String eClassName, Stack<EPackage> ePackagesStack) throws EClassUnresolvableException{
		EClass resolvedEClass = null;
		
		for(EPackage ePackage: ePackagesStack) {				
			resolvedEClass = (EClass) ePackage.getEClassifier(eClassName);
			if(resolvedEClass!=null) {
				return resolvedEClass;
			}
		}
		throw new EClassUnresolvableException(eClassName);
	}
}
