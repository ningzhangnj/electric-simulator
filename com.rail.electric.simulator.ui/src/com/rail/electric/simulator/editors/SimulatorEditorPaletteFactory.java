package com.rail.electric.simulator.editors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.palette.CombinedTemplateCreationEntry;
import org.eclipse.gef.palette.ConnectionCreationToolEntry;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteEntry;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.PaletteSeparator;
import org.eclipse.gef.palette.PaletteToolbar;
import org.eclipse.gef.palette.PanningSelectionToolEntry;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.requests.SimpleFactory;
import org.eclipse.jface.resource.ImageDescriptor;

import com.rail.electric.simulator.SimulatorMessages;
import com.rail.electric.simulator.figures.ComplexCircuitFigure;
import com.rail.electric.simulator.figures.FigureFactory;
import com.rail.electric.simulator.figures.GroundFigure;
import com.rail.electric.simulator.figures.GroundWithResistFigure;
import com.rail.electric.simulator.figures.LabelFigure;
import com.rail.electric.simulator.figures.MainSwitchFigure;
import com.rail.electric.simulator.figures.SwitchFigure;
import com.rail.electric.simulator.figures.ThreePhaseTransformerFigure;
import com.rail.electric.simulator.figures.TwoPhaseTransformerFigure;
import com.rail.electric.simulator.model.SimulatorComplexCircuit;
import com.rail.electric.simulator.model.SimulatorGround;
import com.rail.electric.simulator.model.SimulatorGroundWithResist;
import com.rail.electric.simulator.model.SimulatorLabel;
import com.rail.electric.simulator.model.SimulatorMainSwitch;
import com.rail.electric.simulator.model.SimulatorSwitch;
import com.rail.electric.simulator.model.SimulatorThreePhaseTransformer;
import com.rail.electric.simulator.model.SimulatorTwoPhaseTransformer;
import com.rail.electric.simulator.tools.SimulatorCreationTool;

public class SimulatorEditorPaletteFactory {
	/**
	 * Construct and return a new palette for a {@link SimulatorEditor}
	 */
	public static PaletteRoot createPalette() {
		PaletteRoot palette = new PaletteRoot();
		palette.add(createToolsGroup(palette));
		palette.add(createElementsDrawer());
		return palette;
	}

	/**
	 * Create a toolbar containing the common tools that appear at the top of the palette
	 * and set the selection tool as the default tool.
	 */
	private static PaletteEntry createToolsGroup(PaletteRoot palette) {
		PaletteGroup controlGroup = new PaletteGroup(
				SimulatorMessages.SimulatorPlugin_Category_ControlGroup_Label);
	
		// Add a selection tool to the group
		ToolEntry tool = new PanningSelectionToolEntry();
		controlGroup.add(tool);
		palette.setDefaultEntry(tool);
		
		// Add a tool for creating connections
		tool = new ConnectionCreationToolEntry(
			SimulatorMessages.SimulatorPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Label,
			SimulatorMessages.SimulatorPlugin_Tool_ConnectionCreationTool_ConnectionCreationTool_Description,
			null, 
			ImageDescriptor.createFromImage(FigureFactory.SMALL_CONNECTION),
			ImageDescriptor.createFromImage(FigureFactory.LARGE_CONNECTION)
		);
		controlGroup.add(tool);
				
		return controlGroup;
	}

	/**
	 * Create a drawer containing tools to add the various genealogy model elements
	 */
	private static PaletteEntry createElementsDrawer() {
		PaletteDrawer drawer = new PaletteDrawer(
				SimulatorMessages.SimulatorPlugin_Category_Components_Label);//$NON-NLS-1$

		List entries = new ArrayList();

		CombinedTemplateCreationEntry combined = new CombinedTemplateCreationEntry(
				SimulatorMessages.SimulatorPlugin_Tool_CreationTool_Label_Label,
				SimulatorMessages.SimulatorPlugin_Tool_CreationTool_Label_Description,
				new SimpleFactory(SimulatorLabel.class),
				ImageDescriptor.createFromImage(LabelFigure.SMALL_LABEL),
				ImageDescriptor.createFromImage(LabelFigure.LARGE_LABEL)
		);
		combined.setToolClass(SimulatorCreationTool.class);
		entries.add(combined);	
		
		entries.add(new PaletteSeparator());
		
		combined = new CombinedTemplateCreationEntry(
				SimulatorMessages.SimulatorPlugin_Tool_CreationTool_Ground_Label,
				SimulatorMessages.SimulatorPlugin_Tool_CreationTool_Ground_Description,
				new SimpleFactory(SimulatorGround.class),
				ImageDescriptor.createFromImage(GroundFigure.SMALL_GROUND_ON),
				ImageDescriptor.createFromImage(GroundFigure.LARGE_GROUND_ON)
		);
		combined.setToolClass(SimulatorCreationTool.class);
		entries.add(combined);	
		
		combined = new CombinedTemplateCreationEntry(
				SimulatorMessages.SimulatorPlugin_Tool_CreationTool_ComplexCircuit_Label,
				SimulatorMessages.SimulatorPlugin_Tool_CreationTool_ComplexCircuit_Description,
				new SimpleFactory(SimulatorComplexCircuit.class),
				ImageDescriptor.createFromImage(ComplexCircuitFigure.SMALL_COMPLEX_CIRCUIT_ON),
				ImageDescriptor.createFromImage(ComplexCircuitFigure.LARGE_COMPLEX_CIRCUIT_ON)
		);
		combined.setToolClass(SimulatorCreationTool.class);
		entries.add(combined);	
		
		combined = new CombinedTemplateCreationEntry(
				SimulatorMessages.SimulatorPlugin_Tool_CreationTool_TwoPhaseTransformer_Label,
				SimulatorMessages.SimulatorPlugin_Tool_CreationTool_TwoPhaseTransformer_Description,
				new SimpleFactory(SimulatorTwoPhaseTransformer.class),
				ImageDescriptor.createFromImage(TwoPhaseTransformerFigure.SMALL_TWO_PHASE_TRANSFORMER),
				ImageDescriptor.createFromImage(TwoPhaseTransformerFigure.LARGE_TWO_PHASE_TRANSFORMER)
		);
		combined.setToolClass(SimulatorCreationTool.class);
		entries.add(combined);	
		
		combined = new CombinedTemplateCreationEntry(
				SimulatorMessages.SimulatorPlugin_Tool_CreationTool_ThreePhaseTransformer_Label,
				SimulatorMessages.SimulatorPlugin_Tool_CreationTool_ThreePhaseTransformer_Description,
				new SimpleFactory(SimulatorThreePhaseTransformer.class),
				ImageDescriptor.createFromImage(ThreePhaseTransformerFigure.SMALL_THREE_PHASE_TRANSFORMER_ON),
				ImageDescriptor.createFromImage(ThreePhaseTransformerFigure.LARGE_THREE_PHASE_TRANSFORMER_ON)
		);
		combined.setToolClass(SimulatorCreationTool.class);
		entries.add(combined);	
		
		combined = new CombinedTemplateCreationEntry(
				SimulatorMessages.SimulatorPlugin_Tool_CreationTool_GroundWithResist_Label,
				SimulatorMessages.SimulatorPlugin_Tool_CreationTool_GroundWithResist_Description,
				new SimpleFactory(SimulatorGroundWithResist.class),
				ImageDescriptor.createFromImage(GroundWithResistFigure.SMALL_GROUND_WITH_RESIST),
				ImageDescriptor.createFromImage(GroundWithResistFigure.LARGE_GROUND_WITH_RESIST)
		);
		combined.setToolClass(SimulatorCreationTool.class);
		entries.add(combined);	
		
		combined = new CombinedTemplateCreationEntry(
				SimulatorMessages.SimulatorPlugin_Tool_CreationTool_Switch_Label,
				SimulatorMessages.SimulatorPlugin_Tool_CreationTool_Switch_Description,
				new SimpleFactory(SimulatorSwitch.class),
				ImageDescriptor.createFromImage(SwitchFigure.SMALL_SWITCH_OFF),
				ImageDescriptor.createFromImage(SwitchFigure.LARGE_SWITCH_OFF)
		);
		combined.setToolClass(SimulatorCreationTool.class);
		entries.add(combined);	
		
		combined = new CombinedTemplateCreationEntry(
				SimulatorMessages.SimulatorPlugin_Tool_CreationTool_MainSwitch_Label,
				SimulatorMessages.SimulatorPlugin_Tool_CreationTool_MainSwitch_Description,
				new SimpleFactory(SimulatorMainSwitch.class),
				ImageDescriptor.createFromImage(MainSwitchFigure.SMALL_MAIN_SWITCH_OFF),
				ImageDescriptor.createFromImage(MainSwitchFigure.LARGE_MAIN_SWITCH_OFF)
		);
		combined.setToolClass(SimulatorCreationTool.class);
		entries.add(combined);	

		drawer.addAll(entries);
		return drawer;		
	}
}
