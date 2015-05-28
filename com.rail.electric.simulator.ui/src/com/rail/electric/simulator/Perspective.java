package com.rail.electric.simulator;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class Perspective implements IPerspectiveFactory {

	 public static String ID = "com.ericsson.wrbs.viewer.Perspective"; 

	    @Override
	    public void createInitialLayout(IPageLayout layout) {
	        // create a default layout that looks like the stand alone DDMS.

	        /*layout.setEditorAreaVisible(true);

	        String editorArea = layout.getEditorArea();
	        IFolderLayout folder;

	        folder = layout.createFolder("console", IPageLayout.BOTTOM, 0.8f, 
	                editorArea);
	        folder.addPlaceholder(ConsoleView.ID + ":*"); 
	        folder.addView(ConsoleView.ID);

	        folder = layout.createFolder("nodelist", IPageLayout.LEFT, 0.15f, 
	                editorArea);
	        folder.addPlaceholder(NodeListView.ID + ":*"); 
	        folder.addView(NodeListView.ID);

	        folder = layout.createFolder("testcontrol", IPageLayout.RIGHT, 0.3f, 
	                editorArea);
	        folder.addPlaceholder(StpView.ID + ":*"); 
	        folder.addView(StpView.ID);
	        //folder.addView(TrafficTestView.ID);

	        layout.addPerspectiveShortcut("org.eclipse.jdt.ui.JavaPerspective"); //$NON-NLS-1$

	        layout.addShowViewShortcut(ConsoleView.ID);
	        layout.addShowViewShortcut(NodeListView.ID);
	        layout.addShowViewShortcut(StpView.ID);
	        //layout.addShowViewShortcut(TrafficTestView.ID);
	        
	        layout.addShowViewShortcut(IPageLayout.ID_RES_NAV);
	        layout.addShowViewShortcut(IPageLayout.ID_BOOKMARKS);
	        layout.addShowViewShortcut(IPageLayout.ID_OUTLINE);
	        layout.addShowViewShortcut(IPageLayout.ID_PROP_SHEET);
	        layout.addShowViewShortcut(IPageLayout.ID_PROBLEM_VIEW);
	        layout.addShowViewShortcut(IPageLayout.ID_PROGRESS_VIEW);
	        layout.addShowViewShortcut(IPageLayout.ID_TASK_LIST);*/
	    }

}
