package org.sidiff.remote.common.util;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.sidiff.common.emf.EMFUtil;
import org.sidiff.common.emf.modelstorage.UUIDResource;

/**
 * 
 * @author cpietsch
 *
 */
public class JSONUtil {
	
	public static JsonArray convertEMFResoruce(UUIDResource resource) {
		
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
		for (EObject eObject : resource.getContents()) {
			jsonArrayBuilder.add(transform(eObject));			
		}
		return jsonArrayBuilder.build();
	}
	
	public static JsonArray read(InputStream in) {
		JsonReader reader = Json.createReader(in);
		return reader.read().asJsonArray();
	}
	
	public static String write(JsonArray jsonArray) {
		Map<String, Boolean> config = new HashMap<String, Boolean>();
		config.put( JsonGenerator.PRETTY_PRINTING, true );
		JsonWriterFactory writerFactory = Json.createWriterFactory(config);
		StringWriter out = new StringWriter();
		writerFactory.createWriter(out).write(jsonArray);
		return out.toString(); 
	}
	
	private static JsonObject transform(EObject eObject){
		JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder().add("name", getLabel(eObject)).add("id", EMFUtil.getXmiId(eObject));
		
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
		for(EObject content : eObject.eContents()) {
			JsonObject jsonObject = transform(content);
			if(!jsonObject.isEmpty()) {
				jsonArrayBuilder.add(jsonObject);
			}
		}
		
		JsonArray jsonArray = jsonArrayBuilder.build();
		if(!jsonArray.isEmpty()) {
			jsonObjectBuilder.add("children", jsonArray);
		}
		return jsonObjectBuilder.build();
	}

	
	private static String getLabel(EObject eObject) {
		
		String label = "";
		boolean hasName = false;
		EStructuralFeature nameFeature = eObject.eClass().getEStructuralFeature("name");
		if (nameFeature != null) {
			Object nameValue = eObject.eGet(nameFeature);
			
			if (nameValue != null && nameValue instanceof String && !((String)nameValue).isEmpty()) {
				label += (String) nameValue;
				hasName = true;
			}
		}
		
		if(!hasName){
			// Remove Object ID if present:
			label += eObject.toString().replaceFirst("@.*?\\s", "").replaceAll("\\(.*\\)", "").replaceAll("\\s", "");
		}
				
		return label;
	}
}
