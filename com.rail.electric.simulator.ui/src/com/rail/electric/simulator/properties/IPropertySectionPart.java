package com.rail.electric.simulator.properties;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Composite;

public interface IPropertySectionPart {

    //void init(IPropertyPartContainer container, IGraphicalEditor editor);

    /**
     * Clients are allowed to set layout on the parent.
     */
    void createControl(Composite parent);

    void dispose();

    void refresh();

    void setFocus();

    void setSelection(ISelection selection);

    String getTitle();

}