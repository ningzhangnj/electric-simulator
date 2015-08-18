package com.rail.electric.simulator.figures;

import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.text.FlowPage;
import org.eclipse.draw2d.text.ParagraphTextLayout;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

/**
 * A Figure with a bent corner and an embedded TextFlow within a FlowPage that
 * contains text.
 */
public class LabelFigure extends BentCornerFigure {
	public static final Image SMALL_LABEL = new Image(Display.getCurrent(),			
			LabelFigure.class.getResourceAsStream("icons/label16.gif"));
	public static final Image LARGE_LABEL = new Image(Display.getCurrent(),			
			LabelFigure.class.getResourceAsStream("icons/label24.gif"));
	
	/** The inner TextFlow **/
	private TextFlow textFlow;

	/**
	 * Creates a new LabelFigure with a default MarginBorder size of
	 * DEFAULT_CORNER_SIZE - 3 and a FlowPage containing a TextFlow with the
	 * style WORD_WRAP_SOFT.
	 */
	public LabelFigure() {
		this(BentCornerFigure.DEFAULT_CORNER_SIZE - 3);
	}

	/**
	 * Creates a new LabelFigure with a MarginBorder that is the given size and
	 * a FlowPage containing a TextFlow with the style WORD_WRAP_SOFT.
	 * 
	 * @param borderSize
	 *            the size of the MarginBorder
	 */
	public LabelFigure(int borderSize) {
		setBorder(new MarginBorder(borderSize));
		FlowPage flowPage = new FlowPage();

		textFlow = new TextFlow();

		textFlow.setLayoutManager(new ParagraphTextLayout(textFlow,
				ParagraphTextLayout.WORD_WRAP_SOFT));

		flowPage.add(textFlow);

		setLayoutManager(new StackLayout());
		add(flowPage);
	}

	/**
	 * Returns the text inside the TextFlow.
	 * 
	 * @return the text flow inside the text.
	 */
	public String getText() {
		return textFlow.getText();
	}

	/**
	 * Sets the text of the TextFlow to the given value.
	 * 
	 * @param newText
	 *            the new text value.
	 */
	public void setText(String newText) {
		textFlow.setText(newText);
	}

}
