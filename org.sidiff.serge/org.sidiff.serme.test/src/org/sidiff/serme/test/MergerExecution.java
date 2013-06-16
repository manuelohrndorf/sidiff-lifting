package org.sidiff.serme.test;


import org.eclipse.emf.henshin.model.impl.HenshinPackageImpl;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.papyrus.sysml.internal.impl.SysmlPackageImpl;
import org.sidiff.serme.MergeEngine;

public class MergerExecution implements IApplication {
	
	private boolean editRule = true;
	private String folder = "testrules/";
	//private String henshinFilename = folder + "CREATE_CLASS_IN_PACKAGE_execute.henshin";
	private String henshinFilename = folder + "APPLY_STEREOTYPE_BLOCK.henshin";
	private String savePath = folder + "merged";


	@Override
	public Object start(IApplicationContext context) throws Exception {
		
		System.out.println("SeqMerger starting...");
		String[] args = (String[]) context.getArguments().get(IApplicationContext.APPLICATION_ARGS);		
		henshinFilename = args[0];
		savePath = args[1];
		run();
			
		return IApplication.EXIT_OK;
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}
	
	public void run() {		
		
		HenshinPackageImpl.init();	
		SysmlPackageImpl.init();
	
		MergeEngine mergeEngine = new MergeEngine(henshinFilename);
		HenshinResourceSet resourceSet = new HenshinResourceSet();
		
		try {
			mergeEngine.startEngine(resourceSet);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		MergerExecution mergeExec = new MergerExecution();
		mergeExec.run();
	}
	
}