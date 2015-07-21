package org.sidiff.editrule.generator.serge.core;

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
		if(metaModel.getNsURI().equals( "http://www.eclipse.org/emf/refactor/examples/SimpleWebModelingLanguage")){
			initSWMLMappings();
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

//	private void initEcoreNameMappings() {
//		mappings.add(new NameMapping("<gen-name>", "<understandable-name>"));
//		// ....
//	}

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
	
	private void initEcoreNameMappings(){
		mappings.add(new NameMapping("SET_ATTRIBUTE_EReference_Changeable","SetAttributeEReferenceChangeable"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EEnumLiteral_Literal","SetAttributeEEnumLiteralLiteral"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EAttribute_DefaultValueLiteral","SetAttributeEAttributeDefaultValueLiteral"));
		mappings.add(new NameMapping("changeBidirectionalReferenceType","changeBidirectionalReferenceType"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EAttribute_UpperBound","SetAttributeEAttributeUpperBound"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EPackage_NsPrefix","SetAttributeEPackageNsPrefix"));
		mappings.add(new NameMapping("ADD_EAnnotation_(references)_TGT_EObject","AddEAnnotationTgtEObject"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EDataType_Serializable","SetAttributeEDataTypeSerializable"));
		mappings.add(new NameMapping("CREATE_EReferenceInEClass","CreateEReferenceInEClass"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EEnumLiteral_Name","SetAttributeEEnumLiteralName"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EReference_Unique","SetAttributeEReferenceUnique"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EReference_Transient","SetAttributeEReferenceTransient"));
		mappings.add(new NameMapping("MOVE_EEnumLiteral_FROM_EEnum_(eLiterals)_TO_EEnum_(eLiterals)","MoveEEnumLiteralFromEEnumToEEnum"));
		mappings.add(new NameMapping("MOVE_EOperation_FROM_EClass_(eOperations)_TO_EClass_(eOperations)","MoveEOperationFromEClassToEClass"));
		mappings.add(new NameMapping("DELETE_EEnum_IN_EPackage_(eClassifiers)","DeleteEEnumInEPackage"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EReference_DefaultValueLiteral","SetAttributeEReferenceDefaultValueLiteral"));
		mappings.add(new NameMapping("DELETE_EAnnotation_IN_EModelElement_(eAnnotations)","DeleteEAnnotationInEModelElement"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EAnnotation_Source","SetAttributeEAnnotationSource"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_ETypeParameter_Name","SetAttributeETypeParameterName"));
		mappings.add(new NameMapping("DELETE_EStringToStringMapEntry_IN_EAnnotation_(details)","DeleteEStringToStringMapEntryInEAnnotation"));
		mappings.add(new NameMapping("SET_REFERENCE_EOperation_(eType)_TGT_EClassifier","SetReferenceEOperationTgtEClassifier"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EOperation_LowerBound","SetAttributeEOperationLowerBound"));
		mappings.add(new NameMapping("CREATE_ETypeParameter_IN_EClassifier_(eTypeParameters)","CreateETypeParameterInEClassifier"));
		mappings.add(new NameMapping("UNSET_REFERENCE_EAttribute_(eType)_TGT_EClassifier","UnsetReferenceEAttributeTgtEClassifier"));
		mappings.add(new NameMapping("CHANGE_EReferenceType","ChangeEReferenceType"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EAttribute_Unsettable","SetAttributeEAttributeUnsettable"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EPackage_NsURI","SetAttributeEPackageNsURI"));
		mappings.add(new NameMapping("deleteOppositeReference","deleteOppositeReference"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EOperation_UpperBound","SetAttributeEOperationUpperBound"));
		mappings.add(new NameMapping("DELETE_EEnumLiteral_IN_EEnum_(eLiterals)","DeleteEEnumLiteralInEEnum"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EDataType_InstanceClassName","SetAttributeEDataTypeInstanceClassName"));
		mappings.add(new NameMapping("DELETE_ETypeParameter_IN_EOperation_(eTypeParameters)","DeleteETypeParameterInEOperation"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EAttribute_Transient","SetAttributeEAttributeTransient"));
		mappings.add(new NameMapping("DELETE_EAttribute_IN_EClass_(eStructuralFeatures)","DeleteEAttributeInEClass"));
		mappings.add(new NameMapping("DELETE_EReferenceInEClass","DeleteEReferenceInEClass"));
		mappings.add(new NameMapping("MOVE_EClass_FROM_EPackage_(eClassifiers)_TO_EPackage_(eClassifiers)","MoveEClassFromEPackageToEPackage"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EEnum_Serializable","SetAttributeEEnumSerializable"));
		mappings.add(new NameMapping("CREATE_EClass_IN_EPackage_(eClassifiers)","CreateEClassInEPackage"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EAttribute_Changeable","SetAttributeEAttributeChangeable"));
		mappings.add(new NameMapping("DELETE_EClass_IN_EPackage_(eClassifiers)","DeleteEClassInEPackage"));
		mappings.add(new NameMapping("MOVE_EDataType_FROM_EPackage_(eClassifiers)_TO_EPackage_(eClassifiers)","MoveEDataTypeFromEPackageToEPackage"));
		mappings.add(new NameMapping("MOVE_EEnum_FROM_EPackage_(eClassifiers)_TO_EPackage_(eClassifiers)","MoveEEnumFromEPackageToEPackage"));
		mappings.add(new NameMapping("UNSET_REFERENCE_EReference_(eOpposite)_TGT_EReference","UnsetReferenceEReferenceTgtEReference"));
		mappings.add(new NameMapping("MOVE_ETypeParameter_FROM_EClassifier_(eTypeParameters)_TO_EClassifier_(eTypeParameters)","MoveETypeParameterFromEClassifierToEClassifier"));
		mappings.add(new NameMapping("SET_REFERENCE_EReference_(eOpposite)_TGT_EReference","SetReferenceEReferenceTgtEReference"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EDataType_Name","SetAttributeEDataTypeName"));
		mappings.add(new NameMapping("UNSET_EReference_Ref_eOpposite_tgt_EReference","UnsetEReferenceRefeOppositetgtEReference"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EAttribute_ID","SetAttributeEAttributeId"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EReference_ResolveProxies","SetAttributeEReferenceResolveProxies"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EReference_Containment","SetAttributeEReferenceContainment"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EAttribute_Derived","SetAttributeEAttributeDerived"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EEnumLiteral_Value","SetAttributeEEnumLiteralValue"));
		mappings.add(new NameMapping("DELETE_EOperation_IN_EClass_(eOperations)","DeleteEOperationInEClass"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EParameter_Ordered","SetAttributeEParameterOrdered"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EReference_Volatile","SetAttributeEReferenceVolatile"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EParameter_Name","SetAttributeEParameterName"));
		mappings.add(new NameMapping("CREATE_EEnum_IN_EPackage_(eClassifiers)","CreateEEnumInEPackage"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EClass_Interface","SetAttributeEClassInterface"));
		mappings.add(new NameMapping("MOVE_EPackage_FROM_EPackage_(eSubpackages)_TO_EPackage_(eSubpackages)","MoveEPackageFromEPackageToEPackage"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EClass_InstanceClassName","SetAttributeEClassInstanceClassName"));
		mappings.add(new NameMapping("MOVE_EAttribute_FROM_EClass_(eStructuralFeatures)_TO_EClass_(eStructuralFeatures)","MoveEAttributeFromEClassToEClass"));
		mappings.add(new NameMapping("MOVE_EReference_Ref_eStructuralFeatures_To_EClass","MoveEReferenceRefeStructuralFeaturesToEClass"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EClass_Abstract","SetAttributeEClassAbstract"));
		mappings.add(new NameMapping("SET_EReference_Ref_eOpposite_tgt_EReference","SetEReferenceRefeOppositetgtEReference"));
		mappings.add(new NameMapping("CREATE_EOperation_IN_EClass_(eOperations)","CreateEOperationInEClass"));
		mappings.add(new NameMapping("SET_REFERENCE_EAttribute_(eType)_TGT_EClassifier","SetReferenceEAttributeTgtEClassifier"));
		mappings.add(new NameMapping("ADD_EOperation_(eExceptions)_TGT_EClassifier","AddEOperationTgtEClassifier"));
		mappings.add(new NameMapping("DELETE_EDataType_IN_EPackage_(eClassifiers)","DeleteEDataTypeInEPackage"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EStringToStringMapEntry_Value","SetAttributeEStringToStringMapEntryValue"));
		mappings.add(new NameMapping("DELETE_ETypeParameter_IN_EClassifier_(eTypeParameters)","DeleteETypeParameterInEClassifier"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EClass_InstanceTypeName","SetAttributeEClassInstanceTypeName"));
		mappings.add(new NameMapping("REMOVE_EReference_(eKeys)_TGT_EAttribute","RemoveEReferenceTgtEAttribute"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EClass_Name","SetAttributeEClassName"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EAttribute_Name","SetAttributeEAttributeName"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EStringToStringMapEntry_Key","SetAttributeEStringToStringMapEntryKey"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EEnum_Name","SetAttributeEEnumName"));
		mappings.add(new NameMapping("MOVE_EAnnotation_FROM_EModelElement_(eAnnotations)_TO_EModelElement_(eAnnotations)","MoveEAnnotationFromEModelElementToEModelElement"));
		mappings.add(new NameMapping("MOVE_ETypeParameter_FROM_EOperation_(eTypeParameters)_TO_EOperation_(eTypeParameters)","MoveETypeParameterFromEOperationToEOperation"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EOperation_Unique","SetAttributeEOperationUnique"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EReference_Ordered","SetAttributeEReferenceOrdered"));
		mappings.add(new NameMapping("CREATE_ETypeParameter_IN_EOperation_(eTypeParameters)","CreateETypeParameterInEOperation"));
		mappings.add(new NameMapping("CREATE_EAnnotation_IN_EModelElement_(eAnnotations)","CreateEAnnotationInEModelElement"));
		mappings.add(new NameMapping("UNSET_REFERENCE_EOperation_(eType)_TGT_EClassifier","UnsetReferenceEOperationTgtEClassifier"));
		mappings.add(new NameMapping("CREATE_EEnumLiteral_IN_EEnum_(eLiterals)","CreateEEnumLiteralInEEnum"));
		mappings.add(new NameMapping("deleteBidirectionalReference","deleteBidirectionalReference"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EDataType_InstanceTypeName","SetAttributeEDataTypeInstanceTypeName"));
		mappings.add(new NameMapping("MOVE_EStringToStringMapEntry_FROM_EAnnotation_(details)_TO_EAnnotation_(details)","MoveEStringToStringMapEntryFromEAnnotationToEAnnotation"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EEnum_InstanceTypeName","SetAttributeEEnumInstanceTypeName"));
		mappings.add(new NameMapping("DELETE_EParameter_IN_EOperation_(eParameters)","DeleteEParameterInEOperation"));
		mappings.add(new NameMapping("ADD_EReference_(eKeys)_TGT_EAttribute","AddEReferenceTgtEAttribute"));
		mappings.add(new NameMapping("UNSET_REFERENCE_EParameter_(eType)_TGT_EClassifier","UnsetReferenceEParameterTgtEClassifier"));
		mappings.add(new NameMapping("REMOVE_EClass_(eSuperTypes)_TGT_EClass","RemoveEClassTgtEClass"));
		mappings.add(new NameMapping("CREATE_EDataType_IN_EPackage_(eClassifiers)","CreateEDataTypeInEPackage"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EParameter_Unique","SetAttributeEParameterUnique"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EEnum_InstanceClassName","SetAttributeEEnumInstanceClassName"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EAttribute_Unique","SetAttributeEAttributeUnique"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EParameter_UpperBound","SetAttributeEParameterUpperBound"));
		mappings.add(new NameMapping("CREATE_EAttribute_IN_EClass_(eStructuralFeatures)","CreateEAttributeInEClass"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EOperation_Name","SetAttributeEOperationName"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EReference_UpperBound","SetAttributeEReferenceUpperBound"));
		mappings.add(new NameMapping("CREATE_EParameter_IN_EOperation_(eParameters)","CreateEParameterInEOperation"));
		mappings.add(new NameMapping("REMOVE_EAnnotation_(references)_TGT_EObject","RemoveEAnnotationTgtEObject"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EAttribute_LowerBound","SetAttributeEAttributeLowerBound"));
		mappings.add(new NameMapping("MOVE_EParameter_FROM_EOperation_(eParameters)_TO_EOperation_(eParameters)","MoveEParameterFromEOperationToEOperation"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EParameter_LowerBound","SetAttributeEParameterLowerBound"));
		mappings.add(new NameMapping("REMOVE_EOperation_(eExceptions)_TGT_EClassifier","RemoveEOperationTgtEClassifier"));
		mappings.add(new NameMapping("SET_REFERENCE_EParameter_(eType)_TGT_EClassifier","SetReferenceEParameterTgtEClassifier"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EReference_LowerBound","SetAttributeEReferenceLowerBound"));
		mappings.add(new NameMapping("moveBidirectionalReference","moveBidirectionalReference"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EAttribute_Ordered","SetAttributeEAttributeOrdered"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EPackage_Name","SetAttributeEPackageName"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EReference_Name","SetAttributeEReferenceName"));
		mappings.add(new NameMapping("ADD_EClass_(eSuperTypes)_TGT_EClass","AddEClassTgtEClass"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EReference_Unsettable","SetAttributeEReferenceUnsettable"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EReference_Derived","SetAttributeEReferenceDerived"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EOperation_Ordered","SetAttributeEOperationOrdered"));
		mappings.add(new NameMapping("CREATE_EStringToStringMapEntry_IN_EAnnotation_(details)","CreateEStringToStringMapEntryInEAnnotation"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_EAttribute_Volatile","SetAttributeEAttributeVolatile"));
	}
	

	private void initSWMLMappings(){
		mappings.add(new NameMapping("DELETE_IndexPage_IN_HypertextLayer_(pages)","deleteIndexPageInHypertextLayer"));
		mappings.add(new NameMapping("DELETE_DataPage_IN_HypertextLayer_(pages)","deleteDataPageInHypertextLayer"));
		mappings.add(new NameMapping("DELETE_DataLayer_IN_WebModel_(dataLayer)","deleteDataLayerInWebModel"));
		mappings.add(new NameMapping("MOVE_StaticPage_FROM_HypertextLayer_(pages)_TO_HypertextLayer_(pages)","moveStaticPageFromHypertextLayerToHypertextLayer"));
		mappings.add(new NameMapping("UNSET_REFERENCE_Reference_(type)_TGT_Entity","unsetReferenceReferenceTgtEntity"));
		mappings.add(new NameMapping("MOVE_Reference_FROM_Entity_(references)_TO_Entity_(references)","moveReferenceFromEntityToEntity"));
		mappings.add(new NameMapping("MOVE_DataLayer_FROM_WebModel_(dataLayer)_TO_WebModel_(dataLayer)","moveDataLayerFromWebModelToWebModel"));
		mappings.add(new NameMapping("SET_REFERENCE_Link_(target)_TGT_Page","setReferenceLinkTgtPage"));
		mappings.add(new NameMapping("MOVE_DynamicPage_FROM_HypertextLayer_(pages)_TO_HypertextLayer_(pages)","moveDynamicPageFromHypertextLayerToHypertextLayer"));
		mappings.add(new NameMapping("CREATE_HypertextLayer_IN_WebModel_(hypertextLayer)","createHypertextLayerInWebModel"));
		mappings.add(new NameMapping("CREATE_DynamicPage_IN_HypertextLayer_(pages)","createDynamicPageInHypertextLayer"));
		mappings.add(new NameMapping("DELETE_StaticPage_IN_HypertextLayer_(pages)","deleteStaticPageInHypertextLayer"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_WebModel_Name","setAttributeWebModelName"));
		mappings.add(new NameMapping("CREATE_Link_IN_Page_(links)","createLinkInPage"));
		mappings.add(new NameMapping("DELETE_Link_IN_Page_(links)","deleteLinkInPage"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_Entity_Name","setAttributeEntityName"));
		mappings.add(new NameMapping("DELETE_Entity_IN_DataLayer_(entities)","deleteEntityInDataLayer"));
		mappings.add(new NameMapping("MOVE_Entity_FROM_DataLayer_(entities)_TO_DataLayer_(entities)","moveEntityFromDataLayerToDataLayer"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_Attribute_Type","setAttributeAttributeType"));
		mappings.add(new NameMapping("DELETE_Attribute_IN_Entity_(attributes)","deleteAttributeInEntity"));
		mappings.add(new NameMapping("MOVE_DataPage_FROM_HypertextLayer_(pages)_TO_HypertextLayer_(pages)","moveDataPageFromHypertextLayerToHypertextLayer"));
		mappings.add(new NameMapping("MOVE_Page_FROM_HypertextLayer_(pages)_TO_HypertextLayer_(pages)","movePageFromHypertextLayerToHypertextLayer"));
		mappings.add(new NameMapping("SET_REFERENCE_HypertextLayer_(startPage)_TGT_StaticPage","setReferenceHypertextLayerTgtStaticPage"));
		mappings.add(new NameMapping("DELETE_Reference_IN_Entity_(references)","deleteReferenceInEntity"));
		mappings.add(new NameMapping("DELETE_DynamicPage_IN_HypertextLayer_(pages)","deleteDynamicPageInHypertextLayer"));
		mappings.add(new NameMapping("CREATE_Attribute_IN_Entity_(attributes)","Create Attribute"));
		mappings.add(new NameMapping("MOVE_Attribute_FROM_Entity_(attributes)_TO_Entity_(attributes)","moveAttributeFromEntityToEntity"));
		mappings.add(new NameMapping("UNSET_REFERENCE_Link_(target)_TGT_Page","unsetReferenceLinkTgtPage"));
		mappings.add(new NameMapping("CREATE_Reference_IN_Entity_(references)","createReferenceInEntity"));
		mappings.add(new NameMapping("UNSET_REFERENCE_HypertextLayer_(startPage)_TGT_StaticPage","unsetReferenceHypertextLayerTgtStaticPage"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_Reference_Name","setAttributeReferenceName"));
		mappings.add(new NameMapping("DELETE_HypertextLayer_IN_WebModel_(hypertextLayer)","deleteHypertextLayerInWebModel"));
		mappings.add(new NameMapping("DELETE_Page_IN_HypertextLayer_(pages)","deletePageInHypertextLayer"));
		mappings.add(new NameMapping("CREATE_StaticPage_IN_HypertextLayer_(pages)","createStaticPageInHypertextLayer"));
		mappings.add(new NameMapping("CREATE_DataPage_IN_HypertextLayer_(pages)","createDataPageInHypertextLayer"));
		mappings.add(new NameMapping("SET_REFERENCE_Reference_(type)_TGT_Entity","setReferenceReferenceTgtEntity"));
		mappings.add(new NameMapping("CREATE_DataLayer_IN_WebModel_(dataLayer)","createDataLayerInWebModel"));
		mappings.add(new NameMapping("MOVE_Link_FROM_Page_(links)_TO_Page_(links)","moveLinkFromPageToPage"));
		mappings.add(new NameMapping("CREATE_IndexPage_IN_HypertextLayer_(pages)","createIndexPageInHypertextLayer"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_Attribute_Name","setAttributeAttributeName"));
		mappings.add(new NameMapping("MOVE_HypertextLayer_FROM_WebModel_(hypertextLayer)_TO_WebModel_(hypertextLayer)","moveHypertextLayerFromWebModelToWebModel"));
		mappings.add(new NameMapping("MOVE_IndexPage_FROM_HypertextLayer_(pages)_TO_HypertextLayer_(pages)","moveIndexPageFromHypertextLayerToHypertextLayer"));
		mappings.add(new NameMapping("CREATE_Page_IN_HypertextLayer_(pages)","createPageInHypertextLayer"));
		mappings.add(new NameMapping("UNSET_REFERENCE_DynamicPage_(entity)_TGT_Entity","unsetReferenceDynamicPageTgtEntity"));
		mappings.add(new NameMapping("SET_ATTRIBUTE_Page_Name","setAttributePageName"));
		mappings.add(new NameMapping("SET_REFERENCE_DynamicPage_(entity)_TGT_Entity","setReferenceDynamicPageTgtEntity"));
		mappings.add(new NameMapping("CREATE_Entity_IN_DataLayer_(entities)","Create Entity"));
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
