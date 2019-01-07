package org.sidiff.integration.editor.highlighting.tree.internal;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.sidiff.integration.editor.highlighting.StyledObject;

public class HighlightingLabelProvider extends DelegatingStyledCellLabelProvider {

	private static class BaseLabelProviderWrapper extends ColumnLabelProvider implements IStyledLabelProvider {

		private final IBaseLabelProvider provider;

		BaseLabelProviderWrapper(IBaseLabelProvider provider) {
			this.provider = provider;
		}

		@Override
		public Image getImage(Object element) {
			if(provider instanceof IStyledLabelProvider) {
				return ((IStyledLabelProvider)provider).getImage(element);
			}
			if(provider instanceof ILabelProvider) {
				return ((ILabelProvider)provider).getImage(element);
			}
			return null;
		}
		
		@Override
		public String getText(Object element) {
			if(provider instanceof IStyledLabelProvider) {
				return ((IStyledLabelProvider)provider).getStyledText(element).getString();
			}
			if(provider instanceof ILabelProvider) {
				String text = ((ILabelProvider)provider).getText(element);
				return text == null ? "" : text;
			}
			return "";
		}

		@Override
		public StyledString getStyledText(Object element) {
			if(provider instanceof IStyledLabelProvider) {
				return ((IStyledLabelProvider)provider).getStyledText(element);
			}
			if(provider instanceof ILabelProvider) {
				String text = ((ILabelProvider)provider).getText(element);
				if(text == null)
					text= "";
				return new StyledString(text);
			}
			return new StyledString();
		}

		@Override
		public Color getBackground(Object element) {
			if (provider instanceof IColorProvider) {
				return ((IColorProvider) provider).getBackground(element);
			}
			return null;
		}

		@Override
		public Color getForeground(Object element) {
			if (provider instanceof IColorProvider) {
				return ((IColorProvider) provider).getForeground(element);
			}
			return null;
		}

		@Override
		public Font getFont(Object element) {
			if (provider instanceof IFontProvider) {
				return ((IFontProvider) provider).getFont(element);
			}
			return null;
		}
	}

	private IBaseLabelProvider baseLabelProvider;
	private Map<EObject,StyledObject> treeDecorations;
	
	public HighlightingLabelProvider(IBaseLabelProvider baseLabelProvider, Collection<StyledObject> treeDecorations) {
		super(new BaseLabelProviderWrapper(baseLabelProvider));
		this.baseLabelProvider = baseLabelProvider;
		setTreeDecorations(treeDecorations);
	}
	
	public IBaseLabelProvider getBaseLabelProvider() {
		return baseLabelProvider;
	}

	public Collection<StyledObject> getTreeDecorations() {
		return treeDecorations.values();
	}

	public void setTreeDecorations(Collection<StyledObject> treeDecorations) {
		this.treeDecorations = treeDecorations.stream().collect(Collectors.toMap(styled -> styled.getEObject(), styled -> styled));
	}

	@Override
	public Color getForeground(Object element) {
		StyledObject styled = treeDecorations.get(element);
		if(styled != null) {
			return styled.getColor();
		}
		return super.getForeground(element);
	}
}
