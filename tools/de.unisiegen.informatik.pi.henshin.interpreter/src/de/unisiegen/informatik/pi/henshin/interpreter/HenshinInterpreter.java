package de.unisiegen.informatik.pi.henshin.interpreter;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.UnitApplication;
import org.eclipse.emf.henshin.interpreter.impl.EGraphImpl;
import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.interpreter.impl.UnitApplicationImpl;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Unit;

import de.unisiegen.informatik.pi.henshin.interpreter.exceptions.UnresolvedArgumentException;

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
	private ArgumentManager argumentManager;
	
	/**
	 * The {@link UnitApplication} for applying the given {@link #henshin_module} on {@link #model_resource}
	 */
	private UnitApplication application;
	
	public HenshinInterpreter() {
		this.argumentManager = new ArgumentManager();
	}
	
	/**
	 * applies an {@link Unit} onto the {@link #model_resource}
	 * 
	 * @param unit
	 * 		an henshin {@link Unit}
	 * @return
	 * 		<code>true</code> if successful, <code>false</code> otherwise
	 * @throws UnresolvedArgumentException 
	 */
	public boolean applyUnit(Unit unit) throws UnresolvedArgumentException {
		
		Engine engine = new EngineImpl();
		EGraph graph = new EGraphImpl(this.model_resource);	
		application = new UnitApplicationImpl(engine, graph, unit, null);
		
		for(Argument argument : argumentManager.getArguments()) {
			if(!argument.isUnset()) {
				if(argument.getValue() != null) {
					application.setParameterValue(argument.getParameter().getName(), argument.getValue());
				}else {
					throw new UnresolvedArgumentException(argument.getParameter().getName());
				}
			}else {
				Object defaultValue = argument.getType().getDefaultValue();
				if(defaultValue != null) {
					application.setParameterValue(argument.getParameter().getName(), defaultValue);
				}else {
					throw new UnresolvedArgumentException(argument.getParameter().getName());
				}
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
	
	public ArgumentManager getArgumentManager() {
		return argumentManager;
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
	
	
	
	private void updateArgumentManager() {
		if(this.henshin_module != null) {
			this.argumentManager.init(this.henshin_module);
		}
	}
}
