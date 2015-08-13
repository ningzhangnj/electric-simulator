package com.rail.electric.simulator.figures;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.PolylineDecoration;
import org.eclipse.draw2d.RoutingAnimator;

import com.rail.electric.simulator.model.Wire;

public class FigureFactory {

	public static PolylineConnection createNewBendableWire(Wire wire) {
		PolylineConnection conn = new PolylineConnection();
		conn.addRoutingListener(RoutingAnimator.getDefault());		
		//conn.setSourceDecoration(new PolygonDecoration());
		//conn.setTargetDecoration(new PolylineDecoration());		
		return conn;
	}

	public static PolylineConnection createNewWire(Wire wire) {

		PolylineConnection conn = new PolylineConnection();		
		conn.addRoutingListener(RoutingAnimator.getDefault());
		PolygonDecoration arrow;

		if (wire == null )
			arrow = null;
		else {
			arrow = new PolygonDecoration();
			arrow.setTemplate(PolygonDecoration.INVERTED_TRIANGLE_TIP);
			arrow.setScale(5, 2.5);
		}
		conn.setSourceDecoration(arrow);

		if (wire == null )
			arrow = null;
		else {
			arrow = new PolygonDecoration();
			arrow.setTemplate(PolygonDecoration.INVERTED_TRIANGLE_TIP);
			arrow.setScale(5, 2.5);
		}
		conn.setTargetDecoration(arrow);
		return conn;
	}

	public static IFigure createNewLED() {
		return new LEDFigure();
	}
}
