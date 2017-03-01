package org.eclipse.emf.henshin.editing.utils.handlers;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.henshin.editing.utils.util.EMFHandlerUtil;
import org.eclipse.emf.henshin.model.Action.Type;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;

/**
 * Makes each << preserve >> and << delete >> node as abstract as possible.
 * 
 * @author Manuel Ohrndorf, Timo Kehrer
 */
public class MakeRuleAbstractHandler extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Module module = EMFHandlerUtil.getSelection(event, Module.class);

		if (module != null) {
			module.eAllContents().forEachRemaining(
					element -> {
						if (element instanceof Node) {
							Node node = (Node) element;

							if (node.getActionNode().getAction().getType().equals(Type.DELETE)) {
								node.setType(selectMostSpecificType(getRequiredTypes(node)));
							}

							else if ((node != node.getActionNode())
									&& (node.getActionNode().getAction().getType().equals(Type.PRESERVE))) {
								Node lhsNode = node.getActionNode();
								Set<EClass> requiredTypes = getRequiredTypes(node);
								requiredTypes.addAll(getRequiredTypes(lhsNode));

								EClass mostAbstractType = selectMostSpecificType(requiredTypes);

								node.setType(mostAbstractType);
								lhsNode.setType(mostAbstractType);
							}
						}
					});

			try {
				String fileName = module.eResource().getURI().lastSegment().replace(".henshin", "") + "_reduced";
				URI uri = module.eResource().getURI().trimSegments(1).appendSegment(fileName)
						.appendFileExtension("henshin");
				Resource res = new ResourceSetImpl().createResource(uri);
				res.getContents().add(module);
				res.save(Collections.EMPTY_MAP);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	private Set<EClass> getRequiredTypes(Node node) {
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

	private EClass selectMostSpecificType(Collection<EClass> types) {

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
