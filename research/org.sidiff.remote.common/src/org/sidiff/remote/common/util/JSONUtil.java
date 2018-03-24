package org.sidiff.remote.common.util;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.sidiff.remote.common.tree.TreeLeaf;
import org.sidiff.remote.common.tree.TreeModel;
import org.sidiff.remote.common.tree.TreeNode;

/**
 * 
 * @author cpietsch
 *
 */
public class JSONUtil {
	
	private static final String LABEL = "label";
	private static final String ID = "id";
	private static final String TYPE = "type";
	private static final String CHILDREN = "children";
	
	/**
	 * Converts an EMF Resource with UUIDs into a Json format
	 * @param resource
	 * @return
	 */
	public static String convertEMFResoruce(UUIDResource resource) {
		
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
		for (EObject eObject : resource.getContents()) {
			jsonArrayBuilder.add(transform(eObject));			
		}
		return write(jsonArrayBuilder.build());
		
	}
	
	private static JsonObject transform(EObject eObject){
		JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
		jsonObjectBuilder.add(LABEL, getLabel(eObject));
		jsonObjectBuilder.add(ID, EMFUtil.getXmiId(eObject));
		jsonObjectBuilder.add(TYPE, eObject.eClass().getEPackage().getNsURI() + "#//" + eObject.eClass().getName());
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
		for(EObject content : eObject.eContents()) {
			JsonObject jsonObject = transform(content);
			if(!jsonObject.isEmpty()) {
				jsonArrayBuilder.add(jsonObject);
			}
		}
		
		JsonArray jsonArray = jsonArrayBuilder.build();
		if(!jsonArray.isEmpty()) {
			jsonObjectBuilder.add(CHILDREN, jsonArray);
		}
		return jsonObjectBuilder.build();
	}
	
	
	
	public static String convertFileList(List<File> files, String rootName) {
		Map<File, Set<File>> file_tree = new HashMap<File, Set<File>>();
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
		List<File> roots = new ArrayList<File>();
		for(File file : files) {
			File f = file;
			do{
				if(!file_tree.containsKey(f.getParentFile())) {
					file_tree.put(f.getParentFile(), new HashSet<File>());
				}
				file_tree.get(f.getParentFile()).add(f);
				f = f.getParentFile();
			}while(!f.getName().equals(rootName));
			if(!roots.contains(f)) {
				roots.add(f);
			}
		}
		
		for(File root : roots) {
			JsonObject jsonObject = transform(root, file_tree, rootName);		
			jsonArrayBuilder.add(jsonObject);
		}
		
		return write(jsonArrayBuilder.build());
	}
	
	private static JsonObject transform(File file, Map<File,Set<File>> map, String rootName) {
		JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
		jsonObjectBuilder.add(LABEL, file.getName());
		jsonObjectBuilder.add(ID, file.getPath().toString().substring(file.getPath().toString().indexOf(rootName)));
		
		jsonObjectBuilder.add(TYPE, file.isDirectory()? "folder" : "file");
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
		if(map.containsKey(file)) {
			for(File f : map.get(file)) {
				JsonObject jsonObject = transform(f, map, rootName);
				if(!jsonObject.isEmpty()) {
					jsonArrayBuilder.add(jsonObject);
				}
			}
		
			JsonArray jsonArray = jsonArrayBuilder.build();
			if(!jsonArray.isEmpty()) {
				jsonObjectBuilder.add(CHILDREN, jsonArray);
			}						
		}
		return jsonObjectBuilder.build();
	}

	
	
	public static TreeModel convertJson(String in) {
		TreeModel treeModel = new TreeModel();
		JsonArray jsonArray = read(in);
		for(Object obj : jsonArray.toArray()) {
			JsonObject jsonObject = (JsonObject) obj;
			treeModel.getRoot().getChildren().add(transform(jsonObject, treeModel.getRoot()));
		}
			
		return treeModel;
	}
	
	private static TreeNode transform(JsonObject jsonObject, TreeNode parent) {
		String label = jsonObject.getString(LABEL);
		String id = jsonObject.getString(ID);
		String type = jsonObject.getString(TYPE);
		JsonArray jsonArray = jsonObject.getJsonArray(CHILDREN);
		
		TreeNode treeNode = null;
		if(jsonArray == null || jsonArray.isEmpty()) {
			treeNode = new TreeLeaf(label, id, type);
		}else {
			treeNode = new TreeNode(label, id, type);
			for(Object obj: jsonArray.toArray()) {
				JsonObject children = (JsonObject) obj;
				treeNode.getChildren().add(transform(children, treeNode));
			}
		
		}
		treeNode.setParent(parent);
		treeNode.setType(type);
		
		return treeNode;
	}
	
	
	
	
	private static JsonArray read(String in) {
		JsonReader reader = Json.createReader(new StringReader(in));
		return reader.read().asJsonArray();
	}
	
	private static String write(JsonArray jsonArray) {
		Map<String, Boolean> config = new HashMap<String, Boolean>();
		config.put( JsonGenerator.PRETTY_PRINTING, true );
		JsonWriterFactory writerFactory = Json.createWriterFactory(config);
		StringWriter out = new StringWriter();
		writerFactory.createWriter(out).write(jsonArray);
		return out.toString(); 
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
