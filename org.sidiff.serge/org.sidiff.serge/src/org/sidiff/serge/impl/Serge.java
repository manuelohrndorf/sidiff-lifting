package org.sidiff.serge.impl;

import java.util.Stack;

import org.eclipse.emf.ecore.EPackage;
import org.sidiff.common.emf.ecore.ECoreTraversal;
import org.sidiff.serge.core.Configuration;
import org.sidiff.serge.core.ConfigurationParser;
import org.sidiff.serge.core.MetaModelElementVisitor;
import org.sidiff.serge.exceptions.EPackageNotFoundException;

public class Serge {

	private static Stack<EPackage> ePackagesStack = null;
	
	/**
	 * Initial setup for SERGe.
	 * 
	 * @param pathToConfig
	 */
	public void init(String pathToConfig) {
		
		try {
			ConfigurationParser.parse(pathToConfig);
			ePackagesStack = Configuration.getInstance().getEPackagesStack();
		
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
	}
	
	/**
	 * Method to start the generation process.
	 */
	public void generate() throws EPackageNotFoundException {

		if(!ePackagesStack.isEmpty()){
			MetaModelElementVisitor eClassVisitor = new MetaModelElementVisitor();
			ECoreTraversal.traverse(eClassVisitor, ePackagesStack.toArray(new EPackage[ePackagesStack.size()]));				
		}else{
			throw new EPackageNotFoundException();
		}

	}

}
