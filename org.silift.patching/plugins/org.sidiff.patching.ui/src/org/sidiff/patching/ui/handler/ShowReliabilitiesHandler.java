package org.sidiff.patching.ui.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.State;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.PropertySheet;
import org.eclipse.ui.views.properties.tabbed.ISection;
import org.eclipse.ui.views.properties.tabbed.ITabDescriptor;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.sidiff.patching.ui.view.InputParameterSection;
import org.sidiff.patching.ui.view.OutputParameterSection;

public class ShowReliabilitiesHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		PropertySheet propertySheet;
		State state = event.getCommand().getState("org.sidiff.patching.ui.state.showReliability");
		try {
			propertySheet = (PropertySheet)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("org.eclipse.ui.views.PropertySheet");
			if(propertySheet.getCurrentPage() instanceof TabbedPropertySheetPage){
				TabbedPropertySheetPage page = (TabbedPropertySheetPage)propertySheet.getCurrentPage();
				ITabDescriptor selectedTab = page.getSelectedTab();
				for(ITabDescriptor desc : page.getActiveTabs()){
					page.setSelectedTab(desc.getId());
					for(ISection sec: page.getCurrentTab().getSections()){
						if(sec instanceof InputParameterSection){
							InputParameterSection section = (InputParameterSection)page.getCurrentTab().getSectionAtIndex(0);
							section.showReliability(!(Boolean)state.getValue());
							
						}else if(sec instanceof OutputParameterSection){
							OutputParameterSection section = (OutputParameterSection)page.getCurrentTab().getSectionAtIndex(0);
							section.showReliability(!(Boolean)state.getValue());
						}
					}
				}
				page.setSelectedTab(selectedTab.getId());
				state.setValue(!(Boolean)state.getValue());
					
			}
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
