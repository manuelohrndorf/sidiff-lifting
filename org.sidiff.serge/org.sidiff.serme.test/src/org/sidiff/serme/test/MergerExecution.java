package org.sidiff.serme.test;

import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.sidiff.serme.MergeEngine;

public class MergerExecution implements IApplication {

	
	private String folder = "testrules/";
	private String henshinFilename = "APPLY_STEREOTYPE_BLOCK.henshin";
	private String sourcePath = folder + "source/" + henshinFilename;
	private String savePath = folder + "target/" + henshinFilename ;

	@Override
	public Object start(IApplicationContext context) throws Exception {

		System.out.println("SeqMerger starting...");
		String[] args = (String[]) context.getArguments().get(
				IApplicationContext.APPLICATION_ARGS);
		sourcePath = args[0];
		savePath = args[1];
		run();

		return IApplication.EXIT_OK;
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

	public void run() {
			
		 MergeEngine mergeEngine = new MergeEngine(sourcePath);
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