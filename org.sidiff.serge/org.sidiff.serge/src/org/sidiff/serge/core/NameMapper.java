package org.sidiff.serge.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.henshin.model.Module;

//TODO: Maybe this should be a SERGe config option
public class NameMapper {

	private List<NameMapping> mappings;
	private Map<String, Module> moduleIndex;

	public NameMapper(EPackage metaModel, Set<Module> allModules) {
		// init mappings
		mappings = new ArrayList<NameMapper.NameMapping>(allModules.size());
		if (metaModel.getNsURI().equals(EcorePackage.eINSTANCE.getNsURI())) {
			initEcoreNameMappings();
		}
		if (metaModel.getNsURI().equals("http://SA/1.0")) {
			initSAMappings();
		}

		// init module index
		moduleIndex = new HashMap<String, Module>();
		for (Module module : allModules) {
			moduleIndex.put(module.getName(), module);
		}
	}

	public void replaceNames() {
		for (NameMapping mapping : mappings) {
			Module module = moduleIndex.get(mapping.generatedName);
			if (module != null) {
				module.setName(mapping.manualName);
			}
		}
	}

	private void initEcoreNameMappings() {
		mappings.add(new NameMapping("<gen-name>", "<understandable-name>"));
		// ....
	}

	private void initSAMappings(){
		mappings.add(new NameMapping("CHANGE_REFERENCE_ComponentInstance_(type)_TGT_ComponentType","changeTypeOfComponentInstance"));
		mappings.add(new NameMapping("CHANGE_REFERENCE_Connector_(port)_TGT_PortInstance","changePortOfConnector"));
		mappings.add(new NameMapping("CHANGE_REFERENCE_PortInstance_(type)_TGT_PortType","changeTypeOfPortInstance"));
		mappings.add(new NameMapping("CREATE_Actuator_IN_Architecture_(component)","createActuator"));
		mappings.add(new NameMapping("CREATE_ComponentInstance_IN_Architecture_(instances)","createComponentInstanceInArchitecture"));
		mappings.add(new NameMapping("CREATE_ComponentInstance_IN_ComponentInstance_(subcomponent)","createComponentInstanceInComponentInstance"));
		mappings.add(new NameMapping("CREATE_ComponentType_IN_Architecture_(component)","createComponentType"));
		mappings.add(new NameMapping("CREATE_Connector_IN_Architecture_(connectors)","createConnector"));
		mappings.add(new NameMapping("CREATE_ElectronicDevice_IN_Architecture_(component)","createElectronicDevice"));
		mappings.add(new NameMapping("CREATE_HardwareComponent_IN_Architecture_(component)","createHardwareComponent"));
		mappings.add(new NameMapping("CREATE_MechanicalDevice_IN_Architecture_(component)","createMechanicalDevice"));
		mappings.add(new NameMapping("CREATE_PortInstance_IN_ComponentInstance_(inPorts)","createInPortInstance"));
		mappings.add(new NameMapping("CREATE_PortInstance_IN_ComponentInstance_(outPorts)","createOutPortInstance"));
		mappings.add(new NameMapping("CREATE_PortType_IN_ComponentType_(port_types)","createPortType"));
		mappings.add(new NameMapping("CREATE_Sensor_IN_Architecture_(component)","createSensor"));
		mappings.add(new NameMapping("CREATE_SoftwareComponent_IN_Architecture_(component)","createSoftwareComponent"));
		mappings.add(new NameMapping("DELETE_Actuator_IN_Architecture_(component)","deleteActuator"));
		mappings.add(new NameMapping("DELETE_ComponentInstance_IN_Architecture_(instances)","deleteComponentInstanceFromArchitecture"));
		mappings.add(new NameMapping("DELETE_ComponentInstance_IN_ComponentInstance_(subcomponent)","deleteComponentInstanceFromComponentInstance"));
		mappings.add(new NameMapping("DELETE_ComponentType_IN_Architecture_(component)","deleteComponentType"));
		mappings.add(new NameMapping("DELETE_Connector_IN_Architecture_(connectors)","deleteConnector"));
		mappings.add(new NameMapping("DELETE_ElectronicDevice_IN_Architecture_(component)","deleteElectronicDevice"));
		mappings.add(new NameMapping("DELETE_HardwareComponent_IN_Architecture_(component)","deleteHardwareComponent"));
		mappings.add(new NameMapping("DELETE_MechanicalDevice_IN_Architecture_(component)","deleteMechanicalDevice"));
		mappings.add(new NameMapping("DELETE_PortInstance_IN_ComponentInstance_(inPorts)","deleteInPortInstance"));
		mappings.add(new NameMapping("DELETE_PortInstance_IN_ComponentInstance_(outPorts)","deleteOutPortInstance"));
		mappings.add(new NameMapping("DELETE_PortType_IN_ComponentType_(port_types)","deletePortType"));
		mappings.add(new NameMapping("DELETE_Sensor_IN_Architecture_(component)","deleteSensor"));
		mappings.add(new NameMapping("DELETE_SoftwareComponent_IN_Architecture_(component)","deleteSoftwareComponent"));
		mappings.add(new NameMapping("MOVE_ComponentInstance_FROM_ComponentInstance_(subcomponent)_TO_ComponentInstance_(subcomponent)","moveComponentInstance"));
		mappings.add(new NameMapping("MOVE_REF_COMBI_ComponentInstance_FROM_Architecture_(instances)_TO_ComponentInstance_(subcomponent)","moveComponentInstanceFromArchitectureToComponentInstance"));
		mappings.add(new NameMapping("MOVE_PortInstance_FROM_ComponentInstance_(inPorts)_TO_ComponentInstance_(inPorts)","moveInPortInstance"));
		mappings.add(new NameMapping("MOVE_PortInstance_FROM_ComponentInstance_(outPorts)_TO_ComponentInstance_(outPorts)","moveOutPortInstance"));
		mappings.add(new NameMapping("MOVE_PortType_FROM_ComponentType_(port_types)_TO_ComponentType_(port_types)","movePortType"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_Actuator_Name","setActuatorName"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_Actuator_PowerSupply","setActuatorPowerSupply"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_ComponentInstance_Name","setComponentInstanceName"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_ComponentInstance_Version","setComponentInstanceVersion"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_ComponentType_Name","setComponentTypeName"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_Connector_Name","setConnectorName"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_ElectronicDevice_Name","setElectronicDeviceName"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_ElectronicDevice_PowerSupply","setElectronicDevicePowerSupply"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_HardwareComponent_Name","setHardwareComponentName"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_HardwareComponent_PowerSupply","setHardwareComponentPowerSupply"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_MechanicalDevice_Name","setMechanicalDeviceName"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_MechanicalDevice_PowerSupply","setMechanicalDevicePowerSupply"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_PortInstance_Name","setPortInstanceName"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_PortType_Name","setPortTypeName"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_Sensor_Name","setSensorName"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_Sensor_PowerSupply","setSensorPowerSupply"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_Sensor_Type","setSensorType"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_SoftwareComponent_Name","setSoftwareComponentName"));
	}

	class NameMapping {
		String generatedName;
		String manualName;

		NameMapping(String generatedName, String manualName) {
			super();
			this.generatedName = generatedName;
			this.manualName = manualName;
		}
	}
}
