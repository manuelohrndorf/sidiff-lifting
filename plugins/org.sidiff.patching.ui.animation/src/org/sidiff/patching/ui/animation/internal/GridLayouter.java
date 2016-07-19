package org.sidiff.patching.ui.animation.internal;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.LayoutConstraint;
import org.eclipse.gmf.runtime.notation.Node;

public class GridLayouter {
	
	private static GridLayouter instance;
	
	public static GridLayouter getInstance() {
		if(instance == null){
			instance = new GridLayouter();
		}
		return instance;
	}
	
	private final int margin = 200;
	
	private Rectangle box;
	private int x_pos;
	private int y_pos;
	private int iteration;
	
	public void setup(DiagramEditor diagramEditor){
		Diagram diagram = diagramEditor.getDiagram();
		
		box = new Rectangle(0, 0, 0, 0);
		for(Object view : diagram.getChildren()){
			if(view instanceof Node){
				Node node = (Node) view;
				LayoutConstraint layoutConstraint = node.getLayoutConstraint();
				if(layoutConstraint instanceof Bounds){
					Bounds bounds = (Bounds) layoutConstraint;
					if(bounds.getX() + bounds.getWidth() > box.width){
						box.width = bounds.getX() + bounds.getWidth();
					}
					
					if(bounds.getY() + bounds.getHeight() > box.height){
						box.height = bounds.getY() + bounds.getHeight();
					}
				}
			}
		}
		iteration = 1;
		x_pos = box.x + box.width + margin*iteration;
		y_pos = 100;
		System.out.println(String.format("%s %s", x_pos, y_pos));
	}
	
	public Point nextPosition(){
		Point point = new PrecisionPoint(x_pos, y_pos);
		
		if(y_pos > box.y + box.height + margin*iteration){
			if(x_pos - margin > 0){
				x_pos -= margin;
			} else {
				iteration++;
				y_pos = 100;
				x_pos = box.x + box.width + margin*iteration;
			}
		} else {
			y_pos += margin; 
		}

		System.out.println(String.format("%s %s", x_pos, y_pos));
		return point;
	}
}
