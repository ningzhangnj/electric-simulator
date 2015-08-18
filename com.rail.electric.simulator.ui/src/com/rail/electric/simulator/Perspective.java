package com.rail.electric.simulator;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class Perspective implements IPerspectiveFactory {

	 public static String ID = "com.rail.electric.simulator.Perspective"; 

	    @Override
	    public void createInitialLayout(IPageLayout layout) {

	        layout.setEditorAreaVisible(true);

	        String editorArea = layout.getEditorArea();
	        IFolderLayout folder;

	        folder = layout.createFolder("explorer", IPageLayout.LEFT, 0.15f, 
	                editorArea);
	        folder.addPlaceholder(IPageLayout.ID_PROJECT_EXPLORER + ":*"); 
	        folder.addView(IPageLayout.ID_PROJECT_EXPLORER);
	        folder.addPlaceholder(IPageLayout.ID_PROP_SHEET + ":*"); 
	        folder.addView(IPageLayout.ID_PROP_SHEET);

	        layout.addPerspectiveShortcut("org.eclipse.jdt.ui.JavaPerspective"); //$NON-NLS-1$
        
	        layout.addShowViewShortcut(IPageLayout.ID_PROJECT_EXPLORER);
	        layout.addShowViewShortcut(IPageLayout.ID_PROP_SHEET);
	    }

}
