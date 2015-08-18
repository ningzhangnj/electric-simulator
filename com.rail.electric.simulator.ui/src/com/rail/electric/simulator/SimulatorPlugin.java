package com.rail.electric.simulator;

import java.net.URL;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.rail.electric.simulator.figures.LEDFigure;
import com.rail.electric.simulator.model.LED;
import com.rail.electric.simulator.model.SimulatorLabel;

public class SimulatorPlugin extends AbstractUIPlugin {
	// The plug-in ID
	public static final String PLUGIN_ID = "com.rail.electric.simulator.ui";
	
	// The shared instance
	private static SimulatorPlugin plugin;
	
	public static Dimension getMaximumSizeFor(Class modelClass) {
		if (LED.class.equals(modelClass)) {
			return LEDFigure.SIZE;
		} 
		return IFigure.MAX_DIMENSION;
	}
	
	public static Dimension getMinimumSizeFor(Class modelClass) {
		if (SimulatorLabel.class.equals(modelClass)) {
			return new Dimension(IFigure.MIN_DIMENSION.width, 30);
		} else if (LED.class.equals(modelClass)) {
			return LEDFigure.SIZE;
		} 
		return IFigure.MIN_DIMENSION;
	}
	
	/**
	 * The constructor
	 */
	public SimulatorPlugin() {
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}
	
	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static SimulatorPlugin getDefault() {
		return plugin;
	}
	
	 public static Image getImage(String path) {
		 ImageRegistry imageRegistry = getDefault().getImageRegistry();
		 Image image = imageRegistry.get(path);
		 if (image == null) {
		 	if (path.startsWith("http")) {
		 		try {
		 			ImageDescriptor imageDescriptor = ImageDescriptor.createFromURL(new URL(path));
		 			if (imageDescriptor == null) {
		 				imageDescriptor = ImageDescriptor.getMissingImageDescriptor();
		 			}
		 			image = imageDescriptor.createImage(true);
		 			imageRegistry.put(path, image);
		 		}
		 		catch (Exception localException)
		 		{
		 			ImageDescriptor imageDescriptor = ImageDescriptor.getMissingImageDescriptor();
		 			image = imageDescriptor.createImage(true);
		 			imageRegistry.put(path, image);
		 		}
		 	}
		 	else {
		 		ImageDescriptor imageDescriptor = getImageDescriptor(path);
		 		if (imageDescriptor == null) {
		 			imageDescriptor = ImageDescriptor.getMissingImageDescriptor();
		 		}
		 		image = imageDescriptor.createImage(true);
		 		imageRegistry.put(path, image);
		 	}
		 }
		 return image;
	 }
	
	 public static ImageDescriptor getImageDescriptor(String path) {
		 return imageDescriptorFromPlugin(PLUGIN_ID, "icons/" + path);
	 }
}
