package  org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.part;

import java.util.List;

import org.eclipse.gef.Tool;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gmf.runtime.diagram.ui.tools.UnspecifiedTypeConnectionTool;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;

public class DefaultLinkToolEntry extends ToolEntry {

	private final java.util.List<IElementType> relationshipTypes;

	public DefaultLinkToolEntry(String title, String description, List<IElementType> relationshipTypes) {
		super(title, description, null, null);
		this.relationshipTypes = relationshipTypes;
	}

	public Tool createTool() {
		Tool tool = new UnspecifiedTypeConnectionTool(relationshipTypes);
		tool.setProperties(getToolProperties());
		return tool;
	}
}
