package com.rail.electric.simulator.properties;

import org.eclipse.ui.part.IPageSite;

public interface IPropertyPartContainer {

    void refresh();

    IPageSite getContainerSite();

    void updateSectionTitle(IPropertySectionPart section);

}