package org.sidiff.editrule.generator.core;

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
		if (metaModel.getNsURI().equals("http://FaultTree/1.0")) {
			initFTMappings();
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
		mappings.add(new NameMapping("CHANGE_REFERENCE_ComponentInstance_(type)_TGT_ComponentType","changeTypeOfComponent"));
		mappings.add(new NameMapping("CHANGE_REFERENCE_Connector_(port)_TGT_PortInstance","changePortOfConnector"));
		mappings.add(new NameMapping("CHANGE_REFERENCE_PortInstance_(type)_TGT_PortType","changeTypeOfPort"));
		mappings.add(new NameMapping("CREATE_Actuator_IN_Architecture_(component)","createActuator"));
		mappings.add(new NameMapping("CREATE_ComponentInstance_IN_Architecture_(instances)","createComponent"));
		mappings.add(new NameMapping("CREATE_ComponentInstance_IN_ComponentInstance_(subcomponent)","createSubComponent"));
		mappings.add(new NameMapping("CREATE_ComponentType_IN_Architecture_(component)","createComponentType"));
		mappings.add(new NameMapping("CREATE_Connector_IN_Architecture_(connectors)","createConnector"));
		mappings.add(new NameMapping("CREATE_ElectronicDevice_IN_Architecture_(component)","createElectronicDevice"));
		mappings.add(new NameMapping("CREATE_HardwareComponent_IN_Architecture_(component)","createHardwareComponent"));
		mappings.add(new NameMapping("CREATE_MechanicalDevice_IN_Architecture_(component)","createMechanicalDevice"));
		mappings.add(new NameMapping("CREATE_PortInstance_IN_ComponentInstance_(inPorts)","createInPort"));
		mappings.add(new NameMapping("CREATE_PortInstance_IN_ComponentInstance_(outPorts)","createOutPort"));
		mappings.add(new NameMapping("CREATE_PortType_IN_ComponentType_(port_types)","createPortType"));
		mappings.add(new NameMapping("CREATE_Sensor_IN_Architecture_(component)","createSensor"));
		mappings.add(new NameMapping("CREATE_SoftwareComponent_IN_Architecture_(component)","createSoftwareComponent"));
		mappings.add(new NameMapping("DELETE_Actuator_IN_Architecture_(component)","deleteActuator"));
		mappings.add(new NameMapping("DELETE_ComponentInstance_IN_Architecture_(instances)","deleteComponent"));
		mappings.add(new NameMapping("DELETE_ComponentInstance_IN_ComponentInstance_(subcomponent)","deleteSubComponent"));
		mappings.add(new NameMapping("DELETE_ComponentType_IN_Architecture_(component)","deleteComponentType"));
		mappings.add(new NameMapping("DELETE_Connector_IN_Architecture_(connectors)","deleteConnector"));
		mappings.add(new NameMapping("DELETE_ElectronicDevice_IN_Architecture_(component)","deleteElectronicDevice"));
		mappings.add(new NameMapping("DELETE_HardwareComponent_IN_Architecture_(component)","deleteHardwareComponent"));
		mappings.add(new NameMapping("DELETE_MechanicalDevice_IN_Architecture_(component)","deleteMechanicalDevice"));
		mappings.add(new NameMapping("DELETE_PortInstance_IN_ComponentInstance_(inPorts)","deleteInPort"));
		mappings.add(new NameMapping("DELETE_PortInstance_IN_ComponentInstance_(outPorts)","deleteOutPort"));
		mappings.add(new NameMapping("DELETE_PortType_IN_ComponentType_(port_types)","deletePortType"));
		mappings.add(new NameMapping("DELETE_Sensor_IN_Architecture_(component)","deleteSensor"));
		mappings.add(new NameMapping("DELETE_SoftwareComponent_IN_Architecture_(component)","deleteSoftwareComponent"));
		mappings.add(new NameMapping("MOVE_ComponentInstance_FROM_ComponentInstance_(subcomponent)_TO_ComponentInstance_(subcomponent)","moveComponent"));
		mappings.add(new NameMapping("MOVE_REF_COMBI_ComponentInstance_FROM_Architecture_(instances)_TO_ComponentInstance_(subcomponent)","convertComponentToSubComponent"));
		mappings.add(new NameMapping("MOVE_REF_COMBI_ComponentInstance_FROM_ComponentInstance_(subcomponent)_TO_Architecture_(subcomponent)","moveSubComponentToArchitecture"));
		mappings.add(new NameMapping("MOVE_PortInstance_FROM_ComponentInstance_(inPorts)_TO_ComponentInstance_(inPorts)","moveInPort"));
		mappings.add(new NameMapping("MOVE_PortInstance_FROM_ComponentInstance_(outPorts)_TO_ComponentInstance_(outPorts)","moveOutPort"));
		mappings.add(new NameMapping("MOVE_PortType_FROM_ComponentType_(port_types)_TO_ComponentType_(port_types)","changeComponentTypeOfPortType"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_Actuator_Name","setActuatorName"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_Actuator_PowerSupply","setActuatorPowerSupply"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_ComponentInstance_Name","setComponentName"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_ComponentInstance_Version","setComponentVersion"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_ComponentType_Name","setComponentTypeName"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_Connector_Name","setConnectorName"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_ElectronicDevice_Name","setElectronicDeviceName"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_ElectronicDevice_PowerSupply","setElectronicDevicePowerSupply"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_HardwareComponent_Name","setHardwareComponentName"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_HardwareComponent_PowerSupply","setHardwareComponentPowerSupply"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_MechanicalDevice_Name","setMechanicalDeviceName"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_MechanicalDevice_PowerSupply","setMechanicalDevicePowerSupply"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_PortInstance_Name","setPortName"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_PortType_Name","setPortTypeName"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_Sensor_Name","setSensorName"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_Sensor_PowerSupply","setSensorPowerSupply"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_Sensor_Type","setSensorType"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_SoftwareComponent_Name","setSoftwareComponentName"));
	}

	private void initFTMappings(){
		mappings.add(new NameMapping("ADD_BasicEvent_(instance)_TGT_ErrorInstance","addErrorToBasicEvent"));
		mappings.add(new NameMapping("ADD_Gate_(inputEvents)_TGT_Event","addInputEventToGate"));
		mappings.add(new NameMapping("ADD_Gate_(inputGates)_TGT_Gate","addInputGateToGate"));
		mappings.add(new NameMapping("ADD_IntermediateEvent_(instance)_TGT_FailureInstance","addFailureToIntermediateEvent"));
		mappings.add(new NameMapping("CHANGE_REFERENCE_ErrorInstance_(type)_TGT_ErrorType","changeTypeOfError"));
		mappings.add(new NameMapping("CHANGE_REFERENCE_FailureInstance_(type)_TGT_FailureType","changeTypeOfFailure"));
		mappings.add(new NameMapping("CREATE_AND_IN_Root_(gate)","createAND"));
		mappings.add(new NameMapping("CREATE_BasicEvent_IN_Root_(event)","createBasicEvent"));
		mappings.add(new NameMapping("CREATE_ErrorInstance_IN_Root_(error_instance)","createError"));
		mappings.add(new NameMapping("CREATE_ErrorType_IN_Root_(error_type)","createErrorType"));
		mappings.add(new NameMapping("CREATE_FailureInstance_IN_Root_(failure_instance)","createFailure"));
		mappings.add(new NameMapping("CREATE_FailureType_IN_Root_(failure_type)","createFailureType"));
		mappings.add(new NameMapping("CREATE_Hazard_IN_Root_(hazard)","createHazard"));
		mappings.add(new NameMapping("CREATE_Inhibit_IN_Root_(gate)","createInhibit"));
		mappings.add(new NameMapping("CREATE_IntermediateEvent_IN_Root_(event)","createIntermediateEvent"));
		mappings.add(new NameMapping("CREATE_OR_IN_Root_(gate)","createOR"));
		mappings.add(new NameMapping("CREATE_PriorAND_IN_Root_(gate)","createPriorAND"));
		mappings.add(new NameMapping("CREATE_UndevelopedEvent_IN_Root_(event)","createUndevelopedEvent"));
		mappings.add(new NameMapping("CREATE_XOR_IN_Root_(gate)","createXOR"));
		mappings.add(new NameMapping("DELETE_AND_IN_Root_(gate)","deleteAND"));
		mappings.add(new NameMapping("DELETE_BasicEvent_IN_Root_(event)","deleteBasicEvent"));
		mappings.add(new NameMapping("DELETE_ErrorInstance_IN_Root_(error_instance)","deleteError"));
		mappings.add(new NameMapping("DELETE_ErrorType_IN_Root_(error_type)","deleteErrorType"));
		mappings.add(new NameMapping("DELETE_FailureInstance_IN_Root_(failure_instance)","deleteFailure"));
		mappings.add(new NameMapping("DELETE_FailureType_IN_Root_(failure_type)","deleteFailureType"));
		mappings.add(new NameMapping("DELETE_Hazard_IN_Root_(hazard)","deleteHazard"));
		mappings.add(new NameMapping("DELETE_Inhibit_IN_Root_(gate)","deleteInhibit"));
		mappings.add(new NameMapping("DELETE_IntermediateEvent_IN_Root_(event)","deleteIntermediateEvent"));
		mappings.add(new NameMapping("DELETE_OR_IN_Root_(gate)","deleteOR"));
		mappings.add(new NameMapping("DELETE_PriorAND_IN_Root_(gate)","deletePriorAND"));
		mappings.add(new NameMapping("DELETE_UndevelopedEvent_IN_Root_(event)","deleteUndevelopedEvent"));
		mappings.add(new NameMapping("DELETE_XOR_IN_Root_(gate)","deleteXOR"));
		mappings.add(new NameMapping("REMOVE_BasicEvent_(instance)_TGT_ErrorInstance","removeErrorFromBasicEvent"));
		mappings.add(new NameMapping("REMOVE_Gate_(inputEvents)_TGT_Event","removeInputEventFromGate"));
		mappings.add(new NameMapping("REMOVE_Gate_(inputGates)_TGT_Gate","removeInputGateFromGate"));
		mappings.add(new NameMapping("REMOVE_IntermediateEvent_(instance)_TGT_FailureInstance","removeFailureFromIntermediateEvent"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_BasicEvent_Probability","setBasicEventProbability"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_ErrorInstance_Name","setErrorName"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_ErrorType_Name","setErrorTypeName"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_Event_Description","setEventDescription"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_Event_Id","setEventId"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_Event_Name","setEventName"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_FailureInstance_Name","setFailureName"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_FailureType_Name","setFailureTypeName"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_Gate_Id","setGateId"));
		mappings.add(new NameMapping("SET_REFERENCE_BasicEvent_(outEvent)_TGT_IntermediateEvent","setBasicEventOutEvent"));
		mappings.add(new NameMapping("SET_REFERENCE_ErrorInstance_(error)_TGT_BasicEvent","setErrorEvent"));
		mappings.add(new NameMapping("SET_REFERENCE_Event_(inputGate)_TGT_Gate","setEventInputGate"));
		mappings.add(new NameMapping("SET_REFERENCE_Event_(outputGate)_TGT_Gate","setEventOutputGate"));
		mappings.add(new NameMapping("SET_REFERENCE_FailureInstance_(event)_TGT_IntermediateEvent","setFailureEvent"));
		mappings.add(new NameMapping("SET_REFERENCE_FailureInstance_(previousError)_TGT_ErrorInstance","setFailurePreviousError"));
		mappings.add(new NameMapping("SET_REFERENCE_FailureInstance_(previousFailure)_TGT_FailureInstance","setFailurePreviousFailure"));
		mappings.add(new NameMapping("SET_REFERENCE_Gate_(outputEvent)_TGT_Event","setGateOutputEvent"));
		mappings.add(new NameMapping("SET_REFERENCE_Gate_(outputGate)_TGT_Gate","setGateOutputGate"));
		mappings.add(new NameMapping("SET_REFERENCE_Hazard_(inEvent)_TGT_IntermediateEvent","setHazardInEvent"));
		mappings.add(new NameMapping("SET_REFERENCE_IntermediateEvent_(inEvent)_TGT_Event","setIntermediateEventInEvent"));
		mappings.add(new NameMapping("SET_REFERENCE_IntermediateEvent_(outEvent)_TGT_Event","setIntermediateEventOutEvent"));
		mappings.add(new NameMapping("UNSET_REFERENCE_BasicEvent_(outEvent)_TGT_IntermediateEvent","unsetBasicEventOutEvent"));
		mappings.add(new NameMapping("UNSET_REFERENCE_ErrorInstance_(error)_TGT_BasicEvent","unsetErrorEvent"));
		mappings.add(new NameMapping("UNSET_REFERENCE_Event_(inputGate)_TGT_Gate","unsetEventInputGate"));
		mappings.add(new NameMapping("UNSET_REFERENCE_Event_(outputGate)_TGT_Gate","unsetEventOutputGate"));
		mappings.add(new NameMapping("UNSET_REFERENCE_FailureInstance_(event)_TGT_IntermediateEvent","unsetFailureEvent"));
		mappings.add(new NameMapping("UNSET_REFERENCE_FailureInstance_(previousError)_TGT_ErrorInstance","unsetFailurePreviousError"));
		mappings.add(new NameMapping("UNSET_REFERENCE_FailureInstance_(previousFailure)_TGT_FailureInstance","unsetFailurePreviousFailure"));
		mappings.add(new NameMapping("UNSET_REFERENCE_Gate_(outputEvent)_TGT_Event","unsetGateOutputEvent"));
		mappings.add(new NameMapping("UNSET_REFERENCE_Gate_(outputGate)_TGT_Gate","unsetGateOutputGate"));
		mappings.add(new NameMapping("UNSET_REFERENCE_Hazard_(inEvent)_TGT_IntermediateEvent","unsetHazardInEvent"));
		mappings.add(new NameMapping("UNSET_REFERENCE_IntermediateEvent_(inEvent)_TGT_Event","unsetIntermediateEventInEvent"));
		mappings.add(new NameMapping("UNSET_REFERENCE_IntermediateEvent_(outEvent)_TGT_Event","unsetIntermediateEventOutEvent"));
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
