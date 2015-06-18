package org.eclipse.emf.henshin.provider.descriptors;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Split;

/**
 * Property descriptor for the <code>splitInto</code> feature of model class
 * {@link Split}. This descriptor collects nodes which are provided as a combo
 * box. In particular, only those nodes shall be provided, which are suitable as
 * target.
 * 
 * @author Michaela Rindt
 * 
 */
public class SplitIntoPropertyDescriptor extends ItemPropertyDescriptor {

	/**
	 * Constructor
	 * @param adapterFactory
	 * @param resourceLocator
	 * @param displayName
	 * @param description
	 * @param feature
	 */
	public SplitIntoPropertyDescriptor(AdapterFactory adapterFactory,
			ResourceLocator resourceLocator, String displayName,
			String description, EStructuralFeature feature) {
		super(adapterFactory, resourceLocator, displayName, description, feature);
	}

	/**
	 * Collects all nodes, which are provided by the combo box in a related
	 * property sheet.<br>
	 * Only those nodes are collected, which are &lt;&lt;create&gt;&gt; nodes.
	 * 
	 * @see org.eclipse.emf.edit.provider.ItemPropertyDescriptor#getComboBoxObjects(Object)
	 * 
	 */
	@Override
	protected Collection<?> getComboBoxObjects(Object object) {
		
		Collection<Node> nodeList = new ArrayList<Node>();
		
		if(object instanceof Split) {
			Split split = (Split) object;
			
			Rule rule = (Rule) split.eContainer();
			
			for(Node node: rule.getRhs().getNodes()) {
				if(rule.getMappings().getOrigin(node)==null) {
					nodeList.add(node);
				}
			}
		}
		
		return nodeList;
	}

}
