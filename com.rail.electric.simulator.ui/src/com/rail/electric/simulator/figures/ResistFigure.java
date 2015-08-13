package com.rail.electric.simulator.figures;

import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.rail.electric.simulator.model.SimulatorResist;

public class ResistFigure extends NodeFigure {
	public static final Image RESIST = 
			//ImageDescriptor.createFromFile(LED.class, "icons/resist24.gif").createImage();
			new Image(Display.getCurrent(),
					SimulatorResist.class.getResourceAsStream("icons/resist24.gif"));
	
	public ResistFigure() {
		final ToolbarLayout layout = new ToolbarLayout();
		setLayoutManager(layout);
				
		ImageFigure imageFigure = new ImageFigure(RESIST);
		imageFigure.setPreferredSize(25, 25);
		add(imageFigure);
		
		/*Label l = new Label();
		l.setLabelAlignment(PositionConstants.LEFT);
		l.setIcon(RESIST);
		add(l);*/
		
		FixedConnectionAnchor inputConnectionAnchor = new FixedConnectionAnchor(this);
		inputConnectionAnchor.offsetH = 24;
		inputConnectionAnchors.addElement(inputConnectionAnchor);
		FixedConnectionAnchor outputConnectionAnchor = new FixedConnectionAnchor(
				this);
		outputConnectionAnchor.topDown = false;
		outputConnectionAnchor.offsetH = 24;
		outputConnectionAnchors.addElement(outputConnectionAnchor);
		
		connectionAnchors.put(SimulatorResist.TERMINAL_IN, inputConnectionAnchor );
		connectionAnchors.put(SimulatorResist.TERMINAL_OUT, outputConnectionAnchor );		
		
	}
	
	/*public void paintFigure(Graphics graphics) {
		Rectangle r = getBounds();
		graphics.setBackgroundPattern(new Pattern(Display.getCurrent(), r.x,
				r.y, r.x + r.width, r.y + r.height, ColorConstants.white,
				ColorConstants.lightGray));
		graphics.fillRectangle(r);
	}*/

}
