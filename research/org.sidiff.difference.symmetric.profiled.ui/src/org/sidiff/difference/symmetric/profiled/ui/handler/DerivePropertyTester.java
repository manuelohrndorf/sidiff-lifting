package org.sidiff.difference.symmetric.profiled.ui.handler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IFile;
import org.sidiff.difference.lifting.facade.LiftingFacade;
import org.sidiff.difference.symmetric.SymmetricPackage;

public class DerivePropertyTester extends PropertyTester {

	private static final String FILE_TEST = String.format(".*\"%s\".*",
			SymmetricPackage.eNS_URI);

	@Override
	public boolean test(Object receiver, String property, Object[] args,
			Object expectedValue) {
		if (receiver instanceof IFile) {
			IFile file = (IFile) receiver;
			String filePath = file.getLocation().toOSString();
			if (filePath.endsWith(LiftingFacade.SYMMETRIC_DIFF_EXT)) {
				BufferedReader reader = null;
				try {
					reader = new BufferedReader(new FileReader(new File(
							filePath)));
					reader.readLine();
					if (reader.readLine().matches(FILE_TEST)) {
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
		return false;
	}

}
