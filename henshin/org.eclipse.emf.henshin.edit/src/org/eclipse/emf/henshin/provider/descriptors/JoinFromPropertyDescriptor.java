package org.eclipse.emf.henshin.provider.descriptors;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.henshin.model.Join;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

/**
 * Property descriptor for the <code>joinFrom</code> feature of model class
 * {@link Join}. This descriptor collects nodes which are provided as a combo
 * box. In particular, only those nodes shall be provided, which are suitable as
 * target.
 * 
 * @author Michaela Rindt
 * 
 */
public class JoinFromPropertyDescriptor extends ItemPropertyDescriptor {

	/**
	 * Constructor
	 * @param adapterFactory
	 * @param resourceLocator
	 * @param displayName
	 * @param description
	 * @param feature
	 */
	public JoinFromPropertyDescriptor(AdapterFactory adapterFactory,
			ResourceLocator resourceLocator, String displayName,
			String description, EStructuralFeature feature) {
		super(adapterFactory, resourceLocator, displayName, description, feature);
	}

	/**
	 * Collects all nodes, which are provided by the combo box in a related
	 * property sheet.<br>
	 * Returns only &lt;&lt;preserve&gt;&gt;, &lt;&lt;delete&gt;&gt; and &lt;&lt;require&gt;&gt; nodes.
	 * 
	 * @see org.eclipse.emf.edit.provider.ItemPropertyDescriptor#getComboBoxObjects(Object)
	 * 
	 */
	@Override
	protected Collection<?> getComboBoxObjects(Object object) {
		
		Collection<Node> nodeList = new ArrayList<Node>();
		
		if(object instanceof Join) {
			Join join = (Join) object;
			
			Rule rule = (Rule) join.eContainer();

			// allow preserve & delete
			for(Node node: rule.getLhs().getNodes()) {
				if( (rule.getMappings().getImage(node, rule.getRhs())!=null)
					|| (rule.getMappings().getOrigin(node)==null)) {
					nodeList.add(node);
				}
			}
			// allow require
			for(NestedCondition nc: rule.getLhs().getPACs()) {
				for(Node pacNode: nc.getConclusion().getNodes()) {
					if(nc.getMappings().getOrigin(pacNode)==null) {
						nodeList.add(pacNode);
					}
				}
			}
		}
		
		return nodeList;
	}
	
}
