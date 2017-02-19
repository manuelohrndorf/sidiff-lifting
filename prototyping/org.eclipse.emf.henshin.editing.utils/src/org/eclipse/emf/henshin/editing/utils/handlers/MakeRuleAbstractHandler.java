package org.eclipse.emf.henshin.editing.utils.handlers;

import static org.eclipse.emf.henshin.editing.utils.util.HenshinModelHelper.getRemoteNode;
import static org.eclipse.emf.henshin.editing.utils.util.HenshinModelHelper.isDeletionNode;
import static org.eclipse.emf.henshin.editing.utils.util.HenshinModelHelper.isPreservedNode;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.editing.utils.util.EMFHandlerUtil;
import org.eclipse.emf.henshin.editing.utils.util.UIUtil;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;

/**
 * Makes each << preserve >> and << delete >> node as abstract as possible.
 * 
 * @author Manuel Ohrndorf, Timo Kehrer
 */
public class MakeRuleAbstractHandler extends AbstractHandler implements IHandler  {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Module editRule = EMFHandlerUtil.getSelection(event, Module.class);
		
		if (editRule != null) {
			editRule.eAllContents().forEachRemaining(element -> {
				if (element instanceof Node) {
					Node node = (Node) element;
					
					if (isDeletionNode(node)) {
						node.setType(selectMostSpecificType(getRequiredTypes(node)));
					}
					
					else if (isPreservedNode(node)) {
						Node remoteNode = getRemoteNode(node.getGraph().getRule().getMappings(), node);
						Set<EClass> requieredTypes = getRequiredTypes(node);
						requieredTypes.addAll(getRequiredTypes(remoteNode));
						
						EClass mostAnstractType = selectMostSpecificType(requieredTypes);
						
						node.setType(mostAnstractType);
						remoteNode.setType(mostAnstractType);
					}
				}
			});
			
			try {
				editRule.eResource().save(Collections.emptyMap());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			UIUtil.showMessage("Edit-Rule saved:\n\n" + EcoreUtil.getURI(editRule).toPlatformString(true));
		}
		
		return null;
	}
	
	public static Set<EClass> getRequiredTypes(Node node) {
		Set<EClass> types = new HashSet<>();

		for (Edge ougoing : node.getOutgoing()) {
			types.add(ougoing.getType().getEContainingClass());
		}

		for (Edge incoming : node.getIncoming()) {
			types.add(incoming.getType().getEReferenceType());
		}

		for (Attribute attribute : node.getAttributes()) {
			types.add(attribute.getType().getEContainingClass());
		}

		return types;
	}

	public static EClass selectMostSpecificType(Collection<EClass> types) {

		if (!types.isEmpty()) {
			EClass mostSpecific = types.iterator().next();

			for (EClass type : types) {
				if (type.getEAllSuperTypes().contains(mostSpecific)) {
					mostSpecific = type;
				}
			}

			return mostSpecific;
		}

		return EcorePackage.eINSTANCE.getEObject();
	}
}
