package com.rail.electric.simulator.model.io;

import java.io.PrintWriter;

import com.rail.electric.simulator.model.SimulatorDiagram;
import com.rail.electric.simulator.model.SimulatorSubpart;

/**
 * Stores information from a {@link GenealogyGraph} to an XML stream
 */
public class SimulatorGraphWriter
{
	private static final String INDENT = "  ";
	private static final String INDENT2 = INDENT + INDENT;
	
	private final SimulatorDiagram graph;
	private PrintWriter writer;

	public SimulatorGraphWriter(SimulatorDiagram graph) {
		this.graph = graph;
	}

	public void write(PrintWriter writer) {
		this.writer = writer;
		writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		writer.println("<simulator>");
		writeChildren();
		writer.println("</simulator>");
	}

	private void writeChildren() {
		for (Object child : graph.getChildren()) {
			if (child instanceof SimulatorSubpart) {
				String elementName = child.getClass().getSimpleName();
				SimulatorSubpart subpart = (SimulatorSubpart)child;
				writer.print(INDENT);
				writer.print("<" + elementName);
				writeAttribute("id", subpart.getID());
				writeAttribute("x", subpart.getLocation().x());
				writeAttribute("y", subpart.getLocation().y());
				writeAttribute("width", subpart.getSize().width());
				writeAttribute("height", subpart.getSize().height());
				writer.println("/>");
			}
		}
	}	

	private void writeAttribute(String key, int value) {
		writer.print(" ");
		writer.print(key);
		writer.print("=\"");
		writer.print(value);
		writer.print("\"");
	}

	private void writeAttribute(String key, String value) {
		writer.print(" ");
		writer.print(key);
		writer.print("=\"");
		writeEscapedText(value);
		writer.print("\"");
	}

	private void writeEscapedText(String text) {
		int length = text.length();
		for (int i = 0; i < length; i++) {
			char character = text.charAt(i);
			switch (character) {
				case '<' :
					writer.print("&lt;");
					break;
				case '>' :
					writer.print("&gt;");
					break;
				case '&' :
					writer.print("&amp;");
					break;
				case '\"' :
					writer.print("&quot;");
					break;
				default :
					writer.print(character);
					break;
			}
		}
	}
}
