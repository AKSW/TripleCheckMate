package org.aksw.gwt.TripleCheckMate.client;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;

public class test extends Composite {

	public test() {

		
		TabLayoutPanel tabLayoutPanel = new TabLayoutPanel(1.5, Unit.EM);
		
		ScrollPanel scrollPanel = new ScrollPanel();
		tabLayoutPanel.add(scrollPanel, "New Widget", false);
		
		DecoratedTabPanel decoratedTabPanel = new DecoratedTabPanel();
		tabLayoutPanel.add(decoratedTabPanel, "New Widget", false);
		initWidget(tabLayoutPanel);
	}

}
