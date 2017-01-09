package org.sidiff.difference.lifting.ui.handler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.common.emf.access.Scope;
import org.sidiff.difference.lifting.api.LiftingFacade;
import org.sidiff.difference.lifting.api.util.PipelineUtils;
import org.sidiff.difference.symmetric.SymmetricPackage;
import org.sidiff.difference.technical.util.TechnicalDifferenceBuilderUtil;


public class LiftingPropertyTester extends PropertyTester {
	
	private static final String DIFFERENCE = "difference";
	private static final String MODEL_FILE = "modelFile";
	
	private static final String FILE_TEST = String.format(".*\"%s\".*", SymmetricPackage.eNS_URI);

	public LiftingPropertyTester() {

	}

	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if(property.equals(DIFFERENCE)){
			if(receiver instanceof IFile){
				IFile file = (IFile) receiver;
				String filePath = file.getLocation().toOSString();
				if(filePath.endsWith(LiftingFacade.SYMMETRIC_DIFF_EXT)){
					BufferedReader reader = null;
					try {
						reader = new BufferedReader(new FileReader(new File(filePath)));
						reader.readLine();
						if(reader.readLine().matches(FILE_TEST)){
							return true;
						}
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						try {
							if (reader != null) {
								reader.close();
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		} else if(property.equals(MODEL_FILE)){

			if(receiver instanceof IFile){
				IFile file = (IFile) receiver;
				Resource resource = PipelineUtils.loadModel(file.getLocation().toOSString());
				Set<String> documentType =EMFModelAccess.getDocumentTypes(resource, Scope.RESOURCE_SET);
				if(TechnicalDifferenceBuilderUtil.getAvailableTechnicalDifferenceBuilders(documentType).size()>0)
					return true;				
			}
		}

		return false;
	}

}
