package  org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.part;

import java.util.List;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.Tool;
import org.eclipse.gmf.runtime.diagram.ui.tools.UnspecifiedTypeCreationTool;

public class DefaultNodeToolEntry extends ToolEntry {

	private final List<IElementType> elementTypes;

	public DefaultNodeToolEntry(String title, String description, List<IElementType> elementTypes) {
		super(title, description, null, null);
		this.elementTypes = elementTypes;
	}

	public Tool createTool() {
		Tool tool = new UnspecifiedTypeCreationTool(elementTypes);
		tool.setProperties(getToolProperties());
		return tool;
	}
}
