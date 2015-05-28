package com.rail.electric.simulator.editors.providers;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class ParametersLabelProvider extends LabelProvider implements ITableLabelProvider {

	@Override
	public void addListener(ILabelProviderListener arg0) {
		super.addListener(arg0);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isLabelProperty(Object arg0, String arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener arg0) {
		super.removeListener(arg0);			
	}

	@Override
	public Image getColumnImage(Object arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColumnText(Object obj, int index) {
		switch (index) {
	      case 0: // Name column
	    	  String name;
	          /*if (obj instanceof EquipInfo)
	             name = ((EquipInfo) obj).getName();
	          else*/
	             name = "";		         
	          return name;
	      case 1: // Value column
	    	  String Address;
	    	  /*if (obj instanceof EquipInfo)
	        	  Address = ((EquipInfo) obj).getAddress();
	          else*/
	        	  Address = "";		         
	          return Address;	      
	      default:
	         return "";
	      }
	}

}
