package org.sidiff.javaast.model.adapter;

import java.util.*;

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.sidiff.javaast.model.*;

/**
 * Adapter providing comfortable access to JMethods.
 * 
 * @author kehrer
 */
public class JMethodAdapter extends AdapterImpl {

	private List<JCodeFragment> allCodeFragments;
	private int loc = -1;

	@Override
	public boolean isAdapterForType(Object type) {
		return type == JMethod.class;
	}

	public Integer getLinesOfCodeMetric() {
		if (loc == -1) {
			loc = 0;
			for (JCodeFragment codeFragment : getAllCodeFragments()) {
				if (codeFragment.getCode() != null) {
					loc += codeFragment.getCode().split("\n").length;
				}
			}
		}

		return new Integer(loc);
	}

	/**
	 * Get all code fragments conatained in the adapted method. <br>
	 * The Collection includes fragments of structured code blocks or structured code fragments.
	 * 
	 * @return A collection which can be empty but not <code>null</code>
	 */
	public List<JCodeFragment> getAllCodeFragments() {
		if (allCodeFragments == null) {
			List<JCodeFragment> tmp_all = new ArrayList<JCodeFragment>();
			if (getJMethod().getBody() != null) {
				intlGetAllCodeFragments(getJMethod().getBody(), tmp_all);
			}
			allCodeFragments = Collections.unmodifiableList(tmp_all);
		}

		return allCodeFragments;
	}

	private void intlGetAllCodeFragments(JCodeBlock block, List<JCodeFragment> fragments) {
		// codeFragments in block
		for (JCodeFragment frag : block.getCodeFragments()) {
			fragments.add(frag);
			if (frag instanceof JStructuralCodeFragment) {
				// blocks in structural fragment
				for (JCodeBlock subBlock : ((JStructuralCodeFragment) frag).getCodeBlocks()) {
					intlGetAllCodeFragments(subBlock, fragments);
				}
			}
		}

		// subBlocks
		for (JCodeBlock subBlock : block.getSubBlocks()) {
			intlGetAllCodeFragments(subBlock, fragments);
		}
	}

	private JMethod getJMethod() {
		return (JMethod) getTarget();
	}
}
