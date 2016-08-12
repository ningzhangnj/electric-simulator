package com.rail.electric.simulator.model.io;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.rail.electric.simulator.model.SimulatorDiagram;
import com.rail.electric.simulator.model.SimulatorSubpart;

/**
 * Loads information from an XML stream into a {@link SimulatorDiagram}
 */
public class SimulatorGraphReader extends DefaultHandler
{
	private static final String CLASS_PREFIX = "com.rail.electric.simulator.model.";
	private final SimulatorDiagram graph;
	
	public SimulatorGraphReader(SimulatorDiagram graph) {
		this.graph = graph;
	}

	public void read(InputStream stream) throws ParserConfigurationException, SAXException, IOException {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		parser.parse(stream, this);
		resolveRelationships();
	}
	
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		try {
			Class<?> clz = Class.forName(CLASS_PREFIX + qName);
			try {
				SimulatorSubpart subpart = (SimulatorSubpart)clz.newInstance();
				subpart.setID(readString(attributes, "id"));
				readSimulatorSubpartAttributes(subpart, attributes);
				graph.addChild(subpart);
			} catch (InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void readSimulatorSubpartAttributes(SimulatorSubpart elem, Attributes attributes) {
		elem.setLocation(new Point(readInt(attributes, "x"), readInt(attributes, "y")));
		elem.setSize(new Dimension(readInt(attributes, "width"), readInt(attributes, "height")));
	}

	private int readInt(Attributes attributes, String key) {
		String value = attributes.getValue(key);
		if (value == null)
			return -1;
		return Integer.parseInt(value);
	}
	
	private String readString(Attributes attributes, String key) {
		String value = attributes.getValue(key);
		if (value == null)
			return "";
		return value;
	}

	private void resolveRelationships() {
		// TODO Resolve parent - marriage - child relationships
	}
}
