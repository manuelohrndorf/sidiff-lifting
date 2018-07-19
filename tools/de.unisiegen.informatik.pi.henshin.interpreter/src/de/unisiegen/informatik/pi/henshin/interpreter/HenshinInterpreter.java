package de.unisiegen.informatik.pi.henshin.interpreter;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.UnitApplication;
import org.eclipse.emf.henshin.interpreter.impl.EGraphImpl;
import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.interpreter.impl.UnitApplicationImpl;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.ParameterKind;
import org.eclipse.emf.henshin.model.Unit;

/**
 * 
 * @author cpietsch
 *
 */
public class HenshinInterpreter {

	/**
	 * The {@link Resource} containing the model
	 */
	private Resource model_resource;
	
	/**
	 * The henshin {@link Module} containing all available transformation units
	 */
	private Module henshin_module;
	
	/**
	 * Manager for parameter arguments
	 */
	private Map<Parameter, Object> argumentManager;
	
	private UnitApplication application;
	
	/**
	 * applies an {@link Unit} onto the {@link #model_resource}
	 * 
	 * @param unit
	 * 		an henshin {@link Unit}
	 * @return
	 * 		<code>true</code> if successful, <code>false</code> otherwise
	 */
	public boolean applyUnit(Unit unit) {
		
		Engine engine = new EngineImpl();
		EGraph graph = new EGraphImpl(this.model_resource);	
		application = new UnitApplicationImpl(engine, graph, unit, null);
		
		for(Parameter parameter : unit.getParameters()) {
			if(argumentManager.get(parameter) != null) {
				application.setParameterValue(parameter.getName(), argumentManager.get(parameter));
			}
		}
		
		boolean success = application.execute(null);
		
		
		return success;
	}
	
	
	public boolean undoLast() {
		boolean result = false;
		if(application != null) {
			result = application.undo(null);
			application = null;
		}
		return result;
	}
	
	public Resource getModelResource() {
		return model_resource;
	}
	
	public void setModelResource(Resource model_resource) {
		this.model_resource = model_resource;
		updateArgumentManager();
	}
	
	public Module getHenshinModule() {
		return henshin_module;
	}
	
	public void setHenshinModule(Module henshin_module) {
		this.henshin_module = henshin_module;
		updateArgumentManager();
	}
	
	public Map<Parameter, Object> getArgumentManager() {
		return this.argumentManager;
	}
	
	
	private void updateArgumentManager() {
		this.argumentManager = new HashMap<Parameter, Object>();
		if(this.henshin_module != null) {
			for(Unit unit : this.henshin_module.getUnits()) {
				for(Parameter parameter : unit.getParameters()){
					if(parameter.getKind().equals(ParameterKind.IN) || parameter.getKind().equals(ParameterKind.INOUT)) {
						this.argumentManager.put(parameter, "");
					}
				}
			}
		}
	}
}
